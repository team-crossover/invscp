package com.github.nelsonwilliam.invscp.server.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.EventoMovimentacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.FuncionarioRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.shared.exception.CRUDException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.CargoEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.TipoEventoMovEnum;

public class EventoMovimentacao implements Model<EventoMovimentacaoDTO> {

    private static final long serialVersionUID = 3192048412630028218L;

    private Integer id = null;

    private TipoEventoMovEnum tipo = null;

    private LocalDate data = null;

    private String justificativa = null;

    private Integer idMovimentacao = null;

    private Integer idFuncionario = null;

    @Override
    public void setValuesFromDTO(final EventoMovimentacaoDTO dto) {
        setId(dto.getId());
        setTipo(dto.getTipo());
        setData(dto.getData());
        setJustificativa(dto.getJustificativa());
        if (dto.getMovimentacao() != null) {
            setIdMovimentacao(dto.getMovimentacao().getId());
        }
        if (dto.getFuncionario() != null) {
            setIdFuncionario(dto.getFuncionario().getId());
        }
    }

    @Override
    public EventoMovimentacaoDTO toDTO() {
        final EventoMovimentacaoDTO dto = new EventoMovimentacaoDTO();
        dto.setId(id);
        dto.setTipo(tipo);
        dto.setData(data);
        dto.setJustificativa(justificativa);
        if (idMovimentacao != null) {
            final MovimentacaoRepository repo = new MovimentacaoRepository();
            final Movimentacao mov = repo.getById(idMovimentacao);
            dto.setMovimentacao(mov == null ? null : mov.toDTO());
        }
        if (idFuncionario != null) {
            final FuncionarioRepository repo = new FuncionarioRepository();
            final Funcionario func = repo.getById(idFuncionario);
            func.setIdDepartamento(null);
            dto.setFuncionario(func == null ? null : func.toDTO());
        }
        return dto;
    }

    public static void validarInserir(FuncionarioDTO usuario,
            EventoMovimentacaoDTO novoEvento) throws IllegalInsertException {

        // IDENTIFICADORES
        final EventoMovimentacaoRepository evRepo =
                new EventoMovimentacaoRepository();

        if (novoEvento.getId() != null) {
            final EventoMovimentacao movExistente =
                    evRepo.getById(novoEvento.getId());
            if (movExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir o evento de movimentação pois o ID já existe.");
            }
        }

        // CONTROLE DE ACESSO
        if (usuario == null) {
            throw new IllegalInsertException("Você não está logado.");
        }
        if (usuario.getCargo() == CargoEnum.FUNCIONARIO) {
            throw new IllegalInsertException(
                    "Você não pode inserir eventos de movimentações.");
        }

        // VALIDADE DE DADOS
        try {
            validarCampos(novoEvento);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }

        switch (novoEvento.getTipo()) {
            case ACEITE_ENTRADA:
                validarInserirAceiteEntrada(usuario, novoEvento);
                break;
            case ACEITE_SAIDA:
                validarInserirAceiteSaida(usuario, novoEvento);
                break;
            case NEGACAO_ENTRADA:
                validarInserirNegacaoEntrada(usuario, novoEvento);
                break;
            case NEGACAO_SAIDA:
                validarInserirNegacaoSaida(usuario, novoEvento);
                break;
            case FINALIZACAO:
                validarInserirFinalizacao(usuario, novoEvento);
                break;
            case CANCELAMENTO:
                validarInserirCancelamento(usuario, novoEvento);
                break;
            default:
                throw new IllegalInsertException("Tipo de evento inválido.");
        }
    }

    private static void validarInserirAceiteSaida(final FuncionarioDTO usuario,
            final EventoMovimentacaoDTO evDto) throws IllegalInsertException {

        if (!evDto.getMovimentacao().getEtapa()
                .equals(EtapaMovEnum.AGUARDANDO_AC_SAIDA)) {
            throw new IllegalInsertException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }

        boolean isInterna = evDto.getMovimentacao().getSalaOrigem()
                .getDepartamento().getId().equals(evDto.getMovimentacao()
                        .getSalaDestino().getDepartamento().getId());
        if (isInterna) {
            throw new IllegalInsertException(
                    "Esta ação não é possível para uma movimentação interna.");
        }
    }

    private static void validarInserirAceiteEntrada(
            final FuncionarioDTO usuario, final EventoMovimentacaoDTO evDto)
            throws IllegalInsertException {

        if (!evDto.getMovimentacao().getEtapa()
                .equals(EtapaMovEnum.AGUARDANDO_AC_ENTRADA)) {
            throw new IllegalInsertException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }

        boolean isInterna = evDto.getMovimentacao().getSalaOrigem()
                .getDepartamento().getId().equals(evDto.getMovimentacao()
                        .getSalaDestino().getDepartamento().getId());
        if (isInterna) {
            throw new IllegalInsertException(
                    "Esta ação não é possível para uma movimentação interna.");
        }
    }

    private static void validarInserirNegacaoSaida(final FuncionarioDTO usuario,
            final EventoMovimentacaoDTO evDto) throws IllegalInsertException {

        if (!evDto.getMovimentacao().getEtapa()
                .equals(EtapaMovEnum.AGUARDANDO_AC_SAIDA)) {
            throw new IllegalInsertException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }

        boolean isInterna = evDto.getMovimentacao().getSalaOrigem()
                .getDepartamento().getId().equals(evDto.getMovimentacao()
                        .getSalaDestino().getDepartamento().getId());
        if (isInterna) {
            throw new IllegalInsertException(
                    "Esta ação não é possível para uma movimentação interna.");
        }
    }

    private static void validarInserirNegacaoEntrada(
            final FuncionarioDTO usuario, final EventoMovimentacaoDTO evDto)
            throws IllegalInsertException {

        if (!evDto.getMovimentacao().getEtapa()
                .equals(EtapaMovEnum.AGUARDANDO_AC_ENTRADA)) {
            throw new IllegalInsertException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }

        boolean isInterna = evDto.getMovimentacao().getSalaOrigem()
                .getDepartamento().getId().equals(evDto.getMovimentacao()
                        .getSalaDestino().getDepartamento().getId());
        if (isInterna) {
            throw new IllegalInsertException(
                    "Esta ação não é possível para uma movimentação interna.");
        }
    }

    private static void validarInserirFinalizacao(final FuncionarioDTO usuario,
            final EventoMovimentacaoDTO evDto) throws IllegalInsertException {

        if (evDto.getMovimentacao().getEtapa().equals(EtapaMovEnum.CANCELADA)
                || evDto.getMovimentacao().getEtapa()
                        .equals(EtapaMovEnum.FINALIZADA)
                || evDto.getMovimentacao().getEtapa()
                        .equals(EtapaMovEnum.INICIADA)) {
            throw new IllegalInsertException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }
    }

    private static void validarInserirCancelamento(final FuncionarioDTO usuario,
            final EventoMovimentacaoDTO evDto) throws IllegalInsertException {

        if (evDto.getMovimentacao().getEtapa().equals(EtapaMovEnum.CANCELADA)
                || evDto.getMovimentacao().getEtapa()
                        .equals(EtapaMovEnum.FINALIZADA)
                || evDto.getMovimentacao().getEtapa()
                        .equals(EtapaMovEnum.INICIADA)) {
            throw new IllegalInsertException(
                    "Esta ação não é possível nesta etapa da movimentação.");
        }
    }

    private static void validarCampos(EventoMovimentacaoDTO evento)
            throws CRUDException {
        // TODO
    }

    public static void validarAlterar(FuncionarioDTO usuario, Integer idAntigo,
            EventoMovimentacaoDTO novo) throws IllegalUpdateException {

        throw new IllegalUpdateException(
                "Não é possível alterar eventos de movimentações.");
    }

    public static void validarDeletar(FuncionarioDTO usuario, Integer id)
            throws IllegalDeleteException {

        throw new IllegalDeleteException(
                "Não é possível deletar eventos de movimentações.");
    }

    public static List<String> posInserir(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {

        switch (evento.getTipo()) {
            case ACEITE_ENTRADA:
                return posInserirAceiteEntrada(usuario, evento);
            case ACEITE_SAIDA:
                return posInserirAceiteSaida(usuario, evento);
            case NEGACAO_ENTRADA:
                return posInserirNegacaoEntrada(usuario, evento);
            case NEGACAO_SAIDA:
                return posInserirNegacaoSaida(usuario, evento);
            case FINALIZACAO:
                return posInserirFinalizacao(usuario, evento);
            case CANCELAMENTO:
                return posInserirCancelamento(usuario, evento);
            default:
                return new ArrayList<String>();
        }
    }

    private static List<String> posInserirAceiteSaida(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {
        final List<String> messages = new ArrayList<String>();

        // Muda a etapa da movimentação
        MovimentacaoRepository movRepo = new MovimentacaoRepository();
        Movimentacao mov = movRepo.getById(evento.getMovimentacao().getId());
        mov.setEtapa(EtapaMovEnum.AGUARDANDO_AC_ENTRADA);
        movRepo.update(mov);

        return messages;
    }

    private static List<String> posInserirAceiteEntrada(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {
        final List<String> messages = new ArrayList<String>();

        // Muda a etapa da movimentação
        MovimentacaoRepository movRepo = new MovimentacaoRepository();
        Movimentacao mov = movRepo.getById(evento.getMovimentacao().getId());
        mov.setEtapa(EtapaMovEnum.EM_MOVIMENTACAO);
        movRepo.update(mov);
        
        return messages;
    }

    private static List<String> posInserirNegacaoSaida(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {
        final List<String> messages = new ArrayList<String>();

        // Muda a etapa da movimentação
        MovimentacaoRepository movRepo = new MovimentacaoRepository();
        Movimentacao mov = movRepo.getById(evento.getMovimentacao().getId());
        mov.setEtapa(EtapaMovEnum.CANCELADA);
        movRepo.update(mov);

        // Muda a situação do bem
        BemRepository bemRepo = new BemRepository();
        Bem bem = bemRepo.getById(evento.getMovimentacao().getBem().getId());
        bem.setSituacao(BemSituacaoEnum.INCORPORADO);
        bemRepo.update(bem);

        messages.add("O bem " + bem.getDescricao()
                + " não está mais 'Em movimentação'.");

        return messages;
    }

    private static List<String> posInserirNegacaoEntrada(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {
        final List<String> messages = new ArrayList<String>();

        // Muda a etapa da movimentação
        MovimentacaoRepository movRepo = new MovimentacaoRepository();
        Movimentacao mov = movRepo.getById(evento.getMovimentacao().getId());
        mov.setEtapa(EtapaMovEnum.CANCELADA);
        movRepo.update(mov);

        // Muda a situação do bem
        BemRepository bemRepo = new BemRepository();
        Bem bem = bemRepo.getById(evento.getMovimentacao().getBem().getId());
        bem.setSituacao(BemSituacaoEnum.INCORPORADO);
        bemRepo.update(bem);

        messages.add("O bem " + bem.getDescricao()
                + " não está mais 'Em movimentação'.");

        return messages;
    }

    private static List<String> posInserirFinalizacao(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {
        final List<String> messages = new ArrayList<String>();

        // Muda a etapa da movimentação
        MovimentacaoRepository movRepo = new MovimentacaoRepository();
        Movimentacao mov = movRepo.getById(evento.getMovimentacao().getId());
        mov.setEtapa(EtapaMovEnum.FINALIZADA);
        movRepo.update(mov);

        // Muda a situação e a sala do bem
        BemRepository bemRepo = new BemRepository();
        Bem bem = bemRepo.getById(evento.getMovimentacao().getBem().getId());
        bem.setSituacao(BemSituacaoEnum.INCORPORADO);
        bem.setIdSala(evento.getMovimentacao().getSalaDestino().getId());
        bemRepo.update(bem);

        messages.add("O bem " + bem.getDescricao()
                + " não está mais 'Em movimentação' e sua sala foi atualizada para "
                + evento.getMovimentacao().getSalaDestino().getNome() + ".");

        return messages;
    }

    private static List<String> posInserirCancelamento(FuncionarioDTO usuario,
            EventoMovimentacaoDTO evento) {
        final List<String> messages = new ArrayList<String>();

        // Muda a etapa da movimentação
        MovimentacaoRepository movRepo = new MovimentacaoRepository();
        Movimentacao mov = movRepo.getById(evento.getMovimentacao().getId());
        mov.setEtapa(EtapaMovEnum.CANCELADA);
        movRepo.update(mov);

        // Muda a situação do bem
        BemRepository bemRepo = new BemRepository();
        Bem bem = bemRepo.getById(evento.getMovimentacao().getBem().getId());
        bem.setSituacao(BemSituacaoEnum.INCORPORADO);
        bemRepo.update(bem);

        messages.add("O bem " + bem.getDescricao()
                + " não está mais 'Em movimentação'.");

        return messages;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(final Integer idValor) {
        this.id = idValor;
    }

    public TipoEventoMovEnum getTipo() {
        return tipo;
    }

    public void setTipo(final TipoEventoMovEnum tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(final LocalDate data) {
        this.data = data;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(final String justificativa) {
        this.justificativa = justificativa;
    }

    public Integer getIdMovimentacao() {
        return idMovimentacao;
    }

    public void setIdMovimentacao(final Integer idMovimentacao) {
        this.idMovimentacao = idMovimentacao;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(final Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

}
