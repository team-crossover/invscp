package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;

public class Baixa implements Model<BaixaDTO> {

    private static final long serialVersionUID = 3942513871242698053L;

    private Integer id = null;

    private LocalDate data = null;

    private String motivo = null;

    private String observacoes = null;

    private Integer idFuncionario = null;

    private Integer idBem = null;

    @Override
    public void setValuesFromDTO(BaixaDTO dto) {
        setData(dto.getData());
        setId(dto.getId());
        setMotivo(dto.getMotivo());
        setObservacoes(dto.getObservacoes());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getFuncionario() != null) {
            setIdFuncionario(dto.getFuncionario().getId());
        }
    }

    @Override
    public BaixaDTO toDTO() {
        final BaixaDTO dto = new BaixaDTO();
        dto.setData(data);
        dto.setId(id);
        dto.setMotivo(motivo);
        dto.setObservacoes(observacoes);
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
