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
 *     Mike Norman - June 10 2010, created DDL parser package
 ******************************************************************************/
package org.eclipse.persistence.tools.dbws.metadata.visit;

public class DatabaseTypeDefaultListener implements DatabaseTypeListener {

	public void handleArrayType(String typeName, long size) {
	}

	public void handleBinaryType(long size) {
	}

	public void handleBlobType(long size) {
	}

	public void handleCharType(long size) {
	}

	public void handleClobType(long size) {
	}

	public void handleDecimalType(long precision, long scale) {
	}

	public void handleDoubleType(long precision, long scale) {
	}

	public void handleFieldType(String fieldName, String typeName,
		boolean notNull, boolean pk) {
	}

	public void handleFloatType(long precision, long scale) {
	}

	public void handleLongRawType(long size) {
	}

	public void handleLongType(long size) {
	}

	public void handleNumericType(long precision, long scale) {
	}

	public void beginPLSQLPackageType(String packageName) {
	}
	public void endPLSQLPackageType() {
	}

	public void handleRawType(long size) {
	}

	public void handleRealType(long precision, long scale) {
	}

	public void handleUnresolvedSizedType(String unresolvedTypeName, long size) {
	}

	public void handleUnresolvedType(String unresolvedTypeName) {
	}

	public void beginTableType(String tableName) {
	}
	public void endTableType() {
	}

	public void handleURowIdType(long size) {
	}

	public void handleVarCharType(long size) {
	}

	public void handleVarChar2Type(long size) {
	}

}