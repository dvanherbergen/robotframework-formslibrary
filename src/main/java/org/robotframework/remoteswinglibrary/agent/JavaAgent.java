package org.robotframework.remoteswinglibrary.agent;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;

import org.robotframework.formslibrary.util.Logger;

public class JavaAgent {

    private static final int DEFAULT_REMOTESWINGLIBRARY_PORT = 8181;

    public static void premain(String agentArgument, Instrumentation instrumentation) {

        Logger.info("\nStarting formslib JavaAgent...");

        try {
            String[] args = agentArgument.split(":");
            String host = args[0];
            int port = getRemoteSwingLibraryPort(args[1]);
            boolean debug = true;
            boolean closeSecurityDialogs = Arrays.asList(args).contains("CLOSE_SECURITY_DIALOGS");
            int apport = 0;
            for (String arg : args)
                if (arg.startsWith("APPORT="))
                    apport = Integer.parseInt(arg.split("=")[1]);

            Thread findAppContext = new Thread(new FindAppContextWithWindow(host, port, apport, debug, closeSecurityDialogs));
            findAppContext.setDaemon(true);
            findAppContext.start();
            // Sleep to ensure that findAppContext daemon thread is kept alive
            // until the
            // application is started.
            Thread.sleep(1000);
        } catch (Throwable t) {
            Logger.error(t);
        }
    }

    private static int getRemoteSwingLibraryPort(String port) {
        try {
            return Integer.parseInt(port);
        } catch (NumberFormatException e) {
            return DEFAULT_REMOTESWINGLIBRARY_PORT;
        }
    }
}
