import apiutility.HttpClient;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestApi {
    @Test
    /*
     *   测试不带参数的Get http请求
     */
    public void TestGetRequestForNoParameterRequest(){
        //发送 GET 请求
        String TestUrl = "http://10.21.10.179:9000/";
        String s1 = HttpClient.Get(TestUrl);
        Assert.assertTrue(s1.contains("九九"));
    }
    @Test
    /*
     *   测试不带参数的Get http请求
     */
    public void TestAsyncGetRequestForNoParameterRequest(){
        //发送 GET 请求
        String TestUrl = "http://10.21.10.179:9000/";
        String s1 = HttpClient.AsyncGet(TestUrl);
        Assert.assertTrue(s1.contains("九九"));
    }
    @Test
    /*
     *   测试Post请求
     */
    public void TestAsyncPostRequestForBVLogin(){
        //发送 GET 请求
        String BvpreLoginUrl = "http://192.168.60.60/v1/api/user/m/login";
        Map<String, Object> usrinfo = new HashMap<String, Object>();
        usrinfo.put("userName","18888888889");
        usrinfo.put("password","123456");
        Map<String, Object> Headers = new HashMap<String, Object>();
        Headers.put("Content-Type","application/x-www-form-urlencoded");
        String s1 = HttpClient.AsyncPost(BvpreLoginUrl,Headers,usrinfo);
        Assert.assertTrue(s1.contains("true"));
        Assert.assertTrue(s1.contains("18888888889"));
    }
    @Test
    /*
     *   测试Post请求
     */
    public void TestPostRequestForBVLogin(){
        //发送 GET 请求
        String BvpreLoginUrl = "http://192.168.60.60/v1/api/user/m/login";
        Map<String, Object> usrinfo = new HashMap<String, Object>();
        usrinfo.put("userName","18888888889");
        usrinfo.put("password","123456");
        String s1 = HttpClient.Post(BvpreLoginUrl,usrinfo);
        Assert.assertTrue(s1.contains("true"));
        Assert.assertTrue(s1.contains("18888888889"));
    }

    @Test
    public  void  TestBvGetPersonCenterInfo(){
        //发送 GET 请求
        String BvpreLoginUrl = "http://192.168.60.60/v1/api/user/m/login";
        Map<String, Object> usrinfo = new HashMap<String, Object>();
        usrinfo.put("userName","13200000001");
        usrinfo.put("password","123456a");
        Map<String, Object> Headers = new HashMap<String, Object>();
        Headers.put("Content-Type","application/x-www-form-urlencoded");
        //登录
        HttpClient.Post(BvpreLoginUrl,Headers,usrinfo);
        //获取用户基本信息
        String UserBaseInfoUrl ="http://192.168.60.60/v1/api/user/GetPersonCenter";
        Map<String, Object> userloginCookie = new HashMap<String, Object>();
        userloginCookie.put("Cookie",HttpClient.cookies[0].getValue());
        String info =  HttpClient.Get(UserBaseInfoUrl,userloginCookie);
        Assert.assertTrue(info.contains("true"));
    }

    @Test
    public void TestPostRequestForBVLoginToSetHeader(){
        //发送 GET 请求
        String BvpreLoginUrl = "http://192.168.60.60/v1/api/user/m/login";
        Map<String, Object> usrinfo = new HashMap<String, Object>();
        usrinfo.put("userName","18888888889");
        usrinfo.put("password","123456");

        Map<String, Object> Headers = new HashMap<String, Object>();
        Headers.put("Content-Type","application/x-www-form-urlencoded");
        String s1 = HttpClient.Post(BvpreLoginUrl,Headers,usrinfo);
        Assert.assertTrue(s1.contains("true"));
        Assert.assertTrue(s1.contains("18888888889"));
    }
}
