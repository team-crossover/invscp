package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.view.BemView;

public class BemSwingView extends JDialog implements BemView {

    private static final long serialVersionUID = 4335778104648346672L;

    private final boolean isAdicionar;

    private Integer idBem;

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
    private JTextField fieldEspecificacao;

    /**
     * @param departamento Prédios cujos valores serão exibidos inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de um novo predio (true) ou para atualização de um predio
     *        existente (false).
     */

    public BemSwingView(final JFrame owner, final BemDTO bem,
            final boolean isAdicionar,
            final List<DepartamentoDTO> departamentos,
            final List<SalaDTO> salas,
            final List<GrupoMaterialDTO> gruposMateriais) {

        super(owner, isAdicionar ? "Adicionar bem" : "Alterar bem",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateBem(bem, departamentos, salas, gruposMateriais);
    }

    private void initialize() {
        setBounds(0, 0, 478, 381);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 113, 390, 30 };
        gridBagLayout.rowHeights = new int[] { 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        final ListCellRenderer<? super LocalizacaoDTO> localizacaoListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 8590680235694368760L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((LocalizacaoDTO) value).getNome();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        java.util.Date dataCadastro = new Date();
        String dataStr = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM)
                .format(dataCadastro);

        lblDataDeCadastro = new JLabel("Data de cadastro:");
        GridBagConstraints gbc_lblDataDeCadastro = new GridBagConstraints();
        gbc_lblDataDeCadastro.anchor = GridBagConstraints.WEST;
        gbc_lblDataDeCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeCadastro.gridx = 1;
        gbc_lblDataDeCadastro.gridy = 1;
        getContentPane().add(lblDataDeCadastro, gbc_lblDataDeCadastro);

        fieldDataCadastro = new JLabel(dataStr);
        GridBagConstraints gbc_lblEspecificao = new GridBagConstraints();
        gbc_lblEspecificao.anchor = GridBagConstraints.WEST;
        gbc_lblEspecificao.insets = new Insets(0, 0, 5, 5);
        gbc_lblEspecificao.gridx = 2;
        gbc_lblEspecificao.gridy = 1;
        getContentPane().add(fieldDataCadastro, gbc_lblEspecificao);

        final JLabel lblDescricao = new JLabel("Descricao:");
        final GridBagConstraints gbc_lblDescricao = new GridBagConstraints();
        gbc_lblDescricao.anchor = GridBagConstraints.WEST;
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

        lblDepartamento = new JLabel("Departamento:");
        GridBagConstraints gbc_lblDepartamento = new GridBagConstraints();
        gbc_lblDepartamento.anchor = GridBagConstraints.WEST;
        gbc_lblDepartamento.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartamento.gridx = 1;
        gbc_lblDepartamento.gridy = 3;
        getContentPane().add(lblDepartamento, gbc_lblDepartamento);

        lblSala = new JLabel("Sala:");
        GridBagConstraints gbc_lblSala = new GridBagConstraints();
        gbc_lblSala.anchor = GridBagConstraints.WEST;
        gbc_lblSala.insets = new Insets(0, 0, 5, 5);
        gbc_lblSala.gridx = 1;
        gbc_lblSala.gridy = 4;
        getContentPane().add(lblSala, gbc_lblSala);

        lblGrupoMaterial = new JLabel("Grupo material:");
        GridBagConstraints gbc_lblGrupoMaterial = new GridBagConstraints();
        gbc_lblGrupoMaterial.anchor = GridBagConstraints.WEST;
        gbc_lblGrupoMaterial.insets = new Insets(0, 0, 5, 5);
        gbc_lblGrupoMaterial.gridx = 1;
        gbc_lblGrupoMaterial.gridy = 5;
        getContentPane().add(lblGrupoMaterial, gbc_lblGrupoMaterial);

        lblDataDeAquisio = new JLabel("Data de aquisição:");
        GridBagConstraints gbc_lblDataDeAquisio = new GridBagConstraints();
        gbc_lblDataDeAquisio.anchor = GridBagConstraints.WEST;
        gbc_lblDataDeAquisio.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeAquisio.gridx = 1;
        gbc_lblDataDeAquisio.gridy = 6;
        getContentPane().add(lblDataDeAquisio, gbc_lblDataDeAquisio);

        fieldDataAquisicao = new JTextField();
        GridBagConstraints gbc_fieldDataAquisicao = new GridBagConstraints();
        gbc_fieldDataAquisicao.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDataAquisicao.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldDataAquisicao.gridx = 2;
        gbc_fieldDataAquisicao.gridy = 6;
        getContentPane().add(fieldDataAquisicao, gbc_fieldDataAquisicao);
        fieldDataAquisicao.setColumns(10);

        lblNmeroDoCupom = new JLabel("Número do Cupom Fiscal:");
        GridBagConstraints gbc_lblNmeroDoCupom = new GridBagConstraints();
        gbc_lblNmeroDoCupom.anchor = GridBagConstraints.WEST;
        gbc_lblNmeroDoCupom.insets = new Insets(0, 0, 5, 5);
        gbc_lblNmeroDoCupom.gridx = 1;
        gbc_lblNmeroDoCupom.gridy = 7;
        getContentPane().add(lblNmeroDoCupom, gbc_lblNmeroDoCupom);

        fieldNotaFiscal = new JTextField();
        fieldNotaFiscal.setColumns(10);
        GridBagConstraints gbc_fieldNotaFiscal = new GridBagConstraints();
        gbc_fieldNotaFiscal.insets = new Insets(0, 0, 5, 5);
        gbc_fieldNotaFiscal.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldNotaFiscal.gridx = 2;
        gbc_fieldNotaFiscal.gridy = 7;
        getContentPane().add(fieldNotaFiscal, gbc_fieldNotaFiscal);

        lblEspecificao = new JLabel("Especificação:");
        GridBagConstraints gbc_lblEspecificacao = new GridBagConstraints();
        gbc_lblEspecificacao.anchor = GridBagConstraints.EAST;
        gbc_lblEspecificacao.insets = new Insets(0, 0, 5, 5);
        gbc_lblEspecificacao.gridx = 1;
        gbc_lblEspecificacao.gridy = 8;
        getContentPane().add(lblEspecificao, gbc_lblEspecificacao);

        fieldEspecificacao = new JTextField();
        fieldEspecificacao.setColumns(10);
        GridBagConstraints gbc_fieldEspecificacao = new GridBagConstraints();
        gbc_fieldEspecificacao.insets = new Insets(0, 0, 5, 5);
        gbc_fieldEspecificacao.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldEspecificacao.gridx = 2;
        gbc_fieldEspecificacao.gridy = 8;
        getContentPane().add(fieldEspecificacao, gbc_fieldEspecificacao);

        lblGarantia = new JLabel("Garantia:");
        GridBagConstraints gbc_lblGarantia = new GridBagConstraints();
        gbc_lblGarantia.anchor = GridBagConstraints.WEST;
        gbc_lblGarantia.insets = new Insets(0, 0, 5, 5);
        gbc_lblGarantia.gridx = 1;
        gbc_lblGarantia.gridy = 9;
        getContentPane().add(lblGarantia, gbc_lblGarantia);

        fieldGarantia = new JTextField();
        fieldGarantia.setColumns(10);
        GridBagConstraints gbc_fieldGarantia = new GridBagConstraints();
        gbc_fieldGarantia.insets = new Insets(0, 0, 5, 5);
        gbc_fieldGarantia.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldGarantia.gridx = 2;
        gbc_fieldGarantia.gridy = 9;
        getContentPane().add(fieldGarantia, gbc_fieldGarantia);

        lblNmeroDeTombamento = new JLabel("Número de tombamento:");
        GridBagConstraints gbc_lblNmeroDeTombamento = new GridBagConstraints();
        gbc_lblNmeroDeTombamento.anchor = GridBagConstraints.EAST;
        gbc_lblNmeroDeTombamento.insets = new Insets(0, 0, 5, 5);
        gbc_lblNmeroDeTombamento.gridx = 1;
        gbc_lblNmeroDeTombamento.gridy = 10;
        getContentPane().add(lblNmeroDeTombamento, gbc_lblNmeroDeTombamento);

        fieldNumeroTombamento = new JTextField();
        fieldNumeroTombamento.setColumns(10);
        GridBagConstraints gbc_fieldNumeroTombamento = new GridBagConstraints();
        gbc_fieldNumeroTombamento.insets = new Insets(0, 0, 5, 5);
        gbc_fieldNumeroTombamento.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldNumeroTombamento.gridx = 2;
        gbc_fieldNumeroTombamento.gridy = 10;
        getContentPane().add(fieldNumeroTombamento, gbc_fieldNumeroTombamento);

        lblMarca = new JLabel("Marca:");
        GridBagConstraints gbc_lblMarca = new GridBagConstraints();
        gbc_lblMarca.anchor = GridBagConstraints.WEST;
        gbc_lblMarca.insets = new Insets(0, 0, 5, 5);
        gbc_lblMarca.gridx = 1;
        gbc_lblMarca.gridy = 11;
        getContentPane().add(lblMarca, gbc_lblMarca);

        fieldMarca = new JTextField();
        fieldMarca.setColumns(10);
        GridBagConstraints gbc_fieldMarca = new GridBagConstraints();
        gbc_fieldMarca.insets = new Insets(0, 0, 5, 5);
        gbc_fieldMarca.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldMarca.gridx = 2;
        gbc_fieldMarca.gridy = 11;
        getContentPane().add(fieldMarca, gbc_fieldMarca);

        lblValorDeCompra = new JLabel("Valor de compra");
        GridBagConstraints gbc_lblValorDeCompra = new GridBagConstraints();
        gbc_lblValorDeCompra.anchor = GridBagConstraints.WEST;
        gbc_lblValorDeCompra.insets = new Insets(0, 0, 5, 5);
        gbc_lblValorDeCompra.gridx = 1;
        gbc_lblValorDeCompra.gridy = 12;
        getContentPane().add(lblValorDeCompra, gbc_lblValorDeCompra);

        fieldValorCompra = new JTextField();
        fieldValorCompra.setColumns(10);
        GridBagConstraints gbc_fieldValorCompra = new GridBagConstraints();
        gbc_fieldValorCompra.insets = new Insets(0, 0, 5, 5);
        gbc_fieldValorCompra.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldValorCompra.gridx = 2;
        gbc_fieldValorCompra.gridy = 12;
        getContentPane().add(fieldValorCompra, gbc_fieldValorCompra);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 14;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 14;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateBem(final BemDTO bem,
            final List<DepartamentoDTO> departamentos,
            final List<SalaDTO> salas,
            final List<GrupoMaterialDTO> gruposMateriais) {

        if (bem == null) {
            throw new NullPointerException();
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idBem = bem.getId();

        fieldDescricao.setText(bem.getDescricao());

        // Exibe as localizacoes na lista de localizacoes e seleciona o atual,
        // se
        // tiver, ou 'Nenhum', se não tiver.

        /*
         * final DepartamentoDTO departamento = bem.getDepartamento(); final
         * SalaDTO sala = bem.getSala(); final GrupoMaterialDTO grupoMaterial =
         * bem.getGrupoMaterial(); final DefaultListModel<DepartamentoDTO>
         * listDepartamentoModel = (DefaultListModel<DepartamentoDTO>)
         * listLocalizacoes .getModel(); final DefaultListModel<LocalizacaoDTO>
         * listLocalizacoesModel = (DefaultListModel<LocalizacaoDTO>)
         * listLocalizacoes .getModel(); final DefaultListModel<LocalizacaoDTO>
         * listLocalizacoesModel = (DefaultListModel<LocalizacaoDTO>)
         * listLocalizacoes .getModel(); listLocalizacoesModel.clear(); if
         * (localizacao == null) { listLocalizacoesModel.add(0, null);
         * listLocalizacoes.setSelectedIndex(0); } else {
         * listLocalizacoesModel.add(0, localizacao);
         * listLocalizacoes.setSelectedIndex(0); } if (localizacoes != null) {
         * for (final LocalizacaoDTO l : localizacoes) { if (localizacao == null
         * || !l.getId().equals(localizacao.getId())) {
         * listLocalizacoesModel.addElement(l); } } }
         */

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
        final String messageCompleta = isAdicionar ? "Predio adicionado!"
                : "Predio alterado!";
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
        // bem.setDataCadastro(fieldDataCadastro);
        // bem.setDataAquisicao(fieldDataAquisicao);
        bem.setNumeroNotaFiscal(fieldNotaFiscal.toString());
        bem.setEspecificacao(fieldEspecificacao.getText());
        // bem.setGarantia(fieldGarantia);
        // bem.setNumeroTombamento(fieldNumeroTombamento);
        bem.setMarca(fieldMarca.getText());
        // bem.setValorCompra(fieldValorCompra);
        /*
         * bem.setLocalizacao(listLocalizacoes.getSelectedValue() == null ? null
         * : listLocalizacoes.getSelectedValue());
         */
        return bem;
    }

}
