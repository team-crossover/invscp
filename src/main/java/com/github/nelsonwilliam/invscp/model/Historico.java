package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.dto.HistoricoDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;

public class Historico {

    private Integer idBem = null;

    private List<Integer> idMovimentacoes = null;

    private List<Integer> idOrdens = null;

    private Integer idBaixa = null;

    private LocalDateTime momentoGeracao = null;

    public void setValuesFromDTO(final HistoricoDTO dto) {
        setMomentoGeracao(dto.getMomentoGeracao());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getMovimentacoes() != null) {
            final List<Integer> idMovimentacoes = new ArrayList<Integer>();
            for (final MovimentacaoDTO mov : dto.getMovimentacoes()) {
                idMovimentacoes.add(mov.getId());
            }
            setIdMovimentacoes(idMovimentacoes);
        }
        if (dto.getOrdens() != null) {
            final List<Integer> idOrdens = new ArrayList<Integer>();
            for (final OrdemServicoDTO ord : dto.getOrdens()) {
                idOrdens.add(ord.getId());
            }
            setIdOrdens(idOrdens);
        }
        if (dto.getBaixa() != null) {
            setIdBaixa(dto.getBaixa().getId());
        }
    }

    public HistoricoDTO toDTO() {
        final HistoricoDTO dto = new HistoricoDTO();
        dto.setMomentoGeracao(momentoGeracao);
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        if (idMovimentacoes != null) {
            final List<MovimentacaoDTO> movimentacoes =
                    new ArrayList<MovimentacaoDTO>();
            for (final Integer idMov : idMovimentacoes) {
                final MovimentacaoRepository repo =
                        new MovimentacaoRepository();
                final Movimentacao mov = repo.getById(idMov);
                if (mov != null) {
                    movimentacoes.add(mov.toDTO());
                }
            }
            dto.setMovimentacoes(movimentacoes);
        }
        if (idOrdens != null) {
            final List<OrdemServicoDTO> ordens =
                    new ArrayList<OrdemServicoDTO>();
            for (final Integer idOrdem : idOrdens) {
                final OrdemServicoRepository repo =
                        new OrdemServicoRepository();
                final OrdemServico ord = repo.getById(idOrdem);
                if (ord != null) {
                    ordens.add(ord.toDTO());
                }
            }
            dto.setOrdens(ordens);
        }
        if (idBaixa != null) {
            final BaixaRepository repo = new BaixaRepository();
            final Baixa baixa = repo.getById(idBaixa);
            dto.setBaixa(baixa == null ? null : baixa.toDTO());
        }
        return dto;
    }

    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    public final void setMomentoGeracao(LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

    /**
     * Obtém o valor atual de idBem.
     *
     * @return O valor atual de idBem.
     */
    public final Integer getIdBem() {
        return idBem;
    }

    /**
     * Atualiza o valor atual de idBem.
     *
     * @param newIdBem O novo valor para idBem.
     */
    public final void setIdBem(final Integer newIdBem) {
        idBem = newIdBem;
    }

    /**
     * Obtém o valor atual de idMovimentacoes.
     *
     * @return O valor atual de idMovimentacoes.
     */
    public final List<Integer> getIdMovimentacoes() {
        return idMovimentacoes;
    }

    /**
     * Atualiza o valor atual de idMovimentacoes.
     *
     * @param newIdMovimentacoes O novo valor para idMovimentacoes.
     */
    public final void setIdMovimentacoes(
            final List<Integer> newIdMovimentacoes) {
        idMovimentacoes = newIdMovimentacoes;
    }

    /**
     * Obtém o valor atual de idOrdens.
     *
     * @return O valor atual de idOrdens.
     */
    public final List<Integer> getIdOrdens() {
        return idOrdens;
    }

    /**
     * Atualiza o valor atual de idOrdens.
     *
     * @param newIdOrdens O novo valor para idOrdens.
     */
    public final void setIdOrdens(final List<Integer> newIdOrdens) {
        idOrdens = newIdOrdens;
    }

    /**
     * Obtém o valor atual de idBaixa.
     *
     * @return O valor atual de idBaixa.
     */
    public final Integer getIdBaixa() {
        return idBaixa;
    }

    /**
     * Atualiza o valor atual de idBaixa.
     *
     * @param newIdBaixa O novo valor para idBaixa.
     */
    public final void setIdBaixa(final Integer newIdBaixa) {
        idBaixa = newIdBaixa;
    }

}
