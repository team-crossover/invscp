package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.LocalizacaoView;

public class LocalizacaoPresenter extends Presenter<LocalizacaoView> {

    private final MainPresenter mainPresenter;
    private final LocalizacoesPresenter locasPresenter;

    public LocalizacaoPresenter(final LocalizacaoView view,
            final MainPresenter mainPresenter,
            final LocalizacoesPresenter locasPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.locasPresenter = locasPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final LocalizacaoDTO locaDTO = view.getLocalizacao();
        if (locaDTO == null) {
            view.showError("Não foi possível inserir/alterar o item.");
            return;
        }

        if (locaDTO.getId() == null) {
            onConfirmarAdicao(usuario, locaDTO);
        } else {
            onConfirmarAtualizacao(usuario, locaDTO.getId(), locaDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final LocalizacaoDTO locaNova) {

        try {
            Client.requestValidarInserirLocalizacao(usuario, locaNova);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddLocalizacao(locaNova);
        view.showSucesso();
        view.close();
        locasPresenter.updateLocalizacoes();
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idLocaAnterior, final LocalizacaoDTO locaAtualizada) {

        try {
            Client.requestValidarAlterarLocalizacao(usuario, idLocaAnterior,
                    locaAtualizada);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateLocalizacao(locaAtualizada);
        view.showSucesso();
        view.close();
        locasPresenter.updateLocalizacoes();
    }
}
