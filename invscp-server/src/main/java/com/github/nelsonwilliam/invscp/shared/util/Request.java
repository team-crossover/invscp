package com.github.nelsonwilliam.invscp.shared.util;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = -3401304596470401234L;

    private RequestTypeEnum tipo;

    private Object[] args;

    public Request(final RequestTypeEnum newTipo, final Object... newArgs) {
        super();
        tipo = newTipo;
        args = newArgs;
    }

    public RequestTypeEnum getTipo() {
        return tipo;
    }

    public void setTipo(final RequestTypeEnum newTipo) {
        tipo = newTipo;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(final Object[] newArgs) {
        args = newArgs;
    }

}
