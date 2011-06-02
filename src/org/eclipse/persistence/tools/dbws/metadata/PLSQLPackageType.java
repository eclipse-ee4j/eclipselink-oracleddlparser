package org.eclipse.persistence.tools.dbws.metadata;

public class PLSQLPackageType implements MetadataType {

    public PLSQLPackageType() {
        super();
    }

    //sorta
    public boolean isNested() {
        return false;
    }

    protected String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
