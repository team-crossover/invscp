package com.github.nelsonwilliam.invscp.shared.util;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 7365117466674133190L;

    private final String EXCEPTION_FLAG = "$EXCEPTION$";

    private String message;

    private Object[] objs;

    public Response() {
        super();
        message = null;
        objs = null;
    }

    public Response(final Exception exception) {
        super();
        message = EXCEPTION_FLAG;
        objs = new Object[] { exception };
    }

    public Response(final Object... newObjs) {
        super();
        message = null;
        objs = newObjs;
    }

    public boolean isException() {
        return message != null && message.equals(EXCEPTION_FLAG);
    }

    /**
     * Obtém o valor atual de message
     *
     * @return O valor atual de message.
     */
    public final String getMessage() {
        return message;
    }

    /**
     * Atualiza o valor atual de message.
     *
     * @param newMessage O novo valor de message.
     */
    public final void setMessage(final String newMessage) {
        message = newMessage;
    }

    /**
     * Obtém o valor atual de objs
     *
     * @return O valor atual de objs.
     */
    public final Object[] getObjs() {
        return objs;
    }

    /**
     * Atualiza o valor atual de objs.
     *
     * @param newObjs O novo valor de objs.
     */
    public final void setObjs(final Object[] newObjs) {
        objs = newObjs;
    }

}
