package com.tgxt.result;

import lombok.Data;


//统一返回结果
@Data
public class Result <T>{

    private Integer code;//状态码

    private String message;//返回状态信息（成功/失败）

    private T data;//返回数据

    public Result(){}

    public static <T> Result<T> build(T data, Integer code, String message) {
        Result<T> result = new Result<T>();
        if (data != null) {
            result.setData(data);
        }
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 设置默认返回结果(操作成功)
     */
    public static <T> Result<T> ok(T data){
        return build(data,20000,"成功！");
    }

    /**
     * 设置默认返回结果(操作失败)
     */
    public static <T> Result<T> fail(T data){
        return build(data,20001,"失败！");
    }

    public static <T> Result<T> loginUserNameFail(T data){
        return build(data,20001,"用户不存在！");
    }
    public static <T> Result<T> loginPasswordFail(T data){
        return build(data,20001,"密码不正确！");
    }
    public static <T> Result<T> loginRolesFail(T data){
        return build(data,20001,"你不是管理员或教师禁止登录！");
    }

    /**
     * 自定义返回结果
     * @param msg
     * @return
     */
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
