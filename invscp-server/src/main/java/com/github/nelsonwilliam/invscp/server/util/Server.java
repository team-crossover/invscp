package com.github.nelsonwilliam.invscp.server.util;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.nelsonwilliam.invscp.server.model.Baixa;
import com.github.nelsonwilliam.invscp.server.model.Bem;
import com.github.nelsonwilliam.invscp.server.model.Departamento;
import com.github.nelsonwilliam.invscp.server.model.EventoMovimentacao;
import com.github.nelsonwilliam.invscp.server.model.Funcionario;
import com.github.nelsonwilliam.invscp.server.model.GrupoMaterial;
import com.github.nelsonwilliam.invscp.server.model.Localizacao;
import com.github.nelsonwilliam.invscp.server.model.Movimentacao;
import com.github.nelsonwilliam.invscp.server.model.OrdemServico;
import com.github.nelsonwilliam.invscp.server.model.Predio;
import com.github.nelsonwilliam.invscp.server.model.Sala;
import com.github.nelsonwilliam.invscp.server.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.EventoMovimentacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.GrupoMaterialRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.GuiaTransporteRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.HistoricoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.InventarioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.LocalizacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.OrdemServicoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.PredioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.RelatorioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
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
import com.github.nelsonwilliam.invscp.shared.util.Request;
import com.github.nelsonwilliam.invscp.shared.util.RequestTypeEnum;
import com.github.nelsonwilliam.invscp.shared.util.Response;

public class Server {

    public Response receive(final Request request) {
        final RequestTypeEnum type = request.getTipo();
        final Object[] args = request.getArgs();
        switch (type) {
            case VALIDAR_INSERIR_EVENTO_MOVIMENTACAO:
                return validarInserirEventoMovimentacao(
                        (FuncionarioDTO) args[0],
                        (EventoMovimentacaoDTO) args[1]);
            case ADD_BAIXA:
                return addBaixa((BaixaDTO) args[0]);
            case ADD_BEM:
                return addBem((BemDTO) args[0]);
            case ADD_DEPARTAMENTO:
                return addDepartamento((DepartamentoDTO) args[0]);
            case ADD_EVENTO_MOVIMENTACAO:
                return addEventoMovimentacao((EventoMovimentacaoDTO) args[0]);
            case ADD_FUNCIONARIO:
                return addFuncionario((FuncionarioDTO) args[0]);
            case ADD_LOCALIZACAO:
                return addLocalizacao((LocalizacaoDTO) args[0]);
            case ADD_MOVIMENTACAO:
                return addMovimentacao((MovimentacaoDTO) args[0]);
            case ADD_ORDEM_SERVICO:
                return addOrdemServico((OrdemServicoDTO) args[0]);
            case ADD_PREDIO:
                return addPredio((PredioDTO) args[0]);
            case ADD_SALA:
                return addSala((SalaDTO) args[0]);
            case DELETE_BAIXA:
                return deleteBaixa((Integer) args[0]);
            case DELETE_BEM:
                return deleteBem((Integer) args[0]);
            case DELETE_DEPARTAMENTO:
                return deleteDepartamento((Integer) args[0]);
            case DELETE_EVENTO_MOVIMENTACAO:
                return deleteEventoMovimentacao((Integer) args[0]);
            case DELETE_FUNCIONARIO:
                return deleteFuncionario((Integer) args[0]);
            case DELETE_LOCALIZACAO:
                return deleteLocalizacao((Integer) args[0]);
            case DELETE_MOVIMENTACAO:
                return deleteMovimentacao((Integer) args[0]);
            case DELETE_ORDEM_SERVICO:
                return deleteOrdemServico((Integer) args[0]);
            case DELETE_PREDIO:
                return deletePredio((Integer) args[0]);
            case DELETE_SALA:
                return deleteSala((Integer) args[0]);
            case GERAR_GUIA_TRANSPORTE:
                return gerarGuiaTransporte((MovimentacaoDTO) args[0],
                        (FuncionarioDTO) args[1]);
            case GERAR_HISTORICO:
                return gerarHistorico((BemDTO) args[0]);
            case GERAR_INVENTARIO:
                return gerarInventario();
            case GERAR_NUM_GUIA_TRANSPORTE:
                return gerarNumGuiaTransporte();
            case GERAR_RELATORIO_DEPT:
                return gerarRelatorioDept((DepartamentoDTO) args[0]);
            case GET_BAIXAS:
                return getBaixas();
            case GET_BAIXA_BY_ID:
                return getBaixaById((Integer) args[0]);
            case GET_BAIXA_BY_ID_BEM:
                return getBaixaByIdBem((Integer) args[0]);
            case GET_BEM_BY_ID:
                return getBemById((Integer) args[0]);
            case GET_BENS:
                return getBens();
            case GET_DEPARTAMENTOS:
                return getDepartamentos();
            case GET_DEPARTAMENTO_BY_ID:
                return getDepartamentoById((Integer) args[0]);
            case GET_EVENTOS_MOVIMENTACAO_BY_MOVIMENTACAO:
                return getEventosMovimentacaoByMovimentacao(
                        (MovimentacaoDTO) args[0]);
            case GET_EVENTO_MOVIMENTACAO_BY_ID:
                return getEventoMovimentacaoById((Integer) args[0]);
            case GET_FUNCIONARIOS:
                return getFuncionarios();
            case GET_FUNCIONARIOS_BY_LOGIN:
                return getFuncionarioByLogin((String) args[0]);
            case GET_FUNCIONARIOS_BY_USUARIO:
                return getFuncionariosByUsuario((FuncionarioDTO) args[0]);
            case GET_FUNCIONARIO_BY_ID:
                return getFuncionarioById((Integer) args[0]);
            case GET_GRUPOS_MATERIAIS:
                return getGruposMateriais();
            case GET_GRUPO_MATERIAL_BY_ID:
                return getGrupoMaterialById((Integer) args[0]);
            case GET_LOCALIZACAO_BY_ID:
                return getLocalizacaoById((Integer) args[0]);
            case GET_LOCALIZACOES:
                return getLocalizacoes();
            case GET_MOVIMENTACAO_BY_ID:
                return getMovimentacaoById((Integer) args[0]);
            case GET_MOVIMENTACOES:
                return getMovimentacoes();
            case GET_ORDEM_SERVICO_BY_ID:
                return getOrdemServicoById((Integer) args[0]);
            case GET_ORDENS_SERVICO:
                return getOrdensServico();
            case GET_ORDENS_SERVICO_BY_BEM:
                return getOrdensServicoByBem((Integer) args[0]);
            case GET_POSSIVEIS_CHEFES_PARA_DEPARTAMENTO:
                return getPossiveisChefesParaDepartamento(
                        (DepartamentoDTO) args[0]);
            case GET_POSSIVEIS_DEPARTAMENTOS_PARA_FUNCIONARIO:
                return getPossiveisDepartamentosParaFuncionario(
                        (FuncionarioDTO) args[0]);
            case GET_POSSIVEIS_LOCALIZACOES_PARA_PREDIO:
                return getPossiveisLocalizacoesParaPredio((PredioDTO) args[0]);
            case GET_PREDIOS:
                return getPredios();
            case GET_PREDIOS_BY_LOCALIZACAO:
                return getPrediosByLocalizacao((LocalizacaoDTO) args[0]);
            case GET_PREDIO_BY_ID:
                return getPredioById((Integer) args[0]);
            case GET_SALAS:
                return getSalas();
            case GET_SALAS_BY_PREDIO:
                return getSalasByPredio((PredioDTO) args[0]);
            case GET_SALA_BY_ID:
                return getSalaById((Integer) args[0]);
            case GET_SALA_DEPOSITO:
                return getSalaDeposito();
            case POS_ALTERAR_DEPARTAMENTO:
                return posAlterarDepartamento((FuncionarioDTO) args[0],
                        (DepartamentoDTO) args[1]);
            case POS_CONCLUIR_ORDEM_SERVICO:
                return posConcluirOrdemServico((FuncionarioDTO) args[0],
                        (OrdemServicoDTO) args[1]);
            case POS_INSERIR_BAIXA:
                return posInserirBaixa((FuncionarioDTO) args[0],
                        (BaixaDTO) args[1]);
            case POS_INSERIR_EVENTO_MOVIMENTACAO:
                return posInserirEventoMovimentacao((FuncionarioDTO) args[0],
                        (EventoMovimentacaoDTO) args[1]);
            case POS_INSERIR_MOVIMENTACAO:
                return posInserirMovimentacao((FuncionarioDTO) args[0],
                        (MovimentacaoDTO) args[1]);
            case POS_INSERIR_ORDEM_SERVICO:
                return posInserirOrdemServico((FuncionarioDTO) args[0],
                        (OrdemServicoDTO) args[1]);
            case UPDATE_BAIXA:
                return updateBaixa((BaixaDTO) args[0]);
            case UPDATE_BEM:
                return updateBem((BemDTO) args[0]);
            case UPDATE_DEPARTAMENTO:
                return updateDepartamento((DepartamentoDTO) args[0]);
            case UPDATE_EVENTO_MOVIMENTACAO:
                return updateEventoMovimentacao(
                        (EventoMovimentacaoDTO) args[0]);
            case UPDATE_FUNCIONARIO:
                return updateFuncionario((FuncionarioDTO) args[0]);
            case UPDATE_LOCALIZACAO:
                return updateLocalizacao((LocalizacaoDTO) args[0]);
            case UPDATE_MOVIMENTACAO:
                return updateMovimentacao((MovimentacaoDTO) args[0]);
            case UPDATE_ORDEM_SERVICO:
                return updateOrdemServico((OrdemServicoDTO) args[0]);
            case UPDATE_PREDIO:
                return updatePredio((PredioDTO) args[0]);
            case UPDATE_SALA:
                return updateSala((SalaDTO) args[0]);
            case VALIDAR_ALTERAR_BAIXA:
                return validarAlterarBaixa((FuncionarioDTO) args[0],
                        (Integer) args[1], (BaixaDTO) args[2]);
            case VALIDAR_ALTERAR_BEM:
                return validarAlterarBem((FuncionarioDTO) args[0],
                        (Integer) args[1], (BemDTO) args[2]);
            case VALIDAR_ALTERAR_DEPARTAMENTO:
                return validarAlterarDepartamento((FuncionarioDTO) args[0],
                        (Integer) args[1], (DepartamentoDTO) args[2]);
            case VALIDAR_ALTERAR_EVENTO_MOVIMENTACAO:
                return validarAlterarEventoMovimentacao(
                        (FuncionarioDTO) args[0], (Integer) args[1],
                        (EventoMovimentacaoDTO) args[2]);
            case VALIDAR_ALTERAR_FUNCIONARIO:
                return validarAlterarFuncionario((FuncionarioDTO) args[0],
                        (Integer) args[1], (FuncionarioDTO) args[2]);
            case VALIDAR_ALTERAR_LOCALIZACAO:
                return validarAlterarLocalizacao((FuncionarioDTO) args[0],
                        (Integer) args[1], (LocalizacaoDTO) args[2]);
            case VALIDAR_ALTERAR_MOVIMENTACAO:
                return validarAlterarMovimentacao((FuncionarioDTO) args[0],
                        (Integer) args[1], (MovimentacaoDTO) args[2]);
            case VALIDAR_ALTERAR_ORDEM_SERVICO:
                return validarAlterarOrdemServico((FuncionarioDTO) args[0],
                        (Integer) args[1], (OrdemServicoDTO) args[2]);
            case VALIDAR_ALTERAR_PREDIO:
                return validarAlterarPredio((FuncionarioDTO) args[0],
                        (Integer) args[1], (PredioDTO) args[2]);
            case VALIDAR_ALTERAR_SALA:
                return validarAlterarSala((FuncionarioDTO) args[0],
                        (Integer) args[1], (SalaDTO) args[2]);
            case VALIDAR_DELETE_BAIXA:
                return validarDeleteBaixa((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_BEM:
                return validarDeleteBem((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_DEPARTAMENTO:
                return validarDeleteDepartamento((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_EVENTO_MOVIMENTACAO:
                return validarDeleteEventoMovimentacao((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_FUNCIONARIO:
                return validarDeleteFuncionario((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_LOCALIZACAO:
                return validarDeleteLocalizacao((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_MOVIMENTACAO:
                return validarDeleteMovimentacao((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_ORDEM_SERVICO:
                return validarDeleteOrdemServico((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_PREDIO:
                return validarDeletePredio((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_DELETE_SALA:
                return validarDeleteSala((FuncionarioDTO) args[0],
                        (Integer) args[1]);
            case VALIDAR_INSERIR_BAIXA:
                return validarInserirBaixa((FuncionarioDTO) args[0],
                        (BaixaDTO) args[1]);
            case VALIDAR_INSERIR_BEM:
                return validarInserirBem((FuncionarioDTO) args[0],
                        (BemDTO) args[1]);
            case VALIDAR_INSERIR_DEPARTAMENTO:
                return validarInserirDepartamento((FuncionarioDTO) args[0],
                        (DepartamentoDTO) args[1]);
            case VALIDAR_INSERIR_FUNCIONARIO:
                return validarInserirFuncionario((FuncionarioDTO) args[0],
                        (FuncionarioDTO) args[1]);
            case VALIDAR_INSERIR_LOCALIZACAO:
                return validarInserirLocalizacao((FuncionarioDTO) args[0],
                        (LocalizacaoDTO) args[1]);
            case VALIDAR_INSERIR_MOVIMENTACAO:
                return validarInserirMovimentacao((FuncionarioDTO) args[0],
                        (MovimentacaoDTO) args[1]);
            case VALIDAR_INSERIR_ORDEM_SERVICO:
                return validarInserirOrdemServico((FuncionarioDTO) args[0],
                        (OrdemServicoDTO) args[1]);
            case VALIDAR_INSERIR_PREDIO:
                return validarInserirPredio((FuncionarioDTO) args[0],
                        (PredioDTO) args[1]);
            case VALIDAR_INSERIR_SALA:
                return validarInserirSala((FuncionarioDTO) args[0],
                        (SalaDTO) args[1]);
            default:
                return new Response(new Exception("Tipo de pedido inválido."));
        }
    }

    // ---------------------
    // EVENTOS MOVIMENTAÇÃO
    // ---------------------

    public static Response validarInserirEventoMovimentacao(
            final FuncionarioDTO usuario, final EventoMovimentacaoDTO novo) {
        try {
            EventoMovimentacao.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarEventoMovimentacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final EventoMovimentacaoDTO novo) {
        try {
            EventoMovimentacao.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteEventoMovimentacao(
            final FuncionarioDTO usuario, final Integer id) {
        try {
            EventoMovimentacao.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addEventoMovimentacao(
            final EventoMovimentacaoDTO item) {
        final EventoMovimentacaoRepository repo =
                new EventoMovimentacaoRepository();
        final EventoMovimentacao itemModel = new EventoMovimentacao();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response(itemModel.getId());
    }

    public static Response updateEventoMovimentacao(
            final EventoMovimentacaoDTO item) {

        final EventoMovimentacaoRepository repo =
                new EventoMovimentacaoRepository();
        final EventoMovimentacao itemModel = new EventoMovimentacao();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteEventoMovimentacao(final Integer itemId) {

        final EventoMovimentacaoRepository repo =
                new EventoMovimentacaoRepository();
        final EventoMovimentacao placeholderItem = new EventoMovimentacao();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getEventoMovimentacaoById(final Integer itemId) {

        final EventoMovimentacaoRepository repo =
                new EventoMovimentacaoRepository();
        final EventoMovimentacao item = repo.getById(itemId);
        if (item == null) {
            return new Response((Object) null);
        }
        return new Response(item.toDTO());
    }

    public static Response getEventosMovimentacaoByMovimentacao(
            final MovimentacaoDTO mov) {

        final EventoMovimentacaoRepository repo =
                new EventoMovimentacaoRepository();
        final List<EventoMovimentacao> models =
                repo.getByIdMovimentacao(mov.getId());
        final List<EventoMovimentacaoDTO> dtos = new ArrayList<>();
        for (final EventoMovimentacao model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response posInserirEventoMovimentacao(
            final FuncionarioDTO usuario, final EventoMovimentacaoDTO item) {

        return new Response(EventoMovimentacao.posInserir(usuario, item));
    }

    // -----------
    // RELATÓRIOS
    // -----------

    public static Response gerarGuiaTransporte(final MovimentacaoDTO mov,
            final FuncionarioDTO usuario) {

        final GuiaTransporteRepository repo = new GuiaTransporteRepository();
        final Movimentacao movModel = new Movimentacao();
        movModel.setValuesFromDTO(mov);
        final Funcionario funcModel = new Funcionario();
        funcModel.setValuesFromDTO(usuario);
        return new Response(
                repo.getByMovimentacao(movModel, funcModel).toDTO());
    }

    public static Response gerarRelatorioDept(final DepartamentoDTO dept) {

        final RelatorioRepository repo = new RelatorioRepository();
        final Departamento deptModel = new Departamento();
        deptModel.setValuesFromDTO(dept);
        return new Response(repo.getByDepartamento(deptModel).toDTO());
    }

    public static Response gerarHistorico(final BemDTO bem) {

        final HistoricoRepository repo = new HistoricoRepository();
        final Bem bemModel = new Bem();
        bemModel.setValuesFromDTO(bem);
        return new Response(repo.getByBem(bemModel).toDTO());
    }

    public static Response gerarInventario() {

        final InventarioRepository repo = new InventarioRepository();
        return new Response(repo.get().toDTO());
    }

    // -------------
    // MOVIMENTAÇÃO
    // -------------

    public static Response validarInserirMovimentacao(
            final FuncionarioDTO usuario, final MovimentacaoDTO novo) {
        try {
            Movimentacao.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarMovimentacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final MovimentacaoDTO novo) {
        try {
            Movimentacao.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteMovimentacao(
            final FuncionarioDTO usuario, final Integer id) {
        try {
            Movimentacao.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addMovimentacao(final MovimentacaoDTO item) {

        final MovimentacaoRepository repo = new MovimentacaoRepository();
        final Movimentacao itemModel = new Movimentacao();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response(itemModel.getId());
    }

    public static Response updateMovimentacao(final MovimentacaoDTO item) {

        final MovimentacaoRepository repo = new MovimentacaoRepository();
        final Movimentacao itemModel = new Movimentacao();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteMovimentacao(final Integer itemId) {

        final MovimentacaoRepository repo = new MovimentacaoRepository();
        final Movimentacao placeholderItem = new Movimentacao();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getMovimentacaoById(final Integer itemId) {

        final MovimentacaoRepository repo = new MovimentacaoRepository();
        final Movimentacao item = repo.getById(itemId);
        if (item == null) {
            return new Response((Object) null);
        }
        return new Response(item.toDTO());
    }

    public static Response getMovimentacoes() {

        final MovimentacaoRepository repo = new MovimentacaoRepository();
        final List<Movimentacao> models = repo.getAll();
        final List<MovimentacaoDTO> dtos = new ArrayList<>();
        for (final Movimentacao model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response posInserirMovimentacao(final FuncionarioDTO usuario,
            final MovimentacaoDTO item) {

        return new Response(Movimentacao.posInserir(usuario, item));
    }

    public static Response gerarNumGuiaTransporte() {

        final MovimentacaoRepository repo = new MovimentacaoRepository();
        final Random rand = new Random();
        String guia = null;
        int tentativas = 0;
        while (tentativas < 10 && guia == null) {
            // Gera um número de 9 dígitos
            Integer guiaNum = 100000000;
            guiaNum += rand.nextInt(900000000);

            guia = guiaNum.toString();
            if (repo.existsNumGuiaTransporte(guia)) {
                guia = null;
            }
            tentativas++;
        }
        return new Response(guia);
    }

    // ------
    // BAIXA
    // ------

    public static Response validarInserirBaixa(final FuncionarioDTO usuario,
            final BaixaDTO novo) {

        try {
            Baixa.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarBaixa(final FuncionarioDTO usuario,
            final Integer idAntigo, final BaixaDTO novo) {

        try {
            Baixa.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteBaixa(final FuncionarioDTO usuario,
            final Integer id) {
        try {
            Baixa.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addBaixa(final BaixaDTO item) {

        final BaixaRepository repo = new BaixaRepository();
        final Baixa itemModel = new Baixa();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updateBaixa(final BaixaDTO item) {

        final BaixaRepository repo = new BaixaRepository();
        final Baixa itemModel = new Baixa();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteBaixa(final Integer itemId) {

        final BaixaRepository repo = new BaixaRepository();
        final Baixa placeholderItem = new Baixa();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response posInserirBaixa(final FuncionarioDTO usuario,
            final BaixaDTO item) {

        return new Response(Baixa.posInserir(usuario, item));
    }

    public static Response getBaixaById(final Integer itemId) {

        final BaixaRepository repo = new BaixaRepository();
        final Baixa item = repo.getById(itemId);
        if (item == null) {
            return new Response((Object) null);
        }
        return new Response(item.toDTO());
    }

    public static Response getBaixaByIdBem(final Integer bemId) {

        final BaixaRepository repo = new BaixaRepository();
        final Baixa baixa = repo.getByIdBem(bemId);
        if (baixa == null) {
            return new Response((Object) null);
        }
        return new Response(baixa.toDTO());
    }

    public static Response getBaixas() {

        final BaixaRepository repo = new BaixaRepository();
        final List<Baixa> models = repo.getAll();
        final List<BaixaDTO> dtos = new ArrayList<>();
        for (final Baixa model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    // ----
    // BEM
    // ----

    public static Response validarInserirBem(final FuncionarioDTO usuario,
            final BemDTO novo) {
        try {
            Bem.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarBem(final FuncionarioDTO usuario,
            final Integer idAntigo, final BemDTO novo) {

        try {
            Bem.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteBem(final FuncionarioDTO usuario,
            final Integer id) {

        try {
            Bem.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addBem(final BemDTO item) {

        final BemRepository repo = new BemRepository();
        final Bem itemModel = new Bem();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updateBem(final BemDTO item) {

        final BemRepository repo = new BemRepository();
        final Bem itemModel = new Bem();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteBem(final Integer itemId) {

        final BemRepository repo = new BemRepository();
        final Bem placeholderItem = new Bem();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getBemById(final Integer itemId) {

        final BemRepository repo = new BemRepository();
        final Bem item = repo.getById(itemId);
        if (item == null) {
            return new Response((Object) null);
        }
        return new Response(item.toDTO());
    }

    public static Response getBens() {

        final BemRepository repo = new BemRepository();
        final List<Bem> models = repo.getAll();
        final List<BemDTO> dtos = new ArrayList<>();
        for (final Bem model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getPossiveisGruposMateriaisParaBem(
            final BemDTO bem) {

        return getGruposMateriais();
    }

    public static Response getPossiveisDepartamentosParaBem(final BemDTO bem) {

        return getDepartamentos();
    }

    // --------------
    // ORDEM SERVIÇO
    // --------------

    public static Response validarInserirOrdemServico(
            final FuncionarioDTO usuario, final OrdemServicoDTO novo) {

        try {
            OrdemServico.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarOrdemServico(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final OrdemServicoDTO novo) {

        try {
            OrdemServico.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteOrdemServico(
            final FuncionarioDTO usuario, final Integer id) {
        try {
            OrdemServico.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addOrdemServico(final OrdemServicoDTO item) {

        final OrdemServicoRepository repo = new OrdemServicoRepository();
        final OrdemServico itemModel = new OrdemServico();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updateOrdemServico(final OrdemServicoDTO item) {
        final OrdemServicoRepository repo = new OrdemServicoRepository();
        final OrdemServico itemModel = new OrdemServico();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteOrdemServico(final Integer itemId) {

        final OrdemServicoRepository repo = new OrdemServicoRepository();
        final OrdemServico placeholderItem = new OrdemServico();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response posInserirOrdemServico(final FuncionarioDTO usuario,
            final OrdemServicoDTO item) {

        return new Response(OrdemServico.posInserir(usuario, item));
    }

    public static Response posConcluirOrdemServico(final FuncionarioDTO usuario,
            final OrdemServicoDTO item) {

        return new Response(OrdemServico.posConcluir(usuario, item));
    }

    public static Response getOrdemServicoById(final Integer itemId) {

        final OrdemServicoRepository repo = new OrdemServicoRepository();
        final OrdemServico item = repo.getById(itemId);
        if (item == null) {
            return new Response((Object) null);
        }
        return new Response(item.toDTO());
    }

    public static Response getOrdensServico() {

        final OrdemServicoRepository repo = new OrdemServicoRepository();
        final List<OrdemServico> models = repo.getAll();
        final List<OrdemServicoDTO> dtos = new ArrayList<>();
        for (final OrdemServico model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getOrdensServicoByBem(final Integer idBem) {

        final OrdemServicoRepository repo = new OrdemServicoRepository();
        final List<OrdemServico> models = repo.getByIdBem(idBem);
        final List<OrdemServicoDTO> dtos = new ArrayList<>();
        for (final OrdemServico model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    // ---------------
    // GRUPO MATERIAL
    // ---------------

    public static Response getGrupoMaterialById(final Integer itemId) {

        final GrupoMaterialRepository repo = new GrupoMaterialRepository();
        final GrupoMaterial item = repo.getById(itemId);
        if (item == null) {
            return new Response((Object) null);
        }
        return new Response(item.toDTO());
    }

    public static Response getGruposMateriais() {

        final GrupoMaterialRepository repo = new GrupoMaterialRepository();
        final List<GrupoMaterial> models = repo.getAll();
        final List<GrupoMaterialDTO> dtos = new ArrayList<>();
        for (final GrupoMaterial model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    // ------------
    // FUNCIONARIO
    // ------------

    public static Response validarInserirFuncionario(
            final FuncionarioDTO usuario, final FuncionarioDTO novo) {

        try {
            Funcionario.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarFuncionario(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final FuncionarioDTO novo) {

        try {
            Funcionario.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteFuncionario(
            final FuncionarioDTO usuario, final Integer id) {

        try {
            Funcionario.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addFuncionario(final FuncionarioDTO item) {

        final FuncionarioRepository repo = new FuncionarioRepository();
        final Funcionario itemModel = new Funcionario();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updateFuncionario(final FuncionarioDTO item) {

        final FuncionarioRepository repo = new FuncionarioRepository();
        final Funcionario itemModel = new Funcionario();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteFuncionario(final Integer itemId) {

        final FuncionarioRepository repo = new FuncionarioRepository();
        final Funcionario placeholderItem = new Funcionario();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getFuncionarioByLogin(final String login) {

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario func = funcRepo.getByLogin(login);
        if (func == null) {
            return new Response((Object) null);
        }
        return new Response(func.toDTO());
    }

    public static Response getFuncionarios() {

        final FuncionarioRepository repo = new FuncionarioRepository();
        final List<Funcionario> models = repo.getAll();
        final List<FuncionarioDTO> dtos = new ArrayList<>();
        for (final Funcionario model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    @SuppressWarnings("unchecked")
    public static Response getPossiveisDepartamentosParaFuncionario(
            final FuncionarioDTO func) {

        final List<DepartamentoDTO> depts =
                (List<DepartamentoDTO>) getDepartamentos().getObjs()[0];
        if (func.getDepartamento() != null) {
            for (final DepartamentoDTO dept : depts) {
                if (dept.getId().equals(func.getDepartamento().getId())) {
                    depts.remove(dept);
                    break;
                }
            }
        }
        return new Response(depts);
    }

    public static Response getFuncionariosByUsuario(
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
        return new Response(dtos);
    }

    public static Response getFuncionarioById(final Integer id) {

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final Funcionario func = funcRepo.getById(id);
        if (func == null) {
            return new Response((Object) null);
        }
        return new Response(func.toDTO());
    }

    // --------------
    // DEPARTAMENTO
    // --------------

    public static Response validarInserirDepartamento(
            final FuncionarioDTO usuario, final DepartamentoDTO novo) {
        try {
            Departamento.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarDepartamento(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final DepartamentoDTO novo) {

        try {
            Departamento.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteDepartamento(
            final FuncionarioDTO usuario, final Integer id) {

        try {
            Departamento.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response posAlterarDepartamento(final FuncionarioDTO usuario,
            final DepartamentoDTO item) {

        return new Response(Departamento.posAlterar(usuario, item));
    }

    public static Response addDepartamento(final DepartamentoDTO item) {

        final DepartamentoRepository repo = new DepartamentoRepository();
        final Departamento itemModel = new Departamento();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updateDepartamento(final DepartamentoDTO item) {

        final DepartamentoRepository repo = new DepartamentoRepository();
        final Departamento itemModel = new Departamento();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteDepartamento(final Integer itemId) {

        final DepartamentoRepository repo = new DepartamentoRepository();
        final Departamento placeholderItem = new Departamento();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getDepartamentos() {

        final DepartamentoRepository repo = new DepartamentoRepository();
        final List<Departamento> models = repo.getAll();
        final List<DepartamentoDTO> dtos = new ArrayList<>();
        for (final Departamento model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getPossiveisChefesParaDepartamento(
            final DepartamentoDTO dept) {

        final Departamento placeholderDept = new Departamento();
        placeholderDept.setId(dept.getId());

        final FuncionarioRepository funcRepo = new FuncionarioRepository();
        final List<Funcionario> funcs = dept == null || dept.getId() == null
                ? funcRepo.getSemDepartamento()
                : funcRepo.getByDepartamentoExcetoChefes(placeholderDept);

        final List<FuncionarioDTO> dtos = new ArrayList<>();
        for (final Funcionario model : funcs) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getDepartamentoById(final Integer id) {

        final DepartamentoRepository deptRepo = new DepartamentoRepository();
        final Departamento dept = deptRepo.getById(id);
        if (dept == null) {
            return new Response((Object) null);
        }
        return new Response(dept.toDTO());
    }

    // -------
    // LOCALIZACAO
    // -------

    public static Response validarInserirLocalizacao(
            final FuncionarioDTO usuario, final LocalizacaoDTO novo) {

        try {
            Localizacao.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarLocalizacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final LocalizacaoDTO novo) {

        try {
            Localizacao.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteLocalizacao(
            final FuncionarioDTO usuario, final Integer id) {

        try {
            Localizacao.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addLocalizacao(final LocalizacaoDTO item) {

        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final Localizacao itemModel = new Localizacao();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updateLocalizacao(final LocalizacaoDTO item) {

        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final Localizacao itemModel = new Localizacao();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deleteLocalizacao(final Integer itemId) {

        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final Localizacao placeholderItem = new Localizacao();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getLocalizacoes() {

        final LocalizacaoRepository repo = new LocalizacaoRepository();
        final List<Localizacao> models = repo.getAll();
        final List<LocalizacaoDTO> dtos = new ArrayList<>();
        for (final Localizacao model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getLocalizacaoById(final Integer id) {

        final LocalizacaoRepository locaRepo = new LocalizacaoRepository();
        final Localizacao loca = locaRepo.getById(id);
        if (loca == null) {
            return new Response((Object) null);
        }
        return new Response(loca.toDTO());
    }
    // -------
    // PREDIO
    // -------

    public static Response validarInserirPredio(final FuncionarioDTO usuario,
            final PredioDTO novo) {
        try {
            Predio.validarInserir(usuario, novo);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarPredio(final FuncionarioDTO usuario,
            final Integer idAntigo, final PredioDTO novo) {

        try {
            Predio.validarAlterar(usuario, idAntigo, novo);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeletePredio(final FuncionarioDTO usuario,
            final Integer id) {

        try {
            Predio.validarDeletar(usuario, id);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addPredio(final PredioDTO item) {

        final PredioRepository repo = new PredioRepository();
        final Predio itemModel = new Predio();
        itemModel.setValuesFromDTO(item);
        repo.add(itemModel);
        return new Response();
    }

    public static Response updatePredio(final PredioDTO item) {

        final PredioRepository repo = new PredioRepository();
        final Predio itemModel = new Predio();
        itemModel.setValuesFromDTO(item);
        repo.update(itemModel);
        return new Response();
    }

    public static Response deletePredio(final Integer itemId) {

        final PredioRepository repo = new PredioRepository();
        final Predio placeholderItem = new Predio();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getPredios() {

        final PredioRepository repo = new PredioRepository();
        final List<Predio> models = repo.getAll();
        final List<PredioDTO> dtos = new ArrayList<>();
        for (final Predio model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getPrediosByLocalizacao(final LocalizacaoDTO loca) {

        final PredioRepository repo = new PredioRepository();
        final List<Predio> models = repo.getByIdLocalizacao(loca.getId());
        final List<PredioDTO> dtos = new ArrayList<>();
        for (final Predio model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getPredioById(final Integer id) {

        final PredioRepository predioRepo = new PredioRepository();
        final Predio predio = predioRepo.getById(id);
        if (predio == null) {
            return new Response((Object) null);
        }
        return new Response(predio.toDTO());
    }

    public static Response getPossiveisLocalizacoesParaPredio(
            final PredioDTO predio) {
        return getLocalizacoes();
    }

    // -----
    // SALA
    // -----

    public static Response validarInserirSala(final FuncionarioDTO usuario,
            final SalaDTO novaSala) {
        try {
            Sala.validarInserir(usuario, novaSala);
            return new Response();
        } catch (final IllegalInsertException e) {
            return new Response(e);
        }
    }

    public static Response validarAlterarSala(final FuncionarioDTO usuario,
            final Integer idAntigaSala, final SalaDTO novaSala) {

        try {
            Sala.validarAlterar(usuario, idAntigaSala, novaSala);
            return new Response();
        } catch (final IllegalUpdateException e) {
            return new Response(e);
        }
    }

    public static Response validarDeleteSala(final FuncionarioDTO usuario,
            final Integer idSala) {

        try {
            Sala.validarDeletar(usuario, idSala);
            return new Response();
        } catch (final IllegalDeleteException e) {
            return new Response(e);
        }
    }

    public static Response addSala(final SalaDTO salaDto) {

        final SalaRepository repo = new SalaRepository();
        final Sala sala = new Sala();
        sala.setValuesFromDTO(salaDto);
        repo.add(sala);
        return new Response();
    }

    public static Response updateSala(final SalaDTO salaDto) {

        final SalaRepository repo = new SalaRepository();
        final Sala sala = new Sala();
        sala.setValuesFromDTO(salaDto);
        repo.update(sala);
        return new Response();
    }

    public static Response deleteSala(final Integer itemId) {

        final SalaRepository repo = new SalaRepository();
        final Sala placeholderItem = new Sala();
        placeholderItem.setId(itemId);
        repo.remove(placeholderItem);
        return new Response();
    }

    public static Response getPossiveisPrediosParaSala(final SalaDTO sala) {
        return getPredios();
    }

    public static Response getPossiveisDepartamentosParaSala(
            final SalaDTO sala) {
        return getDepartamentos();
    }

    public static Response getSalas() {

        final SalaRepository repo = new SalaRepository();
        final List<Sala> models = repo.getAll();
        final List<SalaDTO> dtos = new ArrayList<>();
        for (final Sala model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getSalasByPredio(final PredioDTO predio) {

        final SalaRepository repo = new SalaRepository();
        final List<Sala> models = repo.getByIdPredio(predio.getId());
        final List<SalaDTO> dtos = new ArrayList<>();
        for (final Sala model : models) {
            dtos.add(model.toDTO());
        }
        return new Response(dtos);
    }

    public static Response getSalaById(final Integer id) {

        final SalaRepository salaRepo = new SalaRepository();
        final Sala sala = salaRepo.getById(id);
        if (sala == null) {
            return new Response((Object) null);
        }
        return new Response(sala.toDTO());
    }

    public static Response getSalaDeposito() {

        final SalaRepository salaRepo = new SalaRepository();
        final Sala sala = salaRepo.getDeDeposito();
        if (sala == null) {
            return new Response((Object) null);
        }
        return new Response(sala.toDTO());
    }
}
