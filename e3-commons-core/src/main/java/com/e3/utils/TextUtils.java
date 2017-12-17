package com.e3.utils;

/**
 * Created by zhiyuan on 2017/11/24.
 */
public class TextUtils {
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(str!=null&&!"".equals(str)){
            return false;
        }
        return true;
    }
}
