package com.model;

public class Result<T> {
    private int code;
    private String msg;
    private  T  data;

    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm==null)return;
        this.code=cm.getCode();
        this.msg=cm.getMsg();
    }

    public static <T>Result<T> success(T data){
        Result<T> result=new Result<T>(0,"sucess",data);
        return  result;
    }
    public static <T>Result<T> error(CodeMsg cm){
        Result<T> result=new Result<T>(cm);
        return  result;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
