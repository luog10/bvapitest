package apiutility;

import java.util.concurrent.ConcurrentHashMap;

public class CacheTool {

    private static ConcurrentHashMap<String, Object> cacheMap = new ConcurrentHashMap<String, Object> ();

    /**
     * 获取缓存的对象
     *
     * @param Key
     * @return
     */
    public static Object getCache(String Key) {
        // 如果缓冲中有该账号，则返回value
        return cacheMap.get(Key);
    }

    /**
     * 初始化缓存
     *
     * @param Key，Value
     */
    public static void SetCache(String Key, Object Value) {
        // 一般是进行数据库查询，将查询的结果进行缓存
        cacheMap.put(Key, Value);
    }

    /**
     * 移除缓存信息
     *
     * @param Key
     */
    public static void removeCache(String Key) {
        cacheMap.remove(Key);
    }
}