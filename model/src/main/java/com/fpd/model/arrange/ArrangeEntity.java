package com.fpd.model.arrange;

import java.io.Serializable;

/**
 * Created by t450s on 2016/6/4.
 */
public class ArrangeEntity implements Serializable {
    private boolean success;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
