package apiutility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 请求WEBAPI并返回结果
     *
     * @param url      URL地址，http/https格式，不能含?和参数
     * @param paraList 参数，以keyvalue列表的方式，key表示参数名，value为参数值，value不要urlencode编码
     * @param method   POST/GET
     * @return 返回结果，如果发生异常则直接向外抛出
     */
    public  static   String SendRequest(String url, Map<String, String> paraList, String method) throws Exception {
        String strResult = "";
        if (url == null || url == "") {
            return null;
        }
        if (method == null) {
            method = "GET";
        }

        // GET方式
        if (method.toUpperCase() == "GET") {

            String fullUrl = url + "?";
            if (paraList != null) {
                for (String key : paraList.keySet()) {
                    fullUrl += key + "=" + URLEncoder.encode(paraList.get(key), "UTF-8") + "&";
                }
                fullUrl = fullUrl.substring(0, fullUrl.lastIndexOf("&"));
            }

            URL realUrl = new URL(fullUrl);
            URLConnection connection = realUrl.openConnection();
            connection.connect();

            String line;
            BufferedReader sr = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            while ((line = sr.readLine()) != null) {
                strResult += line;
            }
            sr.close();
        }

        // POST方式
        if (method.toUpperCase() == "POST") {
            String fullUrl = url + "?";


            URL realUrl = new URL(fullUrl);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            String paraString = "";
            if (paraList != null) {
                for (String key : paraList.keySet()) {
                    paraString += key + "=" + URLEncoder.encode(paraList.get(key), "UTF-8") + "&";
                }
                paraString = paraString.substring(0, paraString.lastIndexOf("&"));
            }
            out.print(paraString);
            out.flush();
            String line;
            BufferedReader sr = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            while ((line = sr.readLine()) != null) {
                strResult += line;
            }
            sr.close();
        }

        return strResult;

    }
}