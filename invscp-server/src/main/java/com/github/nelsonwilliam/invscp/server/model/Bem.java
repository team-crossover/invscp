package com.github.nelsonwilliam.invscp.server.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.github.nelsonwilliam.invscp.server.model.repository.BaixaRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.BemRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.DepartamentoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.GrupoMaterialRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.MovimentacaoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.OrdemServicoRepository;
import com.github.nelsonwilliam.invscp.server.model.repository.SalaRepository;
import com.github.nelsonwilliam.invscp.shared.exception.CRUDException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalDeleteException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalInsertException;
import com.github.nelsonwilliam.invscp.shared.exception.IllegalUpdateException;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;

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

    private BigDecimal valorCompra = null;

    private BemSituacaoEnum situacao = null;

    private Integer idGrupoMaterial = null;

    private Integer idSala = null;

    private Integer idDepartamento = null;

    @Override
    public void setValuesFromDTO(final BemDTO dto) {
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
        dto.setDepreciacoes(getDepreciacoesPorAno());
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
            final GrupoMaterial gm = repo.getById(idGrupoMaterial);
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

        // CONTROLE DE ACESSO
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

        // VALIDADE DE DADOS
        final BaixaRepository baixaRepo = new BaixaRepository();
        if (baixaRepo.existsBemBaixado(idBem)) {
            throw new IllegalDeleteException(
                    "Não é possível deletar este bem pois ele já foi baixado.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.existsBemPendente(idBem)) {
            throw new IllegalDeleteException(
                    "Não é possível deletar este bem pois ele possui uma ordem de serviço pendente relacionda a ele.");
        }
        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        if (movRepo.existsBemInMov(idBem)) {
            throw new IllegalDeleteException(
                    "Não é possível deletar este bem pois ele possui uma movimentação pendente relacionda a ele.");
        }

        final LocalDate hoje = LocalDate.now();
        if (bem.getDataCadastro() != null && bem.getDataCadastro()
                .getMonthValue() > hoje.getMonthValue()) {
            throw new IllegalDeleteException(
                    "O prazo para remoção deste bem já expirou.");
        }

        if (movRepo.existsBemRelacionado(idBem)) {
            throw new IllegalDeleteException(
                    "Não é possível deletar este bem pois ele possui uma movimentação cadastrada relacionda a ele.");
        }
    }

    public static void validarInserir(final FuncionarioDTO usuario,
            final BemDTO novoBem) throws IllegalInsertException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        final BemRepository bemRepo = new BemRepository();

        if (novoBem.getId() != null) {
            final Bem bemExistente = bemRepo.getById(novoBem.getId());
            if (bemExistente != null) {
                throw new IllegalInsertException(
                        "Não é possível inserir o bem pois o ID já existe.");
            }
        }

        // CONTROLE DE ACESSO
        if (usuario == null) {
            throw new IllegalInsertException("Você não está logado.");
        }
        if (usuario.getDepartamento() == null) {
            throw new IllegalInsertException(
                    "Você não possui um departamento.");
        }
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalInsertException(
                        "Você não tem permissão para inserir bens");
            }
            if (!usuario.getDepartamento().getId()
                    .equals(novoBem.getDepartamento().getId())) {
                throw new IllegalInsertException(
                        "Você não tem permissão para inserir bens");
            }
        }

        // VALIDAR DADOS
        try {
            validarCampos(novoBem);
        } catch (final CRUDException e) {
            throw new IllegalInsertException(e.getMessage());
        }

        if (bemRepo.existsNumTombamento(novoBem.getNumeroTombamento())) {
            throw new IllegalInsertException(
                    "O 'Numero de tombamento' inserido já foi utilizado");
        }

        if (novoBem.getDataAquisicao().isAfter(novoBem.getGarantia())) {
            throw new IllegalInsertException(
                    "A data do campo 'Garantia' não pode ser anterior à data de aquisição do bem");
        }

    }

    public static void validarAlterar(final FuncionarioDTO usuario,
            final Integer idAntigoBem, final BemDTO novoBem)
            throws IllegalUpdateException {

        // ---------------
        // IDENTIFICADORES
        // ---------------

        if (idAntigoBem == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um bem sem seu ID.");
        }

        final BemRepository bemRepo = new BemRepository();
        final Bem antigoBem = bemRepo.getById(idAntigoBem);

        if (antigoBem == null) {
            throw new IllegalUpdateException(
                    "Não é possível alterar um bem inexistente.");
        }

        if (antigoBem.getId() == null) {
            throw new IllegalUpdateException("Não é possível remover o ID do Bem.");
        }

        if (!antigoBem.getId().equals(novoBem.getId())) {
            throw new IllegalUpdateException(
                    "Não é possível alterar o ID do bem.");
        }

        // CONTROLE DE ACESSO
        if (usuario == null) {
            throw new IllegalUpdateException("Você não está logado.");
        }
        if (usuario.getDepartamento() == null) {
            throw new IllegalUpdateException(
                    "Você não possui um departamento.");
        }
        if (!usuario.getCargo().isChefeDePatrimonio()) {
            if (!usuario.getCargo().isChefeDeDepartamento()) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar este item");
            }
            if (!usuario.getDepartamento().getId()
                    .equals(antigoBem.getIdDepartamento())) {
                throw new IllegalUpdateException(
                        "Você não tem permissão para alterar este item");
            }
        }

        // VALIDADE DE DADOS
        final BaixaRepository baixaRepo = new BaixaRepository();
        if (baixaRepo.existsBemBaixado(idAntigoBem)) {
            throw new IllegalUpdateException(
                    "Não é possível alterar este bem pois ele já foi baixado.");
        }

        final OrdemServicoRepository osRepo = new OrdemServicoRepository();
        if (osRepo.existsBemPendente(idAntigoBem)) {
            throw new IllegalUpdateException(
                    "Não é possível alterar este bem pois ele possui uma ordem de serviço pendente relacionda a ele.");
        }
        final MovimentacaoRepository movRepo = new MovimentacaoRepository();
        if (movRepo.existsBemInMov(idAntigoBem)) {
            throw new IllegalUpdateException(
                    "Não é possível alterar este bem pois ele possui uma movimentação pendente relacionda a ele.");
        }

        try {
            validarCampos(novoBem);
        } catch (final CRUDException e) {
            throw new IllegalUpdateException(e.getMessage());
        }

        if (!antigoBem.getNumeroTombamento()
                .equals(novoBem.getNumeroTombamento())
                && bemRepo.existsNumTombamento(novoBem.getNumeroTombamento())) {
            throw new IllegalUpdateException(
                    "O 'Numero de tombamento' inserido já foi utilizado");
        }

        if (novoBem.getDataAquisicao().isAfter(novoBem.getGarantia())) {
            throw new IllegalUpdateException(
                    "A data do campo 'Garantia' não pode ser anterior à data de aquisição do bem");
        }
    }

    private static void validarCampos(final BemDTO bem) throws CRUDException {
        if (bem.getDataAquisicao() == null) {
            throw new CRUDException(
                    "'Data de aquisição' é um campo obrigatório.");
        }
        // Deve ser definida pelo sistema
        if (bem.getDataCadastro() == null) {
            throw new CRUDException(
                    "'Data de cadastro' é um campo obrigatório.");
        }
        if (bem.getDepartamento() == null) {
            throw new CRUDException("O 'Departamento' não pode ser vazio.");
        }
        if (bem.getDescricao() == null || bem.getDescricao().isEmpty()) {
            throw new CRUDException("'Descrição' é um campo obrigatório.");
        }
        if (bem.getGarantia() == null) {
            throw new CRUDException("'Garantia' é um campo obrigatório.");
        }
        if (bem.getGrupoMaterial() == null) {
            throw new CRUDException("O 'Grupo material' não pode ser vazio.");
        }
        if (bem.getMarca() == null || bem.getMarca().isEmpty()) {
            throw new CRUDException("'Marca' é um campo obrigatório.");
        }
        if (bem.getNumeroNotaFiscal() == null
                || bem.getNumeroNotaFiscal().isEmpty()) {
            throw new CRUDException("'Nota fiscal' é um campo obrigatório.");
        }
        if (bem.getNumeroTombamento() == null
                || bem.getNumeroTombamento() < 0) {
            throw new CRUDException(
                    "'Numero de tombamento' é um campo obrigatório.");
        }
        if (bem.getSala() == null) {
            throw new CRUDException("'Sala' é um campo obrigatório.");
        }
        if (bem.getEspecificacao() == null) {
            throw new CRUDException("'Especificação' é um campo obrigatório.");
        }
        // Situação é definida pelo sistema
        if (bem.getSituacao() == null) {
            throw new CRUDException("A 'situação' do bem deve ser definida");
        }

    }

    /**
     * Retorna uma lista com o valor do bem depreciado para todos os anos desde
     * a compra do bem. O primeiro valor corresponde ao ano de compra, enquanto
     * o último valor corresponde ao ano atual.
     *
     * @return
     */
    public BigDecimal[] getDepreciacoesPorAno() {
        final BigDecimal valCompra = getValorCompra();
        final LocalDate hoje = LocalDate.now();
        final Integer qntAnos = hoje.getYear() - getDataAquisicao().getYear();

        final GrupoMaterialRepository gmRepo = new GrupoMaterialRepository();
        final GrupoMaterial gm = gmRepo.getById(getIdGrupoMaterial());
        final BigDecimal depr = gm.getDepreciacao();
        final BigDecimal deprPorAno = valCompra.multiply(depr);
        final BigDecimal[] depreciacoes = new BigDecimal[qntAnos + 1];
        for (int i = 0; i < depreciacoes.length; i++) {
            final BigDecimal deprEsseAno = deprPorAno
                    .multiply(new BigDecimal(i));
            final BigDecimal valDepreciado = valCompra.subtract(deprEsseAno);
            depreciacoes[i] = valDepreciado.compareTo(new BigDecimal(0.01)) <= 0
                    ? new BigDecimal(0.01)
                    : valDepreciado;

        }
        return depreciacoes;
    }

    @Override
    public final Integer getId() {
        return id;
    }

    @Override
    public final void setId(final Integer id) {
        this.id = id;
    }

    public final String getDescricao() {
        return descricao;
    }

    public final void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public final Long getNumeroTombamento() {
        return numeroTombamento;
    }

    public final void setNumeroTombamento(final Long numeroTombamento) {
        this.numeroTombamento = numeroTombamento;
    }

    public final LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public final void setDataCadastro(final LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public final LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public final void setDataAquisicao(final LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public final String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public final void setNumeroNotaFiscal(final String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public final String getEspecificacao() {
        return especificacao;
    }

    public final void setEspecificacao(final String especificacao) {
        this.especificacao = especificacao;
    }

    public final LocalDate getGarantia() {
        return garantia;
    }

    public final void setGarantia(final LocalDate garantia) {
        this.garantia = garantia;
    }

    public final String getMarca() {
        return marca;
    }

    public final void setMarca(final String marca) {
        this.marca = marca;
    }

    public final BigDecimal getValorCompra() {
        return valorCompra;
    }

    public final void setValorCompra(final BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public final BemSituacaoEnum getSituacao() {
        return situacao;
    }

    public final void setSituacao(final BemSituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public final Integer getIdGrupoMaterial() {
        return idGrupoMaterial;
    }

    public final void setIdGrupoMaterial(final Integer idGrupoMaterial) {
        this.idGrupoMaterial = idGrupoMaterial;
    }

    public final Integer getIdSala() {
        return idSala;
    }

    public final void setIdSala(final Integer idSala) {
        this.idSala = idSala;
    }

    public final Integer getIdDepartamento() {
        return idDepartamento;
    }

    public final void setIdDepartamento(final Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public static final long getSerialversionuid() {
        return serialVersionUID;
    }

}
