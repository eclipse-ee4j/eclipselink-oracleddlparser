package org.eclipse.persistence.tools.dbws.metadata;

//javase imports
import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.tools.dbws.metadata.parser.DDLParser;

/**
 * A utility class to help {@link DDLParser} construct {@link DatabaseType}'s.
 * Types are created either in the 'global' namespace or within the scope of
 * a PL/SQL package.
 * 
 * @author mnorman
 */
public class DatabaseTypesRepository {

    // hold on to MetadataTypes that have already been built
    protected Map<String, DatabaseType> repository = new HashMap<String, DatabaseType>();
    
}
