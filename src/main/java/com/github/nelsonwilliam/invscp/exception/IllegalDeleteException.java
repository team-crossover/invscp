package com.github.nelsonwilliam.invscp.exception;

/**
 * Exceção lançada quando não é possível efetuar uma exclusão de uma nova
 * entidade.
 */
public class IllegalDeleteException extends CRUDException {

    private static final long serialVersionUID = -2763368590235532754L;

    public IllegalDeleteException(final String message) {
        super(message);
    }

}
