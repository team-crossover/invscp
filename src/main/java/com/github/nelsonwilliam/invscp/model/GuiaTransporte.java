package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDateTime;

import com.github.nelsonwilliam.invscp.model.dto.GuiaTransporteDTO;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;

public class GuiaTransporte {

    private Integer idMovimentacao = null;

    private Integer idUsuario = null;

    private LocalDateTime momentoGeracao = null;

    public void setValuesFromDTO(final GuiaTransporteDTO dto) {
        setMomentoGeracao(dto.getMomentoGeracao());
        if (dto.getUsuario() != null) {
            setIdUsuario(dto.getUsuario().getId());
        }
        if (dto.getMovimentacao() != null) {
            setIdMovimentacao(dto.getMovimentacao().getId());
        }
    }

    public GuiaTransporteDTO toDTO() {
        final GuiaTransporteDTO dto = new GuiaTransporteDTO();
        dto.setMomentoGeracao(momentoGeracao);
        if (idMovimentacao != null) {
            final MovimentacaoRepository repo = new MovimentacaoRepository();
            final Movimentacao mov = repo.getById(idMovimentacao);
            if (mov != null) {
                dto.setMovimentacao(mov.toDTO());
            }
        }
        if (idUsuario != null) {
            final FuncionarioRepository repo = new FuncionarioRepository();
            final Funcionario func = repo.getById(idUsuario);
            if (func != null) {
                dto.setUsuario(func.toDTO());
            }
        }
        return dto;
    }

    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    public final void setMomentoGeracao(final LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

    /**
     * Obtém o valor atual de idMovimentacao
     *
     * @return O valor atual de idMovimentacao.
     */
    public final Integer getIdMovimentacao() {
        return idMovimentacao;
    }

    /**
     * Atualiza o valor atual de idMovimentacao.
     *
     * @param newIdMovimentacao O novo valor de idMovimentacao.
     */
    public final void setIdMovimentacao(final Integer newIdMovimentacao) {
        idMovimentacao = newIdMovimentacao;
    }

    /**
     * Obtém o valor atual de idUsuario
     *
     * @return O valor atual de idUsuario.
     */
    public final Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Atualiza o valor atual de idUsuario.
     *
     * @param newIdUsuario O novo valor de idUsuario.
     */
    public final void setIdUsuario(final Integer newIdUsuario) {
        idUsuario = newIdUsuario;
    }

}
