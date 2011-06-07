package org.eclipse.persistence.tools.dbws.metadata;

public class BlobType extends SizedType {

    public BlobType() {
        super("BLOB", 0);
    }
    public BlobType(long size) {
        super("BLOB", size);
    }

}