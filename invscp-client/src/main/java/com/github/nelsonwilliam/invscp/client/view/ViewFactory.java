package com.github.nelsonwilliam.invscp.client.view;

import java.util.List;

import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.client.util.ClientSettings;
import com.github.nelsonwilliam.invscp.client.view.swing.BaixaSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.BemSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.BensSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.DepartamentoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.DepartamentosSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.EventoMovimentacaoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.EventosMovimentacaoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.FuncionarioSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.FuncionariosSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.LocalizacaoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.LocalizacoesSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.LoginSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.MainSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.MenuSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.MovimentacaoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.MovimentacoesSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.OrdemServicoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.OrdensServicoSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.PredioSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.PrediosSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.SalaSwingView;
import com.github.nelsonwilliam.invscp.client.view.swing.SalasSwingView;
import com.github.nelsonwilliam.invscp.shared.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.SalaDTO;

public class ViewFactory {

    public static DepartamentosView createDepartamentos() {
        switch (ClientSettings.getViewImpl()) {
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

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new DepartamentoSwingView((JFrame) main, dept,
                        isAddition, chefes, chefesSubsts);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static FuncionariosView createFuncionarios() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new FuncionariosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static FuncionarioView createFuncionario(final MainView main,
            final FuncionarioDTO func, final boolean isAddition,
            final List<DepartamentoDTO> departamentos) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new FuncionarioSwingView((JFrame) main, func, isAddition,
                        departamentos);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LocalizacaoView createLocalizacao(final MainView main,
            final LocalizacaoDTO loca, final boolean isAddition) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new LocalizacaoSwingView((JFrame) main, loca,
                        isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LocalizacoesView createLocalizacoes() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new LocalizacoesSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static LoginView createLogin(final MainView main) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new LoginSwingView((JFrame) main);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MainView createMain(final MenuView menu) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new MainSwingView(menu);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MenuView createMenu() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new MenuSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PrediosView createPredios() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new PrediosSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static PredioView createPredio(final MainView main,
            final PredioDTO predio, final boolean isAddition,
            final List<LocalizacaoDTO> locas) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new PredioSwingView((JFrame) main, predio, isAddition,
                        locas);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static SalasView createSalas() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new SalasSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static SalaView createSala(final MainView main, final SalaDTO sala,
            final boolean isAddition, final List<PredioDTO> predios,
            final List<DepartamentoDTO> departamentos) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new SalaSwingView((JFrame) main, sala, isAddition,
                        predios, departamentos);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static BensView createBens() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new BensSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static BemView createBem(final MainView main, final BemDTO bem,
            final boolean isAddition, final List<DepartamentoDTO> depts,
            final List<GrupoMaterialDTO> gruposMateriais) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new BemSwingView((JFrame) main, bem, isAddition, depts,
                        gruposMateriais);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static BaixaView createBaixa(final MainView main, final BaixaDTO bem,
            final boolean isAddition) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new BaixaSwingView((JFrame) main, bem, isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static OrdensServicoView createOrdensServico(final MainView main) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new OrdensServicoSwingView((JFrame) main);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static OrdemServicoView createOrdemServico(final MainView main,
            final OrdemServicoDTO ordemServico, final boolean isAddition) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new OrdemServicoSwingView((JFrame) main, ordemServico,
                        isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MovimentacoesView createMovimentacoes() {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new MovimentacoesSwingView();
            default:
                throw new IllegalArgumentException();
        }
    }

    public static MovimentacaoView createMovimentacao(final MainView main,
            final MovimentacaoDTO mov, final boolean isAddition) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new MovimentacaoSwingView((JFrame) main, mov, isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static EventosMovimentacaoView createEventosMovimentacao(final MainView main) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new EventosMovimentacaoSwingView((JFrame) main);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static EventoMovimentacaoView createEventoMovimentacao(final MainView main,
            final EventoMovimentacaoDTO mov, final boolean isAddition) {

        switch (ClientSettings.getViewImpl()) {
            case SWING:
                return new EventoMovimentacaoSwingView((JFrame) main, mov, isAddition);
            default:
                throw new IllegalArgumentException();
        }
    }

    public enum ViewImplementation {
        SWING
    }
}
