package com.github.nelsonwilliam.invscp.model.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.EventoMovimentacao;
import com.github.nelsonwilliam.invscp.model.Movimentacao;
import com.github.nelsonwilliam.invscp.model.enums.TipoMovEnum;
import com.github.nelsonwilliam.invscp.util.DatabaseConnection;

public class EventoMovimentacaoRepository implements Repository<EventoMovimentacao> {

    @Override
    public List<EventoMovimentacao> getAll() {
        final Connection connection = DatabaseConnection.getConnection();
        final List<EventoMovimentacao> evs = new ArrayList<EventoMovimentacao>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,tipo,data,justificativa,id_movimentacao,"
                    + "id_funcionario FROM evento_movimentacao ORDER BY id");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String tipo = (String) r.getObject("etapa");
                final LocalDate data = ((Date) r.getObject("data")).toLocalDate();
                final String justificativa = (String) r.getObject("justificativa");
                final Integer idMovimentacao = (Integer) r.getObject("id_movimentacao");
                final Integer idFuncionario = (Integer) r.getObject("id_funcionario");

                final EventoMovimentacao ev = new EventoMovimentacao();
                ev.setId(id);
                ev.setTipo(TipoMovEnum.valueOfTexto(tipo));
                ev.setData(data);
                ev.setJustificativa(justificativa);
                ev.setIdMovimentacao(idMovimentacao);
                ev.setIdFuncionario(idFuncionario);
                evs.add(ev);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return evs;
    }

    public List<EventoMovimentacao> getByMovimentacao(Movimentacao movimentacao) {
        final Connection connection = DatabaseConnection.getConnection();
        final List<EventoMovimentacao> evs = new ArrayList<EventoMovimentacao>();
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT id,tipo,data,justificativa,id_movimentacao,"
                    + "id_funcionario FROM evento_movimentacao WHERE id_movimentacao=?");
            s.setObject(1, movimentacao.getId(), Types.INTEGER);
            
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final Integer id = (Integer) r.getObject("id");
                final String tipo = (String) r.getObject("etapa");
                final LocalDate data = ((Date) r.getObject("data")).toLocalDate();
                final String justificativa = (String) r.getObject("justificativa");
                final Integer idMovimentacao = (Integer) r.getObject("id_movimentacao");
                final Integer idFuncionario = (Integer) r.getObject("id_funcionario");

                final EventoMovimentacao ev = new EventoMovimentacao();
                ev.setId(id);
                ev.setTipo(TipoMovEnum.valueOfTexto(tipo));
                ev.setData(data);
                ev.setJustificativa(justificativa);
                ev.setIdMovimentacao(idMovimentacao);
                ev.setIdFuncionario(idFuncionario);
                evs.add(ev);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return evs;
    }
    
    @Override
    public EventoMovimentacao getById(final Integer id) {
        final Connection connection = DatabaseConnection.getConnection();
        EventoMovimentacao ev = null;
        try {
            final PreparedStatement s = connection.prepareStatement(
                    "SELECT tipo,data,justificativa,id_movimentacao,"
                    + "id_funcionario FROM evento_movimentacao WHERE id=?");
            final ResultSet r = s.executeQuery();
            while (r.next()) {
                final String tipo = (String) r.getObject("etapa");
                final LocalDate data = ((Date) r.getObject("data")).toLocalDate();
                final String justificativa = (String) r.getObject("justificativa");
                final Integer idMovimentacao = (Integer) r.getObject("id_movimentacao");
                final Integer idFuncionario = (Integer) r.getObject("id_funcionario");

                ev = new EventoMovimentacao();
                ev.setId(id);
                ev.setTipo(TipoMovEnum.valueOfTexto(tipo));
                ev.setData(data);
                ev.setJustificativa(justificativa);
                ev.setIdMovimentacao(idMovimentacao);
                ev.setIdFuncionario(idFuncionario);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return ev;
    }

    @Override
    public boolean add(final EventoMovimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            PreparedStatement s;
            if (item.getId() == null) {
                s = connection.prepareStatement(
                        "INSERT INTO evento_movimentacao(tipo,data,justificativa,"
                        + "id_movimentacao,id_funcionario) VALUES (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                s.setObject(1, item.getTipo(), Types.VARCHAR);
                s.setObject(2, item.getData(), Types.DATE);
                s.setObject(3, item.getJustificativa(), Types.VARCHAR);
                s.setObject(4, item.getIdMovimentacao(), Types.INTEGER);
                s.setObject(5, item.getIdFuncionario(), Types.INTEGER);
                s.executeUpdate();
                // Atualiza o item adicionado com seu novo ID
                final ResultSet rs = s.getGeneratedKeys();
                if (rs.next()) {
                    final Integer id = rs.getInt(1);
                    item.setId(id);
                }

            } else {
                s = connection.prepareStatement(
                        "INSERT INTO evento_movimentacao(id,tipo,data,"
                        + "justificativa,id_movimentacao,id_funcionario)"
                        + " VALUES (?,?,?,?,?,?)");
                s.setObject(1, item.getId(), Types.INTEGER);
                s.setObject(2, item.getTipo(), Types.VARCHAR);
                s.setObject(3, item.getData(), Types.DATE);
                s.setObject(4, item.getJustificativa(), Types.VARCHAR);
                s.setObject(5, item.getIdMovimentacao(), Types.INTEGER);
                s.setObject(6, item.getIdFuncionario(), Types.INTEGER);
                s.executeUpdate();
            }

            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean add(final Iterable<EventoMovimentacao> items) {
        boolean added = false;
        for (final EventoMovimentacao item : items) {
            added |= add(item);
        }
        return added;
    }

    @Override
    public boolean update(final EventoMovimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement(
                    "UPDATE evento_movimentacao SET tipo=?, data=?,"
                    + "justificativa=?, id_movimentacao=?, id_funcionario=?"
                    + " WHERE id=?");
            s.setObject(1, item.getTipo(), Types.VARCHAR);
            s.setObject(2, item.getData(), Types.DATE);
            s.setObject(3, item.getJustificativa(), Types.VARCHAR);
            s.setObject(4, item.getIdMovimentacao(), Types.INTEGER);
            s.setObject(5, item.getIdFuncionario(), Types.INTEGER);
            s.setObject(6, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(final Iterable<EventoMovimentacao> items) {
        boolean updated = false;
        for (final EventoMovimentacao item : items) {
            updated |= update(item);
        }
        return updated;
    }

    @Override
    public boolean remove(final EventoMovimentacao item) {
        final Connection connection = DatabaseConnection.getConnection();
        try {
            if (item.getId() == null) {
                return false;
            }
            PreparedStatement s;
            s = connection.prepareStatement("DELETE FROM evento_movimentacao WHERE id=?");
            s.setObject(1, item.getId(), Types.INTEGER);
            s.executeUpdate();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(final Iterable<EventoMovimentacao> items) {
        boolean removed = false;
        for (final EventoMovimentacao item : items) {
            removed |= remove(item);
        }
        return removed;
    }

}
