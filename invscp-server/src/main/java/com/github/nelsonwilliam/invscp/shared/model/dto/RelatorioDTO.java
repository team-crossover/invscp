package com.github.nelsonwilliam.invscp.shared.model.dto;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.nelsonwilliam.invscp.shared.util.Relatorios;
import com.github.nelsonwilliam.invscp.shared.util.Resources;

public class RelatorioDTO implements DTO {

    private static final long serialVersionUID = 5395246890902215243L;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
            .getCurrencyInstance();

    private DepartamentoDTO departamento = null;

    private List<BemDTO> bens = null;

    private LocalDateTime momentoGeracao = null;

    public String toHtml() throws IOException {
        final String tmpRelatorio = Resources
                .readResource("html/templates/relatorio.html");
        final String tmpSala = Resources
                .readResource("html/templates/relatorio-sala.html");
        final String tmpItem = Resources
                .readResource("html/templates/relatorio-sala-item.html");

        // Agrupa os bens por sala
        final Map<Integer, SalaDTO> salasPorId = new HashMap<>();
        final Map<Integer, List<BemDTO>> bensPorSala = new HashMap<>();
        for (final BemDTO bem : bens) {
            if (bensPorSala.containsKey(bem.getSala().getId())) {
                bensPorSala.get(bem.getSala().getId()).add(bem);
            } else {
                bensPorSala.put(bem.getSala().getId(), new ArrayList<BemDTO>());
                bensPorSala.get(bem.getSala().getId()).add(bem);
                salasPorId.put(bem.getSala().getId(), bem.getSala());
            }
        }

        final StringBuilder htmlSalas = new StringBuilder();
        for (final Integer idSala : bensPorSala.keySet()) {
            final SalaDTO sala = salasPorId.get(idSala);
            final List<BemDTO> bens = bensPorSala.get(idSala);

            // Ordena por grupo material e depois por número de tombamento
            bens.sort((bem1, bem2) -> {
                final String nome1 = bem1.getGrupoMaterial().getNome();
                final String nome2 = bem2.getGrupoMaterial().getNome();
                if (nome1.compareTo(nome2) == 0) {
                    final Long num1 = bem1.getNumeroTombamento();
                    final Long num2 = bem2.getNumeroTombamento();
                    return num1.compareTo(num2);
                } else {
                    return nome1.compareTo(nome2);
                }
            });

            final StringBuilder htmlBens = new StringBuilder();
            for (final BemDTO bem : bens) {
                final BigDecimal valDepreciado = bem
                        .getDepreciacoes()[bem.getDepreciacoes().length - 1];
                htmlBens.append(String.format(tmpItem,
                        Relatorios
                                .escapeToHtml(bem.getGrupoMaterial().getNome()),
                        bem.getNumeroTombamento(),
                        Relatorios.escapeToHtml(bem.getDescricao()),
                        Relatorios.escapeToHtml(bem.getMarca()),
                        Relatorios.escapeToHtml(bem.getEspecificacao()),
                        bem.getDataCadastro().format(DATE_FORMATTER),
                        bem.getDataAquisicao().format(DATE_FORMATTER),
                        bem.getGarantia().format(DATE_FORMATTER),
                        Relatorios.escapeToHtml(bem.getNumeroNotaFiscal()),
                        CURRENCY_FORMATTER.format(bem.getValorCompra()),
                        CURRENCY_FORMATTER.format(valDepreciado),
                        bem.getSituacao().getTexto()));
            }

            htmlSalas.append(String.format(tmpSala,
                    Relatorios.escapeToHtml(sala.getNome()),
                    htmlBens.toString()));
        }

        return String.format(tmpRelatorio,
                Relatorios.escapeToHtml(departamento.getNome()),
                htmlSalas.toString(),
                getMomentoGeracao().format(DATE_TIME_FORMATTER));
    }

    /**
     * Obtém o valor atual de departamento.
     *
     * @return O valor atual de departamento.
     */
    public final DepartamentoDTO getDepartamento() {
        return departamento;
    }

    /**
     * Atualiza o valor atual de departamento.
     *
     * @param newDepartamento O novo valor para departamento.
     */
    public final void setDepartamento(final DepartamentoDTO newDepartamento) {
        departamento = newDepartamento;
    }

    /**
     * Obtém o valor atual de bens.
     *
     * @return O valor atual de bens.
     */
    public final List<BemDTO> getBens() {
        return bens;
    }

    /**
     * Atualiza o valor atual de bens.
     *
     * @param newBens O novo valor para bens.
     */
    public final void setBens(final List<BemDTO> newBens) {
        bens = newBens;
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
