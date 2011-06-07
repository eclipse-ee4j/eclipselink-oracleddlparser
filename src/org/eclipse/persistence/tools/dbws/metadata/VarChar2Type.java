package org.eclipse.persistence.tools.dbws.metadata;

public class VarChar2Type extends VarCharType {

    public VarChar2Type() {
        super();
        this.typeName = "VARCHAR2";
    }

    public VarChar2Type(long size) {
        super(size);
        this.typeName = "VARCHAR2";
    }

}