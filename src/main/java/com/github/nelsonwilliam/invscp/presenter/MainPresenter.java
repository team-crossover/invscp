package com.github.nelsonwilliam.invscp.presenter;

import java.awt.event.ActionEvent;

import com.github.nelsonwilliam.invscp.InvSCP;
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
import com.github.nelsonwilliam.invscp.view.ViewFactory;

/**
 * Presenter responsável pela MainView e pela MenuView contida nela.
 */
public class MainPresenter extends Presenter<MainView> {

    private final MenuView menuView;

    private Integer idUsuario;

    public MainPresenter(final MainView mainView, final MenuView menuView) {
        super(mainView);
        this.menuView = menuView;
        setupViewListeners();
    }

    public void setIdUsuario(final Integer id) {
        idUsuario = id;

        final Funcionario func = getUsuario();
        menuView.updateUsuario(func);
        showNothing();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public Funcionario getUsuario() {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        return funcRepo.getById(idUsuario);
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
        final LoginView loginView = ViewFactory.createLogin(InvSCP.VIEW_IMPL,
                view);
        final LoginPresenter loginPresenter = new LoginPresenter(loginView,
                this);
        loginView.setVisible(true);
    }

    private void showLogout() {
        setIdUsuario(null);
        menuView.updateUsuario(null);
        showNothing();
    }

    @SuppressWarnings("unused")
    private void showDepartamentos() {
        // Apenas chefes podem manter departamentos
        if (getUsuario() == null
                || !getUsuario().isChefe()) {
            showNothing();
            return;
        }

        final DepartamentosView deptView = ViewFactory
                .createDepartamentos(InvSCP.VIEW_IMPL);
        final DepartamentosPresenter deptPresenter = new DepartamentosPresenter(
                deptView, this);
        view.updateSelectedView(deptView);
    }

    @SuppressWarnings("unused")
    private void showFuncionarios() {
        // Apenas chefes podem manter departamentos
        if (getUsuario() == null
                || !getUsuario().isChefe()) {
            showNothing();
            return;
        }

        final FuncionariosView funcView = ViewFactory
                .createFuncionarios(InvSCP.VIEW_IMPL);
        final FuncionariosPresenter funcPresenter = new FuncionariosPresenter(
                funcView, this);
        view.updateSelectedView(funcView);
    }

    @SuppressWarnings("unused")
    private void showLocalizacoes() {
        // Apenas chefes podem manter departamentos
        if (getUsuario() == null
                || !getUsuario().isChefe()) {
            showNothing();
            return;
        }

        final LocalizacoesView locaView = ViewFactory
                .createLocalizacoes(InvSCP.VIEW_IMPL);
        final LocalizacoesPresenter locaPresenter = new LocalizacoesPresenter(
                locaView, this);
        view.updateSelectedView(locaView);
    }

    @SuppressWarnings("unused")
    private void showPredios() {
        // Apenas chefes podem manter departamentos
        if (getUsuario() == null
                || !getUsuario().isChefe()) {
            showNothing();
            return;
        }

        final PrediosView predView = ViewFactory
                .createPredios(InvSCP.VIEW_IMPL);
        final PrediosPresenter predPresenter = new PrediosPresenter(predView,
                this);
        view.updateSelectedView(predView);
    }

    @SuppressWarnings("unused")
    private void showSalas() {
        // Apenas chefes podem manter salas
        if (getUsuario() == null
                || !getUsuario().isChefe()) {
            showNothing();
            return;
        }

        final SalasView salasView = ViewFactory.createSalas(InvSCP.VIEW_IMPL);
        final SalasPresenter salasPresenter = new SalasPresenter(salasView,
                this);
        view.updateSelectedView(salasView);
    }
}
