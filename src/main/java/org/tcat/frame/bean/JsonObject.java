package org.tcat.frame.bean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.tcat.frame.enums.MultiLanguage;
import org.tcat.frame.exception.code.CodeMsg;
import org.tcat.frame.exception.code.ErrorCode;

import java.io.Serializable;

/**
 * JSON类
 *
 * @author shadow
 */
@ApiModel(value = "jsonObject", description = "结果对象类")
public final class JsonObject<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 7458959932409467410L;
    public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    /**
     * 操作响应码
     **/
    @ApiModelProperty(value = "操作响应码")
    private String code = ErrorCode.success;

    /**
     * 返回的消息，暂时留用
     **/
    @ApiModelProperty(value = "返回的消息")
    private String msg; // 返回的消息

    /**
     * 需要处理的数据对象
     **/
    @ApiModelProperty(value = "结果数据")
    private T data;

    private JsonObject() {
    }

    private JsonObject(T data) {
        this.data = data;
    }

    public JsonObject(String code, String msg) {
        this.code = code;

        if (!ErrorCode.success.equals(code) && msg == null) {
            this.msg = CodeMsg.getMsg(MultiLanguage.en, code);
        } else {
            this.msg = msg;
        }
    }

    public JsonObject(String code, String msg, T data) {
        this.code = code;
        this.data = data;

        if (!ErrorCode.success.equals(code) && msg == null) {
            this.msg = CodeMsg.getMsg(MultiLanguage.en, code);
        } else {
            this.msg = msg;
        }
    }

    public static <T extends Serializable> JsonObject<T> ok() {
        return new JsonObject<>();
    }

    public static <T extends Serializable> JsonObject<T> ok(T data) {
        return new JsonObject<>(data);
    }

    public static <T extends Serializable> JsonObject<T> error(String code) {
        return new JsonObject<>(code, null);
    }

    public static <T extends Serializable> JsonObject<T> error(String code, String msg) {
        return new JsonObject<>(code, msg);
    }

    public static <T extends Serializable> JsonObject<T> error(String code, String msg, T data) {
        return new JsonObject<>(code, msg, data);
    }

    public String getCode() {
        return code;
    }

    public JsonObject setCode(String code) {
        if (!ErrorCode.success.equals(code)) {
            this.setMsg(CodeMsg.getMsg(MultiLanguage.en, code));
        }
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public JsonObject setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonObject setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

}
