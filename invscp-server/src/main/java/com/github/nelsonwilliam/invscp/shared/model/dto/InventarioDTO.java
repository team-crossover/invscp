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

public class InventarioDTO implements DTO {

    private static final long serialVersionUID = -6287891393314401598L;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat
            .getCurrencyInstance();

    private List<BemDTO> bens = null;

    private LocalDateTime momentoGeracao = null;

    public String toHtml() throws IOException {
        final String tmpInventario = Resources
                .readResource("html/templates/inventario.html");
        final String tmpDepartamento = Resources
                .readResource("html/templates/inventario-departamento.html");
        final String tmpItem = Resources.readResource(
                "html/templates/inventario-departamento-item.html");

        // Agrupa os bens por departamento
        final Map<Integer, DepartamentoDTO> deptsPorId = new HashMap<>();
        final Map<Integer, List<BemDTO>> bensPorDept = new HashMap<>();
        for (final BemDTO bem : bens) {
            if (bensPorDept.containsKey(bem.getDepartamento().getId())) {
                bensPorDept.get(bem.getDepartamento().getId()).add(bem);
            } else {
                bensPorDept.put(bem.getDepartamento().getId(),
                        new ArrayList<BemDTO>());
                bensPorDept.get(bem.getDepartamento().getId()).add(bem);
                deptsPorId.put(bem.getDepartamento().getId(),
                        bem.getDepartamento());
            }
        }

        final StringBuilder htmlDepts = new StringBuilder();
        for (final Integer idDept : bensPorDept.keySet()) {
            final DepartamentoDTO dept = deptsPorId.get(idDept);
            final List<BemDTO> bens = bensPorDept.get(idDept);

            // Ordena por sala, depois grupo material e depois por número de
            // tombamento
            bens.sort((bem1, bem2) -> {
                final String sala1 = bem1.getSala().getNome();
                final String sala2 = bem2.getSala().getNome();
                if (sala1.compareTo(sala2) == 0) {
                    final String grupo1 = bem1.getGrupoMaterial().getNome();
                    final String grupo2 = bem2.getGrupoMaterial().getNome();
                    if (grupo1.compareTo(grupo2) == 0) {
                        final Long num1 = bem1.getNumeroTombamento();
                        final Long num2 = bem2.getNumeroTombamento();
                        return num1.compareTo(num2);
                    } else {
                        return grupo1.compareTo(grupo2);
                    }
                } else {
                    return sala1.compareTo(sala2);
                }
            });

            final StringBuilder htmlBens = new StringBuilder();
            for (final BemDTO bem : bens) {
                final BigDecimal valDepreciado = bem
                        .getDepreciacoes()[bem.getDepreciacoes().length - 1];
                htmlBens.append(String.format(tmpItem,
                        Relatorios.escapeToHtml(bem.getSala().getNome()),
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

            htmlDepts.append(String.format(tmpDepartamento,
                    Relatorios.escapeToHtml(dept.getNome()),
                    htmlBens.toString()));
        }

        return String.format(tmpInventario, htmlDepts.toString(),
                getMomentoGeracao().format(DATE_TIME_FORMATTER));
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

    public final LocalDateTime getMomentoGeracao() {
        return momentoGeracao;
    }

    public final void setMomentoGeracao(final LocalDateTime newMomentoGeracao) {
        momentoGeracao = newMomentoGeracao;
    }

}
