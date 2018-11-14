package com.github.nelsonwilliam.invscp.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.github.nelsonwilliam.invscp.exception.CRUDException;
import com.github.nelsonwilliam.invscp.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.enums.BemSituacaoEnum;
import com.github.nelsonwilliam.invscp.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.model.repository.GrupoMaterialRepository;
import com.github.nelsonwilliam.invscp.model.repository.OrdemServicoRepository;
import com.github.nelsonwilliam.invscp.model.repository.SalaRepository;

public class Bem implements Model<BemDTO> {

    private static final long serialVersionUID = 1684907497812931878L;

    private Integer id = null;

    private String descricao = null;

    private Long numeroTombamento = null;

    private LocalDate dataCadastro = null;

    private LocalDate dataAquisicao = null;

    private String numeroNotaFiscal = null;

    private String especificacao = null;

    private LocalDate garantia = null;

    private String marca = null;

    private Float valorCompra = null;

    private BemSituacaoEnum situacao = null;

    private Integer idGrupoMaterial = null;

    private Integer idSala = null;

    private Integer idDepartamento = null;

    @Override
    public void setValuesFromDTO(BemDTO dto) {
        setDataAquisicao(dto.getDataAquisicao());
        setDataCadastro(dto.getDataCadastro());
        setDescricao(dto.getDescricao());
        setEspecificacao(dto.getEspecificacao());
        setGarantia(dto.getGarantia());
        setId(dto.getId());
        if (dto.getDepartamento() != null) {
            setIdDepartamento(dto.getDepartamento().getId());
        }
        if (dto.getGrupoMaterial() != null) {
            setIdGrupoMaterial(dto.getGrupoMaterial().getId());
        }
        if (dto.getSala() != null) {
            setIdSala(dto.getSala().getId());
        }
        setMarca(dto.getMarca());
        setNumeroNotaFiscal(dto.getNumeroNotaFiscal());
        setNumeroTombamento(dto.getNumeroTombamento());
        setSituacao(dto.getSituacao());
        setValorCompra(dto.getValorCompra());

    }

    @Override
    public BemDTO toDTO() {
        final BemDTO dto = new BemDTO();
        dto.setDataAquisicao(dataAquisicao);
        dto.setDataCadastro(dataCadastro);
        dto.setDescricao(descricao);
        dto.setEspecificacao(especificacao);
        dto.setGarantia(garantia);
        dto.setId(id);
        dto.setMarca(marca);
        dto.setNumeroNotaFiscal(numeroNotaFiscal);
        dto.setNumeroTombamento(numeroTombamento);
        dto.setSituacao(situacao);
        dto.setValorCompra(valorCompra);
        if (idDepartamento != null) {
            final DepartamentoRepository repo = new DepartamentoRepository();
            final Departamento dept = repo.getById(idDepartamento);
            dto.setDepartamento(dept == null ? null : dept.toDTO());
        }
        if (idSala != null) {
            final SalaRepository repo = new SalaRepository();
            final Sala sala = repo.getById(idSala);
            dto.setSala(sala == null ? null : sala.toDTO());
        }
        if (idGrupoMaterial != null) {
            final GrupoMaterialRepository repo = new GrupoMaterialRepository();
            final GrupoMaterial gm = repo.getById(idSala);
            dto.setGrupoMaterial(gm == null ? null : gm.toDTO());
        }
        return dto;
    }

    public static void validarDeletar(final FuncionarioDTO usuario,
            final Integer idBem) throws IllegalDeleteException {
        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idBem == null) {
            throw new IllegalDeleteException(
                    "Não é possível remover bens sem informar o ID.");
        }

        final BemRepository bemRepo = new BemRepository();
        final Bem bem = bemRepo.getById(idBem);

        if (bem == null) {
            throw new IllegalDeleteException(
                    "Não foi possível encontrar o bem desejado.");
        }

        // ------------------
        // CONTROLE DE ACESSO
        // ------------------
        // Verificar controle de acesso (O usuário pode alterar esse tipo
        // de elemento, com esses atributos? Etc.).
        // Bens são podem ser editados por chefes de patrimônio ou por chefes do
        // departamento ao qual pertencem
        if (usuario == null) {
            throw new IllegalDeleteException("Você não está logado.");
        }
        if (usuario.getDepartamento() == null) {
            throw new IllegalDeleteException(
                    "Você não possui um departamento.");
        }
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item");
            }
            if (!usuario.getDepartamento().getId()
                    .equals(bem.getIdDepartamento())) {
                throw new IllegalDeleteException(
                        "Você não tem permissão para deletar este item");
            }
        }

        // TODO ...

        // -----------------
        // VALIDADE DE DADOS
        // -----------------
        // Verificar validade dos dados (Os novos atributos do elemento são
        // válidos? Há itens que não podem ser modificados? Há itens
        // repetidos/duplicados/já utilizados?Há itens obrigatórios faltando?
        // Etc).

        // TODO Verificar se alguma ordem de serviço ou beixa depende desse bem.
        final BaixaRepository baixaRepo = new BaixaRepository();
        if (baixaRepo.getByBem(bem).size() > 0) {
            throw new IllegalDeleteException("Não é possível deletar o bem "
                    + bem.getDescricao() + " pois ele já foi baixado.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.getByBem(bem).size() > 0) {
            throw new IllegalDeleteException("Não é possível deletar o bem "
                    + bem.getDescricao()
                    + " pois ele possui ordens de serviço pendentes relaciondas a ele.");
        }

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDate();
        if (bem.getDataCadastro() != null && bem.getDataCadastro()
                .getMonthValue() >= localDate.getMonthValue()) {
            throw new IllegalDeleteException(
                    "O prazo para remoção deste bem já expirou.");
        }
    }

    public static void validarInserir() throws IllegalInsertException {
    }

    public static void validarAlterar() throws IllegalUpdateException {
    }

    private static void validarCampos(BemDTO bem) throws CRUDException {
        if (bem.getDataAquisicao() == null) {
            throw new CRUDException(
                    "O 'data de aquisição' é um campo obrigatório.");
        }
        if (bem.getDataCadastro() == null) {
            throw new CRUDException(
                    "O 'data de cadastro' é um campo obrigatório.");
        }
        if (bem.getDepartamento() == null) {

        }
        if (bem.getDescricao() == null || bem.getDescricao().isEmpty()) {

        }
        if (bem.getEspecificacao() == null
                || bem.getEspecificacao().isEmpty()) {

        }
        if (bem.getGarantia() == null) {

        }
        if (bem.getGrupoMaterial() == null) {

        }
        if (bem.getMarca() == null || bem.getMarca().isEmpty()) {

        }
        if (bem.getNumeroNotaFiscal() == null
                || bem.getNumeroNotaFiscal().isEmpty()) {

        }
        if (bem.getNumeroTombamento() == null
                || bem.getNumeroTombamento() < 0) {

        }
        if (bem.getSala() == null) {

        }
        if (bem.getSituacao() == null) {

        }

    }

    public final Integer getId() {
        return id;
    }

    public final void setId(Integer id) {
        this.id = id;
    }

    public final String getDescricao() {
        return descricao;
    }

    public final void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public final Long getNumeroTombamento() {
        return numeroTombamento;
    }

    public final void setNumeroTombamento(Long numeroTombamento) {
        this.numeroTombamento = numeroTombamento;
    }

    public final LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public final void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public final LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public final void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public final String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public final void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public final String getEspecificacao() {
        return especificacao;
    }

    public final void setEspecificacao(String especificacao) {
        this.especificacao = especificacao;
    }

    public final LocalDate getGarantia() {
        return garantia;
    }

    public final void setGarantia(LocalDate garantia) {
        this.garantia = garantia;
    }

    public final String getMarca() {
        return marca;
    }

    public final void setMarca(String marca) {
        this.marca = marca;
    }

    public final Float getValorCompra() {
        return valorCompra;
    }

    public final void setValorCompra(Float valorCompra) {
        this.valorCompra = valorCompra;
    }

    public final BemSituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(BemSituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public final String getSituacaoString() {
        return this.situacao.getTexto();
    }

    public final void setSituacaoString(String sit) {
        this.situacao = BemSituacaoEnum.valueOf(sit);
    }

    public final Integer getIdGrupoMaterial() {
        return idGrupoMaterial;
    }

    public final void setIdGrupoMaterial(Integer idGrupoMaterial) {
        this.idGrupoMaterial = idGrupoMaterial;
    }

    public final Integer getIdSala() {
        return idSala;
    }

    public final void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public final Integer getIdDepartamento() {
        return idDepartamento;
    }

    public final void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

}
