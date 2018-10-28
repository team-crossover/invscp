package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.nelsonwilliam.invscp.model.Sala;
import com.github.nelsonwilliam.invscp.model.SalasEnum;
import com.github.nelsonwilliam.invscp.view.SalaView;

public class SalaSwingView extends JDialog implements SalaView {

    private static final long serialVersionUID = -4853835092579146336L;

    private final boolean isAdicionar;

    private Integer idSala;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldNome;
    private JCheckBox chckbxDeposito;
    private JComboBox<SalasEnum> tipo;
    private JLabel lblTipo;
    private JLabel lblDepsito;

    /**
     * @param sala Salas cujos valores serão exibidos inicialmente.
     * 
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova sala (true) ou para atualização de uma sala existente
     *        (false).
     */
    public SalaSwingView(Window owner, Sala sala, boolean isAdicionar) {
        super(owner, isAdicionar ? "Adicionar sala" : "Alterar sala",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateSala(sala);
    }

    private void initialize() {
        setBounds(150, 150, 422, 328);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25, 0 };
        gridBagLayout.rowHeights = new int[] { 25, 25, 0, 0, 0, 20, 0, -26, 25,
                0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((ActionEvent e) -> {
            close();
        });

        JLabel lblNome = new JLabel("Nome:");
        GridBagConstraints gbc_lblNome = new GridBagConstraints();
        gbc_lblNome.anchor = GridBagConstraints.EAST;
        gbc_lblNome.insets = new Insets(0, 0, 5, 5);
        gbc_lblNome.gridx = 1;
        gbc_lblNome.gridy = 1;
        getContentPane().add(lblNome, gbc_lblNome);

        fieldNome = new JTextField();
        GridBagConstraints gbc_txtfldNome = new GridBagConstraints();
        gbc_txtfldNome.insets = new Insets(0, 0, 5, 5);
        gbc_txtfldNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtfldNome.gridx = 2;
        gbc_txtfldNome.gridy = 1;
        getContentPane().add(fieldNome, gbc_txtfldNome);
        fieldNome.setColumns(10);

        lblTipo = new JLabel("Tipo:");
        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.gridx = 1;
        gbc_lblTipo.gridy = 3;
        getContentPane().add(lblTipo, gbc_lblTipo);

        tipo = new JComboBox<SalasEnum>();
        tipo.setModel(new DefaultComboBoxModel<SalasEnum>(SalasEnum.values()));
        GridBagConstraints gbc_tipo = new GridBagConstraints();
        gbc_tipo.insets = new Insets(0, 0, 5, 5);
        gbc_tipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipo.gridx = 2;
        gbc_tipo.gridy = 3;
        getContentPane().add(tipo, gbc_tipo);

        lblDepsito = new JLabel("Depósito:");
        GridBagConstraints gbc_lblDepsito = new GridBagConstraints();
        gbc_lblDepsito.anchor = GridBagConstraints.EAST;
        gbc_lblDepsito.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepsito.gridx = 1;
        gbc_lblDepsito.gridy = 5;
        getContentPane().add(lblDepsito, gbc_lblDepsito);

        chckbxDeposito = new JCheckBox("");
        GridBagConstraints gbc_chckbxDeposito = new GridBagConstraints();
        gbc_chckbxDeposito.anchor = GridBagConstraints.WEST;
        gbc_chckbxDeposito.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxDeposito.gridx = 2;
        gbc_chckbxDeposito.gridy = 5;
        getContentPane().add(chckbxDeposito, gbc_chckbxDeposito);

        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 7;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 7;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void addConfirmarListener(ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateSala(Sala sala) {
        if (sala == null) {
            throw new NullPointerException();
        }

        // O ID da sala sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar a sala.
        idSala = sala.getId();

        fieldNome.setText(sala.getNome());
    }

    @Override
    public void showError(String message) {
        String titulo = "Erro";
        String messageCompleta = "Erro: " + message;
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSucesso() {
        String titulo = "Sucesso";
        String messageCompleta = isAdicionar ? "Sala adicionada!"
                : "Sala alterada!";
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public Sala getSala() {
        Sala sala = new Sala();
        sala.setId(idSala);
        sala.setNome(fieldNome.getText());
        sala.setDeDeposito(chckbxDeposito.isSelected());
        sala.setTipoSala(sala.getTipoSala());

        return sala;
    }

}
