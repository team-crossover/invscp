package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.enums.OSSituacaoEnum;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.OrdemServicoView;
import com.github.nelsonwilliam.invscp.view.OrdensServicoView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class OrdensServicoPresenter extends Presenter<OrdensServicoView> {

    private final MainPresenter mainPresenter;
    private final BensPresenter bensPresenter;

    private final Integer idBem;
    private BemDTO bem;

    public OrdensServicoPresenter(final OrdensServicoView view,
            final MainPresenter mainPresenter,
            final BensPresenter bensPresenter, final BemDTO bem) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.bensPresenter = bensPresenter;
        this.bem = bem;
        idBem = bem.getId();
        setupViewListeners();
        updateOrdensServico();
    }

    private void setupViewListeners() {
        view.addAdicionarOrdemServicoListener((final ActionEvent e) -> {
            onAdicionarOrdemServico();
        });
        view.addAlterarOrdemServicoListener((final ActionEvent e) -> {
            onAlterarOrdemServico();
        });
        view.addConcluirOrdemServicoListener((final ActionEvent e) -> {
            onConcluirOrdemServico();
        });
    }

    private void refreshBem() {
        bem = Client.requestGetBemById(idBem);
    }

    @SuppressWarnings("unused")
    private void onAdicionarOrdemServico() {
        refreshBem();

        final OrdemServicoDTO novoOrdemServico = new OrdemServicoDTO();
        novoOrdemServico.setBem(bem);
        novoOrdemServico.setFuncionario(mainPresenter.getUsuario());
        novoOrdemServico.setDataCadastro(LocalDate.now());
        novoOrdemServico.setSituacao(OSSituacaoEnum.PENDENTE);

        final OrdemServicoView ordemServicoView =
                ViewFactory.createOrdemServico(mainPresenter.getView(),
                        novoOrdemServico, true);
        final OrdemServicoPresenter ordemServicoPresenter =
                new OrdemServicoPresenter(ordemServicoView, mainPresenter,
                        this);
        ordemServicoView.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void onAlterarOrdemServico() {
        final List<Integer> selectedOrdemServicoIds =
                view.getSelectedOrdensServicoIds();
        if (selectedOrdemServicoIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedOrdemServicoId = selectedOrdemServicoIds.get(0);
        final OrdemServicoDTO selectedOrdemServico =
                Client.requestGetOrdemServicoById(selectedOrdemServicoId);

        final OrdemServicoView ordemServicoView =
                ViewFactory.createOrdemServico(mainPresenter.getView(),
                        selectedOrdemServico, false);
        final OrdemServicoPresenter ordemServicoPresenter =
                new OrdemServicoPresenter(ordemServicoView, mainPresenter,
                        this);
        ordemServicoView.setVisible(true);
    }

    public void onConcluirOrdemServico() {
        final List<Integer> selectedOrdemServicoIds =
                view.getSelectedOrdensServicoIds();
        if (selectedOrdemServicoIds.size() != 1) {
            throw new RuntimeException(
                    "Para concluir uma ordem de serviço é necessário que apenas uma esteja selecionada.");
        }

        final Integer selectedOrdemServicoId = selectedOrdemServicoIds.get(0);
        final OrdemServicoDTO selectedOrdemServico =
                Client.requestGetOrdemServicoById(selectedOrdemServicoId);

        view.showConfirmacao("Marcar a ordem de serviço "
                + selectedOrdemServico.getId() + " como conluída?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        concluirOrdemServico(selectedOrdemServico);
                    }
                });
    }

    private void concluirOrdemServico(final OrdemServicoDTO os) {
        os.setDataConclusao(LocalDate.now());
        os.setSituacao(OSSituacaoEnum.CONCLUIDA);

        try {
            Client.requestValidarAlterarOrdemServico(mainPresenter.getUsuario(),
                    os.getId(), os);
        } catch (final IllegalUpdateException e) {
            view.showError(e.getMessage());
            return;
        }

        Client.requestUpdateOrdemServico(os);
        view.showSucesso("A ordem de serviço foi concluída com sucesso!");

        // Executa as pós-inserções e exibe as mensagens resultantes.
        final List<String> messages = Client.requestPosConcluirOrdemServico(
                mainPresenter.getUsuario(), os);
        for (final String message : messages) {
            view.showInfo(message);
        }

        updateOrdensServico();
    }

    public void updateOrdensServico() {
        refreshBem();
        final List<OrdemServicoDTO> ordensServico =
                Client.requestGetOrdensServicoByBem(bem.getId());
        view.updateOrdensServico(bem, ordensServico);
        bensPresenter.updateBens();
    }

}
