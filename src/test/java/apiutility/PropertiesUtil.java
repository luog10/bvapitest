package apiutility;

import java.io.*;
import java.util.Properties;

/**
 * 配置获取工具类
 * */
public class PropertiesUtil {
    public static String getValue(String ConfFilePath,String key){
        String resultvalue = null;
        Properties prop = new Properties();
        try {
            Object cacheInfo = CacheTool.getCache(key);
            if(cacheInfo != null) {
                resultvalue = cacheInfo.toString();
            }
            else {
                //装载配置文件
                prop.load(new FileReader(ConfFilePath));
                resultvalue = prop.getProperty(key);
                if(CommonTool.IsNullOrEmpty(resultvalue)){
                    CommonTool.ThrowNewException(String.format("请在配置文件中配置->%s!",key));
                }
                //设置缓存
                CacheTool.SetCache(key,resultvalue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回获取的值
        return resultvalue;
    }
}
