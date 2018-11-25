package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.EventoMovimentacaoView;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class EventoMovimentacaoPresenter
        extends Presenter<EventoMovimentacaoView> {

    private final MainPresenter mainPresenter;
    private final EventosMovimentacaoPresenter eventosMovimentacaoPresenter;
    private final MovimentacoesPresenter movimentacoesPresenter;

    public EventoMovimentacaoPresenter(final EventoMovimentacaoView view,
            final MainPresenter mainPresenter,
            final EventosMovimentacaoPresenter eventosMovimentacaoPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.eventosMovimentacaoPresenter = eventosMovimentacaoPresenter;
        this.movimentacoesPresenter = null;
        setupViewListeners();
    }

    public EventoMovimentacaoPresenter(final EventoMovimentacaoView view,
            final MainPresenter mainPresenter,
            final MovimentacoesPresenter movimentacoesPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.eventosMovimentacaoPresenter = null;
        this.movimentacoesPresenter = movimentacoesPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final EventoMovimentacaoDTO eventoMovimentacaoDTO =
                view.getEventoMovimentacao();
        if (eventoMovimentacaoDTO == null) {
            view.showError("Não foi possível inserir/alterar o item.");
            return;
        }

        if (eventoMovimentacaoDTO.getId() == null) {
            onConfirmarAdicao(usuario, eventoMovimentacaoDTO);
        } else {
            onConfirmarAtualizacao(usuario, eventoMovimentacaoDTO.getId(),
                    eventoMovimentacaoDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final EventoMovimentacaoDTO eventoMovimentacaoNovo) {

        try {
            Client.requestValidarInserirEventoMovimentacao(usuario,
                    eventoMovimentacaoNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddEventoMovimentacao(eventoMovimentacaoNovo);
        view.showSucesso();
        view.close();

        // Executa as pós-inserções e exibe as mensagens resultantes.
        final List<String> messages =
                Client.requestPosInserirEventoMovimentacao(usuario,
                        eventoMovimentacaoNovo);
        for (final String message : messages) {
            view.showInfo(message);
        }

        if (eventosMovimentacaoPresenter != null)
            eventosMovimentacaoPresenter.updateEventosMovimentacao();
        if (movimentacoesPresenter != null)
            movimentacoesPresenter.updateMovimentacoes();
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idEventoMovimentacaoAnterior,
            final EventoMovimentacaoDTO eventoMovimentacaoAtualizado) {

        try {
            Client.requestValidarAlterarEventoMovimentacao(usuario,
                    idEventoMovimentacaoAnterior, eventoMovimentacaoAtualizado);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateEventoMovimentacao(eventoMovimentacaoAtualizado);
        view.showSucesso();
        view.close();

        if (eventosMovimentacaoPresenter != null)
            eventosMovimentacaoPresenter.updateEventosMovimentacao();
        if (movimentacoesPresenter != null)
            movimentacoesPresenter.updateMovimentacoes();
    }
}
