package apiutility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * Api测试帮助类
 * */
public class ApiTestHelper{
    // 测试模块名称
    public static String TestFunctionName = null;
    // 测试用例名称
    public static String TestCaseName = null;
    //从配置中获取测试环境hosts
    static  String  ApiHost = PropertiesUtil.getValue("TestConfig.properties","EvnHostQA");
    //请求数据
    public  static  ApiRequestData ApiRequestData = null;
    //测试日志系统写入信息
    static TestCaseTestLogInfo TestCaseTestLogInfo = new TestCaseTestLogInfo();
    // 是否记录日志到日志系统
    static String IsToLogSystem = PropertiesUtil.getValue("TestConfig.properties","IsToLogSystem");
    // 被测系统编号
    static String TestSystemSysNo = PropertiesUtil.getValue("TestConfig.properties","TestSystemSysNo");
    // 测试版本号
    static String TestVersionNo = PropertiesUtil.getValue("TestConfig.properties","TestVersionNo");
    // 测试日志系统host
    static String TestLogSystemHost = PropertiesUtil.getValue("TestConfig.properties","TestLogSystemHost");
    // 被测项目名称
    static String TestProjectName = PropertiesUtil.getValue("TestConfig.properties","TestProjectName");
    // 测试日志详情叠加器
    static StringBuilder stringbuilder = null;

    /// <summary>
    /// 执行API测试
    /// </summary>
    /// <returns>测试结果</returns>
    public static String ExcuteApiTset() {
        //初始化测试日志详情叠加器
        stringbuilder = new StringBuilder();
        TestCaseTestLogInfo.SetTestBeginTime(new Date());
        BuildTestCaseTestLogDetail(String.format("%s%n测试开始...%n",CommonTool.GetCurrentTime()));
        String TestResult = "";
        try
        {
            //1.请求业务得到测试结果
            if(ApiRequestData.GetRequestType() == RequestType.Post){
                TestResult = ApiTset4Post(ApiRequestData.GetTestApiApiUrl(),ApiRequestData.GetBizData());
            }else if(ApiRequestData.GetRequestType() == RequestType.Get){
                TestResult = ApiTset4Get( ApiRequestData.GetTestApiApiUrl());
            }
            BuildTestCaseTestLogDetail(String.format("%s%n测试结果:[{%s}].%n", CommonTool.GetCurrentTime(), TestResult));
        }
        catch (Exception ex)
        {
            RecordTestResult(TestResultType.Failed, ex.getMessage());
        }
        return TestResult;
    }

    /// <summary>
    /// 发起HTTP请求
    /// </summary>
    /// <returns></returns>
    static String ApiTset4Post(String TestApiApiUrl,String requestdata) {
        BuildTestCaseTestLogDetail(String.format("%s%n测试数据:[{%s},{%s}].\r\n", CommonTool.GetCurrentTime(), TestApiApiUrl, requestdata));
        return HttpClient.Post(String.format("%s/%s", ApiHost, TestApiApiUrl),ApiRequestData.GetHeaders(), requestdata);
    }

    /// <summary>
    /// 发起HTTP请求
    /// </summary>
    /// <returns></returns>
    static String ApiTset4Get(String TestApiApiUrl) {
        BuildTestCaseTestLogDetail(String.format("%s%n测试数据:[{%s}].\r\n", CommonTool.GetCurrentTime(), TestApiApiUrl));
        return HttpClient.Get(String.format("%s/%s", ApiHost, TestApiApiUrl),ApiRequestData.GetHeaders());
    }

    /// <summary>
    /// 记录测试测试结果
    /// </summary>
    /// <param name="ResultType"></param>
    /// <param name="TestResultDetail"></param>
    public static void RecordTestResult(TestResultType ResultType, String TestResultDetail) {
        //是否记录日志到日志系统
        boolean IswritelogtoLogSystem = false;
        if (!CommonTool.IsNullOrEmpty(IsToLogSystem) && IsToLogSystem.equals("true"))
        {
            //记录日志详情
            IswritelogtoLogSystem = true;
        }

        if (ResultType == TestResultType.Pass)
        {
            if (IswritelogtoLogSystem)
            {
                BuildTestCaseTestLogDetail(String.format("%s%n测试通过,Pass..", CommonTool.GetCurrentTime()));
                TestCaseTestLogInfo.SetTestStatus(1);
                TestCaseTestLogInfo.SetTestEndTime(new Date());
                //调用写入日志系统
                WriteLogToLogSystem();
            }
        }
        else
        {
            if (IswritelogtoLogSystem)
            {
                BuildTestCaseTestLogDetail(String.format("%s%n测试不通过,Failed,%n失败详情：[{1}]", CommonTool.GetCurrentTime(), TestResultDetail));
                TestCaseTestLogInfo.SetTestEndTime(new Date());
                TestCaseTestLogInfo.SetTestStatus(0);
                //调用写入日志系统
                System.out.println(WriteLogToLogSystem());
            }
            CommonTool.ThrowNewException(TestResultDetail);
        }
    }

    /// <summary>
    /// 测试日志写入测试日志系统
    /// </summary>
    /// <param name="ProjectName">被测项目名称</param>
    /// <returns></returns>
    static String WriteLogToLogSystem() {
        //记录测试日志到测试日志系统Precheck
        ToLogSystemPrecheck();
        //调用异步Post请求写入日志系统
        Map<String, String> Headers = new HashMap<String, String>();
        Headers.put("Content-Type",HttpClient.CONTENT_TYPE_JSON_URL);
        //获取测试日志系统账号&密码配置
        String TestSystemAppId_Key = PropertiesUtil.getValue("TestConfig.properties","TestSystemAppId");
        if (TestSystemAppId_Key ==null || TestSystemAppId_Key =="")
        {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestSystemAppId&Key");
        }
        else
        {
            if (!TestSystemAppId_Key.contains("|")) {
                CommonTool.ThrowNewException("TestSystemAppId配置格式错误,AppId&Key应以\"|\"分隔");
            }
            String[] Id_Key = TestSystemAppId_Key.split("\\|");
            if (Id_Key != null && Id_Key.length != 2) {
                CommonTool.ThrowNewException("TestSystemAppId配置不正确！");
            }
            //将日志系统Id_key设置请求报文头
            Headers.put(Id_Key[0], Id_Key[1]);
        }
        String LogSystemUrl = String.format("%s/api/TestCaseTestLog", TestLogSystemHost);
        try
        {
            return Log4TestLogSystemHelper.WriteLogAsync(LogSystemUrl,Headers, TestCaseTestLogInfo);
        }
        catch (Exception ex)
        {
            return ex.getMessage();
        }

    }

    /// <summary>
    /// 写入日志系统前检查
    /// </summary>
    static void ToLogSystemPrecheck() {
        if (CommonTool.IsNullOrEmpty(TestLogSystemHost)){
            CommonTool.ThrowNewException("请在测试配置文件中配置TestLogSystemHost");
        }
        if (CommonTool.IsNullOrEmpty(TestSystemSysNo)) {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestSystemSysNo");
        }
        if (CommonTool.IsNullOrEmpty(TestVersionNo)) {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestVersionNo");
        }
        if (CommonTool.IsNullOrEmpty(TestProjectName)) {
            CommonTool.ThrowNewException("请在测试配置文件中配置TestProjectName");
        }

        TestCaseTestLogInfo.SetProjectName(TestProjectName);
        TestCaseTestLogInfo.SetTestSystemSysno(Integer.parseInt(TestSystemSysNo));
        TestCaseTestLogInfo.SetTestVersion(TestVersionNo);
        TestCaseTestLogInfo.SetUserSysNo(1);

        TestCaseTestLogInfo.SetFunctionName(TestFunctionName);
        TestCaseTestLogInfo.SetTestCaseName(TestCaseName);
    }

    /// <summary>
    /// 将测试详情追加写入到日志详情
    /// </summary>
    /// <param name="steptestlog"></param>
    static void BuildTestCaseTestLogDetail(String steptestlog) {
        TestCaseTestLogInfo.SetLogDetail(stringbuilder.append(steptestlog).toString());
    }
}

