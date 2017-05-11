package org.erhuo.LogCollectorServer;

import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import java.util.Locale;
class LogParse {
        public static final SimpleDateFormat FORMAT = new SimpleDateFormat(
                "d/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);
        public static final SimpleDateFormat dateformat1 = new SimpleDateFormat(
                "yyyyMMddHHmmss");
        public HashMap<String,String> ret;
//        public static Object logtype=new Object(){
//        	public final static String COMMON="1";
//        	public final static String STATIC="2";
//        	public final static String USERERROR="3";
//        	public final static String HOSTERROR="4";
//        	public final static String UNKNOW="5";
//        };
        public static HashMap<String,String> logtype=new HashMap<String,String>();
        public static HashMap<String,String> clientype=new HashMap<String,String>();
        static{
        	logtype.put("COMMON", "COMMON");
        	logtype.put("STATIC", "STATIC");
        	logtype.put("USERERROR", "USERERROR");
        	logtype.put("HOSTERROR", "HOSTERROR");
        	logtype.put("UNKNOW", "UNKNOW");
        	
        	clientype.put("APP", "APP");
        	clientype.put("WECHAT", "WECHAT");
        	clientype.put("PC", "PC");
        	clientype.put("WAP", "WAP");
        	clientype.put("SPIDER", "SPIDER");
        	clientype.put("UNKNOW", "UNKNOW");
        }
        public String time;
        public String ip;
        public String url;
        public String status;
        public String traffic;
        public String userAgent;
        public String logType;
        public String clienType;
        /**
         * 解析英文时间字符串
         * 
         * @param string
         * @return
         * @throws ParseException
         */
        private Date parseDateFormat(String string) {
            Date parse = null;
            try {
                parse = (Date) FORMAT.parse(string);
            } catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return parse;
        }

        /**
         * 解析日志的行记录
         * 
         * @param line
         * @return 数组含有5个元素，分别是ip、时间、url、状态、流量
         */
        public HashMap<String,String> parse(String line) {
        	ret=new HashMap<String,String>();
			try {
				ip= parseIP(line);
				time = parseTime(line);
				url = parseURL(line);
				status = parseStatus(line);
				traffic = parseTraffic(line);
				userAgent = parseUserAgent(line);
				logType = parseLogType();
				clienType = parseClienType();
			}catch (Exception e){
				url=line;
				logType=LogParse.logtype.get("UNKNOW");
			}

           	ret.put("ip",ip);
           	ret.put("time", time);
           	ret.put("traffic", traffic);
           	ret.put("useragent", userAgent);
           	ret.put("url", url);
           	ret.put("status", status);
           	ret.put("logtype", logType);
           	ret.put("clientype", clienType);
           return ret;
        }
        public String  parseUserAgent(String line){
        	String[] data=line.split("\"");
        	return data[data.length-1];
        }
        private String parseTraffic(String line) {
        	 String[] data=line.split("\"");
             String traffic = data[2].trim().split(" ")[1];
            return traffic;
        }

        private String parseStatus(String line) {
        	 String[] data=line.split("\"");
             String status = data[2].trim().split(" ")[0];
             return status;
        }

        private String parseURL(String line) {
        	   String[] data=line.split("\"");
        	   String temp;
        	   try{
        	   temp=data[1].split(" ")[1];
        	   }catch(Exception e){
        		   temp=data[1];
        	   }
               return temp;
        }

        private String parseTime(String line) {
            final int first = line.indexOf("[");
            final int last = line.indexOf("+0800]");
            String time = line.substring(first + 1, last).trim();
            Date date = parseDateFormat(time);
            return dateformat1.format(date);
        }

        private String parseIP(String line) {
            String ip = line.split("- -")[0].trim();
            return ip;
        }
        private String parseLogType(){
        	if(this.status.equals("200")){
        		if(!(url.indexOf(".php")>-1)){
        			return LogParse.logtype.get("STATIC");
        		}
        		if(url.startsWith("/uc_server")){
        			return LogParse.logtype.get("STATIC");
        		}
        		if(url.length()==1){
        			return LogParse.logtype.get("COMMON");
        		}
        		if(url.indexOf(".php")>-1){
        			return LogParse.logtype.get("COMMON");
        		}
        		return LogParse.logtype.get("UNKNOW");
        	}
        	if(this.status.equals("404")){
        		return LogParse.logtype.get("HOSTERROR");
        	}
        	if(this.status.equals("301")||this.status.equals("302")){
        		return LogParse.logtype.get("STATIC");
        	}
        	if(this.status.equals("500")){
        		return LogParse.logtype.get("HOSTERROR");
        	}
        	return LogParse.logtype.get("UNKNOW");
        }
        private String parseClienType(){
        	if(this.userAgent.indexOf("MicroMessenger")>-1){
        		return LogParse.clientype.get("WECHAT");
        	}
        	if(this.userAgent.indexOf("MocuzApp")>-1){
        		return LogParse.clientype.get("APP");
        	}
        
        	if(this.userAgent.toLowerCase().indexOf("bot")>-1||this.userAgent.toLowerCase().indexOf("spider")>-1)
        	{
        		return LogParse.clientype.get("SPIDER");
        	}
        	if(this.userAgent.indexOf("Windows")>-1)
        	{
        		return LogParse.clientype.get("PC");
        	}
        	if(this.userAgent.indexOf("Android")>-1
        			||this.userAgent.toLowerCase().indexOf("iphone")>-1
        			||this.userAgent.toLowerCase().indexOf("ipad")>-1
        			||this.userAgent.toLowerCase().indexOf("mobile")>-1){
        		return LogParse.clientype.get("WAP");
        	}
        	return LogParse.clientype.get("UNKNOW");
        }
    }