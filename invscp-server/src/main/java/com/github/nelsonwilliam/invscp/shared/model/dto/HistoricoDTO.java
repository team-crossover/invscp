package com.github.nelsonwilliam.invscp.shared.model.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.nelsonwilliam.invscp.shared.util.Relatorios;
import com.github.nelsonwilliam.invscp.shared.util.Resources;

public class HistoricoDTO implements DTO {

    private static final long serialVersionUID = 4647504078114571526L;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final NumberFormat CURRENCY_FORMATTER =
            NumberFormat.getCurrencyInstance();

    private static final NumberFormat PERCENT_FORMATTER =
            NumberFormat.getPercentInstance();

    private BemDTO bem = null;

    private List<MovimentacaoDTO> movimentacoes = null;

    private List<OrdemServicoDTO> ordens = null;

    private BaixaDTO baixa = null;

    private LocalDateTime momentoGeracao = null;

    public String toHtml() throws IOException {
        final String tmpHistorico =
                Resources.readResource("html/templates/historico.html");
        final String tmpBaixa =
                Resources.readResource("html/templates/historico-baixa.html");
        final String tmpDepreciacao = Resources
                .readResource("html/templates/historico-depreciacao.html");
        final String tmpDepreciacaoItem = Resources
                .readResource("html/templates/historico-depreciacao-item.html");
        final String tmpInformacoes = Resources
                .readResource("html/templates/historico-info-bem.html");
        final String tmpMovimentacao = Resources
                .readResource("html/templates/historico-movimentacao.html");
        final String tmpMovimentacaoEvento = Resources.readResource(
                "html/templates/historico-movimentacao-evento.html");
        final String tmpOrdens =
                Resources.readResource("html/templates/historico-os.html");
        final String tmpOrdensItens =
                Resources.readResource("html/templates/historico-os-item.html");

        final String htmlInfo = String.format(tmpInformacoes,
                Relatorios.escapeToHtml(bem.getDescricao()),
                CURRENCY_FORMATTER.format(bem.getValorCompra()),
                bem.getNumeroTombamento(),
                Relatorios.escapeToHtml(bem.getNumeroNotaFiscal()),
                Relatorios.escapeToHtml(bem.getEspecificacao()),
                bem.getGarantia().format(DATE_FORMATTER),
                Relatorios.escapeToHtml(bem.getMarca()),
                Relatorios.escapeToHtml(bem.getGrupoMaterial().getNome()),
                Relatorios.escapeToHtml(bem.getSala().getNome()),
                Relatorios.escapeToHtml(bem.getDepartamento().getNome()),
                bem.getDataCadastro().format(DATE_FORMATTER),
                bem.getDataAquisicao().format(DATE_FORMATTER),
                bem.getSituacao().getTexto());

        final String htmlBaixa = baixa == null
                ? "Não foi efetuada baixa deste bem."
                : String.format(tmpBaixa,
                        baixa.getData().format(DATE_FORMATTER),
                        Relatorios.escapeToHtml(baixa.getMotivo().getTexto()),
                        Relatorios
                                .escapeToHtml(baixa.getFuncionario().getNome()),
                        Relatorios.escapeToHtml(baixa.getObservacoes()));

        final StringBuilder htmlDepreciacoesItens = new StringBuilder();
        final int anoAtual = momentoGeracao.getYear();
        final BigDecimal[] depreciacoes = bem.getDepreciacoes();
        for (int i = 0; i < depreciacoes.length; i++) {
            final Integer ano = anoAtual - depreciacoes.length + 1 + i;
            htmlDepreciacoesItens.append(String.format(tmpDepreciacaoItem, ano,
                    CURRENCY_FORMATTER.format(depreciacoes[i])));
        }

        final String htmlDepreciacoes = String.format(tmpDepreciacao,
                htmlDepreciacoesItens.toString(), PERCENT_FORMATTER
                        .format(bem.getGrupoMaterial().getDepreciacao()));

        final StringBuilder htmlOrdensItens = new StringBuilder();
        for (final OrdemServicoDTO ordem : ordens) {
            htmlOrdensItens.append(String.format(tmpOrdensItens,
                    ordem.getDataCadastro().format(DATE_FORMATTER),
                    ordem.getDataConclusao() == null ? "-"
                            : ordem.getDataConclusao().format(DATE_FORMATTER),
                    CURRENCY_FORMATTER.format(ordem.getValor()),
                    Relatorios.escapeToHtml(ordem.getFuncionario().getNome()),
                    Relatorios.escapeToHtml(ordem.getSituacao().getTexto())));
        }
        final String htmlOrdens = ordens.size() > 0
                ? String.format(tmpOrdens, htmlOrdensItens.toString())
                : "Não existem ordens de serviço para este bem.";

        final StringBuilder htmlMovimentacoesItens = new StringBuilder();
        for (final MovimentacaoDTO mov : movimentacoes) {

            final StringBuilder htmlEventos = new StringBuilder();
            for (final EventoMovimentacaoDTO ev : mov.getEventos()) {
                htmlEventos.append(String.format(tmpMovimentacaoEvento,
                        ev.getTipo().getTexto(),
                        ev.getData().format(DATE_FORMATTER),
                        Relatorios.escapeToHtml(ev.getFuncionario().getNome()),
                        Relatorios.escapeToHtml(ev.getJustificativa())));
            }

            htmlMovimentacoesItens.append(String.format(tmpMovimentacao,
                    Relatorios.escapeToHtml(mov.getSalaOrigem().getNome()),
                    Relatorios.escapeToHtml(mov.getSalaDestino().getNome()),
                    mov.getEtapa().getTexto(), htmlEventos.toString()));
        }
        final String htmlMovimentacoes =
                movimentacoes.size() > 0 ? htmlMovimentacoesItens.toString()
                        : "Não foram efetuadas movimentações deste item.";

        return String.format(tmpHistorico,
                Relatorios.escapeToHtml(bem.getDescricao()),
                momentoGeracao.format(DATE_TIME_FORMATTER), htmlInfo, htmlBaixa,
                htmlDepreciacoes, htmlOrdens, htmlMovimentacoes);
    }

    /**
     * Obtém o valor atual de bem.
     *
     * @return O valor atual de bem.
     */
    public final BemDTO getBem() {
        return bem;
    }

    /**
     * Atualiza o valor atual de bem.
     *
     * @param newBem O novo valor para bem.
     */
    public final void setBem(final BemDTO newBem) {
        bem = newBem;
    }

    /**
     * Obtém o valor atual de movimentacoes.
     *
     * @return O valor atual de movimentacoes.
     */
    public final List<MovimentacaoDTO> getMovimentacoes() {
        return movimentacoes;
    }

    /**
     * Atualiza o valor atual de movimentacoes.
     *
     * @param newMovimentacoes O novo valor para movimentacoes.
     */
    public final void setMovimentacoes(
            final List<MovimentacaoDTO> newMovimentacoes) {
        movimentacoes = newMovimentacoes;
    }

    /**
     * Obtém o valor atual de ordens.
     *
     * @return O valor atual de ordens.
     */
    public final List<OrdemServicoDTO> getOrdens() {
        return ordens;
    }

    /**
     * Atualiza o valor atual de ordens.
     *
     * @param newOrdens O novo valor para ordens.
     */
    public final void setOrdens(final List<OrdemServicoDTO> newOrdens) {
        ordens = newOrdens;
    }

    /**
     * Obtém o valor atual de baixa.
     *
     * @return O valor atual de baixa.
     */
    public final BaixaDTO getBaixa() {
        return baixa;
    }

    /**
     * Atualiza o valor atual de baixa.
     *
     * @param newBaixa O novo valor para baixa.
     */
    public final void setBaixa(final BaixaDTO newBaixa) {
        baixa = newBaixa;
    }

    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    public final void setMomentoGeracao(final LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

}
