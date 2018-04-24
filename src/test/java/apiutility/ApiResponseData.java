package apiutility;

import org.apache.http.Header;

/**
 * Api请求实体
 * */
public class ApiResponseData {
    //请求成功标志
    private boolean success;
    public void  Setsuccess(boolean success ){
        this.success = success;
    }
    public boolean  Getsuccess(){
        return  this.success;
    }
    //提示消息
    private String message ;
    public void  Setmessage(String message ){
        this.message = message;
    }
    public String  Getmessage(){
        return  this.message;
    }
    //响应业务数据
    private Object data ;
    public void  Setdata(Object data ){
        this.data = data;
    }
    public Object  Getdata(){
        return  this.data;
    }
    //Cookies
    private  Header[] cookies ;
    public void  SetCookies(Header[] cookies ){
        this.cookies = cookies;
    }
    public  Header[]  Getcookies(){
        return  this.cookies;
    }
}
