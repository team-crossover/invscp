package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.view.PredioView;

public class PredioSwingView extends JDialog implements PredioView {

    private static final long serialVersionUID = -3851382497211004322L;

    private final boolean isAdicionar;

    private Integer idPredio;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldNome;
    private JList<Localizacao> listLocais;
    private JScrollPane scrollPane;

    /**
     * @param departamento Prédios cujos valores serão exibidos inicialmente.
     * 
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de um novo predio (true) ou para atualização de um predio
     *        existente (false).
     */

    public PredioSwingView(Window owner, Predio predio, boolean isAdicionar) {
        super(owner, isAdicionar ? "Adicionar prédio" : "Alterar prédio",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updatePredio(predio);
    }

    private void initialize() {
        setBounds(150, 150, 422, 328);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25, 0 };
        gridBagLayout.rowHeights = new int[] { 25, 25, 0, 0, 0, 25, 25, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0,
                0.0, Double.MIN_VALUE };
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

        JLabel lblChefe = new JLabel("Localização:");
        GridBagConstraints gbc_lblChefe = new GridBagConstraints();
        gbc_lblChefe.anchor = GridBagConstraints.EAST;
        gbc_lblChefe.insets = new Insets(0, 0, 5, 5);
        gbc_lblChefe.gridx = 1;
        gbc_lblChefe.gridy = 3;
        getContentPane().add(lblChefe, gbc_lblChefe);

        // Ajustar !!!!!!!!!!!!!
        ListCellRenderer<? super Localizacao> localizacaoListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 337686638048057981L;

            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    String nome = ((Predio) value).getNome();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 3;
        getContentPane().add(scrollPane, gbc_scrollPane);

        // listLocais = new JList<Localizacao>();
        // listLocais.setModel(new DefaultListModel<Localizacao>());
        // listLocais.setCellRenderer(localizacaoListRenderer);
        // scrollPane.setViewportView(listLocalizacao);

        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 5;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 5;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updatePredio(Predio predio) {
        if (predio == null) {
            throw new NullPointerException();
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idPredio = predio.getId();

        fieldNome.setText(predio.getNome());

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
        String messageCompleta = isAdicionar ? "Predio adicionado!"
                : "Predio alterado!";
        JOptionPane.showMessageDialog(this, messageCompleta, titulo,
                JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public Predio getPredio() {
        Predio predio = new Predio();
        predio.setId(idPredio);
        predio.setNome(fieldNome.getText());

        return predio;
    }

}
