package org.eclipse.persistence.tools.dbws.metadata;

public class BinaryType extends SizedType {

    public BinaryType() {
        super("BINARY", 0);
    }
    public BinaryType(long size) {
        super("BINARY", size);
    }

}