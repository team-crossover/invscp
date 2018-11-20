package com.github.nelsonwilliam.invscp.model.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.Bem;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Relatorio;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class RelatorioRepository {

    public Relatorio getByDepartamento(final Departamento dept) {
        final Connection connection = DatabaseConnection.getConnection();
        final Relatorio relatorio = new Relatorio();
        try {
            PreparedStatement s = connection.prepareStatement(
                    "SELECT id,descricao,numero_tombamento,data_cadastro,data_aquisicao,"
                    + "numero_nota_fiscal,especificacao,garantia,marca,valor_compra,situacao,"
                    + "id_sala,id_departamento,id_grupo_material FROM bem WHERE id_departamento=?"
                    + "GROUP BY id_sala");
            s.setObject(1, dept.getId(), Types.INTEGER);

            final ResultSet r = s.executeQuery();
            List<Integer> idBens = new ArrayList<Integer>();

            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String descricao = (String) r.getObject("descricao");
                final Long numTombamento = (Long) r.getObject("numero_tombamento");
                final LocalDate dataCadastro = ((Date) r.getObject("data_cadastro")).toLocalDate();
                final LocalDate dataAquisicao =
                        ((Date) r.getObject("data_aquisicao")).toLocalDate();
                final String numNotaFiscal = (String) r.getObject("numero_nota_fiscal");
                final String especificacao =
                        (String) r.getObject("especificacao");
                final LocalDate garantia = ((Date) r.getObject("garantia")).toLocalDate();
                final String marca = (String) r.getObject("marca");
                final BigDecimal valorCompra =
                        (BigDecimal) r.getObject("valor_compra");
                final String situacao = (String) r.getObject("situacao");
                final Integer idSala = (Integer) r.getObject("id_sala");
                final Integer idDepartamento = (Integer) r.getObject("id_departamento");
                final Integer idGrupoMaterial = (Integer) r.getObject("id_grupo_material");

                final Bem bem = new Bem();
                bem.setId(id);
                bem.setDescricao(descricao);
                bem.setNumeroTombamento(numTombamento);
                bem.setDataAquisicao(dataAquisicao);
                bem.setDataCadastro(dataCadastro);
                bem.setNumeroNotaFiscal(numNotaFiscal);
                bem.setEspecificacao(especificacao);
                bem.setGarantia(garantia);
                bem.setMarca(marca);
                bem.setValorCompra(valorCompra);
                bem.setSituacao(BemSituacaoEnum.valueOf(situacao));
                bem.setIdSala(idSala);
                bem.setIdDepartamento(idDepartamento);
                bem.setIdGrupoMaterial(idGrupoMaterial);
                idBens.add(id);

            }
            relatorio.setIdBens(idBens);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return relatorio;
    }

}
