package org.eclipse.persistence.tools.dbws.metadata;

public interface DatabaseType {

    public String getTypeName();
    
    public boolean isSimple();    
    public boolean isComplex();
}
