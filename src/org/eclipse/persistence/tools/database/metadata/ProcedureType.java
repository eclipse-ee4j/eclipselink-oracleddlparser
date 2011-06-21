/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Mike Norman - June 10 2011, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.database.metadata;

//javase imports
import java.util.ArrayList;
import java.util.List;

public class ProcedureType extends CompositeDatabaseTypeBase implements CompositeDatabaseType, DatabaseTypeVisitable {

    protected String procedureName;
    protected String schema;
    protected List<ArgumentType> arguments = new ArrayList<ArgumentType>();

    public ProcedureType(String procedureName) {
		super(null);
		setProcedureName(procedureName);
	}

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
        super.typeName = "PROCEDURE " + procedureName;
    }

    public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
       this.schema = schema;
    }

	public List<ArgumentType> getArguments() {
		return arguments;
	}

	@Override
	public void addCompositeType(DatabaseType enclosedType) {
		//TODO
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PROCEDURE ");
        if (schema != null) {
            sb.append(schema);
            sb.append(".");
        }
        sb.append(procedureName);
        sb.append(" (\n");
        
        return sb.toString();
    }

	public void accept(DatabaseTypeVisitor visitor) {
		visitor.visit(this);
	}
}