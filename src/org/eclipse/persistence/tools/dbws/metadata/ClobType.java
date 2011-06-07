package org.eclipse.persistence.tools.dbws.metadata;

public class ClobType extends SizedType {

    public ClobType() {
        super("CLOB", 0);
    }
    public ClobType(long size) {
        super("CLOB", size);
    }

}