package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import com.github.nelsonwilliam.invscp.client.view.FiltroBemView;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FiltroBemDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.BemSituacaoEnum;

public class FiltroBemSwingView extends JDialog implements FiltroBemView {

    private static final long serialVersionUID = -276032841294039698L;

    private JButton btnConfirmar;
    private JTextField fieldDescricao;
    private JTextField fieldNumeroTombamento;
    private JComboBox<BemSituacaoEnum> comboSituacao;
    private JScrollPane scrollDepartamentos;
    private JList<DepartamentoDTO> listDepartamentos;

    public FiltroBemSwingView(final JFrame owner, final FiltroBemDTO filtro,
            final List<DepartamentoDTO> depts) {
        super(owner, "Filtrar bens", ModalityType.APPLICATION_MODAL);
        initialize();
        updateFiltro(filtro, depts);
    }

    private void initialize() {
        setBounds(0, 0, 449, 302);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 149, 205, 25, 0 };
        gridBagLayout.rowHeights = new int[] { 25, 0, 0, 0, 84, 15, 0, 25, 0 };
        gridBagLayout.columnWeights =
                new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        final JLabel lblDescricao = new JLabel("Descrição:");
        final GridBagConstraints gbc_lblDescricao = new GridBagConstraints();
        gbc_lblDescricao.anchor = GridBagConstraints.EAST;
        gbc_lblDescricao.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescricao.gridx = 1;
        gbc_lblDescricao.gridy = 1;
        getContentPane().add(lblDescricao, gbc_lblDescricao);

        fieldDescricao = new JTextField();
        fieldDescricao.setToolTipText(
                "Serão exibidos apenas os bens cuja descrição contém este valor.\nDeixe em branco para permitir todas as descrições.");
        final GridBagConstraints gbc_txtfldDescricao = new GridBagConstraints();
        gbc_txtfldDescricao.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldDescricao.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldDescricao.gridx = 2;
        gbc_txtfldDescricao.gridy = 1;
        getContentPane().add(fieldDescricao, gbc_txtfldDescricao);
        fieldDescricao.setColumns(10);

        final JLabel lblSituacao = new JLabel("Situação:");
        final GridBagConstraints gbc_lblSituacao = new GridBagConstraints();
        gbc_lblSituacao.anchor = GridBagConstraints.EAST;
        gbc_lblSituacao.insets = new Insets(0, 0, 5, 5);
        gbc_lblSituacao.gridx = 1;
        gbc_lblSituacao.gridy = 3;
        getContentPane().add(lblSituacao, gbc_lblSituacao);

        final ListCellRenderer<? super BemSituacaoEnum> bemListRenderer =
                new DefaultListCellRenderer() {

                    private static final long serialVersionUID =
                            -6531942280813288434L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Qualquer uma", index, isSelected,
                                    cellHasFocus);
                        } else {
                            final String nome =
                                    ((BemSituacaoEnum) value).getTexto();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        comboSituacao = new JComboBox<BemSituacaoEnum>();
        final DefaultComboBoxModel<BemSituacaoEnum> comboModel =
                new DefaultComboBoxModel<BemSituacaoEnum>(
                        BemSituacaoEnum.values());
        comboModel.insertElementAt(null, 0);
        comboSituacao.setModel(comboModel);
        comboSituacao.setRenderer(bemListRenderer);
        comboSituacao.setSelectedIndex(0);

        final GridBagConstraints gbc_tipo = new GridBagConstraints();
        gbc_tipo.insets = new Insets(0, 0, 5, 5);
        gbc_tipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipo.gridx = 2;
        gbc_tipo.gridy = 3;
        getContentPane().add(comboSituacao, gbc_tipo);

        final JLabel lblNmeroDeTombamento = new JLabel("Nº de tombamento:");
        final GridBagConstraints gbc_lblNmeroDeTombamento =
                new GridBagConstraints();
        gbc_lblNmeroDeTombamento.anchor = GridBagConstraints.EAST;
        gbc_lblNmeroDeTombamento.insets = new Insets(0, 0, 5, 5);
        gbc_lblNmeroDeTombamento.gridx = 1;
        gbc_lblNmeroDeTombamento.gridy = 2;
        getContentPane().add(lblNmeroDeTombamento, gbc_lblNmeroDeTombamento);

        fieldNumeroTombamento = new JTextField();
        fieldNumeroTombamento.setToolTipText(
                "Serão exibidos apenas os bens cujo nº de tombamento contém este valor.\nDeixe em branco para permitir todos os números de tombamento.");
        final GridBagConstraints gbc_fieldNumeroTombamento =
                new GridBagConstraints();
        gbc_fieldNumeroTombamento.insets = new Insets(0, 0, 5, 5);
        gbc_fieldNumeroTombamento.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldNumeroTombamento.gridx = 2;
        gbc_fieldNumeroTombamento.gridy = 2;
        getContentPane().add(fieldNumeroTombamento, gbc_fieldNumeroTombamento);

        final ListCellRenderer<? super DepartamentoDTO> deptListRenderer =
                new DefaultListCellRenderer() {

                    private static final long serialVersionUID =
                            -6531942280813288434L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Qualquer um", index, isSelected,
                                    cellHasFocus);
                        } else {
                            final String nome =
                                    ((DepartamentoDTO) value).getNome();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        final JLabel lblDepartamento = new JLabel("Departamento:");
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
        listDepartamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listDepartamentos.setModel(new DefaultListModel<DepartamentoDTO>());
        listDepartamentos.setCellRenderer(deptListRenderer);
        scrollDepartamentos
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollDepartamentos.setViewportView(listDepartamentos);

        btnConfirmar = new JButton("Confirmar");
        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.anchor = GridBagConstraints.WEST;
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 6;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener newListener) {
        btnConfirmar.addActionListener(newListener);
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
    public void updateFiltro(final FiltroBemDTO newFiltro,
            final List<DepartamentoDTO> newDepartamentos) {

        if (newFiltro == null) {
            throw new NullPointerException();
        }

        fieldDescricao.setText(newFiltro.getDescricao());
        fieldNumeroTombamento.setText(newFiltro.getNumeroTombamento());
        comboSituacao.setSelectedItem(newFiltro.getSituacao());

        final DepartamentoDTO dept = newFiltro.getDepartamento();
        final DefaultListModel<DepartamentoDTO> listDeptsModel =
                (DefaultListModel<DepartamentoDTO>) listDepartamentos
                        .getModel();
        listDeptsModel.clear();
        listDeptsModel.add(0, null);
        if (dept == null) {
            listDepartamentos.setSelectedIndex(0);
        } else {
            listDeptsModel.add(1, dept);
            listDepartamentos.setSelectedIndex(1);
        }
        if (newDepartamentos != null) {
            for (final DepartamentoDTO d : newDepartamentos) {
                if (dept == null || !d.getId().equals(dept.getId())) {
                    listDeptsModel.addElement(d);
                }
            }
        }

        revalidate();
        repaint();
    }

    public void showError(final String message) {
        final String titulo = "Erro";
        final String messageCompleta = "Erro: " + message;
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.ERROR_MESSAGE);

    }

    @Override
    public FiltroBemDTO getFiltro() {
        final FiltroBemDTO filtro = new FiltroBemDTO();
        filtro.setDescricao(fieldDescricao.getText());
        filtro.setSituacao((BemSituacaoEnum) comboSituacao.getSelectedItem());
        filtro.setDepartamento(listDepartamentos.getSelectedValue());
        filtro.setNumeroTombamento(fieldNumeroTombamento.getText());
        return filtro;
    }

}
