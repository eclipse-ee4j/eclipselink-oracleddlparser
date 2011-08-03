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
package org.eclipse.persistence.tools.oracleddl.test;

//javase imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestHelper {

    static final String DATABASE_DRIVER = "oracle.jdbc.OracleDriver";
    static final String DATABASE_USERNAME_KEY = "db.user";
    static final String DEFAULT_DATABASE_USERNAME = "scott";
    static final String DATABASE_PASSWORD_KEY = "db.pwd";
    static final String DEFAULT_DATABASE_PASSWORD = "tiger";
    static final String DATABASE_URL_KEY = "db.url";
    static final String DEFAULT_DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:ORCL";
    
    public static Connection buildConnection() throws ClassNotFoundException, SQLException {
        String username = System.getProperty(DATABASE_USERNAME_KEY, DEFAULT_DATABASE_USERNAME);
        String password = System.getProperty(DATABASE_PASSWORD_KEY, DEFAULT_DATABASE_PASSWORD);
        String url = System.getProperty(DATABASE_URL_KEY, DEFAULT_DATABASE_URL);
        Class.forName(DATABASE_DRIVER);
        return DriverManager.getConnection(url, username, password);
    }
}