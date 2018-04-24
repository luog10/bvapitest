package apiutility;

import java.util.HashMap;
import java.util.Map;

public class Log4TestLogSystemHelper {
    /// <summary>
    /// 将log写入日志系统
    /// </summary>
    /// <param name="logsysurl"></param>
    /// <param name="loginfo"></param>
    public static <T> String  WriteLog(String logsysurl, T loginfo) {
        try {
            //1.将接收到的对象数据转化成json字符串
            String logs = JsonHelper.ObjectToJson(loginfo);
            //2.设置请求头
            Map<String, String> Headers = new HashMap<String, String>();
            Headers.put("Content-Type","application/x-www-form-urlencoded");
            //3.发起请求写入日志系统
            return HttpClient.Post(logsysurl,Headers, logs);
        }
        catch (Exception ex) {
            return  ex.getMessage();
        }
    }

    /// <summary>
    /// 将log写入日志系统
    /// </summary>
    /// <param name="logsysurl"></param>
    /// <param name="loginfo"></param>
    /// <param name="Headers"></param>
    public static <T> String WriteLog(String logsysurl,Map<String, String> headers, T loginfo) {
        try {
            //1.将接收到的对象数据转化成json字符串
            String logs = JsonHelper.ObjectToJson(loginfo);
            //2.发起请求写入日志系统
            return HttpClient.Post(logsysurl,headers, logs);
        }
        catch (Exception ex) {
            return  ex.getMessage();
        }
    }

    /// <summary>
    /// 将log异步写入日志系统
    /// </summary>
    /// <param name="logsysurl"></param>
    /// <param name="loginfo"></param>
    public static <T> String WriteLogAsync(String logsysurl, T loginfo){
        try {
            //1.将接收到的对象数据转化成json字符串
            String logs = JsonHelper.ObjectToJson(loginfo);
            //2.设置请求头
            Map<String, String> Headers = new HashMap<String, String>();
            Headers.put("Content-Type",HttpClient.CONTENT_TYPE_FORM_URL);
            //3.发起请求写入日志系统
            return HttpClient.AsyncPost(logsysurl,Headers, logs);
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }

    /// <summary>
    /// 将log异步写入日志系统
    /// </summary>
    /// <param name="logsysurl"></param>
    /// <param name="loginfo"></param>
    public static <T> String WriteLogAsync(String logsysurl, Map<String, String> headers, T loginfo) {
        try {
            //1.将接收到的对象数据转化成json字符串
            String logs = JsonHelper.ObjectToJson(loginfo);
            //2.发起请求写入日志系统
            return HttpClient.AsyncPost(logsysurl, headers,logs);
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }
}
