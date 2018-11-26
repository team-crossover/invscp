package com.github.nelsonwilliam.invscp.client.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.GuiaTransporteDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.HistoricoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.InventarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.RelatorioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.shared.util.Request;
import com.github.nelsonwilliam.invscp.shared.util.RequestTypeEnum;
import com.github.nelsonwilliam.invscp.shared.util.Response;

/**
 * Classe utilizada por um cliente para enviar requisições ao servidor e receber
 * respostas apropriadas.
 */
@SuppressWarnings("unchecked")
public class Client {

    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static Socket socket;

    public static void connect()
            throws UnknownHostException, IOException, ClassNotFoundException {

        socket = new Socket(ClientSettings.getServerHost(),
                ClientSettings.getServerPort());
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());

        // Recebe a mensagem de conexão estabelecida
        System.out.println(in.readObject().toString());
    }

    public static void close() throws IOException {
        try {
            out.close();
        }
        finally {
            try {
                in.close();
            }
            finally {
                socket.close();
            }
        }
    }

    private static Object[] request(final RequestTypeEnum tipo,
            final Object... args) throws Exception {

        try {
            final Object output = new Request(tipo, args);
            out.writeObject(output);

            final Object input = in.readObject();
            final Response response = (Response) input;
            if (response.isException()) {
                throw (Exception) response.getObjs()[0];
            } else {
                return response.getObjs();
            }
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------------
    // EVENTOS MOVIMENTAÇÃO
    // ---------------------
    public static void requestValidarInserirEventoMovimentacao(
            final FuncionarioDTO usuario, final EventoMovimentacaoDTO novo)
            throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_EVENTO_MOVIMENTACAO,
                    usuario, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarEventoMovimentacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final EventoMovimentacaoDTO novo) throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_EVENTO_MOVIMENTACAO,
                    usuario, idAntigo, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteEventoMovimentacao(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_EVENTO_MOVIMENTACAO, usuario,
                    id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static int requestAddEventoMovimentacao(
            final EventoMovimentacaoDTO item) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.ADD_EVENTO_MOVIMENTACAO, item);
            return (int) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateEventoMovimentacao(
            final EventoMovimentacaoDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_EVENTO_MOVIMENTACAO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteEventoMovimentacao(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_EVENTO_MOVIMENTACAO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static EventoMovimentacaoDTO requestGetEventoMovimentacaoById(
            final Integer itemId) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.GET_EVENTO_MOVIMENTACAO_BY_ID,
                    itemId);
            return (EventoMovimentacaoDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<EventoMovimentacaoDTO> requestGetEventosMovimentacaoByMovimentacao(
            final MovimentacaoDTO mov) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.GET_EVENTOS_MOVIMENTACAO_BY_MOVIMENTACAO,
                    mov);
            return (List<EventoMovimentacaoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> requestPosInserirEventoMovimentacao(
            final FuncionarioDTO usuario, final EventoMovimentacaoDTO item) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.POS_INSERIR_EVENTO_MOVIMENTACAO,
                            usuario, item);
            return (List<String>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // -----------
    // RELATÓRIOS
    // -----------

    public static GuiaTransporteDTO requestGerarGuiaTransporte(
            final MovimentacaoDTO mov, final FuncionarioDTO usuario) {

        try {
            final Object[] objs = request(RequestTypeEnum.GERAR_GUIA_TRANSPORTE,
                    mov, usuario);
            return (GuiaTransporteDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static RelatorioDTO requestGerarRelatorioDept(
            final DepartamentoDTO dept) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GERAR_RELATORIO_DEPT, dept);
            return (RelatorioDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static HistoricoDTO requestGerarHistorico(final BemDTO bem) {

        try {
            final Object[] objs = request(RequestTypeEnum.GERAR_HISTORICO, bem);
            return (HistoricoDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static InventarioDTO requestGerarInventario() {

        try {
            final Object[] objs = request(RequestTypeEnum.GERAR_INVENTARIO);
            return (InventarioDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // -------------
    // MOVIMENTAÇÃO
    // -------------

    public static void requestValidarInserirMovimentacao(
            final FuncionarioDTO usuario, final MovimentacaoDTO novo)
            throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_MOVIMENTACAO, usuario,
                    novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarMovimentacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final MovimentacaoDTO novo) throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_MOVIMENTACAO, usuario,
                    idAntigo, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteMovimentacao(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_MOVIMENTACAO, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static int requestAddMovimentacao(final MovimentacaoDTO item) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.ADD_MOVIMENTACAO, item);
            return (int) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateMovimentacao(final MovimentacaoDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_MOVIMENTACAO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteMovimentacao(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_MOVIMENTACAO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static MovimentacaoDTO requestGetMovimentacaoById(
            final Integer itemId) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_MOVIMENTACAO_BY_ID, itemId);
            return (MovimentacaoDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<MovimentacaoDTO> requestGetMovimentacoes() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_MOVIMENTACOES);
            return (List<MovimentacaoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> requestPosInserirMovimentacao(
            final FuncionarioDTO usuario, final MovimentacaoDTO item) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.POS_INSERIR_MOVIMENTACAO, usuario, item);
            return (List<String>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String requestGerarNumGuiaTransporte() {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GERAR_NUM_GUIA_TRANSPORTE);
            return (String) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ------
    // BAIXA
    // ------

    public static void requestValidarInserirBaixa(final FuncionarioDTO usuario,
            final BaixaDTO novo) throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_BAIXA, usuario, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarBaixa(final FuncionarioDTO usuario,
            final Integer idAntigo, final BaixaDTO novo)
            throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_BAIXA, usuario, idAntigo,
                    novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteBaixa(final FuncionarioDTO usuario,
            final Integer id) throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_BAIXA, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddBaixa(final BaixaDTO item) {

        try {
            request(RequestTypeEnum.ADD_BAIXA, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateBaixa(final BaixaDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_BAIXA, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteBaixa(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_BAIXA, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> requestPosInserirBaixa(
            final FuncionarioDTO usuario, final BaixaDTO item) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.POS_INSERIR_BAIXA, usuario, item);
            return (List<String>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BaixaDTO requestGetBaixaById(final Integer itemId) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_BAIXA_BY_ID, itemId);
            return (BaixaDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BaixaDTO requestGetBaixaByIdBem(final Integer bemId) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_BAIXA_BY_ID_BEM, bemId);
            return (BaixaDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<BaixaDTO> requestGetBaixas() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_BAIXAS);
            return (List<BaixaDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ----
    // BEM
    // ----

    public static void requestValidarInserirBem(final FuncionarioDTO usuario,
            final BemDTO novo) throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_BEM, usuario, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarBem(final FuncionarioDTO usuario,
            final Integer idAntigo, final BemDTO novo)
            throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_BEM, usuario, idAntigo,
                    novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteBem(final FuncionarioDTO usuario,
            final Integer id) throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_BEM, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddBem(final BemDTO item) {

        try {
            request(RequestTypeEnum.ADD_BEM, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateBem(final BemDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_BEM, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteBem(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_BEM, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BemDTO requestGetBemById(final Integer itemId) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_BEM_BY_ID, itemId);
            return (BemDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<BemDTO> requestGetBens() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_BENS);
            return (List<BemDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<GrupoMaterialDTO> requestGetPossiveisGruposMateriaisParaBem(
            final BemDTO bem) {
        return requestGetGruposMateriais();
    }

    public static List<DepartamentoDTO> requestGetPossiveisDepartamentosParaBem(
            final BemDTO bem) {
        return requestGetDepartamentos();
    }

    // --------------
    // ORDEM SERVIÇO
    // --------------

    public static void requestValidarInserirOrdemServico(
            final FuncionarioDTO usuario, final OrdemServicoDTO novo)
            throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_ORDEM_SERVICO, usuario,
                    novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarOrdemServico(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final OrdemServicoDTO novo) throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_ORDEM_SERVICO, usuario,
                    idAntigo, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteOrdemServico(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_ORDEM_SERVICO, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddOrdemServico(final OrdemServicoDTO item) {

        try {
            request(RequestTypeEnum.ADD_ORDEM_SERVICO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateOrdemServico(final OrdemServicoDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_ORDEM_SERVICO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteOrdemServico(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_ORDEM_SERVICO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> requestPosInserirOrdemServico(
            final FuncionarioDTO usuario, final OrdemServicoDTO item) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.POS_INSERIR_ORDEM_SERVICO, usuario, item);
            return (List<String>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<String> requestPosConcluirOrdemServico(
            final FuncionarioDTO usuario, final OrdemServicoDTO item) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.POS_CONCLUIR_ORDEM_SERVICO, usuario, item);
            return (List<String>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static OrdemServicoDTO requestGetOrdemServicoById(
            final Integer itemId) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_ORDEM_SERVICO_BY_ID, itemId);
            return (OrdemServicoDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<OrdemServicoDTO> requestGetOrdensServico() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_ORDENS_SERVICO);
            return (List<OrdemServicoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<OrdemServicoDTO> requestGetOrdensServicoByBem(
            final Integer idBem) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_ORDENS_SERVICO_BY_BEM, idBem);
            return (List<OrdemServicoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ---------------
    // GRUPO MATERIAL
    // ---------------

    public static GrupoMaterialDTO requestGetGrupoMaterialById(
            final Integer itemId) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_GRUPO_MATERIAL_BY_ID, itemId);
            return (GrupoMaterialDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<GrupoMaterialDTO> requestGetGruposMateriais() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_GRUPOS_MATERIAIS);
            return (List<GrupoMaterialDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // ------------
    // FUNCIONARIO
    // ------------

    public static void requestValidarInserirFuncionario(
            final FuncionarioDTO usuario, final FuncionarioDTO novo)
            throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_FUNCIONARIO, usuario, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarFuncionario(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final FuncionarioDTO novo) throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_FUNCIONARIO, usuario,
                    idAntigo, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteFuncionario(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_FUNCIONARIO, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddFuncionario(final FuncionarioDTO item) {

        try {
            request(RequestTypeEnum.ADD_FUNCIONARIO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateFuncionario(final FuncionarioDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_FUNCIONARIO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteFuncionario(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_FUNCIONARIO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static FuncionarioDTO requestGetFuncionarioByLogin(
            final String login) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_FUNCIONARIOS_BY_LOGIN, login);
            return (FuncionarioDTO) objs[0];
        } catch (final Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<FuncionarioDTO> requestGetFuncionarios() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_FUNCIONARIOS);
            return (List<FuncionarioDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<DepartamentoDTO> requestGetPossiveisDepartamentosParaFuncionario(
            final FuncionarioDTO func) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.GET_POSSIVEIS_DEPARTAMENTOS_PARA_FUNCIONARIO,
                    func);
            return (List<DepartamentoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<FuncionarioDTO> requestGetFuncionariosByUsuario(
            final FuncionarioDTO usuario) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.GET_FUNCIONARIOS_BY_USUARIO, usuario);
            return (List<FuncionarioDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static FuncionarioDTO requestGetFuncionarioById(final Integer id) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_FUNCIONARIO_BY_ID, id);
            return (FuncionarioDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // --------------
    // DEPARTAMENTO
    // --------------

    public static void requestValidarInserirDepartamento(
            final FuncionarioDTO usuario, final DepartamentoDTO novo)
            throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_DEPARTAMENTO,
                    usuario, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarDepartamento(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final DepartamentoDTO novo) throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_DEPARTAMENTO, usuario,
                    idAntigo, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteDepartamento(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_DEPARTAMENTO, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static List<String> requestPosAlterarDepartamento(
            final FuncionarioDTO usuario, final DepartamentoDTO item) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.POS_ALTERAR_DEPARTAMENTO, usuario, item);
            return (List<String>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestAddDepartamento(final DepartamentoDTO item) {

        try {
            request(RequestTypeEnum.ADD_DEPARTAMENTO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateDepartamento(final DepartamentoDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_DEPARTAMENTO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteDepartamento(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_DEPARTAMENTO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<DepartamentoDTO> requestGetDepartamentos() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_DEPARTAMENTOS);
            return (List<DepartamentoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<FuncionarioDTO> requestGetPossiveisChefesParaDepartamento(
            final DepartamentoDTO dept) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.GET_POSSIVEIS_CHEFES_PARA_DEPARTAMENTO,
                    dept);
            return (List<FuncionarioDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static DepartamentoDTO requestGetDepartamentoById(final Integer id) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_DEPARTAMENTO_BY_ID, id);
            return (DepartamentoDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // -------
    // LOCALIZACAO
    // -------

    public static void requestValidarInserirLocalizacao(
            final FuncionarioDTO usuario, final LocalizacaoDTO novo)
            throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_LOCALIZACAO, usuario,
                    novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarLocalizacao(
            final FuncionarioDTO usuario, final Integer idAntigo,
            final LocalizacaoDTO novo) throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_LOCALIZACAO, usuario,
                    idAntigo, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteLocalizacao(
            final FuncionarioDTO usuario, final Integer id)
            throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_LOCALIZACAO, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddLocalizacao(final LocalizacaoDTO item) {

        try {
            request(RequestTypeEnum.ADD_LOCALIZACAO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateLocalizacao(final LocalizacaoDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_LOCALIZACAO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteLocalizacao(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_LOCALIZACAO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<LocalizacaoDTO> requestGetLocalizacoes() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_LOCALIZACOES);
            return (List<LocalizacaoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static LocalizacaoDTO requestGetLocalizacaoById(final Integer id) {
        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_LOCALIZACAO_BY_ID, id);
            return (LocalizacaoDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // -------
    // PREDIO
    // -------

    public static void requestValidarInserirPredio(final FuncionarioDTO usuario,
            final PredioDTO novo) throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_PREDIO, usuario, novo);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarPredio(final FuncionarioDTO usuario,
            final Integer idAntigo, final PredioDTO novo)
            throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_PREDIO, usuario, idAntigo,
                    novo);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeletePredio(final FuncionarioDTO usuario,
            final Integer id) throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_PREDIO, usuario, id);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddPredio(final PredioDTO item) {

        try {
            request(RequestTypeEnum.ADD_PREDIO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdatePredio(final PredioDTO item) {

        try {
            request(RequestTypeEnum.UPDATE_PREDIO, item);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeletePredio(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_PREDIO, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<PredioDTO> requestGetPredios() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_PREDIOS);
            return (List<PredioDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<PredioDTO> requestGetPrediosByLocalizacao(
            final LocalizacaoDTO loca) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_PREDIOS_BY_LOCALIZACAO, loca);
            return (List<PredioDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static PredioDTO requestGetPredioById(final Integer id) {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_PREDIO_BY_ID, id);
            return (PredioDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<LocalizacaoDTO> requestGetPossiveisLocalizacoesParaPredio(
            final PredioDTO predio) {

        try {
            final Object[] objs = request(
                    RequestTypeEnum.GET_POSSIVEIS_LOCALIZACOES_PARA_PREDIO,
                    predio);
            return (List<LocalizacaoDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // -----
    // SALA
    // -----

    public static void requestValidarInserirSala(final FuncionarioDTO usuario,
            final SalaDTO novaSala) throws IllegalInsertException {

        try {
            request(RequestTypeEnum.VALIDAR_INSERIR_SALA, usuario, novaSala);

        } catch (final Exception e) {
            if (e instanceof IllegalInsertException) {
                throw (IllegalInsertException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarAlterarSala(final FuncionarioDTO usuario,
            final Integer idAntigaSala, final SalaDTO novaSala)
            throws IllegalUpdateException {

        try {
            request(RequestTypeEnum.VALIDAR_ALTERAR_SALA, usuario, idAntigaSala,
                    novaSala);

        } catch (final Exception e) {
            if (e instanceof IllegalUpdateException) {
                throw (IllegalUpdateException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestValidarDeleteSala(final FuncionarioDTO usuario,
            final Integer idSala) throws IllegalDeleteException {

        try {
            request(RequestTypeEnum.VALIDAR_DELETE_SALA, usuario, idSala);

        } catch (final Exception e) {
            if (e instanceof IllegalDeleteException) {
                throw (IllegalDeleteException) e;
            } else {
                e.printStackTrace();
            }
        }
    }

    public static void requestAddSala(final SalaDTO salaDto) {

        try {
            request(RequestTypeEnum.ADD_SALA, salaDto);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestUpdateSala(final SalaDTO salaDto) {

        try {
            request(RequestTypeEnum.UPDATE_SALA, salaDto);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void requestDeleteSala(final Integer itemId) {

        try {
            request(RequestTypeEnum.DELETE_SALA, itemId);
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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

        try {
            final Object[] objs = request(RequestTypeEnum.GET_SALAS);
            return (List<SalaDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<SalaDTO> requestGetSalasByPredio(
            final PredioDTO predio) {

        try {
            final Object[] objs =
                    request(RequestTypeEnum.GET_SALAS_BY_PREDIO, predio);
            return (List<SalaDTO>) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SalaDTO requestGetSalaById(final Integer id) {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_SALA_BY_ID, id);
            return (SalaDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SalaDTO requestGetSalaDeposito() {

        try {
            final Object[] objs = request(RequestTypeEnum.GET_SALA_DEPOSITO);
            return (SalaDTO) objs[0];
        } catch (final Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
