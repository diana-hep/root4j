package org.dianahep.root4j.daemon.xrootd;

import org.dianahep.root4j.daemon.xrootd.Callback.DefaultCallback;

/**
 * Perform an xrootd auth operation
 * @author tonyj
 */
class AuthOperation extends Operation<Void> {

    AuthOperation() {
        super("auth", new AuthMessage(), new DefaultCallback());
    }

    private static class AuthMessage extends Message {

        AuthMessage() {
            super(XrootdProtocol.kXR_auth,"unix\u0000" + System.getProperty("user.name") + " " + System.getProperty("user.group", "nogroup") + "\u0000");
        }
    }
}
