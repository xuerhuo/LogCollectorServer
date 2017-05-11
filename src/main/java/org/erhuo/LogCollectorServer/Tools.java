package org.erhuo.LogCollectorServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Tools {
	public static String mapToString(HashMap<String,String> data,String determin){
		String ret="";
		for(Map.Entry<String,String> entry:data.entrySet()){
			ret=ret+entry.getKey()+":"+entry.getValue()+determin;
		}
		 ret=ret.substring(0,ret.length()-1);
		return ret;
	}
	public static String mapToStringOrder(HashMap<String,String> data,String determin){
		String ret="";
		ret=ret+"logtype"+":"+data.get("logtype")+determin;
		ret=ret+"status"+":"+data.get("status")+determin;
		ret=ret+"clientype"+":"+data.get("clientype")+determin;
		ret=ret+"userAgent"+":"+data.get("useragent")+determin;
		ret=ret+"url"+":"+data.get("url")+determin;
		ret=ret+"time"+":"+data.get("time")+determin;
		ret=ret+"ip"+":"+data.get("ip")+determin;
		return ret;
	}
	   public static String arrayToString(String data[],String determin){
	        String ret="";
	        for (int i=0;i<data.length;i++){
	            ret=ret+i+":"+data[i]+determin;
	        }
	        ret=ret.substring(0,ret.length()-1);

	        return ret;
	    }
	    public static HashMap<String,String> initargs(String[] args){
			HashMap<String,String> ret = new HashMap<String,String>();
            String s=null;
            try {
                for (int i = 0; i < args.length; i++, i++) {
                    ret.put(args[i].substring(2), args[i + 1]);
                }

            }catch (Exception e){

            }
            return ret;
		}
}
