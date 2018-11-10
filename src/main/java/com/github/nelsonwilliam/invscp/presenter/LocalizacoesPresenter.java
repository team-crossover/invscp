package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.LocalizacaoView;
import com.github.nelsonwilliam.invscp.view.LocalizacoesView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class LocalizacoesPresenter extends Presenter<LocalizacoesView> {

    private final MainPresenter mainPresenter;

    public LocalizacoesPresenter(final LocalizacoesView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateLocalizacoes();
    }

    private void setupViewListeners() {
        view.addAdicionarLocalizacaoListener((final ActionEvent e) -> {
            onAdicionarLocalizacao();
        });
        view.addDeletarLocalizacoesListener((final ActionEvent e) -> {
            onDeletarLocalizacoes();
        });
        view.addAlterarLocalizacaoListener((final ActionEvent e) -> {
            onAlterarLocalizacao();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarLocalizacao() {
        final LocalizacaoDTO novoLoca = new LocalizacaoDTO();

        final LocalizacaoView locaView = ViewFactory
                .createLocalizacao(mainPresenter.getView(), novoLoca, true);
        final LocalizacaoPresenter locaPresenter = new LocalizacaoPresenter(
                locaView, mainPresenter, this);
        locaView.setVisible(true);
    }

    private void onDeletarLocalizacoes() {
        final List<Integer> selectedLocaIds = view.getSelectedLocalizacoesIds();
        view.showConfirmacao(
                "Deletar " + selectedLocaIds.size() + " localizações(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarLocalizacoes(selectedLocaIds);
                    }
                });
    }

    private void deletarLocalizacoes(final List<Integer> locaIds) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        int deletados = 0;
        for (int i = 0; i < locaIds.size(); i++) {
            final Integer idLoca = locaIds.get(i);

            try {
                Client.requestValidarDeleteLocalizacao(usuario, idLoca);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            Client.requestDeleteLocalizacao(idLoca);
            deletados++;
        }

        if (deletados > 0) {
            view.showSucesso("Exclusão de localização(ões) concluída.");
        }
        updateLocalizacoes();
    }

    @SuppressWarnings("unused")
    private void onAlterarLocalizacao() {
        final List<Integer> selectedLocaIds = view.getSelectedLocalizacoesIds();
        if (selectedLocaIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedLocaId = selectedLocaIds.get(0);
        final LocalizacaoDTO selectedLoca = Client
                .requestGetLocalizacaoById(selectedLocaId);
        final LocalizacaoView locaView = ViewFactory.createLocalizacao(
                mainPresenter.getView(), selectedLoca, false);
        final LocalizacaoPresenter locaPresenter = new LocalizacaoPresenter(
                locaView, mainPresenter, this);
        locaView.setVisible(true);
    }

    public void updateLocalizacoes() {
        final List<LocalizacaoDTO> locas = Client.requestGetLocalizacoes();
        view.updateLocalizacoes(locas);
    }

}
