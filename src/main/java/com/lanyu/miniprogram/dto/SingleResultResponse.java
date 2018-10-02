package com.lanyu.miniprogram.dto;

/**
 * @author i343746
 */
public class SingleResultResponse {
    Object result;

    public SingleResultResponse(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
