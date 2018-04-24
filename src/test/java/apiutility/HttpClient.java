package apiutility;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

public class HttpClient {
    // utf-8字符编码
    public static final String CHARSET_UTF_8 = "utf-8";
    // HTTP内容类型。
    public static final String CONTENT_TYPE_TEXT_HTML = "text/xml";
     // HTTP内容类型。相当于form表单的形式，提交数据
     public static final String CONTENT_TYPE_FORM_URL = "application/x-www-form-urlencoded";
     // HTTP内容类型。相当于Json形式，提交数据
     public static final String CONTENT_TYPE_JSON_URL = "application/json;charset=utf-8";
    /**
     * Headers
     * */
    public  static  Header[] cookies = null;

    /**
     * 发送Http Get请求
    */
    public static String Get(String url)  {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送异步Http Get请求,请求带headers
     */
    public static String AsyncGet(String url)  {
        CloseableHttpAsyncClient httpclient =  null;
        String result = "";
        try {
            httpclient = HttpAsyncClients.createDefault();
            httpclient.start();
            final CountDownLatch latch = new CountDownLatch(1);
            final HttpGet request = new HttpGet(url);
            Future<HttpResponse> Response = httpclient.execute(request, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    try {
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                public void failed(final Exception ex) {
                    latch.countDown();
                }
                public void cancelled() {
                    latch.countDown();
                }
            });
            latch.await();
            try {
                result =  EntityUtils.toString(Response.get().getEntity(), "UTF-8");
            }catch (ExecutionException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
        return  result;
    }

    /**
     * 发送Http Get请求,请求带headers
     */
    public static String Get(String url,Map<String, String> headers) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);

            // 添加设置请求头信息
            if (null != headers && headers.size() > 0) {
                for (Map.Entry<String, String > header : headers.entrySet()) {
                    httpGet.setHeader(header.getKey(),header.getValue());
                }
            }
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送异步Http Get请求,请求带headers
     */
    public static String AsyncGet(String url,Map<String, String> headers)  {
        CloseableHttpAsyncClient httpclient =  null;
        String result = "";
        try {
            httpclient = HttpAsyncClients.createDefault();
            httpclient.start();
            final CountDownLatch latch = new CountDownLatch(1);
            final HttpGet request = new HttpGet(url);

            // 添加设置请求头信息
            if (null != headers && headers.size() > 0) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.setHeader(header.getKey(),header.getValue());
                }
            }

            Future<HttpResponse> Response = httpclient.execute(request, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    try {
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                public void failed(final Exception ex) {
                    latch.countDown();
                }
                public void cancelled() {
                    latch.countDown();
                }
            });
            latch.await();
            try {
                result =  EntityUtils.toString(Response.get().getEntity(), "UTF-8");
            }catch (ExecutionException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
        return  result;
    }

    /**
     * 发送Http Post请求,带自定义Header
     */
    public static String Post(String url, Map<String, String > headers,String requestbody) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);

        // 添加设置请求头信息
        if (null != headers && headers.size() > 0) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(),header.getValue());
            }
        }

        // 设置post请求参数
        try {
            StringEntity se = new StringEntity(requestbody, Consts.UTF_8);
            httpPost.setEntity(se);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 输入Headers
            if(cookies != null) cookies =null;
            cookies = httpResponse.getHeaders("Set-Cookie");
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送Http AsyncPost请求
     */
    public static String AsyncPost(String url,Map<String, String> headers, String requestbody) {
        CloseableHttpAsyncClient httpclient =  null;
        String result = "";
        try {
            httpclient = HttpAsyncClients.createDefault();
            httpclient.start();
            final CountDownLatch latch = new CountDownLatch(1);
            final HttpPost request = new HttpPost(url);

            // 添加设置请求头信息
            if (null != headers && headers.size() > 0) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    request.setHeader(header.getKey(),header.getValue());
                }
            }

            // 设置post请求参数
            try {
                StringEntity se = new StringEntity(requestbody, Consts.UTF_8);
                request.setEntity(se);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Future<HttpResponse> Response = httpclient.execute(request, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    try {
                        latch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                public void failed(final Exception ex) {
                    latch.countDown();
                }
                public void cancelled() {
                    latch.countDown();
                }
            });
            latch.await();
            try {
                result =  EntityUtils.toString(Response.get().getEntity(), "UTF-8");
            }catch (ExecutionException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            httpclient.close();
        } catch (IOException ignore) {
            ignore.printStackTrace();
        }
        return  result;
    }
}