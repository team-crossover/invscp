package com.github.nelsonwilliam.invscp.shared.exception;

/**
 * Exceção lançada quando não é possível efetuar uma inserção de uma nova
 * entidade.
 */
public class IllegalInsertException extends CRUDException {

    private static final long serialVersionUID = -2763368590235532754L;

    public IllegalInsertException(final String message) {
        super(message);
    }

}
