package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
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
    private JScrollPane scrollLocalizacoes;
    private JLabel lblDataDeCadastro;

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
        setBounds(0, 0, 736, 429);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 102, 0, 30 };
        gridBagLayout.rowHeights = new int[] { 15, 0, 28, 15, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        final JLabel lblDescricao = new JLabel("Descricao:");
        final GridBagConstraints gbc_lblDescricao = new GridBagConstraints();
        gbc_lblDescricao.anchor = GridBagConstraints.EAST;
        gbc_lblDescricao.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescricao.gridx = 1;
        gbc_lblDescricao.gridy = 1;
        getContentPane().add(lblDescricao, gbc_lblDescricao);

        fieldDescricao = new JTextField();
        final GridBagConstraints gbc_txtfldDescricao = new GridBagConstraints();
        gbc_txtfldDescricao.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldDescricao.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldDescricao.gridx = 2;
        gbc_txtfldDescricao.gridy = 1;
        getContentPane().add(fieldDescricao, gbc_txtfldDescricao);
        fieldDescricao.setColumns(10);

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

        lblDataDeCadastro = new JLabel("Data de cadastro");
        GridBagConstraints gbc_lblDataDeCadastro = new GridBagConstraints();
        gbc_lblDataDeCadastro.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblDataDeCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeCadastro.gridx = 1;
        gbc_lblDataDeCadastro.gridy = 2;
        getContentPane().add(lblDataDeCadastro, gbc_lblDataDeCadastro);

        scrollLocalizacoes = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.anchor = GridBagConstraints.NORTH;
        gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 2;
        getContentPane().add(scrollLocalizacoes, gbc_scrollPane);
        scrollLocalizacoes
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 4;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 4;
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
        /*
         * bem.setLocalizacao(listLocalizacoes.getSelectedValue() == null ? null
         * : listLocalizacoes.getSelectedValue());
         */
        return bem;
    }

}
