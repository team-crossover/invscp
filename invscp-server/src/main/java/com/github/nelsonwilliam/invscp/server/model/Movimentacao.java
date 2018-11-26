package com.github.nelsonwilliam.invscp.server.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.github.nelsonwilliam.invscp.server.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.EventoMovimentacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.OrdemServicoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.shared.exception.CRUDException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.shared.model.enums.TipoEventoMovEnum;

public class Movimentacao implements Model<MovimentacaoDTO> {

    private static final long serialVersionUID = 336584770255104292L;

    private Integer id = null;

    private EtapaMovEnum etapa = null;

    private Integer idBem = null;

    private Integer idSalaOrigem = null;

    private Integer idSalaDestino = null;

    private String numGuiaTransporte = null;

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
        setNumGuiaTransporte(dto.getNumGuiaTransporte());

    }

    @Override
    public MovimentacaoDTO toDTO() {
        final MovimentacaoDTO dto = new MovimentacaoDTO();
        dto.setId(id);
        dto.setEtapa(etapa);
        if (idBem != null) {
            final BemRepository repo = new BemRepository();
            final Bem bem = repo.getById(idBem);
            dto.setBem(bem == null ? null : bem.toDTO());
        }
        if (idSalaOrigem != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala salaO = repo.getById(idSalaOrigem);
            dto.setSalaOrigem(salaO == null ? null : salaO.toDTO());
        }
        if (idSalaDestino != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala salaD = repo.getById(idSalaDestino);
            dto.setSalaDestino(salaD == null ? null : salaD.toDTO());
        }

        final EventoMovimentacaoRepository eventoRepo =
                new EventoMovimentacaoRepository();
        final List<EventoMovimentacaoDTO> eventosDto = new ArrayList<>();
        final List<EventoMovimentacao> eventos =
                eventoRepo.getByIdMovimentacao(id);
        for (final EventoMovimentacao ev : eventos) {
            // Para evitar loops infinitos ao criar os DTOs
            // movimentacao/eventoMovimentacao, o DTO do evento movimentação
            // dessa lista não tem o ID da movimentação.
            ev.setIdMovimentacao(null);
            eventosDto.add(ev.toDTO());
        }
        dto.setEventos(eventosDto);
        dto.setNumGuiaTransporte(numGuiaTransporte);

        return dto;
    }

    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idMov) throws IllegalDeleteException {

        throw new IllegalDeleteException(
                "Não é possível deletar movimentações.");
    }

    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigaMov, final MovimentacaoDTO newNovo)
            throws IllegalUpdateException {

        throw new IllegalUpdateException(
                "Não é possível alterar movimentações.");
    }

    public static void validarInserir(final FuncionarioDTO usuario,
            final MovimentacaoDTO novaMov) throws IllegalInsertException {

        // IDENTIFICADORES
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

        if (!novaMov.isParaMesmaCidade()) {
            if (novaMov.getNumGuiaTransporte() == null) {
                throw new IllegalInsertException(
                        "Movimentações entre cidades devem possuir um número de guia de transporte.");
            }

            if (movRepo
                    .existsNumGuiaTransporte(novaMov.getNumGuiaTransporte())) {
                throw new IllegalInsertException(
                        "Este número de guia de transporte já foi utilizado.");
            }
        }

        try {
            validarCampos(novaMov);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
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

    /**
     * Metodo a ser usado quando uma movimentação é iniciada.
     *
     * @param usuario
     * @param movDto
     * @return mensagem
     */
    public static List<String> posInserir(final FuncionarioDTO usuario,
            final MovimentacaoDTO movDto) {

        System.out.println(LocalTime.now() + " - " + movDto.getId());
        final List<String> messages = new ArrayList<String>();

        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        final Movimentacao mov = movRepo.getById(movDto.getId());
        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(movDto.getBem().getId());

        // Colocar o bem com o status "em movimentação"
        bem.setSituacao(BemSituacaoEnum.EM_MOVIMENTACAO);
        bemRepo.update(bem);
        messages.add("O bem " + bem.getDescricao()
                + " está agora 'Em movimentação'.");

        // Mudar a etapa da movimentação pra "aguardando aceite saída" ou
        // "finalizada" dependendo se é externa ou interna
        if (mov.isInterna()) {
            mov.setEtapa(EtapaMovEnum.EM_MOVIMENTACAO);
        } else {
            mov.setEtapa(EtapaMovEnum.AGUARDANDO_AC_SAIDA);
        }
        movRepo.update(mov);

        // Adicionar o evento de criação da movimentação
        final EventoMovimentacaoRepository eventoRepo =
                new EventoMovimentacaoRepository();
        final EventoMovimentacao evento = new EventoMovimentacao();
        evento.setData(LocalDate.now());
        evento.setIdFuncionario(usuario.getId());
        evento.setIdMovimentacao(movDto.getId());
        evento.setTipo(TipoEventoMovEnum.CRIACAO);
        eventoRepo.add(evento);

        return messages;
    }

    public boolean isInterna() {
        final SalaRepository salaRepo = new SalaRepository();
        final Sala origem = salaRepo.getById(idSalaOrigem);
        final Sala destino = salaRepo.getById(idSalaDestino);
        return origem.getIdDepartamento().equals(destino.getIdDepartamento());
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

    public String getNumGuiaTransporte() {
        return numGuiaTransporte;
    }

    public void setNumGuiaTransporte(final String numGuiaTransporte) {
        this.numGuiaTransporte = numGuiaTransporte;
    }

}
