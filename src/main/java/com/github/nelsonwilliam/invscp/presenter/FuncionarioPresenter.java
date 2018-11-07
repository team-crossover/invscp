package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.view.FuncionarioView;

public class FuncionarioPresenter extends Presenter<FuncionarioView> {

    private final MainPresenter mainPresenter;
    private final FuncionariosPresenter funcsPresenter;

    public FuncionarioPresenter(final FuncionarioView view, final MainPresenter mainPresenter,
            final FuncionariosPresenter funcsPresenter) {
        super(view);
        this.mainPresenter = mainPresenter;
        this.funcsPresenter = funcsPresenter;
        setupViewListeners();
    }

    private void setupViewListeners() {
        view.addConfirmarListener((final ActionEvent e) -> {
            onConfirmar();
        });
    }

    private void onConfirmar() {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario func = view.getFuncionario();
        final Funcionario funcLogado = mainPresenter.getUsuario();
        final Funcionario funcAntigo = funcRepo.getById(func.getId());
        final boolean eraChefePrincipal = funcAntigo != null
                && (funcAntigo.isChefeDeDepartamentoPrincipal()
                        || funcAntigo.isChefeDePatrimonioPrincipal());
        final boolean eraChefeSubstituto = funcAntigo != null
                && (funcAntigo.isChefeDeDepartamentoSubstituto()
                        || funcAntigo.isChefeDePatrimonioSubstituto());

        // VALIDAÇÃO DOS DADOS

        if (func.getLogin() == null || func.getLogin().isEmpty()) {
            view.showError("O 'login' é um campo obrigatório.");
            return;
        }
        final Funcionario funcByLogin = funcRepo.getByLogin(func.getLogin());
        if (funcByLogin != null && !funcByLogin.getId().equals(func.getId())) {
            view.showError("O 'login' informado já foi utilizado por outro funcionário.");
            return;
        }
        if (func.getSenha() == null || func.getSenha().isEmpty()) {
            view.showError("A 'senha' é um campo obrigatório.");
            return;
        }
        if (func.getNome() == null || func.getNome().isEmpty()) {
            view.showError("O 'nome' é um campo obrigatório.");
            return;
        }
        if (func.getCpf() == null) {
            view.showError("O 'CPF' é um campo obrigatório.");
            return;
        }
        if (func.getCpf().length() != 11) {
            view.showError("O 'CPF' deve possuir exatamente 11 números, sem traços ou pontos.");
            return;
        }
        if (func.getEmail() == null || func.getEmail().isEmpty()) {
            view.showError("O 'e-mail' é um campo obrigatório.");
            return;
        }
        if (func.getIdDepartamento() != null && func.getDepartamento() == null) {
            view.showError("O 'departamento' selecionado não existe.");
            return;
        }

        if (funcAntigo != null) {
            if (eraChefePrincipal && (func.getIdDepartamento() == null
                    || !funcAntigo.getIdDepartamento().equals(func.getIdDepartamento()))) {
                view.showError(
                        "O 'departamento' deste funcionário não pode ser alterado pois ele(a) é chefe principal de seu departamento atual.");
                return;
            }
        }

        // APLICA A ALTERAÇÃO

        if (func.getId() == null) {
            onConfirmarAdicao(func);
        } else {
            onConfirmarAtualizacao(funcAntigo, func);
        }

        // PÓS-ATUALIZAÇÕES

        // Se era chefe substituto e mudou de departamento, o departamento antigo deixa de ter chefe
        // substituto.
        if (eraChefeSubstituto && (func.getIdDepartamento() == null
                || !func.getIdDepartamento().equals(funcAntigo.getIdDepartamento()))) {
            final DepartamentoRepository deptRepo = new DepartamentoRepository();
            final Departamento deptAntigo = funcAntigo.getDepartamento();
            deptAntigo.setIdChefeSubstituto(null);
            deptRepo.update(deptAntigo);
            view.showInfo(
                    "O 'departamento' anterior deste funcionário não possui mais chefe substituto.");
        }

        // Se o funcionário atualizado é o que estava logado, atualiza o main para garantir dados
        // atualizados são exibidos no menu.
        if (funcLogado.getId().equals(func.getId())) {
            mainPresenter.setIdUsuario(funcLogado.getId());
            return;
        }

    }

    private void onConfirmarAdicao(final Funcionario funcNovo) {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        funcRepo.add(funcNovo);
        view.showSucesso();
        view.close();
        funcsPresenter.updateFuncionarios();

    }

    private void onConfirmarAtualizacao(final Funcionario funcAnterior,
            final Funcionario funcAtualizado) {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        funcRepo.update(funcAtualizado);
        view.showSucesso();
        view.close();
        funcsPresenter.updateFuncionarios();
    }
}
