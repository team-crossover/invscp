package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.client.view.BemView;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;

public class BemSwingView extends JDialog implements BemView {

    private static final long serialVersionUID = 4335778104648346672L;

    private final boolean isAdicionar;

    private Integer idBem;
    private Integer idSala;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldDescricao;
    private JLabel lblDataDeCadastro;
    private JLabel lblNmeroDoCupom;
    private JLabel lblDataDeAquisio;
    private JLabel lblGarantia;
    private JLabel lblMarca;
    private JLabel lblValorDeCompra;
    private JLabel fieldDataCadastro;
    private JTextField fieldDataAquisicao;
    private JTextField fieldNotaFiscal;
    private JTextField fieldGarantia;
    private JTextField fieldMarca;
    private JTextField fieldValorCompra;
    private JLabel lblSala;
    private JLabel lblDepartamento;
    private JLabel lblGrupoMaterial;
    private JLabel lblNmeroDeTombamento;
    private JTextField fieldNumeroTombamento;
    private JLabel lblEspecificao;
    private JTextPane fieldEspecificacao;
    private JLabel labelSala;
    private JLabel lblSituacao;
    private JLabel labelSituacao;
    private JList<GrupoMaterialDTO> listGruposMateriais;
    private JScrollPane scrollGruposMateriais;
    private JList<DepartamentoDTO> listDepartamentos;
    private JScrollPane scrollDepartamentos;

    private final FuncionarioDTO usuario;

    /**
     * @param departamento Prédios cujos valores serão exibidos inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de um novo predio (true) ou para atualização de um predio
     *        existente (false).
     */

    public BemSwingView(final JFrame owner, final BemDTO bem,
            final boolean isAdicionar, final FuncionarioDTO usuario,
            final List<DepartamentoDTO> departamentos,
            final List<GrupoMaterialDTO> gruposMateriais) {

        super(owner, isAdicionar ? "Adicionar bem" : "Bem",
                ModalityType.APPLICATION_MODAL);
        this.usuario = usuario;
        this.isAdicionar = isAdicionar;
        initialize();
        updateBem(bem, departamentos, gruposMateriais);
    }

    private void initialize() {
        setBounds(0, 0, 750, 750);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 113, 390, 30 };
        gridBagLayout.rowHeights = new int[] { 15, 0, 0, 0, 125, 0, 125, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblDataDeCadastro = new JLabel("Data de cadastro:");
        final GridBagConstraints gbc_lblDataDeCadastro =
                new GridBagConstraints();
        gbc_lblDataDeCadastro.anchor = GridBagConstraints.EAST;
        gbc_lblDataDeCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeCadastro.gridx = 1;
        gbc_lblDataDeCadastro.gridy = 1;
        getContentPane().add(lblDataDeCadastro, gbc_lblDataDeCadastro);

        fieldDataCadastro = new JLabel();
        final GridBagConstraints gbc_lblEspecificao = new GridBagConstraints();
        gbc_lblEspecificao.anchor = GridBagConstraints.WEST;
        gbc_lblEspecificao.insets = new Insets(0, 0, 5, 5);
        gbc_lblEspecificao.gridx = 2;
        gbc_lblEspecificao.gridy = 1;
        getContentPane().add(fieldDataCadastro, gbc_lblEspecificao);

        final JLabel lblDescricao = new JLabel("Descrição:");
        final GridBagConstraints gbc_lblDescricao = new GridBagConstraints();
        gbc_lblDescricao.anchor = GridBagConstraints.EAST;
        gbc_lblDescricao.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescricao.gridx = 1;
        gbc_lblDescricao.gridy = 2;
        getContentPane().add(lblDescricao, gbc_lblDescricao);

        fieldDescricao = new JTextField();
        final GridBagConstraints gbc_txtfldDescricao = new GridBagConstraints();
        gbc_txtfldDescricao.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldDescricao.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldDescricao.gridx = 2;
        gbc_txtfldDescricao.gridy = 2;
        getContentPane().add(fieldDescricao, gbc_txtfldDescricao);
        fieldDescricao.setColumns(10);

        lblSituacao = new JLabel("Situação:");
        final GridBagConstraints gbc_lblSituacao = new GridBagConstraints();
        gbc_lblSituacao.anchor = GridBagConstraints.EAST;
        gbc_lblSituacao.insets = new Insets(0, 0, 5, 5);
        gbc_lblSituacao.gridx = 1;
        gbc_lblSituacao.gridy = 3;
        getContentPane().add(lblSituacao, gbc_lblSituacao);

        labelSituacao = new JLabel((String) null);
        final GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.anchor = GridBagConstraints.WEST;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 2;
        gbc_label.gridy = 3;
        getContentPane().add(labelSituacao, gbc_label);

        final ListCellRenderer<? super DepartamentoDTO> deptListRenderer =
                new DefaultListCellRenderer() {
                    private static final long serialVersionUID =
                            437686638148057981L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Nenhum", index, isSelected, cellHasFocus);
                        } else {
                            final String nome =
                                    ((DepartamentoDTO) value).getNome();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        lblDepartamento = new JLabel("Departamento:");
        final GridBagConstraints gbc_lblDepartamento = new GridBagConstraints();
        gbc_lblDepartamento.anchor = GridBagConstraints.EAST;
        gbc_lblDepartamento.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartamento.gridx = 1;
        gbc_lblDepartamento.gridy = 4;
        getContentPane().add(lblDepartamento, gbc_lblDepartamento);

        scrollDepartamentos = new JScrollPane();
        final GridBagConstraints gbc_scrollPaneDepts = new GridBagConstraints();
        gbc_scrollPaneDepts.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneDepts.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPaneDepts.gridx = 2;
        gbc_scrollPaneDepts.gridy = 4;
        getContentPane().add(scrollDepartamentos, gbc_scrollPaneDepts);

        listDepartamentos = new JList<DepartamentoDTO>();
        listDepartamentos.setModel(new DefaultListModel<DepartamentoDTO>());
        listDepartamentos.setCellRenderer(deptListRenderer);
        scrollDepartamentos
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollDepartamentos.setViewportView(listDepartamentos);

        lblSala = new JLabel("Sala:");
        final GridBagConstraints gbc_lblSala = new GridBagConstraints();
        gbc_lblSala.anchor = GridBagConstraints.EAST;
        gbc_lblSala.insets = new Insets(0, 0, 5, 5);
        gbc_lblSala.gridx = 1;
        gbc_lblSala.gridy = 5;
        getContentPane().add(lblSala, gbc_lblSala);

        labelSala = new JLabel("");
        final GridBagConstraints gbc_labelSala = new GridBagConstraints();
        gbc_labelSala.anchor = GridBagConstraints.WEST;
        gbc_labelSala.insets = new Insets(0, 0, 5, 5);
        gbc_labelSala.gridx = 2;
        gbc_labelSala.gridy = 5;
        getContentPane().add(labelSala, gbc_labelSala);

        final ListCellRenderer<? super GrupoMaterialDTO> grupoMatListRenderer =
                new DefaultListCellRenderer() {
                    private static final long serialVersionUID =
                            437686638148057981L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Nenhum", index, isSelected, cellHasFocus);
                        } else {
                            final String nome =
                                    ((GrupoMaterialDTO) value).getNome();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        lblGrupoMaterial = new JLabel("Grupo material:");
        final GridBagConstraints gbc_lblGrupoMaterial =
                new GridBagConstraints();
        gbc_lblGrupoMaterial.anchor = GridBagConstraints.EAST;
        gbc_lblGrupoMaterial.insets = new Insets(0, 0, 5, 5);
        gbc_lblGrupoMaterial.gridx = 1;
        gbc_lblGrupoMaterial.gridy = 6;
        getContentPane().add(lblGrupoMaterial, gbc_lblGrupoMaterial);

        scrollGruposMateriais = new JScrollPane();
        final GridBagConstraints gbc_scrollPaneGrupos =
                new GridBagConstraints();
        gbc_scrollPaneGrupos.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneGrupos.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPaneGrupos.gridx = 2;
        gbc_scrollPaneGrupos.gridy = 6;
        getContentPane().add(scrollGruposMateriais, gbc_scrollPaneGrupos);

        listGruposMateriais = new JList<GrupoMaterialDTO>();
        listGruposMateriais.setModel(new DefaultListModel<GrupoMaterialDTO>());
        listGruposMateriais.setCellRenderer(grupoMatListRenderer);
        scrollGruposMateriais
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollGruposMateriais.setViewportView(listGruposMateriais);

        lblDataDeAquisio = new JLabel("Data de aquisição:");
        final GridBagConstraints gbc_lblDataDeAquisio =
                new GridBagConstraints();
        gbc_lblDataDeAquisio.anchor = GridBagConstraints.EAST;
        gbc_lblDataDeAquisio.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeAquisio.gridx = 1;
        gbc_lblDataDeAquisio.gridy = 7;
        getContentPane().add(lblDataDeAquisio, gbc_lblDataDeAquisio);

        fieldDataAquisicao = new JTextField();
        final GridBagConstraints gbc_fieldDataAquisicao =
                new GridBagConstraints();
        gbc_fieldDataAquisicao.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDataAquisicao.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldDataAquisicao.gridx = 2;
        gbc_fieldDataAquisicao.gridy = 7;
        getContentPane().add(fieldDataAquisicao, gbc_fieldDataAquisicao);
        fieldDataAquisicao.setColumns(10);

        lblNmeroDoCupom = new JLabel("Número do cupom fiscal:");
        final GridBagConstraints gbc_lblNmeroDoCupom = new GridBagConstraints();
        gbc_lblNmeroDoCupom.anchor = GridBagConstraints.EAST;
        gbc_lblNmeroDoCupom.insets = new Insets(0, 0, 5, 5);
        gbc_lblNmeroDoCupom.gridx = 1;
        gbc_lblNmeroDoCupom.gridy = 8;
        getContentPane().add(lblNmeroDoCupom, gbc_lblNmeroDoCupom);

        fieldNotaFiscal = new JTextField();
        fieldNotaFiscal.setColumns(10);
        final GridBagConstraints gbc_fieldNotaFiscal = new GridBagConstraints();
        gbc_fieldNotaFiscal.insets = new Insets(0, 0, 5, 5);
        gbc_fieldNotaFiscal.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldNotaFiscal.gridx = 2;
        gbc_fieldNotaFiscal.gridy = 8;
        getContentPane().add(fieldNotaFiscal, gbc_fieldNotaFiscal);

        lblEspecificao = new JLabel("Especificação:");
        final GridBagConstraints gbc_lblEspecificacao =
                new GridBagConstraints();
        gbc_lblEspecificacao.fill = GridBagConstraints.VERTICAL;
        gbc_lblEspecificacao.anchor = GridBagConstraints.EAST;
        gbc_lblEspecificacao.insets = new Insets(0, 0, 5, 5);
        gbc_lblEspecificacao.gridx = 1;
        gbc_lblEspecificacao.gridy = 9;
        getContentPane().add(lblEspecificao, gbc_lblEspecificacao);

        fieldEspecificacao = new JTextPane();
        final GridBagConstraints gbc_fieldEspecificacao =
                new GridBagConstraints();
        gbc_fieldEspecificacao.insets = new Insets(0, 0, 5, 5);
        gbc_fieldEspecificacao.fill = GridBagConstraints.BOTH;
        gbc_fieldEspecificacao.gridx = 2;
        gbc_fieldEspecificacao.gridy = 9;
        fieldEspecificacao
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        getContentPane().add(fieldEspecificacao, gbc_fieldEspecificacao);

        lblGarantia = new JLabel("Garantia:");
        final GridBagConstraints gbc_lblGarantia = new GridBagConstraints();
        gbc_lblGarantia.anchor = GridBagConstraints.EAST;
        gbc_lblGarantia.insets = new Insets(0, 0, 5, 5);
        gbc_lblGarantia.gridx = 1;
        gbc_lblGarantia.gridy = 10;
        getContentPane().add(lblGarantia, gbc_lblGarantia);

        fieldGarantia = new JTextField();
        fieldGarantia.setColumns(10);
        final GridBagConstraints gbc_fieldGarantia = new GridBagConstraints();
        gbc_fieldGarantia.insets = new Insets(0, 0, 5, 5);
        gbc_fieldGarantia.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldGarantia.gridx = 2;
        gbc_fieldGarantia.gridy = 10;
        getContentPane().add(fieldGarantia, gbc_fieldGarantia);

        lblNmeroDeTombamento = new JLabel("Número de tombamento:");
        final GridBagConstraints gbc_lblNmeroDeTombamento =
                new GridBagConstraints();
        gbc_lblNmeroDeTombamento.anchor = GridBagConstraints.EAST;
        gbc_lblNmeroDeTombamento.insets = new Insets(0, 0, 5, 5);
        gbc_lblNmeroDeTombamento.gridx = 1;
        gbc_lblNmeroDeTombamento.gridy = 11;
        getContentPane().add(lblNmeroDeTombamento, gbc_lblNmeroDeTombamento);

        fieldNumeroTombamento = new JTextField();
        fieldNumeroTombamento.setColumns(10);
        final GridBagConstraints gbc_fieldNumeroTombamento =
                new GridBagConstraints();
        gbc_fieldNumeroTombamento.insets = new Insets(0, 0, 5, 5);
        gbc_fieldNumeroTombamento.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldNumeroTombamento.gridx = 2;
        gbc_fieldNumeroTombamento.gridy = 11;
        getContentPane().add(fieldNumeroTombamento, gbc_fieldNumeroTombamento);

        lblMarca = new JLabel("Marca:");
        final GridBagConstraints gbc_lblMarca = new GridBagConstraints();
        gbc_lblMarca.anchor = GridBagConstraints.EAST;
        gbc_lblMarca.insets = new Insets(0, 0, 5, 5);
        gbc_lblMarca.gridx = 1;
        gbc_lblMarca.gridy = 12;
        getContentPane().add(lblMarca, gbc_lblMarca);

        fieldMarca = new JTextField();
        fieldMarca.setColumns(10);
        final GridBagConstraints gbc_fieldMarca = new GridBagConstraints();
        gbc_fieldMarca.insets = new Insets(0, 0, 5, 5);
        gbc_fieldMarca.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldMarca.gridx = 2;
        gbc_fieldMarca.gridy = 12;
        getContentPane().add(fieldMarca, gbc_fieldMarca);

        lblValorDeCompra = new JLabel("Valor de compra");
        final GridBagConstraints gbc_lblValorDeCompra =
                new GridBagConstraints();
        gbc_lblValorDeCompra.anchor = GridBagConstraints.EAST;
        gbc_lblValorDeCompra.insets = new Insets(0, 0, 5, 5);
        gbc_lblValorDeCompra.gridx = 1;
        gbc_lblValorDeCompra.gridy = 13;
        getContentPane().add(lblValorDeCompra, gbc_lblValorDeCompra);

        fieldValorCompra = new JTextField();
        fieldValorCompra.setColumns(10);
        final GridBagConstraints gbc_fieldValorCompra =
                new GridBagConstraints();
        gbc_fieldValorCompra.insets = new Insets(0, 0, 5, 5);
        gbc_fieldValorCompra.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldValorCompra.gridx = 2;
        gbc_fieldValorCompra.gridy = 13;
        getContentPane().add(fieldValorCompra, gbc_fieldValorCompra);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 15;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 15;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateBem(final BemDTO bem,
            final List<DepartamentoDTO> departamentos,
            final List<GrupoMaterialDTO> gruposMateriais) {

        if (bem == null) {
            throw new NullPointerException();
        }

        final boolean chefeDept =
                usuario != null && usuario.getCargo().isChefeDeDepartamento();
        final boolean chefePatr =
                usuario != null && usuario.getCargo().isChefeDePatrimonio();
        final boolean mesmoDeptUsuario = usuario != null && bem != null
                && bem.getDepartamento() != null && usuario.getDepartamento()
                        .getId().equals(bem.getDepartamento().getId());
        final boolean incorporado =
                bem.getSituacao() == BemSituacaoEnum.INCORPORADO;
        final boolean podeEditar = isAdicionar || (incorporado
                && (chefePatr || (chefeDept && mesmoDeptUsuario)));
        fieldDataAquisicao.setEnabled(podeEditar);
        fieldDataCadastro.setEnabled(podeEditar);
        fieldDescricao.setEnabled(podeEditar);
        fieldEspecificacao.setEnabled(podeEditar);
        fieldGarantia.setEnabled(podeEditar);
        fieldMarca.setEnabled(podeEditar);
        fieldNotaFiscal.setEnabled(podeEditar);
        fieldNumeroTombamento.setEnabled(podeEditar);
        fieldValorCompra.setEnabled(podeEditar);
        listDepartamentos.setEnabled(podeEditar);
        listGruposMateriais.setEnabled(podeEditar);
        btnConfirmar.setEnabled(podeEditar);
        if (chefeDept) {
            listDepartamentos.setEnabled(false);
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idBem = bem.getId();
        idSala = bem.getSala() == null ? null : bem.getSala().getId();

        fieldDescricao.setText(bem.getDescricao());

        if (bem.getDataCadastro() != null) {
            fieldDataCadastro.setText(
                    bem.getDataCadastro().format(DateTimeFormatter.ISO_DATE));
        }
        if (bem.getDataAquisicao() != null) {
            fieldDataAquisicao.setText(
                    bem.getDataAquisicao().format(DateTimeFormatter.ISO_DATE));
        }
        if (bem.getGarantia() != null) {
            fieldGarantia.setText(
                    bem.getGarantia().format(DateTimeFormatter.ISO_DATE));
        }
        fieldMarca.setText(bem.getMarca());
        if (bem.getNumeroTombamento() != null) {
            fieldNumeroTombamento.setText(bem.getNumeroTombamento().toString());
        }
        if (bem.getValorCompra() != null) {
            fieldValorCompra.setText(bem.getValorCompra().toString());
        }
        if (bem.getSala() != null) {
            labelSala.setText(bem.getSala().getNome());
        }
        if (bem.getSituacao() != null) {
            labelSituacao.setText(bem.getSituacao().getTexto());
        }

        fieldNotaFiscal.setText(bem.getNumeroNotaFiscal());
        fieldEspecificacao.setText(bem.getEspecificacao());

        // Exibe os departamentos na lista de departamentos e seleciona o atual,
        // se tiver, ou 'Nenhum', se não tiver.
        final DepartamentoDTO dept = bem.getDepartamento();
        final DefaultListModel<DepartamentoDTO> listDeptsModel =
                (DefaultListModel<DepartamentoDTO>) listDepartamentos
                        .getModel();
        listDeptsModel.clear();
        if (dept == null) {
            listDeptsModel.add(0, null);
            listDepartamentos.setSelectedIndex(0);
        } else {
            listDeptsModel.add(0, dept);
            listDepartamentos.setSelectedIndex(0);
        }
        if (departamentos != null) {
            for (final DepartamentoDTO d : departamentos) {
                if (dept == null || !d.getId().equals(dept.getId())) {
                    listDeptsModel.addElement(d);
                }
            }
        }

        // Exibe os departamentos na lista de departamentos e seleciona o atual,
        // se tiver, ou 'Nenhum', se não tiver.
        final GrupoMaterialDTO grupo = bem.getGrupoMaterial();
        final DefaultListModel<GrupoMaterialDTO> listGruposModel =
                (DefaultListModel<GrupoMaterialDTO>) listGruposMateriais
                        .getModel();
        listGruposModel.clear();
        if (grupo == null) {
            listGruposModel.add(0, null);
            listGruposMateriais.setSelectedIndex(0);
        } else {
            listGruposModel.add(0, grupo);
            listGruposMateriais.setSelectedIndex(0);
        }
        if (gruposMateriais != null) {
            for (final GrupoMaterialDTO g : gruposMateriais) {
                if (grupo == null || !g.getId().equals(grupo.getId())) {
                    listGruposModel.addElement(g);
                }
            }
        }

        revalidate();
        repaint();
    }

    @Override
    public void showError(final String message) {
        final String titulo = "Erro";
        final String messageCompleta = "Erro: " + message;
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public void showSucesso() {
        final String titulo = "Sucesso";
        final String messageCompleta =
                isAdicionar ? "Bem adicionado!" : "Bem alterado!";
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void showInfo(final String message) {
        final String titulo = "Informação";
        JOptionPane.showMessageDialog(this, message, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public BemDTO getBem() {
        final BemDTO bem = new BemDTO();
        bem.setId(idBem);
        bem.setDescricao(fieldDescricao.getText());
        bem.setNumeroNotaFiscal(fieldNotaFiscal.getText());
        bem.setEspecificacao(fieldEspecificacao.getText());
        bem.setMarca(fieldMarca.getText());
        bem.setDepartamento(listDepartamentos.getSelectedValue());
        bem.setGrupoMaterial(listGruposMateriais.getSelectedValue());
        bem.setSituacao(BemSituacaoEnum.valueOfTexto(labelSituacao.getText()));

        final SalaDTO placeholderSala = new SalaDTO();
        placeholderSala.setId(idSala);
        bem.setSala(placeholderSala);

        try {
            bem.setDataCadastro(LocalDate.parse(fieldDataCadastro.getText(),
                    DateTimeFormatter.ISO_DATE));
        } catch (final DateTimeParseException e) {
            showError("O formato da data de cadastro é inválido.");
            return null;
        }
        try {
            bem.setDataAquisicao(LocalDate.parse(fieldDataAquisicao.getText(),
                    DateTimeFormatter.ISO_DATE));
        } catch (final DateTimeParseException e) {
            showError("O formato da data de aquisição é inválido.");
            return null;
        }
        try {
            bem.setGarantia(LocalDate.parse(fieldGarantia.getText(),
                    DateTimeFormatter.ISO_DATE));
        } catch (final DateTimeParseException e) {
            showError("O formato da data de garantia é inválido.");
            return null;
        }
        try {
            bem.setNumeroTombamento(
                    Long.parseLong(fieldNumeroTombamento.getText()));
        } catch (final NumberFormatException e) {
            showError("O número de tombamento deve ser um número válido.");
            return null;
        }
        try {
            bem.setValorCompra(new BigDecimal(Double.parseDouble(
                    fieldValorCompra.getText().replace(',', '.'))));
        } catch (final NumberFormatException e) {
            showError("O valor de compra deve ser um número válido.");
            return null;
        }

        return bem;
    }

}
