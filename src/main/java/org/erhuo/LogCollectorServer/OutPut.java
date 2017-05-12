package org.erhuo.LogCollectorServer;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;

import javax.print.DocFlavor;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Created by erhuo on 2017/5/9.
 */
public class OutPut {
    public static TransportClient client;
    public static String els_index;
    public static String els_type;
    public static void output(HashMap<String,String> vale) {
        if(LogCollectorServer.margs.get("outputype").equals("elasticsearch")){
          elsoutput(vale);
        }
    }
    public static int elsoutput(HashMap<String,String> json) {
        IndexResponse response = client.prepareIndex("twitter", "tweet")
                .setSource(json)
                .get();
        // Index name
        String _index = response.getIndex();
// Type name
        String _type = response.getType();
// Document ID (generated or not)
        String _id = response.getId();
// Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
// status has stored current instance statement.
        RestStatus status = response.status();
        return status.getStatus();
    }
}
