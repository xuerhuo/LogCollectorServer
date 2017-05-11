package org.erhuo.LogCollectorServer;

import java.util.HashMap;

/**
 * Created by erhuo on 2017/5/2.
 */
public class NginxLogParse extends LogParse {
    public String[] spacedata;
    public HashMap<String,String> parse(String line) {
        return super.parse(line);
    }

//    public String  parseUserAgent(String line){
//        String[] data=line.split("\"");
//        return data[data.length-2];
//    }
}
