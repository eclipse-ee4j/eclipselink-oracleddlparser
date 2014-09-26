/*******************************************************************************
 * Copyright (c) 2011, 2014 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2011, created DDL parser package
 *     David McCann - July 2011, visit tests
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.test;

//javase imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestHelper {

    public static final String DATABASE_DRIVER_KEY = "db.driver";
    public static final String DEFAULT_DATABASE_DRIVER = "oracle.jdbc.OracleDriver";
    public static final String DATABASE_USERNAME_KEY = "db.user";
    public static final String DEFAULT_DATABASE_USERNAME = "scott";
    public static final String DATABASE_PASSWORD_KEY = "db.pwd";
    public static final String DEFAULT_DATABASE_PASSWORD = "tiger";
    public static final String DATABASE_URL_KEY = "db.url";
    public static final String DEFAULT_DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    public static final String DATABASE_DDL_CREATE_KEY = "db.ddl.create";
    public static final String DEFAULT_DATABASE_DDL_CREATE = "false";
    public static final String DATABASE_DDL_DROP_KEY = "db.ddl.drop";
    public static final String DEFAULT_DATABASE_DDL_DROP = "false";
    public static final String DATABASE_DDL_DEBUG_KEY = "db.ddl.debug";
    public static final String DEFAULT_DATABASE_DDL_DEBUG = "false";

    public static Connection buildConnection() throws ClassNotFoundException, SQLException {
        String username = System.getProperty(DATABASE_USERNAME_KEY, DEFAULT_DATABASE_USERNAME);
        String password = System.getProperty(DATABASE_PASSWORD_KEY, DEFAULT_DATABASE_PASSWORD);
        String url = System.getProperty(DATABASE_URL_KEY, DEFAULT_DATABASE_URL);
        String dbDriver = System.getProperty(DATABASE_DRIVER_KEY, DEFAULT_DATABASE_DRIVER);
        Class.forName(dbDriver);
        return DriverManager.getConnection(url, username, password);
    }

    public static void runDdl(Connection conn, String ddl, boolean printStackTrace) {
        try {
            PreparedStatement pStmt = conn.prepareStatement(ddl);
            pStmt.execute();
        }
        catch (SQLException e) {
            if (printStackTrace) {
                e.printStackTrace();
            }
        }
    }
}