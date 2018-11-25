package com.github.nelsonwilliam.invscp.shared.model.dto;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.nelsonwilliam.invscp.shared.util.Relatorios;
import com.github.nelsonwilliam.invscp.shared.util.Resources;

public class GuiaTransporteDTO implements DTO {

    private static final long serialVersionUID = -6804625876543752972L;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final NumberFormat CURRENCY_FORMATTER =
            NumberFormat.getCurrencyInstance();

    private MovimentacaoDTO movimentacao = null;

    private LocalDateTime momentoGeracao = null;

    private FuncionarioDTO usuario = null;

    public String toHtml() throws IOException {
        final String tmpGuia =
                Resources.readResource("html/templates/guiatransporte.html");
        final String tmpInfo = Resources
                .readResource("html/templates/guiatransporte-info-bem.html");

        final BemDTO bem = movimentacao.getBem();

        final String htmlInfo = String.format(tmpInfo,
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
                bem.getDataAquisicao().format(DATE_FORMATTER));

        return String.format(tmpGuia, movimentacao.getNumGuiaTransporte(),
                getMomentoGeracao().format(DATE_TIME_FORMATTER),
                Relatorios.escapeToHtml(usuario.getNome()),
                Relatorios.escapeToHtml(
                        usuario.getCargo().getTexto().toLowerCase()),
                Relatorios.escapeToHtml(bem.getDescricao()),
                Relatorios.escapeToHtml(movimentacao.getSalaOrigem().getPredio()
                        .getLocalizacao().getCidade()),
                movimentacao.getSalaOrigem().getPredio().getLocalizacao()
                .getUf().toString(),
                Relatorios.escapeToHtml(movimentacao.getSalaDestino()
                        .getPredio().getLocalizacao().getCidade()),
                movimentacao.getSalaDestino().getPredio().getLocalizacao()
                .getUf().toString(),
                htmlInfo.toString(), getMomentoGeracao().getYear());
    }

    /**
     * Obtém o valor atual de movimentacao
     *
     * @return O valor atual de movimentacao.
     */
    public final MovimentacaoDTO getMovimentacao() {
        return movimentacao;
    }

    /**
     * Atualiza o valor atual de movimentacao.
     *
     * @param newMovimentacao O novo valor de movimentacao.
     */
    public final void setMovimentacao(final MovimentacaoDTO newMovimentacao) {
        movimentacao = newMovimentacao;
    }

    /**
     * Obtém o valor atual de momentoGeracao
     *
     * @return O valor atual de momentoGeracao.
     */
    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    /**
     * Atualiza o valor atual de momentoGeracao.
     *
     * @param newMomentoGeracao O novo valor de momentoGeracao.
     */
    public final void setMomentoGeracao(final LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

    /**
     * Obtém o valor atual de usuario
     *
     * @return O valor atual de usuario.
     */
    public final FuncionarioDTO getUsuario() {
        return usuario;
    }

    /**
     * Atualiza o valor atual de usuario.
     *
     * @param newUsuario O novo valor de usuario.
     */
    public final void setUsuario(final FuncionarioDTO newUsuario) {
        usuario = newUsuario;
    }

}
