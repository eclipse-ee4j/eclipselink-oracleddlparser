package org.eclipse.persistence.tools.dbws.plsqlparser;

@SuppressWarnings("all")
public class PLSQLNode extends SimpleNode {
    
    protected PLSQLPackageNode packageNode;

    public PLSQLNode(int id) {
        super(id);
    }

    public PLSQLNode(PLSQLParser p, int id) {
        super(p, id);
    }

    public PLSQLPackageNode getPackageNode() {
        return packageNode;
    }

    public void setPackageNode(PLSQLPackageNode packageNode) {
        this.packageNode = packageNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (value != null) {
            sb.append("[");
            sb.append(value);
            sb.append("]");
        }
        return sb.toString();
    }
}