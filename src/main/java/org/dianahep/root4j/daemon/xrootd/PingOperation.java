package org.dianahep.root4j.daemon.xrootd;

import org.dianahep.root4j.daemon.xrootd.Callback.DefaultCallback;

/**
 * Ping a server.
 * @author tonyj
 */
class PingOperation extends Operation<Void> {

    PingOperation() {
        super("ping", new Message(XrootdProtocol.kXR_ping), new DefaultCallback());
    }
}
