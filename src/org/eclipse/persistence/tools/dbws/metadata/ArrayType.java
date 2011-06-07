package org.eclipse.persistence.tools.dbws.metadata;

public class ArrayType extends SizedType {

    public ArrayType() {
        super("ARRAY", 0);
    }
    public ArrayType(long size) {
        super("ARRAY", size);
    }

}