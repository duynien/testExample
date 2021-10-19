package com.java.demo_ttcscn.enitities.result;

import java.io.Serializable;

public class Response implements Serializable {
    private boolean status = true;
    private Object content;

    public Response() {}

    public Response(boolean status, Object content) {
        this.status = status;
        this.content = content;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

