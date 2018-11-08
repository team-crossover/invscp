package com.github.nelsonwilliam.invscp.model.enums;

public enum CargoEnum {
    INTERESSADO(1, "Interessado"),
    FUNCIONARIO(2, "Funcionário"),
    CHEFE_PATRIMONIO(3, "Chefe de patrimônio"),
    CHEFE_PATRIMONIO_SUBST(4, "Chefe de patrimônio (subst.)"),
    CHEFE_DEPT(5, "Chefe de departamento"),
    CHEFE_DEPT_SUBST(6, "Chefe de departamento (subst.)");

    private final int valor;
    private final String texto;

    CargoEnum(final int valorOpcao, final String textoOpcao) {
        valor = valorOpcao;
        texto = textoOpcao;
    }

    public static TipoSalaEnum valueOfTexto(final String texto) {
        final TipoSalaEnum[] values = TipoSalaEnum.values();
        for (final TipoSalaEnum value : values) {
            if (value.toString().equals(texto)) {
                return value;
            }
        }
        return null;
    }

    public int getValor() {
        return valor;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isChefe() {
        return isChefeDePatrimonio() || isChefeDeDepartamento();
    }

    public boolean isChefePrincipal() {
        return this == CHEFE_PATRIMONIO || this == CHEFE_DEPT;
    }

    public boolean isChefeSubstituto() {
        return this == CHEFE_PATRIMONIO_SUBST || this == CHEFE_DEPT_SUBST;
    }

    public boolean isChefeDePatrimonio() {
        return this == CHEFE_PATRIMONIO || this == CHEFE_PATRIMONIO_SUBST;
    }

    public boolean isChefeDeDepartamento() {
        return this == CHEFE_DEPT || this == CHEFE_DEPT_SUBST;
    }

}
