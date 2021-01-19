package demo.demo.infra.utils.common;

import java.io.Serializable;

/**
 * @Author: Mo Zhipeng
 * @Description:
 * @Date: 2019/8/8 21:05
 * @Modified: Last modified by
 */
public class WebResponse<T> extends AbstractResponseStatus implements Serializable {

    // 响应主题
    private T body;

    // 提示消息
    private String message;

    public static WebResponse<?> create() {
        return new WebResponse<>();
    }

    public static <T> WebResponse<?> create(T body) {
        return new WebResponse<>(body);
    }

    public WebResponse() {
    }

    private WebResponse(T body) {
        this.body = body;
    }

    /**
     * 修改 body
     *
     * @param body 主体内容
     * @return this
     */
    public WebResponse body(T body) {
        this.setBody(body);
        return this;
    }

    /**
     * 修改 消息
     *
     * @param message 提示消息
     * @return this
     */
    public WebResponse message(String message) {
        this.setMessage(message);
        return this;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
