package org.eclipse.persistence.tools.dbws.metadata;

public class NCharType extends CharType {

	static final String TYPENAME = "NCHAR";
	static final long DEFAULT_SIZE = 1l;
	
    public NCharType() {
        super(TYPENAME, DEFAULT_SIZE);
    }
    public NCharType(long size) {
        super(TYPENAME, size);
    }
    
	@Override
	public long getDefaultSize() {
		return DEFAULT_SIZE;
	}

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}
