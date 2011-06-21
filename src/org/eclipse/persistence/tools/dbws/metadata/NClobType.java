package org.eclipse.persistence.tools.dbws.metadata;

public class NClobType extends ClobType {

	static long DEFAULT_SIZE = 0l;
	
    public NClobType() {
        super("NCLOB", DEFAULT_SIZE);
    }

	@Override
	public long getDefaultSize() {
		return DEFAULT_SIZE;
	}
	
	@Override
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}
