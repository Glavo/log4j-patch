package org.apache.logging.log4j.core.lookup;

public class JndiLookup {
    public JndiLookup() {
        throw new NoClassDefFoundError("JNDI lookup is disabled");
    }
}
