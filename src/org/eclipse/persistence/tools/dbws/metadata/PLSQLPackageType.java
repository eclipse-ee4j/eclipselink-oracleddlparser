package org.eclipse.persistence.tools.dbws.metadata;

public class PLSQLPackageType implements ComplexDatabaseType {

    public PLSQLPackageType() {
        super();
    }

    public String getTypeName() {
        return "PL/SQL Package";
    }

    public boolean isSimple() {
        return false;
    }
    public boolean isComplex() {
        return true;
    }

    protected String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void addEnclosedType(DatabaseType enclosedType) {
       
    }
}