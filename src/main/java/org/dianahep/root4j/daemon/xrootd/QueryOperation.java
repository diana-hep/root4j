package org.dianahep.root4j.daemon.xrootd;

import org.dianahep.root4j.daemon.xrootd.Callback.StringCallback;

/**
 * Query a file.
 * @author tonyj
 */
class QueryOperation extends Operation<String> {

    QueryOperation(int queryType, String path) {
        super("query", new QueryMessage(queryType, path), new StringCallback());
    }

    private static class QueryMessage extends Message {

        QueryMessage(int queryType, String path) {
            super(XrootdProtocol.kXR_query, path);
            writeShort(queryType);
        }
    }
}
