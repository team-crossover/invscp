package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.InvSCP;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.view.SalaView;
import com.github.nelsonwilliam.invscp.view.SalasView;
import com.github.nelsonwilliam.invscp.view.ViewFactory;

public class SalasPresenter extends Presenter<SalasView> {
    private final MainPresenter mainPresenter;

    public SalasPresenter(final SalasView view,
            final MainPresenter mainPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        setupViewListeners();
        updateSalas();
    }

    private void setupViewListeners() {
        view.addAdicionarSalaListener((final ActionEvent e) -> {
            onAdicionarSala();
        });
        view.addDeletarSalasListener((final ActionEvent e) -> {
            onDeletarSalas();
        });
        view.addAlterarSalaListener((final ActionEvent e) -> {
            onAlterarSala();
        });
    }

    @SuppressWarnings("unused")
    private void onAdicionarSala() {
        final Sala novoSala = new Sala();
        final SalaDTO novoSalaDTO = new SalaDTO();
        novoSalaDTO.fromModel(novoSala);

        final List<Predio> predios = novoSala.getPossiveisPredios();
        final List<PredioDTO> prediosDTOs = new ArrayList<PredioDTO>();
        for (final Predio p : predios) {
            final PredioDTO predioDTO = new PredioDTO();
            predioDTO.fromModel(p);
            prediosDTOs.add(predioDTO);
        }

        final List<Departamento> depts = novoSala.getPossiveisDepartamentos();
        final List<DepartamentoDTO> deptsDTOs = new ArrayList<DepartamentoDTO>();
        for (final Departamento d : depts) {
            final DepartamentoDTO deptDTO = new DepartamentoDTO();
            deptDTO.fromModel(d);
            deptsDTOs.add(deptDTO);
        }

        final SalaView salaView = ViewFactory.createSala(InvSCP.VIEW_IMPL,
                mainPresenter.getView(), novoSalaDTO, false, prediosDTOs,
                deptsDTOs);
        final SalaPresenter salaPresenter = new SalaPresenter(salaView,
                mainPresenter, this);
        salaView.setVisible(true);
    }

    private void onDeletarSalas() {
        final List<Integer> selectedPrdiosIds = view.getSelectedSalasIds();
        view.showConfirmacao(
                "Deletar " + selectedPrdiosIds.size() + " salas(s)?",
                (final Boolean confirmado) -> {
                    if (confirmado) {
                        deletarSalas(selectedPrdiosIds);
                    }
                });
    }

    private void deletarSalas(final List<Integer> salasIds) {
        final Funcionario usuario = mainPresenter.getUsuario();
        final SalaRepository salaRepo = new SalaRepository();

        int deletados = 0;
        for (int i = 0; i < salasIds.size(); i++) {
            final Integer idSala = salasIds.get(i);

            try {
                Sala.validarDeletar(usuario, idSala);
            } catch (final IllegalDeleteException e) {
                view.showError(e.getMessage());
                continue;
            }

            final Sala sala = salaRepo.getById(idSala);
            salaRepo.remove(sala);
            deletados++;
        }

        if (deletados > 0) {
            view.showSucesso("Exclusão de sala(s) concluída.");
        }
        updateSalas();
    }

    @SuppressWarnings("unused")
    private void onAlterarSala() {
        final List<Integer> selectedSalaIds = view.getSelectedSalasIds();
        if (selectedSalaIds.size() != 1) {
            throw new RuntimeException(
                    "Para alterar um elemento é necessário que apenas um esteja selecionado.");
        }

        final SalaRepository salaRepo = new SalaRepository();
        final Sala selectedSala = salaRepo.getById(selectedSalaIds.get(0));
        final SalaDTO selectedSalaDTO = new SalaDTO();
        selectedSalaDTO.fromModel(selectedSala);

        final List<Predio> predios = selectedSala.getPossiveisPredios();
        final List<PredioDTO> prediosDTOs = new ArrayList<PredioDTO>();
        for (final Predio p : predios) {
            final PredioDTO predioDTO = new PredioDTO();
            predioDTO.fromModel(p);
            prediosDTOs.add(predioDTO);
        }

        final List<Departamento> depts = selectedSala
                .getPossiveisDepartamentos();
        final List<DepartamentoDTO> deptsDTOs = new ArrayList<DepartamentoDTO>();
        for (final Departamento d : depts) {
            final DepartamentoDTO deptDTO = new DepartamentoDTO();
            deptDTO.fromModel(d);
            deptsDTOs.add(deptDTO);
        }

        final SalaView salaView = ViewFactory.createSala(InvSCP.VIEW_IMPL,
                mainPresenter.getView(), selectedSalaDTO, false, prediosDTOs,
                deptsDTOs);
        final SalaPresenter salaPresenter = new SalaPresenter(salaView,
                mainPresenter, this);
        salaView.setVisible(true);
    }

    public void updateSalas() {
        final SalaRepository salaRepo = new SalaRepository();
        final List<Sala> salas = salaRepo.getAll();
        final List<SalaDTO> salasDTOs = new ArrayList<SalaDTO>();
        for (final Sala s : salas) {
            final SalaDTO salaDTO = new SalaDTO();
            salaDTO.fromModel(s);
            salasDTOs.add(salaDTO);
        }
        view.updateSalas(salasDTOs);
    }

}
