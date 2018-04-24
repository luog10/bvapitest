package apiutility;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonHelper {
    // 从一个对象信息生成Json串
    public static <T> String ObjectToJson(T t) {
        // 使用new方法
        Gson gson = new Gson();
        // toJson 将bean对象转换为json字符串
        return gson.toJson(t);
    }

    /// <summary>
    /// Json 反序列化
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <param name="jsonString"></param>
    /// <returns></returns>
    public static <T> T JsonDeserialize(String jsonString,T t){
        // 使用new方法
        Gson gson = new Gson();
        // 反序列化
        return gson.fromJson(jsonString,(Type)t);
    }
}
