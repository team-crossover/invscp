package com.github.nelsonwilliam.invscp.exception;

/**
 * Exceção lançada quando não é possível efetuar uma exclusão de uma nova
 * entidade.
 */
public class IllegalDeleteException extends IllegalArgumentException {

    private static final long serialVersionUID = -2763368590235532754L;

    public IllegalDeleteException(final String message) {
        super(message);
    }

}
