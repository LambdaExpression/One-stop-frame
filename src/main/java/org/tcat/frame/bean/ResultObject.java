package org.tcat.frame.bean;

import java.io.Serializable;

/**
 * 结果对象
 * Created by Lin on 2017/8/4.
 */
public class ResultObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作结果
     */
    private boolean result = true;
    /**
     * 返回的消息
     */
    private String message;

    /**
     * 操作结果
     *
     * @return 操作结果
     */
    public boolean getResult() {
        return result;
    }

    /**
     * 操作结果
     *
     * @param result 操作结果
     * @return this
     */
    public ResultObject setResult(boolean result) {
        this.result = result;
        return this;
    }

    /**
     * 返回的消息
     *
     * @return 返回的消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 返回的消息
     *
     * @param message 返回的消息
     * @return this
     */
    public ResultObject setMessage(String message) {
        this.message = message;
        return this;
    }
}
