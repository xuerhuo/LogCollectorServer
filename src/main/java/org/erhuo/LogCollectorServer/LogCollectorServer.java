package org.erhuo.LogCollectorServer;
import org.elasticsearch.common.settings.Settings;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import static java.lang.Thread.sleep;

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
        margs = Tools.initargs(args);
        initSys();
        System.exit(1);
        System.out.print(redis.llen(margs.get("logrediskey")));
        while (true) {
            while (redis.llen(margs.get("logrediskey")) > 1) {
                temp = redis.lpop(margs.get("logrediskey"));
                temp =Base64.decode(temp);
                    parselog = ng.parse(temp);

                Thread.sleep(2000);
                OutPut.output(Tools.mapToStringOrder(parselog,"|"));
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }

        }

    }
    public static void initSys() throws UnknownHostException {
        if(margs.get("inputype").equals("redis")){
            redis=new Redis(margs.get("redis_host"),margs.get("redis_port"),margs.get("redis_password"));
        }
        redis.keys();
        System.out.println(margs.get("els_host")+margs.get("els_port"));
        OutPut.client=new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(margs.get("els_host")), Integer.parseInt(margs.get("els_port"))));
        System.out.print(OutPut.client.nodeName());
    }


}
