package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.RelatorioDTO;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;

public class Relatorio {

    private Integer idDepartamento = null;

    private List<Integer> idBens = null;

    private LocalDateTime momentoGeracao = null;

    public void setValuesFromDTO(final RelatorioDTO dto) {
        setMomentoGeracao(dto.getMomentoGeracao());
        if (dto.getDepartamento() != null) {
            setIdDepartamento(dto.getDepartamento().getId());
        }
        if (dto.getBens() != null) {
            final List<Integer> idBens = new ArrayList<Integer>();
            for (final BemDTO bem : dto.getBens()) {
                idBens.add(bem.getId());
            }
            setIdBens(idBens);
        }
    }

    public RelatorioDTO toDTO() {
        final RelatorioDTO dto = new RelatorioDTO();
        dto.setMomentoGeracao(momentoGeracao);
        if (idDepartamento != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(idDepartamento);
            dto.setDepartamento(dept == null ? null : dept.toDTO());
        }
        if (idBens != null) {
            final List<BemDTO> bens = new ArrayList<BemDTO>();
            for (final Integer idBem : idBens) {
                final BemRepository repo = new BemRepository();
                final Bem bem = repo.getById(idBem);
                if (bem != null) {
                    bens.add(bem.toDTO());
                }
            }
            dto.setBens(bens);
        }
        return dto;
    }

    /**
     * Obtém o valor atual de idDepartamento.
     *
     * @return O valor atual de idDepartamento.
     */
    public final Integer getIdDepartamento() {
        return idDepartamento;
    }

    /**
     * Atualiza o valor atual de idDepartamento.
     *
     * @param newIdDepartamento O novo valor para idDepartamento.
     */
    public final void setIdDepartamento(final Integer newIdDepartamento) {
        idDepartamento = newIdDepartamento;
    }

    /**
     * Obtém o valor atual de idBens.
     *
     * @return O valor atual de idBens.
     */
    public final List<Integer> getIdBens() {
        return idBens;
    }

    /**
     * Atualiza o valor atual de idBens.
     *
     * @param newIdBens O novo valor para idBens.
     */
    public final void setIdBens(final List<Integer> newIdBens) {
        idBens = newIdBens;
    }

    /**
     * Obtém o valor atual de momentoGeracao.
     *
     * @return O valor atual de momentoGeracao.
     */
    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    /**
     * Atualiza o valor atual de momentoGeracao.
     *
     * @param newMomentoGeracao O novo valor para momentoGeracao.
     */
    public final void setMomentoGeracao(final LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

}
