package com.crazyshopping.common;

import com.crazyshopping.app.MyApp;

import java.io.File;

public class Constans {
    public static final String Base_Url = "http://cdwan.cn/api/";

    //网络缓存地址
    public static final String PATH_DATA = MyApp.app.getCacheDir().getPath()+ File.separator+"data";
    public static final String PATH_CACHE = PATH_DATA + "/shop";

}
