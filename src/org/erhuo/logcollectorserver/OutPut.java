package org.erhuo.logcollectorserver;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
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
    public static int sleep;
    public static BulkRequestBuilder sendbuffer;
    public static BulkProcessor bulkProcessor;
    public static void init(){
        try {
            OutPut.client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(LogCollectorServer.margs.get("els_host")), Integer.parseInt(LogCollectorServer.margs.get("els_port"))));
            OutPut.sendbuffer=OutPut.client.prepareBulk();
            OutPut.sendnum=Integer.parseInt(LogCollectorServer.margs.get("sendbuffernum"));
            bulkProcessor=BulkProcessor.builder(OutPut.client,new BulkProcessor.Listener() {
                @Override
                public void beforeBulk(long l, BulkRequest bulkRequest) {

                }

                @Override
                public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {

                }

                @Override
                public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {

                }
            }).setBulkActions(sendnum).setConcurrentRequests(0).build();
        }catch (Exception e){
            System.out.println(e.toString()+";OutPut init error");
            System.exit(-2);
        }
        OutPut.els_index=LogCollectorServer.margs.get("els_index");
        OutPut.els_type = LogCollectorServer.margs.get("els_type");
        if(LogCollectorServer.margs.get("sleep")!=null){
            sleep=Integer.parseInt(LogCollectorServer.margs.get("sleep"));
        }
    }
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
       // sendbuffer.add(client.prepareIndex(els_index,els_type).setSource(json));
        IndexRequest indexRequest=new IndexRequest(els_index,els_type).source(json);
        bulkProcessor.add(indexRequest);
        i++;
        return 0;
    }
}
