package org.erhuo.LogCollectorServer;

import java.util.HashMap;

/**
 * Created by erhuo on 2017/5/12.
 */
public class SingleThread implements Runnable {
    HashMap<String,String> data;
    public SingleThread(HashMap<String,String> data1){
        this.data=data1;
    }
    public void run() {
        OutPut.output(this.data);
    }
}
