package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.enums.OSsituacaoEnum;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;

public class OrdemServico implements Model<OrdemServicoDTO> {

    private static final long serialVersionUID = 5301908503322503604L;

    private Integer id = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataConclusao = null;

    private Float valor = null;

    private OSsituacaoEnum situacao = null;

    private Integer idFuncionario = null;

    private Integer idBem = null;

    @Override
    public void setValuesFromDTO(OrdemServicoDTO dto) {
        setDataCadastro(dto.getDataCadastro());
        setDataConclusao(dto.getDataConclusao());
        setId(dto.getId());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getFuncionario() != null) {
            setIdFuncionario(dto.getFuncionario().getId());
        }
        setSituacao(dto.getSituacao());
        setValor(dto.getValor());

    }

    @Override
    public OrdemServicoDTO toDTO() {
        OrdemServicoDTO dto = new OrdemServicoDTO();
        dto.setDataCadastro(dataCadastro);
        dto.setDataConclusao(dataConclusao);
        dto.setId(id);
        dto.setValor(valor);
        if (idFuncionario != null) {
            final FuncionarioRepository repo = new FuncionarioRepository();
            final Funcionario func = repo.getById(idFuncionario);
            func.setIdDepartamento(null);
            dto.setFuncionario(func == null ? null : func.toDTO());
        }
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idFuncionario);
            bem.setIdDepartamento(null);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        return dto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public final OSsituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(OSsituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public final String getSituacaoString() {
    	return this.situacao.toString();
    }

    public final void setSituacaoString(final String sit) {
    	this.situacao = OSsituacaoEnum.valueOf(sit);
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(Integer idBem) {
        this.idBem = idBem;
    }

}
