package org.eclipse.persistence.tools.dbws.metadata;

public class VarCharType extends SizedType {

    public VarCharType() {
        super("VARCHAR", 1);
    }
    public VarCharType(long size) {
        super("VARCHAR", size);
    }

}