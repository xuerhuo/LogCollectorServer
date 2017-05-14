package org.erhuo.logcollectorserver;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.rest.RestStatus;

import java.util.HashMap;

/**
 * Created by erhuo on 2017/5/9.
 */
public class OutPut {
    public static TransportClient client;
    public static String els_index;
    public static String els_type;
    public static long i=1;
    public static int sendnum;
    public static BulkRequestBuilder sendbuffer;
    public static void output(HashMap<String,String> vale) {
        if(LogCollectorServer.margs.get("outputype").equals("elasticsearch")){
            try {
                elsoutput(vale);
            }catch (Exception e){
                System.out.print(e.toString());
                System.exit(-3);
            }

        }
    }
    public static int elsoutput(HashMap<String,String> json) {
        sendbuffer.add(client.prepareIndex(els_index,els_type).setSource(json));
        i++;
        if(i%sendnum==0){
            sendbuffer.execute().actionGet();
            System.out.println("i:"+i);
        }
//        if(i>=3000){
//            System.exit(-4);
//        }
        return 0;
    }
}
