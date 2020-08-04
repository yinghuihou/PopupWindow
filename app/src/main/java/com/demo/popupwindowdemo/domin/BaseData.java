package com.demo.popupwindowdemo.domin;


import android.text.TextUtils;

/**
 * 抽象的基类数据类
 *
 * @author libo
 * @date 18/1/5
 */

public abstract class BaseData<T> {
    /**
     * 错误信息
     */
    public String errMsg;

    /**
     * 服务端下发的状态码
     * 默认成功
     */
    public int serverCode = 0;

    /**
     * 依附的host，一般指某个view
     * //TODO 不应该在此处，容易造成内存泄漏
     */
    public Object host;

    /**
     * 服务端返回的json格式的结果（一般在服务端报错，即severCode不为0时，对此赋值）
     * note: 可能为空
     */
    public String jsonError;

    /**
     * 此方法会将默认错误码设置为-1
     * 建议使用 {@link #errMsg(String, int)} 主动设置serverCode
     */
    @Deprecated
    public T errMsg(String errMsg) {
        this.errMsg = errMsg;
        this.serverCode = -1;
        return (T) this;
    }

    public T errMsg(String errMsg, int serverCode) {
        this.errMsg = errMsg;
        this.serverCode = serverCode;
        return (T) this;
    }

    public T errMsg(String errMsg, int serverCode, String jsonError) {
        this.errMsg = errMsg;
        this.serverCode = serverCode;
        this.jsonError = jsonError;
        return (T) this;
    }

    public T host(Object host) {
        this.host = host;
        return (T) this;
    }

    public boolean hasError() {
        return !TextUtils.isEmpty(errMsg);
    }
}
