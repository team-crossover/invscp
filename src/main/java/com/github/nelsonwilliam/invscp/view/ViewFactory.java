package com.github.nelsonwilliam.invscp.view;

import java.util.List;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.util.ClientSettings;
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

    public static DepartamentosView createDepartamentos() {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new DepartamentosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static DepartamentoView createDepartamento(final MainView main,
            final DepartamentoDTO dept, final boolean isAddition,
            final List<FuncionarioDTO> chefes,
            final List<FuncionarioDTO> chefesSubsts) {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new DepartamentoSwingView((JFrame) main, dept,
                        isAddition, chefes, chefesSubsts);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static FuncionariosView createFuncionarios() {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new FuncionariosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static FuncionarioView createFuncionario(final MainView main,
            final FuncionarioDTO func, final boolean isAddition,
            final List<DepartamentoDTO> departamentos) {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new FuncionarioSwingView((JFrame) main, func, isAddition,
                        departamentos);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LocalizacaoView createLocalizacao(final MainView main,
            final LocalizacaoDTO loca, final boolean isAddition) {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new LocalizacaoSwingView((JFrame) main, loca,
                        isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LocalizacoesView createLocalizacoes() {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new LocalizacoesSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LoginView createLogin(final MainView main) {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new LoginSwingView((JFrame) main);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MainView createMain(final MenuView menu) {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new MainSwingView(menu);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MenuView createMenu() {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new MenuSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PrediosView createPredios() {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new PrediosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PredioView createPredio(final MainView main,
            final PredioDTO predio, final boolean isAddition,
            final List<LocalizacaoDTO> locas) {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new PredioSwingView((JFrame) main, predio, isAddition,
                        locas);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static SalasView createSalas() {

        switch (ClientSettings.VIEW_IMPL) {
            case SWING:
                return new SalasSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static SalaView createSala(final MainView main, final SalaDTO sala,
            final boolean isAddition, final List<PredioDTO> predios,
            final List<DepartamentoDTO> departamentos) {

        switch (ClientSettings.VIEW_IMPL) {
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
