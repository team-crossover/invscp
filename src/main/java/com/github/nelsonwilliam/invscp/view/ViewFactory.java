package com.github.nelsonwilliam.invscp.view;

import java.util.List;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.view.swing.DepartamentoSwingView;
import com.github.nelsonwilliam.invscp.view.swing.DepartamentosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.FuncionarioSwingView;
import com.github.nelsonwilliam.invscp.view.swing.FuncionariosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.LocalizacaoSwingView;
import com.github.nelsonwilliam.invscp.view.swing.LocalizacoesSwingView;
import com.github.nelsonwilliam.invscp.view.swing.LoginSwingView;
import com.github.nelsonwilliam.invscp.view.swing.MainSwingView;
import com.github.nelsonwilliam.invscp.view.swing.MenuSwingView;
import com.github.nelsonwilliam.invscp.view.swing.PredioSwingView;
import com.github.nelsonwilliam.invscp.view.swing.PrediosSwingView;
import com.github.nelsonwilliam.invscp.view.swing.SalaSwingView;
import com.github.nelsonwilliam.invscp.view.swing.SalasSwingView;

public class ViewFactory {

    public static DepartamentosView createDepartamentos(
            final ViewImplementation impl) {

        switch (impl) {
            case SWING:
                return new DepartamentosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static DepartamentoView createDepartamento(
            final ViewImplementation impl, final MainView main,
            final Departamento dept, final boolean isAddition) {

        switch (impl) {
            case SWING:
                return new DepartamentoSwingView((JFrame) main, dept,
                        isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static FuncionariosView createFuncionarios(
            final ViewImplementation impl) {

        switch (impl) {
            case SWING:
                return new FuncionariosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static FuncionarioView createFuncionario(
            final ViewImplementation impl, final MainView main,
            final Funcionario func, final boolean isAddition) {

        switch (impl) {
            case SWING:
                return new FuncionarioSwingView((JFrame) main, func,
                        isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LocalizacaoView createLocalizacao(
            final ViewImplementation impl, final MainView main,
            final Localizacao loca, final boolean isAddition) {

        switch (impl) {
            case SWING:
                return new LocalizacaoSwingView((JFrame) main, loca,
                        isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LocalizacoesView createLocalizacoes(
            final ViewImplementation impl) {

        switch (impl) {
            case SWING:
                return new LocalizacoesSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LoginView createLogin(final ViewImplementation impl,
            final MainView main) {

        switch (impl) {
            case SWING:
                return new LoginSwingView((JFrame) main);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MainView createMain(final ViewImplementation impl,
            final MenuView menu) {

        switch (impl) {
            case SWING:
                return new MainSwingView(menu);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MenuView createMenu(final ViewImplementation impl) {

        switch (impl) {
            case SWING:
                return new MenuSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PrediosView createPredios(final ViewImplementation impl) {

        switch (impl) {
            case SWING:
                return new PrediosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PredioView createPredio(final ViewImplementation impl,
            final MainView main, final Predio predio,
            final boolean isAddition) {

        switch (impl) {
            case SWING:
                return new PredioSwingView((JFrame) main, predio, isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static SalasView createSalas(final ViewImplementation impl) {

        switch (impl) {
            case SWING:
                return new SalasSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static SalaView createSala(final ViewImplementation impl,
            final MainView main, final SalaDTO sala, final boolean isAddition,
            final List<PredioDTO> predios,
            final List<DepartamentoDTO> departamentos) {

        switch (impl) {
            case SWING:
                return new SalaSwingView((JFrame) main, sala, isAddition,
                        predios, departamentos);
            default:
                throw new IllegalArgumentException();
        }
    }

    public enum ViewImplementation {
        SWING
    }
}
