import apiutility.HttpRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestApi {
    @Test
    /*
     *   测试不带参数的Get http请求
     */
    public void TestGetRequestForNoParameterRequest(){
        //发送 GET 请求
        String TestUrl = "http://10.21.10.179:9000/";
        String s= HttpRequest.sendGet(TestUrl);
        //String s1= HttpRequest.SendRequest(TestUrl,  , "GET");
        Assert.assertTrue(s.contains("九九"));
    }
}
