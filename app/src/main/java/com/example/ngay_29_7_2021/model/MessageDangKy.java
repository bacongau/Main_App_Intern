package com.example.ngay_29_7_2021.model;

public class MessageDangKy {
    private int code;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public MessageDangKy(int code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public MessageDangKy() {
    }

    @Override
    public String toString() {
        return "MessageDangKy{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
