package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.GrupoMaterialRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Bem implements Model<BemDTO> {

    private static final long serialVersionUID = 1684907497812931878L;

    private Integer id = null;

    private String descricao = null;

    private Long numeroTombamento = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataAquisicao = null;

    private String numeroNotaFiscal = null;

    private String especificacao = null;

    private String garantia = null;

    private String marca = null;

    private Float valorCompra = null;

    private String situacao = null;

    private Integer idGrupoMaterial = null;

    private Integer idSala = null;

    private Integer idDepartamento = null;

    @Override
    public void setValuesFromDTO(BemDTO dto) {
        setDataAquisicao(dto.getDataAquisicao());
        setDataCadastro(dto.getDataCadastro());
        setDescricao(dto.getDescricao());
        setEspecificacao(dto.getEspecificacao());
        setGarantia(dto.getGarantia());
        setId(dto.getId());
        if (dto.getDepartamento() != null) {
            setIdDepartamento(dto.getDepartamento().getId());
        }
        if (dto.getGrupoMaterial() != null) {
            setIdGrupoMaterial(dto.getGrupoMaterial().getId());
        }
        if (dto.getSala() != null) {
            setIdSala(dto.getSala().getId());
        }
        setMarca(dto.getMarca());
        setNumeroNotaFiscal(dto.getNumeroNotaFiscal());
        setNumeroTombamento(dto.getNumeroTombamento());
        setSituacao(dto.getSituacao());
        setValorCompra(dto.getValorCompra());

    }

    @Override
    public BemDTO toDTO() {
        final BemDTO dto = new BemDTO();
        dto.setDataAquisicao(dataAquisicao);
        dto.setDataCadastro(dataCadastro);
        dto.setDescricao(descricao);
        dto.setEspecificacao(especificacao);
        dto.setGarantia(garantia);
        dto.setId(id);
        dto.setMarca(marca);
        dto.setNumeroNotaFiscal(numeroNotaFiscal);
        dto.setNumeroTombamento(numeroTombamento);
        dto.setSituacao(situacao);
        dto.setValorCompra(valorCompra);
        if (idDepartamento != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(idDepartamento);
            dto.setDepartamento(dept == null ? null : dept.toDTO());
        }
        if (idSala != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala sala = repo.getById(idSala);
            dto.setSala(sala == null ? null : sala.toDTO());
        }
        if (idGrupoMaterial != null) {
            final GrupoMaterialRepository repo = new GrupoMaterialRepository();
            final GrupoMaterial gm = repo.getById(idSala);
            dto.setGrupoMaterial(gm == null ? null : gm.toDTO());
        }
        return dto;
    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getDescricao() {
        return descricao;
    }

    public final void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public final Long getNumeroTombamento() {
        return numeroTombamento;
    }

    public final void setNumeroTombamento(Long numeroTombamento) {
        this.numeroTombamento = numeroTombamento;
    }

    public final LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public final void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public final LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public final void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public final String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public final void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public final String getEspecificacao() {
        return especificacao;
    }

    public final void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public final String getGarantia() {
        return garantia;
    }

    public final void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public final String getMarca() {
        return marca;
    }

    public final void setMarca(String marca) {
        this.marca = marca;
    }

    public final Float getValorCompra() {
        return valorCompra;
    }

    public final void setValorCompra(Float valorCompra) {
        this.valorCompra = valorCompra;
    }

    public final String getSituacao() {
        return situacao;
    }

    public final void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public final Integer getIdGrupoMaterial() {
        return idGrupoMaterial;
    }

    public final void setIdGrupoMaterial(Integer idGrupoMaterial) {
        this.idGrupoMaterial = idGrupoMaterial;
    }

    public final Integer getIdSala() {
        return idSala;
    }

    public final void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public final Integer getIdDepartamento() {
        return idDepartamento;
    }

    public final void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

}
