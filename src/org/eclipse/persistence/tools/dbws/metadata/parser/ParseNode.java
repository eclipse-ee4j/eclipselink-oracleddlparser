package org.eclipse.persistence.tools.dbws.metadata.parser;

import org.eclipse.persistence.tools.dbws.metadata.PLSQLPackageNode;

@SuppressWarnings("all")
public class ParseNode extends SimpleNode {

    public ParseNode(int id) {
        super(id);
    }

    public ParseNode(DDLParser p, int id) {
        super(p, id);
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