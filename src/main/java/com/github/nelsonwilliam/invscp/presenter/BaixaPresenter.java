package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.BaixaView;

public class BaixaPresenter extends Presenter<BaixaView> {

    private final MainPresenter mainPresenter;
    private final BensPresenter bensPresenter;

    public BaixaPresenter(final BaixaView view,
            final MainPresenter mainPresenter,
            final BensPresenter bensPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.bensPresenter = bensPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();
        final BaixaDTO baixaDTO = view.getBaixa();
        if (baixaDTO == null) {
            view.showError("Não foi possível inserir/alterar o item.");
            return;
        }

        if (baixaDTO.getId() == null) {
            onConfirmarAdicao(usuario, baixaDTO);
        } else {
            onConfirmarAtualizacao(usuario, baixaDTO.getId(), baixaDTO);
        }
    }

    private void onConfirmarAdicao(final FuncionarioDTO usuario,
            final BaixaDTO baixaNovo) {

        try {
            Client.requestValidarInserirBaixa(usuario, baixaNovo);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestAddBaixa(baixaNovo);
        view.showSucesso();
        view.close();

        // Executa as pós-inserções e exibe as mensagens resultantes.
        final List<String> messages =
                Client.requestPosInserirBaixa(usuario, baixaNovo);
        for (final String message : messages) {
            view.showInfo(message);
        }

        bensPresenter.updateBens();
    }

    private void onConfirmarAtualizacao(final FuncionarioDTO usuario,
            final Integer idOrdemServicoAnterior,
            final BaixaDTO baixaAtualizada) {

        try {
            Client.requestValidarAlterarBaixa(usuario, idOrdemServicoAnterior,
                    baixaAtualizada);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateBaixa(baixaAtualizada);
        view.showSucesso();
        view.close();

        bensPresenter.updateBens();
    }

}
