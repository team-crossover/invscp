package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;
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
        final Localizacao novoLoca = new Localizacao();
        final LocalizacaoDTO novoLocaDTO = new LocalizacaoDTO();
        novoLocaDTO.setValuesFromModel(novoLoca);

        final LocalizacaoView locaView = ViewFactory.createLocalizacao(
                InvSCP.VIEW_IMPL, mainPresenter.getView(), novoLocaDTO, true);
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
        final Funcionario usuario = mainPresenter.getUsuario();
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();

        int deletados = 0;
        for (int i = 0; i < locaIds.size(); i++) {
            final Integer idLoca = locaIds.get(i);

            try {
                Localizacao.validarDeletar(usuario, idLoca);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            final Localizacao loca = locaRepo.getById(idLoca);
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
        final Localizacao selectedLocalizacao = locaRepo
                .getById(selectedLocaIds.get(0));
        final LocalizacaoDTO selectedLocalizacaoDTO = new LocalizacaoDTO();
        selectedLocalizacaoDTO.setValuesFromModel(selectedLocalizacao);

        final LocalizacaoView locaView = ViewFactory.createLocalizacao(
                InvSCP.VIEW_IMPL, mainPresenter.getView(),
                selectedLocalizacaoDTO, false);
        final LocalizacaoPresenter locaPresenter = new LocalizacaoPresenter(
                locaView, mainPresenter, this);
        locaView.setVisible(true);
    }

    public void updateLocalizacoes() {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final List<Localizacao> locas = locaRepo.getAll();
        final List<LocalizacaoDTO> locasDTOs = new ArrayList<LocalizacaoDTO>();
        for (final Localizacao l : locas) {
            final LocalizacaoDTO locaDTO = new LocalizacaoDTO();
            locaDTO.setValuesFromModel(l);
            locasDTOs.add(locaDTO);
        }
        view.updateLocalizacoes(locasDTOs);
    }

}
