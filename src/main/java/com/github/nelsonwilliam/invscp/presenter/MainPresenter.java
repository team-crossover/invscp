package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.view.DepartamentosView;
import com.github.nelsonwilliam.invscp.view.FuncionariosView;
import com.github.nelsonwilliam.invscp.view.LocalizacoesView;
import com.github.nelsonwilliam.invscp.view.LoginView;
import com.github.nelsonwilliam.invscp.view.MainView;
import com.github.nelsonwilliam.invscp.view.MenuView;
import com.github.nelsonwilliam.invscp.view.PrediosView;
import com.github.nelsonwilliam.invscp.view.SalasView;
import com.github.nelsonwilliam.invscp.view.swing.DepartamentosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.FuncionariosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.LocalizacoesSwingView;
import com.github.nelsonwilliam.invscp.view.swing.LoginSwingView;
import com.github.nelsonwilliam.invscp.view.swing.PrediosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.SalasSwingView;

/**
 * Presenter responsável pela MainView e pela MenuView contida nela.
 */
public class MainPresenter extends Presenter<MainView> {

    private final MenuView menuView;

    private Integer idFuncionarioLogado;

    public MainPresenter(final MainView mainView, final MenuView menuView) {
        super(mainView);
        this.menuView = menuView;
        setupViewListeners();
    }

    public void setIdFuncionarioLogado(final Integer id) {
        idFuncionarioLogado = id;

        final Funcionario func = getFuncionarioLogado();
        menuView.updateFuncionarioLogado(func);
        showNothing();
    }

    public Integer getIdFuncionarioLogado() {
        return idFuncionarioLogado;
    }

    public Funcionario getFuncionarioLogado() {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        return funcRepo.getById(idFuncionarioLogado);
    }

    private void setupViewListeners() {
        menuView.addLoginListener((final ActionEvent e) -> {
            showLogin();
        });
        menuView.addLogoutListener((final ActionEvent e) -> {
            showLogout();
        });
        menuView.addDepartamentosListener((final ActionEvent e) -> {
            showDepartamentos();
        });
        menuView.addFuncionariosListener((final ActionEvent e) -> {
            showFuncionarios();
        });
        menuView.addLocalizacoesListener((final ActionEvent e) -> {
            showLocalizacoes();
        });
        menuView.addPrediosListener((final ActionEvent e) -> {
            showPredios();
        });
        menuView.addSalasListener((final ActionEvent e) -> {
            showSalas();
        });
    }

    private void showNothing() {
        view.updateSelectedView(null);
    }

    @SuppressWarnings("unused")
    private void showLogin() {
        final LoginView loginView = new LoginSwingView((JFrame) getView());
        final LoginPresenter loginPresenter = new LoginPresenter(loginView, this);
        loginView.setVisible(true);
    }

    private void showLogout() {
        setIdFuncionarioLogado(null);
        menuView.updateFuncionarioLogado(null);
        showNothing();
    }

    @SuppressWarnings("unused")
    private void showDepartamentos() {
        // Apenas chefes podem manter departamentos
        if (getFuncionarioLogado() == null || !getFuncionarioLogado().isChefe()) {
            showNothing();
            return;
        }

        final DepartamentosView deptView = new DepartamentosSwingView();
        final DepartamentosPresenter deptPresenter = new DepartamentosPresenter(deptView, this);
        view.updateSelectedView(deptView);
    }

    @SuppressWarnings("unused")
    private void showFuncionarios() {
        // Apenas chefes podem manter departamentos
        if (getFuncionarioLogado() == null || !getFuncionarioLogado().isChefe()) {
            showNothing();
            return;
        }

        final FuncionariosView funcView = new FuncionariosSwingView();
        final FuncionariosPresenter funcPresenter = new FuncionariosPresenter(funcView, this);
        view.updateSelectedView(funcView);
    }

    @SuppressWarnings("unused")
    private void showLocalizacoes() {
        // Apenas chefes podem manter departamentos
        if (getFuncionarioLogado() == null || !getFuncionarioLogado().isChefe()) {
            showNothing();
            return;
        }

        final LocalizacoesView locaView = new LocalizacoesSwingView();
        final LocalizacoesPresenter locaPresenter = new LocalizacoesPresenter(locaView, this);
        view.updateSelectedView(locaView);
    }

    @SuppressWarnings("unused")
    private void showPredios() {
        // Apenas chefes podem manter departamentos
        if (getFuncionarioLogado() == null || !getFuncionarioLogado().isChefe()) {
            showNothing();
            return;
        }

        final PrediosView predView = new PrediosSwingView();
        final PrediosPresenter predPresenter = new PrediosPresenter(predView, this);
        view.updateSelectedView(predView);
    }

    @SuppressWarnings("unused")
    private void showSalas() {
        // Apenas chefes podem manter salas
        if (getFuncionarioLogado() == null || !getFuncionarioLogado().isChefe()) {
            showNothing();
            return;
        }

        final SalasView salasView = new SalasSwingView();
        final SalasPresenter salasPresenter = new SalasPresenter(salasView, this);
        view.updateSelectedView(salasView);
    }
}
