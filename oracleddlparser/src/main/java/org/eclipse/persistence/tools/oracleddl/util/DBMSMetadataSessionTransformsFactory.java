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
 ******************************************************************************/
package org.eclipse.persistence.tools.oracleddl.util;

//javase imports
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBMSMetadataSessionTransformsFactory implements DBMSMetadataSessionTransforms {

    public static final String TRANSFORMS = "transforms.properties";

    public Properties getTransformProperties() {
        InputStream is = DBMSMetadataSessionTransformsFactory.class.getResourceAsStream(TRANSFORMS);
        if (is != null) {
            Properties transformsProperties = new Properties();
            try {
                transformsProperties.load(is);
                return transformsProperties;
            } catch (IOException e) {
                // ignore
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    //ignore
                }
            }
        }
        return null;
    }

}