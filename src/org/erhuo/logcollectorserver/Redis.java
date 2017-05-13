package org.erhuo.logcollectorserver;

import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by erhuo on 2017/5/9.
 */
public class Redis {
    boolean connected=false;
    static Jedis jedis;

    public  Redis(){
        jedis=new Jedis(LogCollectorServer.margs.get("redis_host"),Integer.parseInt(LogCollectorServer.margs.get("redis_port")));
        if(LogCollectorServer.margs.get("redis_password")!=null){
            if (jedis.auth(LogCollectorServer.margs.get("redis_password")).equals("OK")){
                this.connected=true;
            }
        }
    }
    public void keys(){
        HashSet<String> list = (HashSet<String>) this.jedis.keys("*");
//      for (Iterator<String> it=list.iterator();it.hasNext();){
//          //System.out.println(it.next());
//      }
    }
    public long llen(String keys){
        return jedis.llen(keys);
    }
    public String lpop(String key){
        return jedis.lpop(key);
    }
}
