package TestCase.User;

import ApiUtility.ApiRequestData;
import ApiUtility.ApiTestHelper;
import com.test.utility.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class TestUserMgr {
    @Test
    public void TestPostRequestForBVLogin_01(){
        //1.获取测试模块名称、测试用例名称
        ApiTestHelper.TestFunctionName = this.getClass().getName();
        ApiTestHelper.TestCaseName = Thread.currentThread() .getStackTrace()[1].getMethodName();
        //2.设置测试数据
        ApiRequestData apirequestdata  = new ApiRequestData();
        //2.1 测试接口地址或方法名称
        apirequestdata.SetTestApiApiUrl("/user/m/login");
        //2.2 测试业务数据
        apirequestdata.SetBizData(FileOpHelper.Read("/User/BVLoginData")
                .replace("#userName#","15882002098")
                .replace("#password#","123456a"));
        //2.3 测试请求方式
        apirequestdata.SetRequestType(RequestType.Post);
        //2.4 测试请求头
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type",HttpClient.CONTENT_TYPE_FORM_URL);
        apirequestdata.SetHeaders(headers);
        ApiTestHelper.ApiRequestData=apirequestdata;
        //3.执行接口测试
        String s1 = ApiTestHelper.ExcuteApiTset();
        //4.断言测试结果
        try {
            Assert.assertTrue(s1.contains("true"));
            Assert.assertTrue(s1.contains("15882002099"));
            ApiTestHelper.RecordTestResult(TestResultType.Pass, "测试通过");
        }
        catch (AssertionError ex) {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, ex.getMessage());
            Assert.fail();
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
        catch (AssertionError ex) {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, ex.getMessage());
            Assert.fail();
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
        catch (AssertionError ex) {
            ApiTestHelper.RecordTestResult(TestResultType.Failed, ex.getMessage());
            Assert.fail();
        }
    }
}
