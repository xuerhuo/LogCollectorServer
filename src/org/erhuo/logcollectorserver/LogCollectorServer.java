package org.erhuo.logcollectorserver;
import org.elasticsearch.common.settings.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import static java.lang.Thread.sleep;
import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by erhuo on 2017/5/9.
 */
public class LogCollectorServer {
    public static HashMap<String, String> margs;
    public static HashMap<String, String> parselog;
    public static NginxLogParse ng=new NginxLogParse();
    public static Redis redis;
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        String temp;
        long len;
        margs = Tools.initargs(args);
        ExecutorService threadPool = Executors.newFixedThreadPool(Integer.parseInt(margs.get("thread")));
        initSys();
        System.out.print(redis.llen(margs.get("logrediskey")));
        while (true) {
            len=redis.llen(margs.get("logrediskey"));
            System.out.println(len);
            while ( len> 1) {
                try {
                    temp = redis.lpop(margs.get("logrediskey"));
                }catch (Exception e){
                    initSys();
                    temp = redis.lpop(margs.get("logrediskey"));
                }
                temp =Base64.decode(temp);
                    parselog = ng.parse(temp);
                threadPool.execute(new SingleThread(parselog));
                len=redis.llen(margs.get("logrediskey"));
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }

        }

    }
    public static void initSys() throws UnknownHostException {
        if(margs.get("inputype").equals("redis")){
            redis=new Redis();
        }
        redis.keys();
        System.out.println(margs.get("els_host")+margs.get("els_port"));
        try {
            OutPut.client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(margs.get("els_host")), Integer.parseInt(margs.get("els_port"))));
        }catch (Exception e){
            System.out.println(e.toString());
            System.exit(-1);
        }
            OutPut.els_index=margs.get("els_index");
        OutPut.els_type = margs.get("els_type");

    }
}
