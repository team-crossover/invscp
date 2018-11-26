package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.nelsonwilliam.invscp.client.view.MenuView;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

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
    private JButton btnBens;
    private JButton btnPredios;
    private JButton btnLocalizacoes;
    private JButton btnFuncionarios;
    private JButton btnMovimentacoes;
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
        btnBens = new JButton("Bens");
        btnMovimentacoes = new JButton("Movimentações");
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
    public void updateUsuario(final FuncionarioDTO user) {
        pnlSession.removeAll();
        pnlButtons.removeAll();
        pnlLoginLogout.removeAll();

        final boolean isLogado = user != null;
        final boolean isChefe = isLogado && user.getCargo().isChefe();
        final boolean isChefeDePatrimonio =
                isLogado && user.getCargo().isChefeDePatrimonio();

        // Exibe os botões adequados dependendo do papel do funcionário logado.
        if (!isLogado) {
            // SESSAO ANÔNIMA
            pnlLoginLogout.add(btnLogin);
            pnlSession.add(lblSessaoAnonima);
            pnlButtons.add(btnBens);

        } else {
            // FUNCIONARIO LOGADO
            pnlLoginLogout.add(btnLogout);
            lblFuncionario.setText(user.getNome());
            lblCargo.setText(user.getCargo().getTexto());
            pnlSession.add(lblFuncionario);
            pnlSession.add(lblCargo);
            if (user.getDepartamento() != null) {
                lblDepartamento.setText(user.getDepartamento().getNome());
                pnlSession.add(lblDepartamento);
            }

            if (isChefe) {
                if (isChefeDePatrimonio) {
                    // Chefe de patrimônio
                    pnlButtons.add(btnBens);
                    pnlButtons.add(btnMovimentacoes);
                    pnlButtons.add(btnSalas);
                    pnlButtons.add(btnPredios);
                    pnlButtons.add(btnLocalizacoes);
                    pnlButtons.add(btnDepartamentos);
                    pnlButtons.add(btnFuncionarios);
                } else {
                    // Chefe de departamento
                    pnlButtons.add(btnBens);
                    pnlButtons.add(btnMovimentacoes);
                    pnlButtons.add(btnFuncionarios);
                }
            } else {
                // Funcionário comum
                pnlButtons.add(btnBens);
                pnlButtons.add(btnMovimentacoes);

            }
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

    @Override
    public void addBensListener(final ActionListener listener) {
        btnBens.addActionListener(listener);

    }

    @Override
    public void addMovimentacoesListener(final ActionListener listener) {
        btnMovimentacoes.addActionListener(listener);

    }
}
