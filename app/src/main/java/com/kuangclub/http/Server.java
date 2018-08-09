package com.kuangclub.http;

import com.kuangclub.BuildConfig;

/**
 * Created by Woodslake on 2018/1/17.
 */

public class Server {
    public static String ApiHost = null;

    private static final String ApiHostDevelop = "http://192.168.1.6:8080/";
    private static final String ApiHostMaintain = "http://192.168.1.6:8080/";
    private static final String ApiHostProduce = "http://192.168.1.6:8080/";

    private static final String DEBUG = "DEBUG";        //debug
    private static final String DEVELOP = "DEVELOP";    //开发
    private static final String MAINTAIN = "MAINTAIN";  //运维
    private static final String PRODUCE = "RELEASE";    //生产

    private static final String ENVIRONMENT = BuildConfig.ENVIRONMENT;

    static {
        switch (ENVIRONMENT){
            case DEBUG:
                ApiHost = ApiHostDevelop;
                break;
            case DEVELOP:
                ApiHost = ApiHostDevelop;
                break;
            case MAINTAIN:
                ApiHost = ApiHostMaintain;
                break;
            case PRODUCE:
                ApiHost = ApiHostProduce;
                break;
        }
    }

}
