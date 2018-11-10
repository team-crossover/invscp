package com.github.nelsonwilliam.invscp.util;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.Departamento;
import com.github.nelsonwilliam.invscp.model.Funcionario;
import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

/**
 * Classe utilizada por um cliente para enviar requisições ao servidor
 * (recebidas por {@link ClientHandler}), e receber respostas apropriadas.
 */
public class Client {

    // TODO Por enquanto não é utilizado pois o cliente-servidor não foi
    // implementado. Futuramente, a classe main do projeto cliente deve ser
    // capaz de inicializar a Client e conectá-la ao servidor. Por enquanto,
    // todas as requisições são respondidas diretamente, já que cliente e
    // servidor estão juntos.

    public static void connect() {
        // TODO
    }

    public static void close() {
        // TODO
    }

    // ------------
    // FUNCIONARIO
    // ------------

    public static void requestValidarInserirFuncionario(
            final FuncionarioDTO usuario, final FuncionarioDTO novo)
            throws IllegalInsertException {
        Funcionario.validarInserir(usuario, novo);
    }

    public static void requestValidarAlterarFuncionario(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final FuncionarioDTO novo) throws IllegalUpdateException {
        Funcionario.validarAlterar(usuario, idAntigo, novo);
    }

    public static void requestValidarDeleteFuncionario(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {
        Funcionario.validarDeletar(usuario, id);
    }

    public static void requestAddFuncionario(final FuncionarioDTO item) {
        final FuncionarioRepository repo = new FuncionarioRepository();
        final Funcionario itemModel = new Funcionario();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
    }

    public static void requestUpdateFuncionario(final FuncionarioDTO item) {
        final FuncionarioRepository repo = new FuncionarioRepository();
        final Funcionario itemModel = new Funcionario();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
    }

    public static void requestDeleteFuncionario(final Integer itemId) {
        final FuncionarioRepository repo = new FuncionarioRepository();
        final Funcionario placeholderItem = new Funcionario();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
    }

    public static FuncionarioDTO requestGetFuncionarioByLogin(
            final String login) {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario func = funcRepo.getByLogin(login);
        if (func == null) {
            return null;
        }
        return func.toDTO();
    }

    public static List<FuncionarioDTO> requestGetFuncionarios() {
        final FuncionarioRepository repo = new FuncionarioRepository();
        final List<Funcionario> models = repo.getAll();
        final List<FuncionarioDTO> dtos = new ArrayList<>();
        for (final Funcionario model : models) {
            dtos.add(model.toDTO());
        }
        return dtos;
    }

    public static List<DepartamentoDTO> requestGetPossiveisDepartamentosParaFuncionario(
            final FuncionarioDTO func) {
        final List<DepartamentoDTO> depts = requestGetDepartamentos();
        if (func.getDepartamento() != null) {
            for (final DepartamentoDTO dept : depts) {
                if (dept.getId().equals(func.getDepartamento().getId())) {
                    depts.remove(dept);
                    break;
                }
            }
        }
        return depts;
    }

    public static List<FuncionarioDTO> requestGetFuncionariosByUsuario(
            final FuncionarioDTO usuario) {

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final List<Funcionario> funcs;
        if (usuario.getCargo().isChefeDePatrimonio()) {
            funcs = funcRepo.getAll();
        } else if (usuario.getCargo().isChefeDeDepartamento()) {
            final Departamento placeholderDept = new Departamento();
            placeholderDept.setId(usuario.getDepartamento().getId());
            funcs = funcRepo.getByDepartamento(placeholderDept);
        } else {
            throw new AccessControlException(
                    "Apenas chefes devem gerenciar funcionários.");
        }

        final List<FuncionarioDTO> dtos = new ArrayList<>();
        for (final Funcionario func : funcs) {
            dtos.add(func.toDTO());
        }
        return dtos;
    }

    public static FuncionarioDTO requestGetFuncionarioById(final Integer id) {
        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario func = funcRepo.getById(id);
        if (func == null) {
            return null;
        }
        return func.toDTO();
    }

    // --------------
    // DEPARTAMENTO
    // --------------

    public static void requestValidarInserirDepartamento(
            final FuncionarioDTO usuario, final DepartamentoDTO novo)
            throws IllegalInsertException {
        Departamento.validarInserir(usuario, novo);
    }

    public static void requestValidarAlterarDepartamento(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final DepartamentoDTO novo) throws IllegalUpdateException {
        Departamento.validarAlterar(usuario, idAntigo, novo);
    }

    public static void requestValidarDeleteDepartamento(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {
        Departamento.validarDeletar(usuario, id);
    }

    public static List<String> requestPosAlterarDepartamento(
            final FuncionarioDTO usuario, final DepartamentoDTO item) {
        return Departamento.posAlterar(usuario, item);
    }

    public static void requestAddDepartamento(final DepartamentoDTO item) {
        final DepartamentoRepository repo = new DepartamentoRepository();
        final Departamento itemModel = new Departamento();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
    }

    public static void requestUpdateDepartamento(final DepartamentoDTO item) {
        final DepartamentoRepository repo = new DepartamentoRepository();
        final Departamento itemModel = new Departamento();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
    }

    public static void requestDeleteDepartamento(final Integer itemId) {
        final DepartamentoRepository repo = new DepartamentoRepository();
        final Departamento placeholderItem = new Departamento();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
    }

    public static List<DepartamentoDTO> requestGetDepartamentos() {
        final DepartamentoRepository repo = new DepartamentoRepository();
        final List<Departamento> models = repo.getAll();
        final List<DepartamentoDTO> dtos = new ArrayList<>();
        for (final Departamento model : models) {
            dtos.add(model.toDTO());
        }
        return dtos;
    }

    public static List<FuncionarioDTO> requestGetPossiveisChefesParaDepartamento(
            final DepartamentoDTO dept) {

        final Departamento placeholderDept = new Departamento();
        placeholderDept.setId(dept.getId());

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final List<Funcionario> funcs = (dept == null || dept.getId() == null)
                ? funcRepo.getSemDepartamento()
                : funcRepo.getByDepartamentoExcetoChefes(placeholderDept);

        final List<FuncionarioDTO> dtos = new ArrayList<>();
        for (final Funcionario model : funcs) {
            dtos.add(model.toDTO());
        }
        return dtos;
    }

    public static DepartamentoDTO requestGetDepartamentoById(final Integer id) {
        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final Departamento dept = deptRepo.getById(id);
        if (dept == null) {
            return null;
        }
        return dept.toDTO();
    }

    // -------
    // LOCALIZACAO
    // -------

    public static void requestValidarInserirLocalizacao(
            final FuncionarioDTO usuario, final LocalizacaoDTO novo)
            throws IllegalInsertException {
        Localizacao.validarInserir(usuario, novo);
    }

    public static void requestValidarAlterarLocalizacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final LocalizacaoDTO novo) throws IllegalUpdateException {
        Localizacao.validarAlterar(usuario, idAntigo, novo);
    }

    public static void requestValidarDeleteLocalizacao(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {
        Localizacao.validarDeletar(usuario, id);
    }

    public static void requestAddLocalizacao(final LocalizacaoDTO item) {
        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final Localizacao itemModel = new Localizacao();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
    }

    public static void requestUpdateLocalizacao(final LocalizacaoDTO item) {
        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final Localizacao itemModel = new Localizacao();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
    }

    public static void requestDeleteLocalizacao(final Integer itemId) {
        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final Localizacao placeholderItem = new Localizacao();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
    }

    public static List<LocalizacaoDTO> requestGetLocalizacoes() {
        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final List<Localizacao> models = repo.getAll();
        final List<LocalizacaoDTO> dtos = new ArrayList<>();
        for (final Localizacao model : models) {
            dtos.add(model.toDTO());
        }
        return dtos;
    }

    public static LocalizacaoDTO requestGetLocalizacaoById(final Integer id) {
        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final Localizacao loca = locaRepo.getById(id);
        if (loca == null) {
            return null;
        }
        return loca.toDTO();
    }
    // -------
    // PREDIO
    // -------

    public static void requestValidarInserirPredio(final FuncionarioDTO usuario,
            final PredioDTO novo) throws IllegalInsertException {
        Predio.validarInserir(usuario, novo);
    }

    public static void requestValidarAlterarPredio(final FuncionarioDTO usuario,
            final Integer idAntigo, final PredioDTO novo)
            throws IllegalUpdateException {
        Predio.validarAlterar(usuario, idAntigo, novo);
    }

    public static void requestValidarDeletePredio(final FuncionarioDTO usuario,
            final Integer id) throws IllegalDeleteException {
        Predio.validarDeletar(usuario, id);
    }

    public static void requestAddPredio(final PredioDTO item) {
        final PredioRepository repo = new PredioRepository();
        final Predio itemModel = new Predio();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
    }

    public static void requestUpdatePredio(final PredioDTO item) {
        final PredioRepository repo = new PredioRepository();
        final Predio itemModel = new Predio();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
    }

    public static void requestDeletePredio(final Integer itemId) {
        final PredioRepository repo = new PredioRepository();
        final Predio placeholderItem = new Predio();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
    }

    public static List<PredioDTO> requestGetPredios() {
        final PredioRepository repo = new PredioRepository();
        final List<Predio> models = repo.getAll();
        final List<PredioDTO> dtos = new ArrayList<>();
        for (final Predio model : models) {
            dtos.add(model.toDTO());
        }
        return dtos;
    }

    public static PredioDTO requestGetPredioById(final Integer id) {
        final PredioRepository predioRepo = new PredioRepository();
        final Predio predio = predioRepo.getById(id);
        if (predio == null) {
            return null;
        }
        return predio.toDTO();
    }

    public static List<LocalizacaoDTO> requestGetPossiveisLocalizacoesParaPredio(
            final PredioDTO predio) {
        return requestGetLocalizacoes();
    }

    // -----
    // SALA
    // -----

    public static void requestValidarInserirSala(final FuncionarioDTO usuario,
            final SalaDTO novaSala) throws IllegalInsertException {
        Sala.validarInserir(usuario, novaSala);
    }

    public static void requestValidarAlterarSala(final FuncionarioDTO usuario,
            final Integer idAntigaSala, final SalaDTO novaSala)
            throws IllegalUpdateException {
        Sala.validarAlterar(usuario, idAntigaSala, novaSala);
    }

    public static void requestValidarDeleteSala(final FuncionarioDTO usuario,
            final Integer idSala) throws IllegalDeleteException {
        Sala.validarDeletar(usuario, idSala);
    }

    public static void requestAddSala(final SalaDTO salaDto) {
        final SalaRepository repo = new SalaRepository();
        final Sala sala = new Sala();
        sala.setValuesFromDTO(salaDto);
        repo.add(sala);
    }

    public static void requestUpdateSala(final SalaDTO salaDto) {
        final SalaRepository repo = new SalaRepository();
        final Sala sala = new Sala();
        sala.setValuesFromDTO(salaDto);
        repo.update(sala);
    }

    public static void requestDeleteSala(final Integer itemId) {
        final SalaRepository repo = new SalaRepository();
        final Sala placeholderItem = new Sala();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
    }

    public static List<PredioDTO> requestGetPossiveisPrediosParaSala(
            final SalaDTO sala) {
        return requestGetPredios();
    }

    public static List<DepartamentoDTO> requestGetPossiveisDepartamentosParaSala(
            final SalaDTO sala) {
        return requestGetDepartamentos();
    }

    public static List<SalaDTO> requestGetSalas() {
        final SalaRepository repo = new SalaRepository();
        final List<Sala> models = repo.getAll();
        final List<SalaDTO> dtos = new ArrayList<>();
        for (final Sala model : models) {
            dtos.add(model.toDTO());
        }
        return dtos;
    }

    public static SalaDTO requestGetSalaById(final Integer id) {
        final SalaRepository salaRepo = new SalaRepository();
        final Sala sala = salaRepo.getById(id);
        if (sala == null) {
            return null;
        }
        return sala.toDTO();
    }
}
