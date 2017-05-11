package org.erhuo.LogCollectorServer;

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

    public  Redis(String host,String port,String password){
        jedis=new Jedis(host,Integer.parseInt(port));
        if(!password.isEmpty()){
            if (jedis.auth(password).equals("OK")){
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
