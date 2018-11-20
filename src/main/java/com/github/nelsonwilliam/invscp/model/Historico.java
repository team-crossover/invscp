package com.github.nelsonwilliam.invscp.model;

import com.github.nelsonwilliam.invscp.model.dto.HistoricoDTO;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;

public class Historico implements Model<HistoricoDTO> {

    private static final long serialVersionUID = -4789606265418026534L;

    private Integer id = null;

    private Integer idBem = null;

    private Integer idMovimentacao = null;
 
    private Integer idOrdem = null;

    private Integer idBaixa = null;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        this.id = integer;
    }

    @Override
    public void setValuesFromDTO(HistoricoDTO dto) {
        setId(dto.getId());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getMovimentacao() != null) {
            setIdMovimentacao(dto.getMovimentacao().getId());
        }
        if (dto.getOrdem() != null) {
            setIdOrdem(dto.getOrdem().getId());
        }
        if (dto.getBaixa() != null) {
            setIdBaixa(dto.getBaixa().getId());
        }
    }

    @Override
    public HistoricoDTO toDTO() {
        final HistoricoDTO dto = new HistoricoDTO();
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        if (idMovimentacao != null) {
            final MovimentacaoRepository repo = new MovimentacaoRepository();
            final Movimentacao mov = repo.getById(idMovimentacao);
            dto.setMovimentacao(mov == null ? null : mov.toDTO());
        }
        if (idOrdem != null) {
            final OrdemServicoRepository repo = new OrdemServicoRepository();
            final OrdemServico ordem = repo.getById(idOrdem);
            dto.setOrdem(ordem == null ? null : ordem.toDTO());
        }
        if (idBaixa != null) {
            final BaixaRepository repo = new BaixaRepository();
            final Baixa baixa = repo.getById(idBaixa);
            dto.setBaixa(baixa == null ? null : baixa.toDTO());
        }
        return dto;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(Integer idBem) {
        this.idBem = idBem;
    }

    public Integer getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(Integer idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Integer getIdOrdem() {
        return idOrdem;
    }

    public void setIdOrdem(Integer idOrdem) {
        this.idOrdem = idOrdem;
    }

    public Integer getIdBaixa() {
        return idBaixa;
    }

    public void setIdBaixa(Integer idBaixa) {
        this.idBaixa = idBaixa;
    }

}
