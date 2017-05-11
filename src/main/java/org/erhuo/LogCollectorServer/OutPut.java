package org.erhuo.LogCollectorServer;

import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.UnknownHostException;

/**
 * Created by erhuo on 2017/5/9.
 */
public class OutPut {
    public static TransportClient client;
    public static void output(String vale) {
        if(LogCollectorServer.margs.get("outputype").equals("elasticsearch")){
           elsoutput(vale);
        }
    }
    public static void elsoutput(String value) {
        System.out.println(value);
    }
}
