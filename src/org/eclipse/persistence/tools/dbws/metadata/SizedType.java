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
package org.eclipse.persistence.tools.dbws.metadata;

public abstract class SizedType extends ScalarDatabaseTypeBase implements ScalarDatabaseType {

	protected long size;
    
    public SizedType(String typeName, long size) {
        super(typeName);
        this.size = size;
    }

    public long getSize() {
        return size;
    }
    
    public abstract long getDefaultSize();

    @Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		if (size != getDefaultSize()) {
			sb.append('(');
			sb.append(size);
			sb.append(')');
		}
		return sb.toString();
	}

}