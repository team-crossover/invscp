package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.view.LocalizacaoView;
import com.github.nelsonwilliam.invscp.view.LocalizacoesView;
import com.github.nelsonwilliam.invscp.view.swing.LocalizacaoSwingView;

public class LocalizacoesPresenter extends Presenter<LocalizacoesView> {

    private final MainPresenter mainPresenter;

    public LocalizacoesPresenter(final LocalizacoesView view, final MainPresenter mainPresenter) {
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
        final Localizacao novoLoca = new Localizacao();
        final LocalizacaoView locaView = new LocalizacaoSwingView((JFrame) mainPresenter.getView(),
                novoLoca, true);
        final LocalizacaoPresenter locaPresenter = new LocalizacaoPresenter(locaView, mainPresenter,
                this);
        locaView.setVisible(true);
    }

    private void onDeletarLocalizacoes() {
        final List<Integer> selectedLocaIds = view.getSelectedLocalizacoesIds();
        view.showConfirmacao("Deletar " + selectedLocaIds.size() + " localizações(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarLocalizacoes(selectedLocaIds);
                    }
                });
    }

    private void deletarLocalizacoes(final List<Integer> locaIds) {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final PredioRepository predioRepo = new PredioRepository();
        int deletados = 0;
        for (int i = 0; i < locaIds.size(); i++) {
            final Localizacao loca = locaRepo.getById(locaIds.get(i));

            // VALIDAÇÃO DE DADOS
            if (predioRepo.getByLocalizacao(loca).size() > 0) {
                view.showError("Não é possível deletar a localização " + loca.getNome()
                        + " pois existem prédios com esta localização.");
                continue;
            }

            // REMOVE O ITEM

            locaRepo.remove(loca);
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

        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final Localizacao selectedLocalizacao = locaRepo.getById(selectedLocaIds.get(0));
        final LocalizacaoView locaView = new LocalizacaoSwingView((JFrame) mainPresenter.getView(),
                selectedLocalizacao, false);
        final LocalizacaoPresenter locaPresenter = new LocalizacaoPresenter(locaView, mainPresenter,
                this);
        locaView.setVisible(true);
    }

    public void updateLocalizacoes() {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final List<Localizacao> locas = locaRepo.getAll();
        view.updateLocalizacoes(locas);
    }

}
