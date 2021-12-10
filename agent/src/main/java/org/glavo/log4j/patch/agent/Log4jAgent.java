package org.glavo.log4j.patch.agent;

import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public final class Log4jAgent {
    static final String TARGET_CLASS_NAME = "org/apache/logging/log4j/core/lookup/JndiLookup";
    static final byte[] NEW_JNDILOOKUP = loadNewJndiLookup();

    private static byte[] loadNewJndiLookup() {
        try {
            InputStream input = null;
            try {
                input = Log4jAgent.class.getResourceAsStream("JndiLookup.class.bin");
                if (input == null) {
                    throw new AssertionError("JndiLookup.class.bin not found");
                }
                int available = input.available();
                if (available <= 0) {
                    throw new AssertionError();
                }
                byte[] res = new byte[available];
                if (input.read(res) != available) {
                    throw new AssertionError();
                }

                return res;
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        System.setProperty("org.glavo.log4j.patch.agent.patched", "false");
        inst.addTransformer(new Transformer());
    }

    private static final class Transformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (TARGET_CLASS_NAME.equals(className)) {
                System.setProperty("org.glavo.log4j.patch.agent.patched", "true");
                return NEW_JNDILOOKUP;
            }
            return null;
        }
    }

}
