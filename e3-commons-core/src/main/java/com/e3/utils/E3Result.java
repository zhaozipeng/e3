package com.e3.utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/20.
 */
public class E3Result implements Serializable{

    public String status;
    public String msg;
    public Object data;

    public E3Result() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public E3Result(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public static E3Result ok(Object data){
        return new E3Result("200","ok",data);
    }
    public static E3Result bulid(String status, String msg, Object data){
        return new E3Result(status,msg,data);
    }

}
