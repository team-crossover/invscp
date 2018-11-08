package com.github.nelsonwilliam.invscp.exception;

/**
 * Exceção lançada quando não é possível efetuar uma atualização em uma
 * entidade.
 */
public class IllegalUpdateException extends CRUDException {

    private static final long serialVersionUID = -1863368590235532754L;

    public IllegalUpdateException(final String message) {
        super(message);
    }

}
