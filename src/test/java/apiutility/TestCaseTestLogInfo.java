package apiutility;

import java.util.Date;

/**
 * 日志系统实体
 * */
public class TestCaseTestLogInfo {
    //测试用例名称
    private String TestCaseName ;
    public  void  SetTestCaseName(String TestCaseName){
        this.TestCaseName = TestCaseName;
    }
    public  String  GetTestCaseName(){
        return  this.TestCaseName;
    }
    //测试状态 0测试不通过，1 测试通过
    private int TestStatus ;
    public  void  SetTestStatus(int TestStatus){
        this.TestStatus = TestStatus;
    }
    public  int  GetTestStatus(){
        return  this.TestStatus;
    }
    //测试版本号
    private String TestVersion;
    public  void  SetTestVersion(String TestVersion){
        this.TestVersion = TestVersion;
    }
    public  String  GetTestVersion(){
        return  this.TestVersion;
    }
    //被测系统编号
    private int TestSystemSysno;
    public  void  SetTestSystemSysno(int TestSystemSysno){
        this.TestSystemSysno = TestSystemSysno;
    }
    public  int  GetTestSystemSysno(){
        return  this.TestSystemSysno;
    }
    //项目名称
    private String ProjectName;
    public  void  SetProjectName(String ProjectName){
        this.ProjectName = ProjectName;
    }
    public  String  GetProjectName(){
        return  this.ProjectName;
    }
    //功能模块名称
    private String FunctionName ;
    public  void  SetFunctionName(String FunctionName){
        this.FunctionName = FunctionName;
    }
    public  String  GetFunctionName(){
        return  this.FunctionName;
    }
    //测试过程详细信息
    private String LogDetail;
    public void SetLogDetail(String LogDetail){
        this.LogDetail = LogDetail;
    }
    public String  GetLogDetail(){
        return this.LogDetail;
    }
    //测试开始时间
    private Date TestBeginTime;
    public void SetTestBeginTime(Date TestBeginTime){
        this.TestBeginTime = TestBeginTime;
    }
    public Date GetTestBeginTime(){
        return this.TestBeginTime;
    }
    //测试结束时间
    private String TestBeginTimeText;
    public String GetTestBeginTimeText(){
        return this.TestBeginTime.toString();
    }
    //测试结束时间
    private Date TestEndTime;
    public void  SetTestEndTime(Date TestEndTime){
        this.TestEndTime = TestEndTime;
    }
    public Date TestEndTime(){
        return this.TestEndTime;
    }
    //测试结束时间
    private String TestEndTimeText;
    public String TestEndTimeText(){
        return this.TestEndTime.toString();
    }
    //创建人编号
    private int UserSysNo;
    public void SetUserSysNo(int UserSysNo){
        this.UserSysNo = UserSysNo;
    }
    public int GetUserSysNo(){
        return this.UserSysNo;
    }
}
