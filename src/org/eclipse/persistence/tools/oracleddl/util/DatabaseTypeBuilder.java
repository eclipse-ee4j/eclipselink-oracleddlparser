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
import java.util.List;

//DDL parser imports
import org.eclipse.persistence.tools.oracleddl.metadata.DatabaseType;
import org.eclipse.persistence.tools.oracleddl.metadata.FunctionType;
import org.eclipse.persistence.tools.oracleddl.metadata.PLSQLPackageType;
import org.eclipse.persistence.tools.oracleddl.metadata.ProcedureType;
import org.eclipse.persistence.tools.oracleddl.metadata.TableType;
import org.eclipse.persistence.tools.oracleddl.parser.DDLParser;
import org.eclipse.persistence.tools.oracleddl.parser.ParseException;

public class DatabaseTypeBuilder {

	static final String DBMS_METADATA_SESSION_TRANSFORM_STMT =
	    "BEGIN " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'PRETTY',TRUE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'SQLTERMINATOR',TRUE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'CONSTRAINTS', TRUE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'CONSTRAINTS_AS_ALTER', TRUE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'REF_CONSTRAINTS',FALSE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'SEGMENT_ATTRIBUTES',FALSE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'STORAGE',FALSE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'TABLESPACE',FALSE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'SPECIFICATION',TRUE); " +
	        "DBMS_METADATA.SET_TRANSFORM_PARAM(DBMS_METADATA.SESSION_TRANSFORM,'BODY',FALSE); " +
	    "END;";
	static final String DBMS_METADATA_DDL_STMT_SUFFIX =
	    "', SYS_CONTEXT('USERENV', 'CURRENT_USER')) AS RESULT FROM DUAL";

	protected boolean metadataSessionTransformSet = false;
	
    public DatabaseTypeBuilder() {
		super();
	}
    
    static final String DBMS_METADATA_GET_TABLE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('TABLE', '";
	public TableType buildTable(Connection conn, String tableName) throws ParseException {
		TableType tableType = null;
		if (setDbmsMetadataSession(conn)) {
	        String ddl = getDDL(conn, DBMS_METADATA_GET_TABLE_DDL_STMT_PREFIX + tableName +
	            DBMS_METADATA_DDL_STMT_SUFFIX);
	        if (ddl != null) {
		        DDLParser parser = newDDLParser(ddl);
		        tableType = parser.parseTable();
				if (tableType != null) {
					UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
					unresolvedTypesVisitor.visit(tableType);
					if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
						resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), tableType);
					}
				}
	        }
		}
		return tableType;
	}
	
    protected void resolvedTypes(DDLParser parser, List<String> unresolvedTypes, DatabaseType databaseType) {
		// TODO - go thru list of unresolved types and recursively
		// fix up the databaseType's object-graph
	}

	static final String DBMS_METADATA_GET_PACKAGE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('PACKAGE_SPEC', '";
	public PLSQLPackageType buildPackage(Connection conn, String packageName) throws ParseException {
		PLSQLPackageType packageType = null;
		if (setDbmsMetadataSession(conn)) {
	        String ddl = getDDL(conn, DBMS_METADATA_GET_PACKAGE_DDL_STMT_PREFIX + packageName +
	        	DBMS_METADATA_DDL_STMT_SUFFIX);
	        if (ddl != null) {
		        DDLParser parser = newDDLParser(ddl);
		        packageType = parser.parsePLSQLPackage();
				if (packageType != null) {
					UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
					unresolvedTypesVisitor.visit(packageType);
					if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
						resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), packageType);
					}
				}
	        }
			
		}
		return packageType;
	}

    static final String DBMS_METADATA_GET_PROCEDURE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('PROCEDURE', '";
	public ProcedureType buildProcedure(Connection conn, String procedureName) throws ParseException {
		ProcedureType procedureType = null;
		if (setDbmsMetadataSession(conn)) {
	        String ddl = getDDL(conn, DBMS_METADATA_GET_PROCEDURE_DDL_STMT_PREFIX + procedureName +
	        	DBMS_METADATA_DDL_STMT_SUFFIX);
	        if (ddl != null) {
		        DDLParser parser = newDDLParser(ddl);
		        procedureType = parser.parseTopLevelProcedure();
				if (procedureType != null) {
					UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
					unresolvedTypesVisitor.visit(procedureType);
					if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
						resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), procedureType);
					}
				}
	        }
			
		}
		return procedureType;
    }

    static final String DBMS_METADATA_GET_FUNCTION_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('FUNCTION', '";
	public FunctionType buildFunction(Connection conn, String procedureName) throws ParseException {
		FunctionType functionType = null;
		if (setDbmsMetadataSession(conn)) {
	        String ddl = getDDL(conn, DBMS_METADATA_GET_FUNCTION_DDL_STMT_PREFIX + procedureName +
	        	DBMS_METADATA_DDL_STMT_SUFFIX);
	        if (ddl != null) {
		        DDLParser parser = newDDLParser(ddl);
		        functionType = parser.parseTopLevelFunction();
				if (functionType != null) {
					UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
					unresolvedTypesVisitor.visit(functionType);
					if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
						resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), functionType);
					}
				}
	        }
			
		}
		return functionType;
    }

    static final String DBMS_METADATA_GET_TYPE_DDL_STMT_PREFIX =
        "SELECT DBMS_METADATA.GET_DDL('TYPE_SPEC', '";
	public DatabaseType buildType(Connection conn, String typeName) throws ParseException {
		DatabaseType databaseType = null;
		if (setDbmsMetadataSession(conn)) {
	        String ddl = getDDL(conn, DBMS_METADATA_GET_TYPE_DDL_STMT_PREFIX + typeName +
	        	DBMS_METADATA_DDL_STMT_SUFFIX);
	        if (ddl != null) {
		        DDLParser parser = newDDLParser(ddl);
		        //TODO - object types parser.parseType();
				if (databaseType != null) {
					UnresolvedTypesVisitor unresolvedTypesVisitor = new UnresolvedTypesVisitor();
					//TODO - //TODO - unresolvedTypesVisitor.visit(databaseType);
					if (!unresolvedTypesVisitor.getUnresolvedTypes().isEmpty()) {
						resolvedTypes(parser, unresolvedTypesVisitor.getUnresolvedTypes(), databaseType);
					}
				}
	        }
		}
		return databaseType;
    } 
   
	protected DDLParser newDDLParser(String ddl) {
		DDLParser parser = new DDLParser(new StringReader(ddl));
        parser.setTypesRepository(new DatabaseTypesRepository());
        return parser;
	}

	protected String getDDL(Connection conn, String metadataSpec) {
        String ddl = null;
        try {
            PreparedStatement ps = conn.prepareStatement(metadataSpec);
            ResultSet rs = ps.executeQuery();
            rs.next();
            ddl = rs.getString("RESULT").trim();
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
        if (ddl.endsWith("/")) {
            ddl = (String)ddl.subSequence(0, ddl.length()-1);
        }
        return ddl;
    }

	protected boolean setDbmsMetadataSession(Connection conn) {
		if (metadataSessionTransformSet) {
			return true;
		}
	    boolean worked = true;
	    try {
			CallableStatement callableStatement = conn.prepareCall(
				DBMS_METADATA_SESSION_TRANSFORM_STMT);
	        callableStatement.execute();
	    }
	    catch (SQLException e) {
	       worked = false;
	    }
	    if (worked) {
	    	metadataSessionTransformSet = true;
	    }
	    return worked;
	}
}