package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.EventoMovimentacaoView;
import com.github.nelsonwilliam.invscp.client.view.EventosMovimentacaoView;
import com.github.nelsonwilliam.invscp.client.view.ViewFactory;
import com.github.nelsonwilliam.invscp.shared.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.MovimentacaoDTO;

public class EventosMovimentacaoPresenter
        extends Presenter<EventosMovimentacaoView> {

    private final MainPresenter mainPresenter;
    private final MovimentacoesPresenter movimentacoesPresenter;

    private final Integer idMovimentacao;
    private MovimentacaoDTO movimentacao;

    public EventosMovimentacaoPresenter(final EventosMovimentacaoView view,
            final MainPresenter mainPresenter,
            final MovimentacoesPresenter movimentacoesPresenter,
            final MovimentacaoDTO movimentacao) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.movimentacoesPresenter = movimentacoesPresenter;
        this.movimentacao = movimentacao;
        idMovimentacao = movimentacao.getId();
        setupViewListeners();
        updateEventosMovimentacao();
    }

    private void setupViewListeners() {
        view.addVerEventoMovimentacaoListener((final ActionEvent e) -> {
            onVerEventoMovimentacao();
        });
    }

    private void refreshMovimentacao() {
        movimentacao = Client.requestGetMovimentacaoById(idMovimentacao);
    }

    @SuppressWarnings("unused")
    private void onVerEventoMovimentacao() {
        final List<Integer> selectedEventoMovimentacaoIds =
                view.getSelectedEventosMovimentacaoIds();
        if (selectedEventoMovimentacaoIds.size() != 1) {
            throw new RuntimeException(
                    "Para ver um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedEventoMovimentacaoId =
                selectedEventoMovimentacaoIds.get(0);
        final EventoMovimentacaoDTO selectedEventoMovimentacao = Client
                .requestGetEventoMovimentacaoById(selectedEventoMovimentacaoId);

        final EventoMovimentacaoView eventoServicoView =
                ViewFactory.createEventoMovimentacao(mainPresenter.getView(),
                        selectedEventoMovimentacao, false);
        final EventoMovimentacaoPresenter eventoServicoPresenter =
                new EventoMovimentacaoPresenter(eventoServicoView,
                        mainPresenter, this);
        eventoServicoView.setVisible(true);
    }

    public void updateEventosMovimentacao() {
        refreshMovimentacao();
        final List<EventoMovimentacaoDTO> eventosServico = Client
                .requestGetEventosMovimentacaoByMovimentacao(movimentacao);
        view.updateEventosMovimentacao(movimentacao, eventosServico);
        movimentacoesPresenter.updateMovimentacoes();
    }

}
