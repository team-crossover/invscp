package com.github.nelsonwilliam.invscp.client.view.swing;

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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.client.view.DepartamentoView;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public class DepartamentoSwingView extends JDialog implements DepartamentoView {

    private static final long serialVersionUID = -7832838016863775738L;

    private final boolean isAdicionar;

    @SuppressWarnings("unused")
    private FuncionarioDTO usuario;

    private Integer idDepartamento;
    private boolean dePatrimonio;

    private GridBagLayout gridBagLayout;
    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblDePatrimonio;
    private JTextField fieldNome;
    private JList<FuncionarioDTO> listChefe;
    private JList<FuncionarioDTO> listChefeSubstituto;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;

    /**
     * @param departamento DepartamentoDTO cujos valores serão exibidos
     *        inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de um novo departamento (true) ou para atualização de um
     *        departamento existente (false).
     */
    public DepartamentoSwingView(final JFrame owner,
            final DepartamentoDTO departamento, final boolean isAdicionar,
            final List<FuncionarioDTO> chefes,
            final List<FuncionarioDTO> chefesSubsts,
            final FuncionarioDTO usuario) {

        super(owner, isAdicionar ? "Adicionar departamento" : "Departamento",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        this.usuario = usuario;
        initialize();
        updateDepartamento(departamento, chefes, chefesSubsts, usuario);
    }

    private void initialize() {
        setBounds(0, 0, 500, 400);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25, 0 };
        gridBagLayout.rowHeights = new int[] { 25, 25, 0, 0, 0, 25, 25, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0,
                0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblDePatrimonio = new JLabel("Este é o departamento de patrimônio.");

        final JLabel lblNome = new JLabel("Nome:");
        final GridBagConstraints gbc_lblNome = new GridBagConstraints();
        gbc_lblNome.anchor = GridBagConstraints.EAST;
        gbc_lblNome.insets = new Insets(0, 0, 5, 5);
        gbc_lblNome.gridx = 1;
        gbc_lblNome.gridy = 2;
        getContentPane().add(lblNome, gbc_lblNome);

        fieldNome = new JTextField();
        final GridBagConstraints gbc_txtfldNome = new GridBagConstraints();
        gbc_txtfldNome.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldNome.gridx = 2;
        gbc_txtfldNome.gridy = 2;
        getContentPane().add(fieldNome, gbc_txtfldNome);
        fieldNome.setColumns(10);

        final JLabel lblChefe = new JLabel("Chefe:");
        final GridBagConstraints gbc_lblChefe = new GridBagConstraints();
        gbc_lblChefe.anchor = GridBagConstraints.EAST;
        gbc_lblChefe.insets = new Insets(0, 0, 5, 5);
        gbc_lblChefe.gridx = 1;
        gbc_lblChefe.gridy = 3;
        getContentPane().add(lblChefe, gbc_lblChefe);

        final ListCellRenderer<? super FuncionarioDTO> funcionarioListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 337686638048057981L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((FuncionarioDTO) value).getNome();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        scrollPane = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 3;
        getContentPane().add(scrollPane, gbc_scrollPane);

        listChefe = new JList<FuncionarioDTO>();
        listChefe.setModel(new DefaultListModel<FuncionarioDTO>());
        listChefe.setCellRenderer(funcionarioListRenderer);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollPane.setViewportView(listChefe);

        final JLabel lblChefeSubstituto = new JLabel("Chefe substituto:");
        final GridBagConstraints gbc_lblChefeSubstituto = new GridBagConstraints();
        gbc_lblChefeSubstituto.anchor = GridBagConstraints.EAST;
        gbc_lblChefeSubstituto.insets = new Insets(0, 0, 5, 5);
        gbc_lblChefeSubstituto.gridx = 1;
        gbc_lblChefeSubstituto.gridy = 4;
        getContentPane().add(lblChefeSubstituto, gbc_lblChefeSubstituto);

        scrollPane_1 = new JScrollPane();
        final GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.gridx = 2;
        gbc_scrollPane_1.gridy = 4;
        getContentPane().add(scrollPane_1, gbc_scrollPane_1);

        listChefeSubstituto = new JList<FuncionarioDTO>();
        listChefeSubstituto.setModel(new DefaultListModel<FuncionarioDTO>());
        listChefeSubstituto.setCellRenderer(funcionarioListRenderer);
        scrollPane_1.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollPane_1.setViewportView(listChefeSubstituto);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 5;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 5;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
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
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateDepartamento(final DepartamentoDTO departamento,
            final List<FuncionarioDTO> chefes,
            final List<FuncionarioDTO> chefesSubsts,
            final FuncionarioDTO usuario) {
        if (departamento == null) {
            throw new NullPointerException();
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idDepartamento = departamento.getId();
        dePatrimonio = departamento.getId() != null
                && departamento.getDePatrimonio();

        // Exibe uma label indicando que é de patrimônio.
        getContentPane().remove(lblDePatrimonio);
        if (dePatrimonio) {
            gridBagLayout.rowHeights[1] = 25;
            final GridBagConstraints gbc_lblDePatrimonio = new GridBagConstraints();
            gbc_lblDePatrimonio.anchor = GridBagConstraints.CENTER;
            gbc_lblDePatrimonio.insets = new Insets(0, 0, 5, 5);
            gbc_lblDePatrimonio.gridx = 1;
            gbc_lblDePatrimonio.gridy = 1;
            gbc_lblDePatrimonio.gridwidth = 2;
            getContentPane().add(lblDePatrimonio, gbc_lblDePatrimonio);
        } else {
            gridBagLayout.rowHeights[1] = 0;
        }

        fieldNome.setText(departamento.getNome());

        // Exibe os funcionários na lista de chefes e seleciona o atual, se
        // tiver, ou 'Nenhum', se não tiver.
        final FuncionarioDTO chefe = departamento.getChefe();
        final DefaultListModel<FuncionarioDTO> listChefeModel = (DefaultListModel<FuncionarioDTO>) listChefe
                .getModel();
        listChefeModel.clear();
        if (chefe == null) {
            listChefeModel.add(0, null);
            listChefe.setSelectedIndex(0);
        } else {
            listChefeModel.add(0, chefe);
            listChefe.setSelectedIndex(0);
        }
        if (chefes != null) {
            for (final FuncionarioDTO funcionario : chefes) {
                if (chefe == null
                        || !funcionario.getId().equals(chefe.getId())) {
                    listChefeModel.addElement(funcionario);
                }
            }
        }

        // Exibe os funcionários na lista de chefes substitutos e seleciona o
        // atual, se
        // tiver, ou 'Nenhum', se não tiver.
        final FuncionarioDTO chefeSubstituto = departamento
                .getChefeSubstituto();
        final DefaultListModel<FuncionarioDTO> listChefeSubstitutoModel = (DefaultListModel<FuncionarioDTO>) listChefeSubstituto
                .getModel();
        listChefeSubstitutoModel.clear();
        if (chefeSubstituto == null) {
            listChefeSubstitutoModel.add(0, null);
            listChefeSubstituto.setSelectedIndex(0);
        } else {
            listChefeSubstitutoModel.add(0, null);
            listChefeSubstitutoModel.add(1, chefeSubstituto);
            listChefeSubstituto.setSelectedIndex(1);
        }

        if (chefesSubsts != null) {
            for (final FuncionarioDTO funcionario : chefesSubsts) {
                if (chefeSubstituto == null || !funcionario.getId()
                        .equals(chefeSubstituto.getId())) {
                    listChefeSubstitutoModel.addElement(funcionario);
                }
            }
        }

        final boolean ehChefePatrimonio = (usuario != null
                && usuario.getCargo().isChefeDePatrimonio());
        btnConfirmar.setEnabled(ehChefePatrimonio);
        lblDePatrimonio.setEnabled(ehChefePatrimonio);
        fieldNome.setEnabled(ehChefePatrimonio);
        listChefe.setEnabled(ehChefePatrimonio);
        listChefeSubstituto.setEnabled(ehChefePatrimonio);
        scrollPane.setEnabled(ehChefePatrimonio);
        scrollPane_1.setEnabled(ehChefePatrimonio);

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
    public void showInfo(final String message) {
        final String titulo = "Informação";
        JOptionPane.showMessageDialog(this, message, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showSucesso() {
        final String titulo = "Sucesso";
        final String messageCompleta = isAdicionar ? "Departamento adicionado!"
                : "Departamento alterado!";
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public DepartamentoDTO getDepartamento() {
        final DepartamentoDTO departamento = new DepartamentoDTO();
        departamento.setId(idDepartamento);
        departamento.setNome(fieldNome.getText());
        departamento.setDePatrimonio(dePatrimonio);
        departamento.setChefe(listChefe.getSelectedValue());
        departamento.setChefeSubstituto(listChefeSubstituto.getSelectedValue());
        return departamento;

    }

}
