package TestCase.Demo;

import com.test.utility.HttpClient;
import com.test.utility.PropertiesUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDemo {
    @Test
    public void testcase1(){
        try {
            Assert.assertEquals(1,1);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        Assert.assertEquals(1,1);
    }

    @Test
    public void testcase2(){
        Assert.assertEquals(1,1);
    }

    @Test
    public void TestGetRequestForNoParameterRequest() {
        //发送 GET 请求
        String TestUrl = "http://10.21.10.179:9000/";
        String s1 = HttpClient.Get(TestUrl);
        Assert.assertTrue(s1.contains("九九"));
    }

    @Test
    public void TestAsyncGetRequestForNoParameterRequest(){
        //发送 GET 请求
        String TestUrl = "http://10.21.10.179:9000/";
        String s1 = HttpClient.AsyncGet(TestUrl);
        Assert.assertTrue(s1.contains("九九"));
    }

    @Test
    public  void  TestReadConfig(){
        String ConfigPath = "TestConfig.properties";
        String str =  PropertiesUtil.getValue(ConfigPath,"TestProjectName");
        String str1 =  PropertiesUtil.getValue(ConfigPath,"TestProjectName");
    }
}