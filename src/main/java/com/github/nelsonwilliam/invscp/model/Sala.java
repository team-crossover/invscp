package com.github.nelsonwilliam.invscp.model;

public class Sala implements Model {

    private static final long serialVersionUID = 509047893405447267L;

    private Integer id = null;

    private String nome = "Sala";

    private boolean deDeposito = false;

    private SalasEnum tipoSala;

    private String tipo;

    private Integer idPredio = null;

    private Integer idDepartamento = null;

    public Integer getIdPredio() {
        return idPredio;
    }

    public void setIdPredio(Integer idPredio) {
        this.idPredio = idPredio;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public SalasEnum getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(SalasEnum tipoSala) {
        this.tipoSala = tipoSala;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDeDeposito() {
        return deDeposito;
    }

    public void setDeDeposito(boolean deDeposito) {
        this.deDeposito = deDeposito;
    }

    public String getTipo() {
	return tipo;
    }

    public void setTipo(String tipo) {
	this.tipo = tipo;
    }

}
