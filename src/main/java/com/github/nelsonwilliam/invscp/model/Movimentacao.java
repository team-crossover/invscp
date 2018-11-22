package com.github.nelsonwilliam.invscp.model;

import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.BaixaDTO;
import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.model.enums.CargoEnum;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.EventoMovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Movimentacao implements Model<MovimentacaoDTO> {

    private static final long serialVersionUID = 336584770255104292L;

    private Integer id = null;

    private EtapaMovEnum etapa = null;

    private Integer idBem = null;

    private Integer idSalaOrigem = null;

    private Integer idSalaDestino = null;

    @Override
    public void setValuesFromDTO(final MovimentacaoDTO dto) {
        setId(dto.getId());
        setEtapa(dto.getEtapa());
        if (dto.getBem() != null) {
            setIdBem(dto.getBem().getId());
        }
        if (dto.getSalaOrigem() != null) {
            setIdSalaOrigem(dto.getSalaOrigem().getId());
        }
        if (dto.getSalaDestino() != null) {
            setIdSalaDestino(dto.getSalaDestino().getId());
        }

    }

    @Override
    public MovimentacaoDTO toDTO() {
        final MovimentacaoDTO dto = new MovimentacaoDTO();
        dto.setId(id);
        dto.setEtapa(etapa);
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            bem.setIdDepartamento(null);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        if (idSalaOrigem != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala salaO = repo.getById(idSalaOrigem);
            salaO.setIdDepartamento(null);
            dto.setSalaOrigem(salaO == null ? null : salaO.toDTO());
        }
        if (idSalaDestino != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala salaD = repo.getById(idSalaOrigem);
            salaD.setIdDepartamento(null);
            dto.setSalaDestino(salaD == null ? null : salaD.toDTO());
        }

        final EventoMovimentacaoRepository eventoRepo = new EventoMovimentacaoRepository();
        final List<EventoMovimentacaoDTO> eventosDto = new ArrayList<>();
        final List<EventoMovimentacao> eventos = eventoRepo
                .getByMovimentacao(this);
        for (EventoMovimentacao ev : eventos) {
            eventosDto.add(ev.toDTO());
        }
        dto.setEventos(eventosDto);

        return dto;
    }

    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idMov) throws IllegalDeleteException {

        throw new IllegalDeleteException(
                "Não é possível deletar movimentações.");
    }

    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigaMov, final BaixaDTO novaMov)
            throws IllegalUpdateException {

        throw new IllegalUpdateException(
                "Não é possível alterar movimentações.");
    }

    public static void validarInserir(final FuncionarioDTO usuario,
            final MovimentacaoDTO novaMov) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final MovimentacaoRepository movRepo = new MovimentacaoRepository();

        if (novaMov.getId() != null) {
            final Movimentacao movExistente = movRepo.getById(novaMov.getId());
            if (movExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível iniciar a movimentação pois o ID já existe.");
            }
        }

        // CONTROLE DE ACESSO
        if (usuario == null) {
            throw new IllegalInsertException("Você não está logado.");
        }
        if (usuario.getCargo() == CargoEnum.FUNCIONARIO) {
            throw new IllegalInsertException(
                    "Você não pode iniciar movimentações.");
        }

        // VALIDADE DE DADOS
        final BaixaRepository baixaRepo = new BaixaRepository();
        if (baixaRepo.existsBemBaixado(novaMov.getBem().getId())) {
            throw new IllegalInsertException(
                    "Não é possível movimentar este bem pois ele já foi baixado.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.existsBemPendente(novaMov.getBem().getId())) {
            throw new IllegalInsertException(
                    "Não é possível movimentar este bem pois ele possui uma ordem de serviço pendente relacionda a ele.");
        }
        if (movRepo.existsBemInMov(novaMov.getBem().getId())) {
            throw new IllegalInsertException(
                    "Não é possível movimentar este bem pois ele já possui uma movimentação pendente relacionda a ele.");
        }

        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }
    }

    /**
     * Metodo a ser usado para validar o registro de um aceite de saída.
     * 
     * @param usuario
     * @param idAntigaMov
     * @param novaMov
     * @throws IllegalUpdateException
     */
    public static void validarAceiteSaida(final FuncionarioDTO usuario,
            final Integer idAntigaMov, final MovimentacaoDTO novaMov)
            throws IllegalUpdateException {

        if (idAntigaMov == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um bem sem seu ID.");
        }

        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        final Movimentacao antigaMov = movRepo.getById(idAntigaMov);

        if (antigaMov == null) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar uma movimentação inexistente.");
        }

        if (antigaMov.getId() == null) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar o ID da movimentação.");
        }

        if (!antigaMov.getId().equals(novaMov.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar o ID da movimentação.");
        }

        if (!antigaMov.getIdBem().equals(novaMov.getBem().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o Bem da movimentação.");
        }

        if (!antigaMov.getIdSalaOrigem()
                .equals(novaMov.getSalaOrigem().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar a sala de origem da movimentação.");
        }

        if (!antigaMov.getIdSalaDestino()
                .equals(novaMov.getSalaDestino().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar a sala de destino da movimentação.");
        }

        if (!novaMov.getEtapa().equals(EtapaMovEnum.AGUARDANDO_AC_SAIDA)) {
            throw new IllegalUpdateException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }

        if (!antigaMov.isInterna()) {
            throw new IllegalUpdateException(
                    "Esta ação não é possível para uma movimentação interna.");
        }

        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    /**
     * Metodo a ser usado para validar o registro de um aceite de entrada.
     * 
     * @param usuario
     * @param idAntigaMov
     * @param novaMov
     * @throws IllegalUpdateException
     */
    public static void validarAceiteEntrada(final FuncionarioDTO usuario,
            final Integer idAntigaMov, final MovimentacaoDTO novaMov)
            throws IllegalUpdateException {

        if (idAntigaMov == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um bem sem seu ID.");
        }

        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        final Movimentacao antigaMov = movRepo.getById(idAntigaMov);

        if (antigaMov == null) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar uma movimentação inexistente.");
        }

        if (antigaMov.getId() == null) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar o ID da movimentação.");
        }

        if (!antigaMov.getId().equals(novaMov.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar o ID da movimentação.");
        }

        if (!antigaMov.getIdBem().equals(novaMov.getBem().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o Bem da movimentação.");
        }

        if (!antigaMov.getIdSalaOrigem()
                .equals(novaMov.getSalaOrigem().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar a sala de origem da movimentação.");
        }

        if (!antigaMov.getIdSalaDestino()
                .equals(novaMov.getSalaDestino().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar a sala de destino da movimentação.");
        }

        if (!novaMov.getEtapa().equals(EtapaMovEnum.AGUARDANDO_AC_ENTRADA)) {
            throw new IllegalUpdateException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }

        if (!antigaMov.isInterna()) {
            throw new IllegalUpdateException(
                    "Esta ação não é possível para uma movimentação interna.");
        }

        if (!antigaMov.getEtapa().equals(EtapaMovEnum.AGUARDANDO_AC_SAIDA)) {
            throw new IllegalUpdateException(
                    "Esta ação só é possível se o aceite de saída para a mivimentação já estiver dado");
        }

        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    public static void validarCancelar(final FuncionarioDTO usuario,
            final Integer idAntigaMov, final MovimentacaoDTO novaMov)
            throws IllegalUpdateException {
        if (idAntigaMov == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um bem sem seu ID.");
        }

        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        final Movimentacao antigaMov = movRepo.getById(idAntigaMov);

        if (antigaMov == null) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar uma movimentação inexistente.");
        }

        if (antigaMov.getId() == null) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar o ID da movimentação.");
        }

        if (!antigaMov.getId().equals(novaMov.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível atualizar o ID da movimentação.");
        }

        if (!antigaMov.getIdBem().equals(novaMov.getBem().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o Bem da movimentação.");
        }

        if (!antigaMov.getIdSalaOrigem()
                .equals(novaMov.getSalaOrigem().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar a sala de origem da movimentação.");
        }

        if (!antigaMov.getIdSalaDestino()
                .equals(novaMov.getSalaDestino().getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar a sala de destino da movimentação.");
        }

        if (!novaMov.getEtapa().equals(EtapaMovEnum.FINALIZADA)) {
            throw new IllegalUpdateException(
                    "Esta ação não é possível para movimentações já finalizadas.");
        }

        if (!novaMov.getEtapa().equals(EtapaMovEnum.CANCELADA)) {
            throw new IllegalUpdateException(
                    "Esta movimentação já foi cancelada.");
        }

        try {

            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }
    }

    private static void validarCampos(final MovimentacaoDTO mov)
            throws CRUDException {
        if (mov.getBem() == null) {
            throw new CRUDException("O 'Bem' não pode ser vazio.");
        }
        // Definida inicialmente pelo sistema
        if (mov.getEtapa() == null) {
            throw new CRUDException("A 'Etapa' deve ser definida.");
        }
        if (mov.getSalaOrigem() == null) {
            throw new CRUDException("A 'Sala de Origem' não pode ser vazia.");
        }
        if (mov.getSalaDestino() == null) {
            throw new CRUDException("A 'Sala de Destino' não pode ser vazia.");
        }
    }

    public boolean isInterna() {
        if (idSalaOrigem == idSalaDestino) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo a ser usado quando uma movimentação é iniciada.
     * 
     * @param usuario
     * @param mov
     * @return mensagem
     */
    public static List<String> posInserir(final BemDTO usuario,
            final MovimentacaoDTO mov) {

        final List<String> messages = new ArrayList<String>();
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(mov.getBem().getId());

        // Colocar o bem com o status "em movimentação"
        bem.setSituacao(BemSituacaoEnum.EM_MOVIMENTACAO);
        bemRepo.update(bem);
        messages.add("O bem " + bem.getDescricao()
                + " está agora 'Em movimentação'.");

        return messages;

    }

    /**
     * Metodo a ser usado quando uma movimentação é Finalizada ou Cancelada.
     * 
     * @param usuario
     * @param mov
     * @return mensagem
     */
    public static List<String> posConcluir(final FuncionarioDTO usuario,
            final MovimentacaoDTO mov) {
        final List<String> messages = new ArrayList<String>();
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(mov.getBem().getId());

        // Colocar o bem com o status "Incorporado"
        bem.setSituacao(BemSituacaoEnum.INCORPORADO);
        bemRepo.update(bem);
        messages.add("O bem " + bem.getDescricao()
                + " está agora 'Em movimentação'.");

        return messages;

    }

    public EtapaMovEnum getEtapa() {
        return etapa;
    }

    public void setEtapa(final EtapaMovEnum etapa) {
        this.etapa = etapa;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer idValor) {
        id = idValor;
    }

    public Integer getIdBem() {
        return idBem;
    }

    public void setIdBem(final Integer idBem) {
        this.idBem = idBem;
    }

    public Integer getIdSalaOrigem() {
        return idSalaOrigem;
    }

    public void setIdSalaOrigem(final Integer idSalaOrigem) {
        this.idSalaOrigem = idSalaOrigem;
    }

    public Integer getIdSalaDestino() {
        return idSalaDestino;
    }

    public void setIdSalaDestino(final Integer idSalaDestino) {
        this.idSalaDestino = idSalaDestino;
    }

}
