package org.cloud.blog.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
public class Response {
    private String msg;
    private Integer code;
    private Boolean success;
    private Object data;

    private Response(){

    }

    public Response setMsg(String msg){
        this.msg = msg;
        return this;
    }

    public Response setCode(Integer code){
        this.code = code;
        return this;
    }
    public static Response ok(Object data){
        return new Response("请求成功", 200, true, data);
    }

    public static Response error(){
        return new Response("请求失败", 500, false, null);
    }
}
