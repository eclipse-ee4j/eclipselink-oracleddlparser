/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2011, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.util;

//javase imports
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.CompositeDatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.UnresolvedTypesVisitor;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;

public class DatabaseTypeBuilder {

    static DBMSMetadataSessionTransforms TRANSFORMS_FACTORY;
    static {
        ServiceLoader<DBMSMetadataSessionTransforms> transformsFactories =
            ServiceLoader.load(DBMSMetadataSessionTransforms.class);
        Iterator<DBMSMetadataSessionTransforms> i = transformsFactories.iterator();
        //we are only expecting one transforms factory - any additional are ignored
        if (i.hasNext()) {
            TRANSFORMS_FACTORY = i.next();
        }
        else {
            TRANSFORMS_FACTORY = null;
        }
    }

    static final String DBMS_METADATA_GET_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('";
    static final String DBMS_METADATA_GET_DDL_STMT1 =
    	"', AO.OBJECT_NAME) AS RESULT FROM ALL_OBJECTS AO WHERE ";
    static final String DBMS_METADATA_GET_DDL_EXCLUDE_ADMIN_SCHEMAS =
        "NOT REGEXP_LIKE(OWNER," +
            "'*SYS*|XDB|*ORD*|DBSNMP|ANONYMOUS|OUTLN|MGMT_VIEW|SI_INFORMTN_SCHEMA|WK_TEST|WKPROXY') ";
    static final String DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA1 =
        "REGEXP_LIKE(OWNER,'"; 
    static final String DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA2 =
        "') ";
    static final String DBMS_METADATA_GET_DDL_STMT2 =
    	"AND OBJECT_TYPE = '";
    static final String DBMS_METADATA_GET_DDL_STMT3 =
    	"' AND OBJECT_NAME LIKE '";
    static final String DBMS_METADATA_DDL_STMT_SUFFIX =
        "'";

    protected boolean transformsSet = false;

    public DatabaseTypeBuilder() {
        super();
    }

    public List<TableType> buildTables(Connection conn, String schemaPattern, String tablePattern)
        throws ParseException {
    	List<TableType> tableTypes = null;
        if (setDbmsMetadataSessionTransforms(conn)) {
        	List<String> ddls = getDDLs(conn, DBMS_METADATA_GET_DDL_STMT_PREFIX + "TABLE" +
        		DBMS_METADATA_GET_DDL_STMT1 + 
        	    ((schemaPattern == null || schemaPattern.length() == 0 || "%".equals(schemaPattern))
        	        ? DBMS_METADATA_GET_DDL_EXCLUDE_ADMIN_SCHEMAS
        	        : DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA1 + schemaPattern + 
        	          DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA2) +
        		DBMS_METADATA_GET_DDL_STMT2 + "TABLE" + DBMS_METADATA_GET_DDL_STMT3 +
        		tablePattern + DBMS_METADATA_DDL_STMT_SUFFIX);
            if (ddls != null) {
            	tableTypes = new ArrayList<TableType>();
            	for (String ddl : ddls) {
	                DDLParser parser = newDDLParser(ddl);
	                TableType tableType = parser.parseTable();
	                if (tableType != null) {
	                	tableTypes.add(tableType);
	                    UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
	                    unresolvedTypesVisitor.visit(tableType);
	                    if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
	                        resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), tableType);
	                    }
	                }
            	}
            }
        }
        return tableTypes;
    }

    protected void resolvedTypes(DDLParser parser, List<String> unresolvedTypes, DatabaseType databaseType) {
        // TODO - go thru list of unresolved types and fix up the databaseType's object-graph
    }

    public List<PLSQLPackageType> buildPackages(Connection conn, String schemaPattern,
        String packagePattern) throws ParseException {
    	List<PLSQLPackageType> packageTypes = null;
        if (setDbmsMetadataSessionTransforms(conn)) {
            List<String> ddls = getDDLs(conn, DBMS_METADATA_GET_DDL_STMT_PREFIX + "PACKAGE" +
                DBMS_METADATA_GET_DDL_STMT1 + 
                ((schemaPattern == null || schemaPattern.length() == 0 || "%".equals(schemaPattern))
                    ? DBMS_METADATA_GET_DDL_EXCLUDE_ADMIN_SCHEMAS
                    : DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA1 + schemaPattern + 
                      DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA2) +
                DBMS_METADATA_GET_DDL_STMT2 + "PACKAGE" + DBMS_METADATA_GET_DDL_STMT3 +
                packagePattern + DBMS_METADATA_DDL_STMT_SUFFIX);
            if (ddls != null) {
            	packageTypes = new ArrayList<PLSQLPackageType>();
            	for (String ddl : ddls) {
	                DDLParser parser = newDDLParser(ddl);
	                PLSQLPackageType packageType = parser.parsePLSQLPackage();
	                if (packageType != null) {
	                	packageTypes.add(packageType);
	                    UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
	                    unresolvedTypesVisitor.visit(packageType);
	                    if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
	                        resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), packageType);
	                    }
	                }
            	}
            }

        }
        return packageTypes;
    }

    public List<ProcedureType> buildProcedures(Connection conn, String schemaPattern,
        String procedurePattern) throws ParseException {
    	List<ProcedureType> procedureTypes = null;
        if (setDbmsMetadataSessionTransforms(conn)) {
            List<String> ddls = getDDLs(conn, DBMS_METADATA_GET_DDL_STMT_PREFIX + "PROCEDURE" +
                DBMS_METADATA_GET_DDL_STMT1 + 
                ((schemaPattern == null || schemaPattern.length() == 0 || "%".equals(schemaPattern))
                    ? DBMS_METADATA_GET_DDL_EXCLUDE_ADMIN_SCHEMAS
                    : DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA1 + schemaPattern + 
                      DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA2) +
                DBMS_METADATA_GET_DDL_STMT2 + "PROCEDURE" + DBMS_METADATA_GET_DDL_STMT3 +
                procedurePattern + DBMS_METADATA_DDL_STMT_SUFFIX);
            if (ddls != null) {
            	procedureTypes = new ArrayList<ProcedureType>();
            	for (String ddl : ddls) {
	                DDLParser parser = newDDLParser(ddl);
	                ProcedureType procedureType = parser.parseTopLevelProcedure();
	                if (procedureType != null) {
	                	procedureTypes.add(procedureType);
	                    UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
	                    unresolvedTypesVisitor.visit(procedureType);
	                    if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
	                        resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), procedureType);
	                    }
	                }
            	}
            }

        }
        return procedureTypes;
    }

    public List<FunctionType> buildFunctions(Connection conn, String schemaPattern,
        String functionPattern) throws ParseException {
    	List<FunctionType> functionTypes = null;
        if (setDbmsMetadataSessionTransforms(conn)) {
            List<String> ddls = getDDLs(conn, DBMS_METADATA_GET_DDL_STMT_PREFIX + "FUNCTION" +
                DBMS_METADATA_GET_DDL_STMT1 + 
                ((schemaPattern == null || schemaPattern.length() == 0 || "%".equals(schemaPattern))
                    ? DBMS_METADATA_GET_DDL_EXCLUDE_ADMIN_SCHEMAS
                    : DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA1 + schemaPattern + 
                      DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA2) +
                DBMS_METADATA_GET_DDL_STMT2 + "FUNCTION" + DBMS_METADATA_GET_DDL_STMT3 +
                functionPattern + DBMS_METADATA_DDL_STMT_SUFFIX);
            if (ddls != null) {
            	functionTypes = new ArrayList<FunctionType>();
            	for (String ddl : ddls) {
	                DDLParser parser = newDDLParser(ddl);
	                FunctionType functionType = parser.parseTopLevelFunction();
	                if (functionType != null) {
	                	functionTypes.add(functionType);
	                    UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
	                    unresolvedTypesVisitor.visit(functionType);
	                    if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
	                        resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), functionType);
	                    }
	                }
            	}
            }

        }
        return functionTypes;
    }

    public List<CompositeDatabaseType> buildType(Connection conn, String schemaPattern,
        String typePattern) throws ParseException {
    	List<CompositeDatabaseType> databaseTypes = null;
        if (setDbmsMetadataSessionTransforms(conn)) {
            List<String> ddls = getDDLs(conn, DBMS_METADATA_GET_DDL_STMT_PREFIX + "TYPE" +
                DBMS_METADATA_GET_DDL_STMT1 + 
                ((schemaPattern == null || schemaPattern.length() == 0 || "%".equals(schemaPattern))
                    ? DBMS_METADATA_GET_DDL_EXCLUDE_ADMIN_SCHEMAS
                    : DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA1 + schemaPattern + 
                      DBMS_METADATA_GET_DDL_INCLUDE_SCHEMA2) +
                DBMS_METADATA_GET_DDL_STMT2 + "TYPE" + DBMS_METADATA_GET_DDL_STMT3 +
                typePattern + DBMS_METADATA_DDL_STMT_SUFFIX);
            if (ddls != null) {
            	databaseTypes = new ArrayList<CompositeDatabaseType>();
            	for (String ddl : ddls) {
	                DDLParser parser = newDDLParser(ddl);
	            	CompositeDatabaseType databaseType = parser.parseType();
	                if (databaseType != null) {
	                	databaseTypes.add(databaseType);
	                    UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
	                    if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
	                        resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), databaseType);
	                    }
	                }
            	}
            }
        }
        return databaseTypes;
    }

    protected DDLParser newDDLParser(String ddl) {
        DDLParser parser = new DDLParser(new StringReader(ddl));
        parser.setTypesRepository(new DatabaseTypesRepository());
        return parser;
    }

    protected List<String> getDDLs(Connection conn, String metadataSpec) {
    	List<String> ddls = null;
        try {
            PreparedStatement ps = conn.prepareStatement(metadataSpec);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {  
            	ddls = new ArrayList<String>();
                do {
                	String ddl = rs.getString("RESULT").trim();
                    if (ddl.endsWith("/")) {
                        ddl = (String)ddl.subSequence(0, ddl.length()-1);
                    }
                	ddls.add(ddl);
                
                } while (rs.next());  
            }
            try {
                rs.close();
                ps.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ddls;
    }

    public Properties getTransformProperties() throws DatabaseTypeBuilderException  {
        if (TRANSFORMS_FACTORY == null) {
            throw DatabaseTypeBuilderException.noTransformsFactories();
        }
        Properties transformProperties = TRANSFORMS_FACTORY.getTransformProperties();
        if (transformProperties == null) {
            throw DatabaseTypeBuilderException.noTransformsProperties();
        }
        return transformProperties;
    }

    protected boolean setDbmsMetadataSessionTransforms(Connection conn) {
        if (transformsSet) {
            return true;
        }
        boolean worked = true;
        CallableStatement cStmt = null;
        try {
            Properties transformProperties = getTransformProperties();
            StringBuilder sb = new StringBuilder("BEGIN");
            for (Map.Entry<Object, Object> me : transformProperties.entrySet()) {
                sb.append("\n");
                sb.append("DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'");
                sb.append(me.getKey());
                sb.append("',");
                sb.append(me.getValue());
                sb.append(");");
            }
            sb.append("\nEND;");
            cStmt = conn.prepareCall(sb.toString());
            cStmt.execute();
        }
        catch (Exception e) {
           worked = false;
        }
        finally {
            try {
                cStmt.close();
            }
            catch (SQLException e) {
                // ignore
            }
        }
        if (worked) {
            transformsSet = true;
        }
        return worked;
    }
}