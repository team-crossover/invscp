package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.GuiaTransporteDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.TipoEventoMovEnum;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.util.Relatorios;
import com.github.nelsonwilliam.invscp.view.EventoMovimentacaoView;
import com.github.nelsonwilliam.invscp.view.EventosMovimentacaoView;
import com.github.nelsonwilliam.invscp.view.MovimentacaoView;
import com.github.nelsonwilliam.invscp.view.MovimentacoesView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class MovimentacoesPresenter extends Presenter<MovimentacoesView> {

    private final MainPresenter mainPresenter;

    public MovimentacoesPresenter(final MovimentacoesView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateMovimentacoes();
    }

    private void setupViewListeners() {
        view.addVerMovimentacaoListener((final ActionEvent e) -> {
            onVerMovimentacao();
        });
        view.addAceitarEntradaListener((final ActionEvent e) -> {
            onEvento(TipoEventoMovEnum.ACEITE_ENTRADA);
        });
        view.addNegarEntradaListener((final ActionEvent e) -> {
            onEvento(TipoEventoMovEnum.NEGACAO_ENTRADA);
        });
        view.addAceitarSaidaListener((final ActionEvent e) -> {
            onEvento(TipoEventoMovEnum.ACEITE_SAIDA);
        });
        view.addNegarSaidaListener((final ActionEvent e) -> {
            onEvento(TipoEventoMovEnum.NEGACAO_SAIDA);
        });
        view.addCancelarListener((final ActionEvent e) -> {
            onEvento(TipoEventoMovEnum.CANCELAMENTO);
        });
        view.addFinalizarListener((final ActionEvent e) -> {
            onFinalizacao();
        });
        view.addEventosMovimentacaoListener((final ActionEvent e) -> {
            onEventos();
        });
        view.addGerarGuiaTransporte((final ActionEvent e) -> {
            onGerarGuia();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarMovimentacao() {
        final MovimentacaoDTO novaMov = new MovimentacaoDTO();

        final MovimentacaoView novaView = ViewFactory
                .createMovimentacao(mainPresenter.getView(), novaMov, true);
        final MovimentacaoPresenter novaPresenter =
                new MovimentacaoPresenter(novaView, mainPresenter, this);
        novaView.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void onVerMovimentacao() {
        final List<Integer> selectedMovIds = view.getSelectedMovimentacoesIds();
        if (selectedMovIds.size() != 1) {
            throw new RuntimeException(
                    "Para ver um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedMovId = selectedMovIds.get(0);
        final MovimentacaoDTO selectedMov =
                Client.requestGetMovimentacaoById(selectedMovId);

        final MovimentacaoView movView = ViewFactory.createMovimentacao(
                mainPresenter.getView(), selectedMov, false);
        final MovimentacaoPresenter movPresenter =
                new MovimentacaoPresenter(movView, mainPresenter, this);
        movView.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void onEvento(final TipoEventoMovEnum tipo) {
        final List<Integer> selectedMovIds = view.getSelectedMovimentacoesIds();
        if (selectedMovIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final Integer selectedMovId = selectedMovIds.get(0);
        final MovimentacaoDTO selectedMov =
                Client.requestGetMovimentacaoById(selectedMovId);

        final EventoMovimentacaoDTO ev = new EventoMovimentacaoDTO();
        ev.setData(LocalDate.now());
        ev.setFuncionario(mainPresenter.getUsuario());
        ev.setMovimentacao(selectedMov);
        ev.setTipo(tipo);

        final EventoMovimentacaoView evView = ViewFactory
                .createEventoMovimentacao(mainPresenter.getView(), ev, true);
        final EventoMovimentacaoPresenter evPresenter =
                new EventoMovimentacaoPresenter(evView, mainPresenter, this);
        evView.setVisible(true);
    }

    private void onFinalizacao() {
        final List<Integer> selectedMovIds = view.getSelectedMovimentacoesIds();
        if (selectedMovIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedMovId = selectedMovIds.get(0);
        final MovimentacaoDTO selectedMov =
                Client.requestGetMovimentacaoById(selectedMovId);

        view.showConfirmacao("Marcar a movimentação " + selectedMov.getId()
        + " como finalizada?", (final Boolean confirmado) -> {
            if (confirmado) {
                finalizar(selectedMov);
            }
        });
    }

    private void finalizar(final MovimentacaoDTO mov) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        final EventoMovimentacaoDTO ev = new EventoMovimentacaoDTO();
        ev.setData(LocalDate.now());
        ev.setFuncionario(mainPresenter.getUsuario());
        ev.setMovimentacao(mov);
        ev.setTipo(TipoEventoMovEnum.FINALIZACAO);

        try {
            Client.requestValidarInserirEventoMovimentacao(usuario, ev);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final Integer newId = Client.requestAddEventoMovimentacao(ev);
        ev.setId(newId);
        view.showSucesso("Movimentação finalizada.");

        // Executa as pós-alterações e exibe as mensagens resultantes.
        final List<String> messages =
                Client.requestPosInserirEventoMovimentacao(usuario, ev);
        for (final String message : messages) {
            view.showInfo(message);
        }

        updateMovimentacoes();
    }

    @SuppressWarnings("unused")
    private void onEventos() {
        final List<Integer> selectedMovIds = view.getSelectedMovimentacoesIds();
        if (selectedMovIds.size() != 1) {
            throw new RuntimeException(
                    "Para ver as ordens de serviço, necessário que apenas uma movimentação esteja selecionada.");
        }

        final Integer selectedMovId = selectedMovIds.get(0);
        final MovimentacaoDTO selectedMov =
                Client.requestGetMovimentacaoById(selectedMovId);

        final EventosMovimentacaoView eventosView =
                ViewFactory.createEventosMovimentacao(mainPresenter.view);
        final EventosMovimentacaoPresenter eventosPresenter =
                new EventosMovimentacaoPresenter(eventosView, mainPresenter,
                        this, selectedMov);
        eventosView.setVisible(true);
    }

    public void onGerarGuia() {
        final List<Integer> selectedMovIds = view.getSelectedMovimentacoesIds();
        if (selectedMovIds.size() != 1) {
            throw new RuntimeException(
                    "Para gerar a guia de autorização de transporte, é necessário que apenas uma movimentação esteja selecionada.");
        }

        final Integer selectedMovId = selectedMovIds.get(0);
        final MovimentacaoDTO selectedMov =
                Client.requestGetMovimentacaoById(selectedMovId);

        if (selectedMov.isParaMesmaCidade()) {
            view.showError(
                    "Não é possível gerar guia de autorização de transporte para movimentações para a mesma cidade.");
            return;
        }

        final GuiaTransporteDTO guia = Client.requestGerarGuiaTransporte(
                selectedMov, mainPresenter.getUsuario());

        final String fileName = "guia-autorizacao-transporte-"
                + selectedMov.getId() + "_"
                + guia.getMomentoGeracao().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"))
                + ".html";

        String htmlText = null;
        File file = null;
        try {
            htmlText = guia.toHtml();
            file = Relatorios.salvar("relatorios/" + fileName, htmlText);
        } catch (final IOException e) {
            view.showError(
                    "Não foi possível gerar a guia de autorização de transporte.");
            e.printStackTrace();
            return;
        }

        view.showSucesso(
                "Guia de autorização de transporte gerada e salvo em:\n"
                        + file.toPath().toAbsolutePath());
    }

    public void updateMovimentacoes() {
        final List<MovimentacaoDTO> movs = Client.requestGetMovimentacoes();
        view.updateMovimentacoes(movs);
    }

}
