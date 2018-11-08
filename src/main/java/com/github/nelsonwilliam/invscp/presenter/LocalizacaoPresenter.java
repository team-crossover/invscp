package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final LocalizacaoDTO locaDTO = view.getLocalizacao();
        final Localizacao loca = locaDTO == null ? null : locaDTO.toModel();

        if (loca.getId() == null) {
            onConfirmarAdicao(usuario, loca);
        } else {
            onConfirmarAtualizacao(usuario, loca.getId(), loca);
        }
    }

    private void onConfirmarAdicao(final Funcionario usuario,
            final Localizacao locaNova) {

        try {
            Localizacao.validarInserir(usuario, locaNova);
        } catch (final IllegalInsertException e) {
            view.showError(e.getMessage());
            return;
        }

        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        locaRepo.add(locaNova);
        view.showSucesso();
        view.close();
        locasPresenter.updateLocalizacoes();
    }

    private void onConfirmarAtualizacao(final Funcionario usuario,
            final Integer idLocaAnterior, final Localizacao locaAtualizada) {

        try {
            Localizacao.validarAlterar(usuario, idLocaAnterior, locaAtualizada);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        locaRepo.update(locaAtualizada);
        view.showSucesso();
        view.close();
        locasPresenter.updateLocalizacoes();
    }
}
