package org.eclipse.persistence.tools.dbws.metadata;

public class NVarChar2Type extends VarChar2Type {

	static final String TYPENAME = "NVARCHAR2";
	
    public NVarChar2Type() {
        super(VarCharType.DEFAULT_SIZE);
        this.typeName = TYPENAME;
    }
    public NVarChar2Type(long size) {
        super(size);
        this.typeName = TYPENAME;
    }
	
	@Override
	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}

}
