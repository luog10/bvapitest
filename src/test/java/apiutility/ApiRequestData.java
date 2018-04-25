package ApiUtility;

import com.test.utility.RequestType;

import java.util.Map;

/**
 * Api请求实体
 * */
public class ApiRequestData {
    //被测ApiUrl，如：v1/api/user/m/login
    private  String TestApiApiUrl;
    public void  SetTestApiApiUrl(String TestApiApiUrl){
        this.TestApiApiUrl = TestApiApiUrl;
    }
    public String  GetTestApiApiUrl(){
        return this.TestApiApiUrl;
    }
    //请求业务数据
    private String BizData;
    public void  SetBizData(String BizData){
        this.BizData = BizData;
    }
    public String  GetBizData(){
        return this.BizData;
    }
    //请求类型
    private RequestType RequestType;
    public void  SetRequestType(RequestType RequestType){
        this.RequestType = RequestType;
    }
    public RequestType  GetRequestType(){
        return this.RequestType;
    }
    //请求Headers
    private  Map<String, String> headers;
    public  void  SetHeaders(Map<String, String> headers){
        this.headers = headers;
    }
    public Map<String, String> GetHeaders(){
        return  this.headers;
    }
}
