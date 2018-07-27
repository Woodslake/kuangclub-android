package com.kuangclub.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by Woodslake on 2018/1/30.
 */

public class CookieStore {
    private static final Map<String, ConcurrentHashMap<String, Cookie>> cookies = new HashMap<>();

    public void add(HttpUrl url, Cookie cookie){
        String name = getCookieToken(cookie);

        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(url.host())) {
                cookies.put(url.host(), new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(url.host()).put(name, cookie);
        } else {
            if (cookies.containsKey(url.host())) {
                cookies.get(url.host()).remove(name);
            }
        }
    }

    public List<Cookie> get(HttpUrl url){
        ArrayList<Cookie> ret = new ArrayList<>();
        if (cookies.containsKey(url.host())){
            ret.addAll(cookies.get(url.host()).values());
        }
        return ret;
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

}
