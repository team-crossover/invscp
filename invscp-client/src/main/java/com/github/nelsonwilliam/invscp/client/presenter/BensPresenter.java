package com.github.nelsonwilliam.invscp.client.presenter;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.BaixaView;
import com.github.nelsonwilliam.invscp.client.view.BemView;
import com.github.nelsonwilliam.invscp.client.view.BensView;
import com.github.nelsonwilliam.invscp.client.view.MovimentacaoView;
import com.github.nelsonwilliam.invscp.client.view.OrdensServicoView;
import com.github.nelsonwilliam.invscp.client.view.ViewFactory;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.HistoricoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.InventarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.shared.util.Relatorios;

public class BensPresenter extends Presenter<BensView> {

    private final MainPresenter mainPresenter;

    public BensPresenter(final BensView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateBens();
    }

    private void setupViewListeners() {
        view.addAdicionarBemListener((final ActionEvent e) -> {
            onAdicionarBem();
        });
        view.addDeletarBensListener((final ActionEvent e) -> {
            onDeletarBens();
        });
        view.addAlterarBemListener((final ActionEvent e) -> {
            onAlterarBem();
        });
        view.addOrdemServicoListener((final ActionEvent e) -> {
            onOrdemServico();
        });
        view.addBaixarBemListener((final ActionEvent e) -> {
            onBaixa();
        });
        view.addGerarInventarioListener((final ActionEvent e) -> {
            onInventario();
        });
        view.addGerarHistoricoListener((final ActionEvent e) -> {
            onHistorico();
        });
        view.addMoverBemListener((final ActionEvent e) -> {
            onMover();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarBem() {
        final BemDTO novoBem = new BemDTO();
        novoBem.setSituacao(BemSituacaoEnum.INCORPORADO);
        novoBem.setDataAquisicao(LocalDate.now());
        novoBem.setDataCadastro(LocalDate.now());
        novoBem.setGarantia(LocalDate.now());
        novoBem.setSala(Client.requestGetSalaDeposito());

        final List<DepartamentoDTO> bens =
                Client.requestGetPossiveisDepartamentosParaBem(novoBem);
        final List<GrupoMaterialDTO> grupos =
                Client.requestGetPossiveisGruposMateriaisParaBem(novoBem);

        final BemView bemView = ViewFactory.createBem(mainPresenter.getView(),
                novoBem, true, bens, grupos);
        final BemPresenter bemPresenter =
                new BemPresenter(bemView, mainPresenter, this);
        bemView.setVisible(true);
    }

    private void onDeletarBens() {
        final List<Integer> selectedBemIds = view.getSelectedBensIds();
        if (selectedBemIds.size() < 1) {
            view.showError("Nenhum item foi selecionado.");
            return;
        }

        view.showConfirmacao("Deletar " + selectedBemIds.size() + " bem(ns)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarBens(selectedBemIds);
                    }
                });
    }

    private void deletarBens(final List<Integer> bemIds) {
        final FuncionarioDTO usuario = mainPresenter.getUsuario();

        int deletados = 0;
        for (final Integer idBem : bemIds) {
            try {
                Client.requestValidarDeleteBem(usuario, idBem);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            Client.requestDeleteBem(idBem);
            deletados++;
        }

        if (deletados > 0) {
            view.showSucesso("Bem(s) deletado(s) com sucesso.");
        }
        updateBens();
    }

    @SuppressWarnings("unused")
    private void onAlterarBem() {
        final List<Integer> selectedBemIds = view.getSelectedBensIds();
        if (selectedBemIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final Integer selectedBemId = selectedBemIds.get(0);
        final BemDTO selectedBem = Client.requestGetBemById(selectedBemId);
        final List<DepartamentoDTO> bens =
                Client.requestGetPossiveisDepartamentosParaBem(selectedBem);
        final List<GrupoMaterialDTO> grupos =
                Client.requestGetPossiveisGruposMateriaisParaBem(selectedBem);

        final BemView bemView = ViewFactory.createBem(mainPresenter.getView(),
                selectedBem, false, bens, grupos);
        final BemPresenter bemPresenter =
                new BemPresenter(bemView, mainPresenter, this);
        bemView.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void onOrdemServico() {
        final List<Integer> selectedBemIds = view.getSelectedBensIds();
        if (selectedBemIds.size() != 1) {
            throw new RuntimeException(
                    "Para ver as ordens de serviço, necessário que apenas um bem esteja selecionado.");
        }

        final Integer selectedBemId = selectedBemIds.get(0);
        final BemDTO selectedBem = Client.requestGetBemById(selectedBemId);

        final OrdensServicoView ordensView =
                ViewFactory.createOrdensServico(mainPresenter.view);
        final OrdensServicoPresenter ordensPresenter =
                new OrdensServicoPresenter(ordensView, mainPresenter, this,
                        selectedBem);
        ordensView.setVisible(true);
    }

    @SuppressWarnings("unused")
    private void onBaixa() {
        final List<Integer> selectedBemIds = view.getSelectedBensIds();
        if (selectedBemIds.size() != 1) {
            throw new RuntimeException(
                    "Para ver baixa, é necessário que apenas um bem esteja selecionado.");
        }

        final Integer selectedBemId = selectedBemIds.get(0);
        final BemDTO selectedBem = Client.requestGetBemById(selectedBemId);
        BaixaDTO baixa = Client.requestGetBaixaByIdBem(selectedBemId);
        if (baixa == null) {
            baixa = new BaixaDTO();
            baixa.setFuncionario(mainPresenter.getUsuario());
            baixa.setData(LocalDate.now());
            baixa.setBem(selectedBem);
        }

        final BaixaView baixaView = ViewFactory.createBaixa(
                mainPresenter.getView(), baixa, baixa.getId() == null);
        final BaixaPresenter baixasPresenter =
                new BaixaPresenter(baixaView, mainPresenter, this);
        baixaView.setVisible(true);
    }
    
    @SuppressWarnings("unused")
    private void onMover() {
        final List<Integer> selectedBemIds = view.getSelectedBensIds();
        if (selectedBemIds.size() != 1) {
            throw new RuntimeException(
                    "Para adicionar movimentação, é necessário que apenas um bem esteja selecionado.");
        }

        final Integer selectedBemId = selectedBemIds.get(0);
        final BemDTO selectedBem = Client.requestGetBemById(selectedBemId);
        
        MovimentacaoDTO novaMov = new MovimentacaoDTO();
        novaMov.setEtapa(EtapaMovEnum.INICIADA);
        novaMov.setBem(selectedBem);
        novaMov.setSalaOrigem(selectedBem.getSala());
        novaMov.setSalaDestino(selectedBem.getSala());
        
        final MovimentacaoView movView = ViewFactory.createMovimentacao(mainPresenter.getView(), novaMov, true);
        MovimentacaoPresenter movPresenter = new MovimentacaoPresenter(movView, mainPresenter, this);
        movView.setVisible(true);
    }

    public void updateBens() {
        final List<BemDTO> bens = Client.requestGetBens();
        view.updateBens(bens);
    }

    public void onHistorico() {
        final List<Integer> selectedBemIds = view.getSelectedBensIds();
        if (selectedBemIds.size() != 1) {
            throw new RuntimeException(
                    "Para gerar histórico, é necessário que apenas um bem esteja selecionado.");
        }

        final Integer selectedBemId = selectedBemIds.get(0);
        final BemDTO selectedBem = Client.requestGetBemById(selectedBemId);
        final HistoricoDTO historico =
                Client.requestGerarHistorico(selectedBem);

        final String fileName = "historico-bem-" + selectedBem.getId() + "_"
                + historico.getMomentoGeracao().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss"))
                + ".html";

        String htmlText = null;
        File file = null;
        try {
            htmlText = historico.toHtml();
            file = Relatorios.salvar("relatorios/" + fileName, htmlText);
        } catch (final IOException e) {
            view.showError("Não foi possível gerar o histórico.");
            e.printStackTrace();
            return;
        }

        view.showSucesso("Histórico gerado e salvo em:\n"
                + file.toPath().toAbsolutePath());
    }

    public void onInventario() {
        final InventarioDTO inventario = Client.requestGerarInventario();

        final String fileName =
                "inventario_"
                        + inventario.getMomentoGeracao()
                                .format(DateTimeFormatter
                                        .ofPattern("dd-MM-yyyy_HH-mm-ss"))
                        + ".html";

        String htmlText = null;
        File file = null;
        try {
            htmlText = inventario.toHtml();
            file = Relatorios.salvar("relatorios/" + fileName, htmlText);
        } catch (final IOException e) {
            view.showError("Não foi possível gerar o inventário.");
            e.printStackTrace();
            return;
        }

        view.showSucesso("Inventário gerado e salvo em:\n"
                + file.toPath().toAbsolutePath());
    }

}
