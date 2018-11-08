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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.view.FuncionarioView;

public class FuncionarioSwingView extends JDialog implements FuncionarioView {

    private static final long serialVersionUID = -7832838016863775738L;

    private final boolean isAdicionar;

    private Integer idFuncionario;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldNome;
    private JTextField fieldCpf;
    private JTextField fieldEmail;
    private JTextField fieldLogin;
    private JPasswordField fieldSenha;
    private JList<DepartamentoDTO> listDepartamentos;
    private JScrollPane scrollDepartamentos;

    /**
     * @param funcionario FuncionarioDTO cujos valores serão exibidos
     *        inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de um novo funcionario (true) ou para atualização de um
     *        funcionario existente (false).
     */
    public FuncionarioSwingView(final JFrame owner,
            final FuncionarioDTO funcionario, final boolean isAdicionar,
            final List<DepartamentoDTO> departamentos) {

        super(owner,
                isAdicionar ? "Adicionar funcionário" : "Alterar funcionário",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateFuncionario(funcionario, departamentos);
    }

    private void initialize() {
        setBounds(0, 0, 500, 400);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25, 0 };
        gridBagLayout.rowHeights = new int[] { 25, 25, 0, 0, 0, 25, 25, 0, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                1.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        final JLabel lblNome = new JLabel("Nome:");
        final GridBagConstraints gbc_lblNome = new GridBagConstraints();
        gbc_lblNome.anchor = GridBagConstraints.EAST;
        gbc_lblNome.insets = new Insets(0, 0, 5, 5);
        gbc_lblNome.gridx = 1;
        gbc_lblNome.gridy = 1;
        getContentPane().add(lblNome, gbc_lblNome);

        fieldNome = new JTextField();
        final GridBagConstraints gbc_txtfldNome = new GridBagConstraints();
        gbc_txtfldNome.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldNome.gridx = 2;
        gbc_txtfldNome.gridy = 1;
        getContentPane().add(fieldNome, gbc_txtfldNome);
        fieldNome.setColumns(10);

        final JLabel lblCpf = new JLabel("CPF:");
        final GridBagConstraints gbc_lblCpf = new GridBagConstraints();
        gbc_lblCpf.anchor = GridBagConstraints.EAST;
        gbc_lblCpf.insets = new Insets(0, 0, 5, 5);
        gbc_lblCpf.gridx = 1;
        gbc_lblCpf.gridy = 2;
        getContentPane().add(lblCpf, gbc_lblCpf);

        fieldCpf = new JTextField();
        final GridBagConstraints gbc_fieldCpf = new GridBagConstraints();
        gbc_fieldCpf.insets = new Insets(0, 0, 5, 5);
        gbc_fieldCpf.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldCpf.gridx = 2;
        gbc_fieldCpf.gridy = 2;
        getContentPane().add(fieldCpf, gbc_fieldCpf);
        fieldCpf.setColumns(11);

        final JLabel lblEmail = new JLabel("E-mail:");
        final GridBagConstraints gbc_lblEmail = new GridBagConstraints();
        gbc_lblEmail.anchor = GridBagConstraints.EAST;
        gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
        gbc_lblEmail.gridx = 1;
        gbc_lblEmail.gridy = 3;
        getContentPane().add(lblEmail, gbc_lblEmail);

        fieldEmail = new JTextField();
        final GridBagConstraints gbc_fieldEmail = new GridBagConstraints();
        gbc_fieldEmail.insets = new Insets(0, 0, 5, 5);
        gbc_fieldEmail.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldEmail.gridx = 2;
        gbc_fieldEmail.gridy = 3;
        getContentPane().add(fieldEmail, gbc_fieldEmail);
        fieldEmail.setColumns(20);

        final JLabel lblLogin = new JLabel("Login:");
        final GridBagConstraints gbc_lblLogin = new GridBagConstraints();
        gbc_lblLogin.anchor = GridBagConstraints.EAST;
        gbc_lblLogin.insets = new Insets(0, 0, 5, 5);
        gbc_lblLogin.gridx = 1;
        gbc_lblLogin.gridy = 4;
        getContentPane().add(lblLogin, gbc_lblLogin);

        fieldLogin = new JTextField();
        final GridBagConstraints gbc_fieldLogin = new GridBagConstraints();
        gbc_fieldLogin.insets = new Insets(0, 0, 5, 5);
        gbc_fieldLogin.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldLogin.gridx = 2;
        gbc_fieldLogin.gridy = 4;
        getContentPane().add(fieldLogin, gbc_fieldLogin);
        fieldLogin.setColumns(10);

        final JLabel lblSenha = new JLabel("Senha:");
        final GridBagConstraints gbc_lblSenha = new GridBagConstraints();
        gbc_lblSenha.anchor = GridBagConstraints.EAST;
        gbc_lblSenha.insets = new Insets(0, 0, 5, 5);
        gbc_lblSenha.gridx = 1;
        gbc_lblSenha.gridy = 5;
        getContentPane().add(lblSenha, gbc_lblSenha);

        fieldSenha = new JPasswordField();
        final GridBagConstraints gbc_fieldSenha = new GridBagConstraints();
        gbc_fieldSenha.insets = new Insets(0, 0, 5, 5);
        gbc_fieldSenha.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldSenha.gridx = 2;
        gbc_fieldSenha.gridy = 5;
        getContentPane().add(fieldSenha, gbc_fieldSenha);
        fieldSenha.setColumns(10);

        final JLabel lblDept = new JLabel("Departamento:");
        final GridBagConstraints gbc_lblChefe = new GridBagConstraints();
        gbc_lblChefe.anchor = GridBagConstraints.EAST;
        gbc_lblChefe.insets = new Insets(0, 0, 5, 5);
        gbc_lblChefe.gridx = 1;
        gbc_lblChefe.gridy = 6;
        getContentPane().add(lblDept, gbc_lblChefe);

        final ListCellRenderer<? super DepartamentoDTO> deptListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 337686638048057981L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((DepartamentoDTO) value).getNome();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        scrollDepartamentos = new JScrollPane();
        final GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 6;
        getContentPane().add(scrollDepartamentos, gbc_scrollPane);

        listDepartamentos = new JList<DepartamentoDTO>();
        listDepartamentos.setModel(new DefaultListModel<DepartamentoDTO>());
        listDepartamentos.setCellRenderer(deptListRenderer);
        scrollDepartamentos
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollDepartamentos.setViewportView(listDepartamentos);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 7;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 7;
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
    public void updateFuncionario(final FuncionarioDTO funcionario,
            final List<DepartamentoDTO> departamentos) {
        if (funcionario == null) {
            throw new NullPointerException();
        }

        // O ID do funcionario sendo exibido é armazenado para que seja possível
        // retorná-lo na hora de salvar o funcionario.
        idFuncionario = funcionario.getId();

        fieldNome.setText(funcionario.getNome());
        fieldCpf.setText(funcionario.getCpf());
        fieldEmail.setText(funcionario.getEmail());
        fieldLogin.setText(funcionario.getLogin());
        fieldSenha.setText(funcionario.getSenha());

        // Exibe os departamentos na lista de departamentos e seleciona o atual,
        // se tiver, ou 'Nenhum', se não tiver.
        final DepartamentoDTO departamento = funcionario.getDepartamento();
        final DefaultListModel<DepartamentoDTO> listDepartamentoModel = (DefaultListModel<DepartamentoDTO>) listDepartamentos
                .getModel();
        listDepartamentoModel.clear();
        listDepartamentoModel.add(0, null);
        if (departamento == null) {
            listDepartamentos.setSelectedIndex(0);
        } else {
            listDepartamentoModel.add(1, departamento);
            listDepartamentos.setSelectedIndex(1);
        }
        if (departamentos != null) {
            for (final DepartamentoDTO d : departamentos) {
                if (departamento == null
                        || !d.getId().equals(departamento.getId())) {
                    listDepartamentoModel.addElement(d);
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
        final String messageCompleta = isAdicionar
                ? "Funcionario adicionado!"
                : "Funcionario alterado!";
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
    public FuncionarioDTO getFuncionario() {
        final FuncionarioDTO funcionario = new FuncionarioDTO();
        funcionario.setId(idFuncionario);
        funcionario.setNome(fieldNome.getText());
        funcionario.setCpf(fieldCpf.getText());
        funcionario.setEmail(fieldEmail.getText());
        funcionario.setLogin(fieldLogin.getText());
        funcionario.setSenha(new String(fieldSenha.getPassword()));
        funcionario.setDepartamento(
                listDepartamentos.getSelectedValue() == null ? null
                        : listDepartamentos.getSelectedValue());
        return funcionario;
    }

}
