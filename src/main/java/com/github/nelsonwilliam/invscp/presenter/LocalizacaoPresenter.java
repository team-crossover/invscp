package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.view.LocalizacaoView;

public class LocalizacaoPresenter extends Presenter<LocalizacaoView> {

    private final MainPresenter mainPresenter;
    private final LocalizacoesPresenter locasPresenter;

    public LocalizacaoPresenter(final LocalizacaoView view, final MainPresenter mainPresenter,
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
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final Localizacao loca = view.getLocalizacao();
        final Localizacao locaAntiga = locaRepo.getById(loca.getId());

        // VALIDAÇÃO DE DADOS

        if (loca.getNome() == null || loca.getNome().isEmpty()) {
            view.showError("O 'nome' é um campo obrigatório.");
            return;
        }
        if (loca.getEndereco() == null || loca.getEndereco().isEmpty()) {
            view.showError("O 'endereço' é um campo obrigatório.");
            return;
        }
        if (loca.getCep() == null || loca.getCep().isEmpty()) {
            view.showError("O 'CEP' é um campo obrigatório.");
            return;
        }
        if (loca.getCep().length() != 8) {
            view.showError("O 'CEP' deve possuir exatamente 8 números, sem traços ou pontos.");
            return;
        }
        if (loca.getCidade() == null || loca.getCidade().isEmpty()) {
            view.showError("A 'cidade' é um campo obrigatório.");
            return;
        }
        if (loca.getUfString() == null || loca.getUfString().isEmpty()) {
            view.showError("A 'UF' é um campo obrigatório.");
            return;
        }
        if (loca.getUfString().length() != 2) {
            view.showError("O 'UF' deve possuir exatamente 2 caracteres.");
            return;
        }

        // APLICA A ALTERAÇÃO

        if (loca.getId() == null) {
            onConfirmarAdicao(loca);
        } else {
            onConfirmarAtualizacao(locaAntiga, loca);
        }
    }

    private void onConfirmarAdicao(final Localizacao locaNova) {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        locaRepo.add(locaNova);
        view.showSucesso();
        view.close();
        locasPresenter.updateLocalizacoes();
    }

    private void onConfirmarAtualizacao(final Localizacao locaAnterior,
            final Localizacao locaAtualizada) {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        locaRepo.update(locaAtualizada);
        view.showSucesso();
        view.close();
        locasPresenter.updateLocalizacoes();
    }
}
