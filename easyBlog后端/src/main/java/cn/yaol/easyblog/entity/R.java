package cn.yaol.easyblog.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yaol
 * @description: 相应数据类型
 * @date 2023/8/31 13:00
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 200);
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
