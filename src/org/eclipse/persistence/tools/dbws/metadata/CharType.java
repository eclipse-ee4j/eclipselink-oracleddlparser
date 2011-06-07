package org.eclipse.persistence.tools.dbws.metadata;

public class CharType extends SizedType {

    public CharType() {
        super("CHAR", 1);
    }
    public CharType(long size) {
        super("CHAR", size);
    }

}