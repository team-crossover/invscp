package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.view.MenuView;

public class MenuSwingView extends JPanel implements MenuView {

    private static final long serialVersionUID = 8975992154717828680L;

    private JLabel lblSessaoAnonima;
    private JLabel lblDepartamento;
    private JLabel lblCargo;
    private JLabel lblFuncionario;
    private JButton btnLogin;
    private JButton btnLogout;
    private JButton btnDepartamentos;
    private JButton btnSalas;
    private JButton btnPredios;
    private JButton btnLocalizacoes;
    private JButton btnFuncionarios;
    private JPanel pnlSession;
    private JPanel pnlButtons;
    private JPanel pnlLoginLogout;

    public MenuSwingView() {
        initialize();
        updateUsuario(null);
    }

    private void initialize() {
        lblSessaoAnonima = new JLabel("Sessão anônima");
        lblSessaoAnonima.setHorizontalAlignment(SwingConstants.CENTER);
        lblFuncionario = new JLabel("Funcionario");
        lblFuncionario.setHorizontalAlignment(SwingConstants.CENTER);
        lblCargo = new JLabel("Cargo");
        lblCargo.setHorizontalAlignment(SwingConstants.CENTER);
        lblDepartamento = new JLabel("Departamento");
        lblDepartamento.setHorizontalAlignment(SwingConstants.CENTER);
        btnFuncionarios = new JButton("Funcionarios");
        btnDepartamentos = new JButton("Departamentos");
        btnLocalizacoes = new JButton("Localizações");
        btnPredios = new JButton("Prédios");
        btnSalas = new JButton("Salas");
        btnLogout = new JButton("Logout");
        btnLogin = new JButton("Login");

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 200 };
        gridBagLayout.columnWeights = new double[] { 1.0 };
        gridBagLayout.rowHeights = new int[] { 500, 0, 10, 0 };
        gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0 };
        setLayout(gridBagLayout);

        pnlButtons = new JPanel();
        final GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.NORTH;
        gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        add(pnlButtons, gbc_panel_1);
        pnlButtons.setLayout(new GridLayout(0, 1, 10, 10));

        pnlSession = new JPanel();
        final GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.NORTH;
        gbc_panel.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 1;
        add(pnlSession, gbc_panel);
        pnlSession.setLayout(new GridLayout(0, 1, 10, 5));

        pnlLoginLogout = new JPanel();
        final GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.anchor = GridBagConstraints.NORTH;
        gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_2.insets = new Insets(0, 0, 5, 5);
        gbc_panel_2.gridx = 0;
        gbc_panel_2.gridy = 3;
        add(pnlLoginLogout, gbc_panel_2);
        pnlLoginLogout.setLayout(new GridLayout(0, 1, 10, 0));
    }

    @Override
    public void updateUsuario(final FuncionarioDTO funcionario) {
        pnlSession.removeAll();
        pnlButtons.removeAll();
        pnlLoginLogout.removeAll();

        final boolean isLogado = (funcionario != null);
        final boolean isChefe = isLogado && funcionario.getCargo().isChefe();
        final boolean isChefeDePatrimonio = isLogado
                && funcionario.getCargo().isChefeDePatrimonio();

        // Exibe os botões adequados dependendo do papel do funcionário logado.
        if (!isLogado) {
            // SESSAO ANONIMA
            pnlSession.add(lblSessaoAnonima);
            pnlLoginLogout.add(btnLogin);

        } else {
            // FUNCIONARIO LOGADO

            // Session
            lblFuncionario.setText(funcionario.getNome());
            pnlSession.add(lblFuncionario);
            lblCargo.setText(funcionario.getCargo().getTexto());
            pnlSession.add(lblCargo);
            final DepartamentoDTO dept = funcionario.getDepartamento();
            if (dept != null) {
                lblDepartamento.setText(dept.getNome());
                pnlSession.add(lblDepartamento);
            }

            // Buttons
            if (isChefe) {
                // BOTOES DE CHEFES
                pnlButtons.add(btnFuncionarios);
                pnlButtons.add(btnDepartamentos);
                pnlButtons.add(btnLocalizacoes);
                pnlButtons.add(btnPredios);
                pnlButtons.add(btnSalas);

                if (isChefeDePatrimonio) {
                    // BOTOES DE CHEFE DE PATIRMONIO
                    // ...
                }

            } else {
                // BOTOES DE FUNCIONARIOS COMUNS
            }

            // Logout
            pnlLoginLogout.add(btnLogout);
        }

        revalidate();
        repaint();
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void addLoginListener(final ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    @Override
    public void addLogoutListener(final ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    @Override
    public void addLocalizacoesListener(final ActionListener listener) {
        btnLocalizacoes.addActionListener(listener);

    }

    @Override
    public void addPrediosListener(final ActionListener listener) {
        btnPredios.addActionListener(listener);

    }

    @Override
    public void addSalasListener(final ActionListener listener) {
        btnSalas.addActionListener(listener);

    }

    @Override
    public void addDepartamentosListener(final ActionListener listener) {
        btnDepartamentos.addActionListener(listener);
    }

    @Override
    public void addFuncionariosListener(final ActionListener listener) {
        btnFuncionarios.addActionListener(listener);

    }

}
