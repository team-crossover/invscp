package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentoView;

public class DepartamentoPresenter extends Presenter<DepartamentoView> {

    private final MainPresenter mainPresenter;
    private final DepartamentosPresenter deptsPresenter;

    public DepartamentoPresenter(final DepartamentoView view, final MainPresenter mainPresenter,
            final DepartamentosPresenter deptsPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.deptsPresenter = deptsPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Departamento dept = view.getDepartamento();
        final Departamento deptAntigo = deptRepo.getById(dept.getId());

        final Funcionario funcLogado = mainPresenter.getUsuario();
        final Integer funcLogadoDeptId = funcLogado.getIdDepartamento();
        final boolean funcLogadoEraChefeDept = funcLogado.isChefeDeDepartamento();
        final boolean funcLogadoEraChefePatrimonio = funcLogado.isChefeDePatrimonio();

        // CONTROLE DE ACESSO

        if (dept.getId() == null && !funcLogadoEraChefePatrimonio) {
            view.showError("Apenas chefes de patrimônio podem criar novos departamentos.");
            return;
        }

        if (dept.getId() != null && !(funcLogadoEraChefePatrimonio
                || (funcLogadoEraChefeDept && funcLogadoDeptId.equals(dept.getId())))) {
            view.showError(
                    "Apenas chefes de patrimônio e chefes do próprio departamento podem alterá-lo.");
            return;
        }

        // VALIDADE DOS DADOS

        if (dept.getNome() == null || dept.getNome().isEmpty()) {
            view.showError("O 'nome' é um campo obrigatório.");
            return;
        }
        if (dept.getDePatrimonio() == null) {
            view.showError("O 'de patrimônio' é um campo obrigatório.");
            return;
        }
        if (dept.getDePatrimonio() && !dept.getId().equals(deptRepo.getDePatrimonio().getId())) {
            view.showError("Só pode existir um departamento 'de patrimônio'.");
            return;
        }
        if (dept.getIdChefe() == null) {
            view.showError("O 'chefe' é um campo obrigatório.");
            return;
        }
        if (dept.getChefe() == null) {
            view.showError("O 'chefe' selecionado não existe.");
            return;
        }
        if (dept.getChefe().getIdDepartamento() != null
                && !dept.getChefe().getIdDepartamento().equals(dept.getId())) {
            System.out.println(dept.getChefe().getIdDepartamento());
            view.showError("O 'chefe' selecionado não pode pertencer a outro departamento.");
            return;
        }
        if (dept.getIdChefeSubstituto() != null
                && dept.getIdChefeSubstituto().equals(dept.getIdChefe())) {
            view.showError("O 'chefe' não pode ser o mesmo que o 'chefe substituto'.");
            return;
        }

        // APLICA A ALTERAÇÃO

        if (dept.getId() == null) {
            onConfirmarAdicao(dept);
        } else {
            onConfirmarAtualizacao(deptAntigo, dept);
        }

        // PÓS-ATUALIZAÇÕES

        // Se o novo chefe não possuia departamento, ele deve ser movido para o departamento
        // alterado.
        if (dept.getChefe().getIdDepartamento() == null) {
            final Funcionario chefe = dept.getChefe();
            chefe.setIdDepartamento(dept.getId());
            funcRepo.update(chefe);
            view.showInfo("O funcionário " + chefe.getNome() + " foi movido ao departamento "
                    + dept.getNome() + ".");
        }

        // Se o departamento do funcionario logado tiver sido alterado, força a reatualização da
        // exibição da tela para este usuário
        if (funcLogado.getIdDepartamento().equals(dept.getId())) {
            mainPresenter.setIdUsuario(funcLogado.getId());
            return;
        }
    }

    private void onConfirmarAdicao(final Departamento deptNovo) {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        deptRepo.add(deptNovo);
        view.showSucesso();
        view.close();
        deptsPresenter.updateDepartamentos();
    }

    private void onConfirmarAtualizacao(final Departamento deptAnterior,
            final Departamento deptAtualizado) {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        deptRepo.update(deptAtualizado);
        view.showSucesso();
        view.close();
        deptsPresenter.updateDepartamentos();
    }
}
