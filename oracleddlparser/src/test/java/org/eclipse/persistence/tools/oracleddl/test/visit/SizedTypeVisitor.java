/*
 * Copyright (c) 2011, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Mike Norman - June 10 2011, created DDL parser package
//     David McCann - July 2011, visit tests
package org.eclipse.persistence.tools.oracleddl.test.visit;

//DDL imports
import org.eclipse.persistence.tools.oracleddl.metadata.BinaryType;
import org.eclipse.persistence.tools.oracleddl.metadata.BlobType;
import org.eclipse.persistence.tools.oracleddl.metadata.CharType;
import org.eclipse.persistence.tools.oracleddl.metadata.ClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.LongRawType;
import org.eclipse.persistence.tools.oracleddl.metadata.LongType;
import org.eclipse.persistence.tools.oracleddl.metadata.NCharType;
import org.eclipse.persistence.tools.oracleddl.metadata.NClobType;
import org.eclipse.persistence.tools.oracleddl.metadata.NVarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.RawType;
import org.eclipse.persistence.tools.oracleddl.metadata.SizedType;
import org.eclipse.persistence.tools.oracleddl.metadata.URowIdType;
import org.eclipse.persistence.tools.oracleddl.metadata.VarChar2Type;
import org.eclipse.persistence.tools.oracleddl.metadata.VarCharType;
import org.eclipse.persistence.tools.oracleddl.metadata.visit.BaseDatabaseTypeVisitor;

/**
 * Visitor for use with SizedType.  The visit methods simply
 * gather all relevant information such that it can be 
 * returned as a String when visiting is complete. 
 */
class SizedTypeVisitor extends BaseDatabaseTypeVisitor {
    protected long size;
    protected long defaultSize;
    protected String typeName;
    
    public void visit(SizedType databaseType) {
        size = databaseType.getSize();
        typeName = databaseType.getTypeName();
        defaultSize = databaseType.getDefaultSize();
    }
    public void visit(BinaryType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(BlobType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(LongRawType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(RawType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(CharType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(NCharType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(ClobType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(NClobType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(URowIdType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(VarCharType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(VarChar2Type databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(LongType databaseType) {
        visit((SizedType)databaseType);
    }
    public void visit(NVarChar2Type databaseType) {
        visit((SizedType)databaseType);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder(typeName);
        if (size != defaultSize) {
            sb.append('(');
            sb.append(size);
            sb.append(')');
        }
        return sb.toString();

    }
}
