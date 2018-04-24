package apiutility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonTool {
    //获取当前时间字符串
    public static String GetCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    //判断字符串是NullOrEmpty
    public static boolean IsNullOrEmpty(String string) {
        if(string == null ||  string == "" || string.length() <=0){
            return  true;
        }
        return false;
    }

    //抛出业务异常
    public static void ThrowNewException(String ExceptionMsg){
        try {
            throw new Exception(ExceptionMsg);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
