import apiutility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestApi {
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

    @Test
    public void TestPostRequestForBVLogin_01(){
        ApiTestHelper.TestFunctionName = this.getClass().getName();
        ApiTestHelper.TestCaseName = Thread.currentThread() .getStackTrace()[1].getMethodName();

        ApiRequestData apirequestdata  = new ApiRequestData();
        apirequestdata.SetTestApiApiUrl("/user/m/login");
        apirequestdata.SetBizData(FileOpHelper.Read("BVLoginData")
                .replace("#userName#","15882002098")
                .replace("#password#","123456a"));
        apirequestdata.SetRequestType(RequestType.Post);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type",HttpClient.CONTENT_TYPE_FORM_URL);
        apirequestdata.SetHeaders(headers);
        ApiTestHelper.ApiRequestData=apirequestdata;

        String s1 = ApiTestHelper.ExcuteApiTset();

        //4.断言测试结果
        try {
            Assert.assertTrue(s1.contains("true"));
            Assert.assertTrue(s1.contains("15882002098"));
            ApiTestHelper.RecordTestResult(TestResultType.Pass, "测试通过");
        }
        catch (Exception ex)
        {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, ex.getMessage());
        }
    }

    @Test
    public  void  TestBvGetPersonCenterInfo(){
        ApiTestHelper.TestFunctionName = this.getClass().getName();
        ApiTestHelper.TestCaseName = Thread.currentThread() .getStackTrace()[1].getMethodName();

        ApiRequestData apirequestdata  = new ApiRequestData();
        apirequestdata.SetTestApiApiUrl("/user/m/login");
        apirequestdata.SetBizData(String.format("userName=%s&password=%s", "15882002098", "123456a"));
        apirequestdata.SetRequestType(RequestType.Post);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type",HttpClient.CONTENT_TYPE_FORM_URL);
        apirequestdata.SetHeaders(headers);
        ApiTestHelper.ApiRequestData=apirequestdata;

        String loginfo = ApiTestHelper.ExcuteApiTset();

        //4.断言测试结果
        try {
            Assert.assertTrue(loginfo.contains("true"));
            Assert.assertTrue(loginfo.contains("15882002098"));
        }
        catch (Exception ex) {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, ex.getMessage());
        }

        //测试获取用户基本信息
        ApiRequestData apirequestdata01  = new ApiRequestData();
        apirequestdata01.SetTestApiApiUrl("user/GetPersonCenter");
        apirequestdata01.SetRequestType(RequestType.Get);
        Map<String, String> userloginCookie = new HashMap<String, String>();
        userloginCookie.put("Cookie",HttpClient.cookies[0].getValue());
        apirequestdata01.SetHeaders(userloginCookie);
        ApiTestHelper.ApiRequestData=apirequestdata01;

        String info =  ApiTestHelper.ExcuteApiTset();
        try {
            Assert.assertTrue(info.contains("true"));
            ApiTestHelper.RecordTestResult(TestResultType.Pass, "测试通过");
        }
        catch (Exception ex)
        {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, ex.getMessage());
        }
    }
}
