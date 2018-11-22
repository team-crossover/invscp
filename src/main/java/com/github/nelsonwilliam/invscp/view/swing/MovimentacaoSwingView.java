package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.view.MovimentacaoView;

public class MovimentacaoSwingView extends JDialog implements MovimentacaoView {

    private static final long serialVersionUID = 2636535013435748325L;

    private final boolean isAdicionar;

    private Integer idMovimentacao;
    private BemDTO bem;
    private SalaDTO salaOrigem;
    private SalaDTO salaDestino;
    private EtapaMovEnum etapa;
    private List<EventoMovimentacaoDTO> eventos;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblEtapa;
    private JLabel lblBem;
    private JLabel lblSalaDeOrigem;
    private JLabel lblSalaDeDestino;
    private JLabel lblEventos;
    private JLabel fieldEtapa;
    private JLabel fieldBem;
    private JLabel fieldSalaOrigem;
    private JLabel SalaDestino;
    private JComboBox<EtapaMovEnum> comboBoxEventos;

    /**
     * @param movimentacao Movimentações cujos valores serão exibidos
     *        inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova ordem de serviço (true) ou para atualização de uma
     *        ordem de serviço existente (false).
     */

    public MovimentacaoSwingView(final JFrame owner,
            final MovimentacaoDTO movimentacao, final boolean isAdicionar) {

        super(owner,
                isAdicionar ? "Adicionar movimentação" : "Ver movimentação",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateMovimentacao(movimentacao);
    }

    private void initialize() {
        setBounds(0, 0, 500, 327);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25 };
        gridBagLayout.rowHeights = new int[] { 29, 29, 29, 29, 29, 29, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblEtapa = new JLabel("Etapa:");
        GridBagConstraints gbc_lblEtapa = new GridBagConstraints();
        gbc_lblEtapa.anchor = GridBagConstraints.EAST;
        gbc_lblEtapa.insets = new Insets(0, 0, 5, 5);
        gbc_lblEtapa.gridx = 1;
        gbc_lblEtapa.gridy = 0;
        getContentPane().add(lblEtapa, gbc_lblEtapa);

        fieldEtapa = new JLabel("");
        GridBagConstraints gbc_fieldEtapa = new GridBagConstraints();
        gbc_fieldEtapa.anchor = GridBagConstraints.WEST;
        gbc_fieldEtapa.insets = new Insets(0, 0, 5, 5);
        gbc_fieldEtapa.gridx = 2;
        gbc_fieldEtapa.gridy = 0;
        getContentPane().add(fieldEtapa, gbc_fieldEtapa);

        lblBem = new JLabel("Bem:");
        GridBagConstraints gbc_lblBem = new GridBagConstraints();
        gbc_lblBem.anchor = GridBagConstraints.EAST;
        gbc_lblBem.insets = new Insets(0, 0, 5, 5);
        gbc_lblBem.gridx = 1;
        gbc_lblBem.gridy = 1;
        getContentPane().add(lblBem, gbc_lblBem);

        fieldBem = new JLabel("");
        GridBagConstraints gbc_fieldBem = new GridBagConstraints();
        gbc_fieldBem.anchor = GridBagConstraints.WEST;
        gbc_fieldBem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldBem.gridx = 2;
        gbc_fieldBem.gridy = 1;
        getContentPane().add(fieldBem, gbc_fieldBem);

        lblSalaDeOrigem = new JLabel("Sala de origem:");
        GridBagConstraints gbc_lblSalaDeOrigem = new GridBagConstraints();
        gbc_lblSalaDeOrigem.anchor = GridBagConstraints.EAST;
        gbc_lblSalaDeOrigem.insets = new Insets(0, 0, 5, 5);
        gbc_lblSalaDeOrigem.gridx = 1;
        gbc_lblSalaDeOrigem.gridy = 2;
        getContentPane().add(lblSalaDeOrigem, gbc_lblSalaDeOrigem);

        fieldSalaOrigem = new JLabel("");
        GridBagConstraints gbc_fieldSalaOrigem = new GridBagConstraints();
        gbc_fieldSalaOrigem.anchor = GridBagConstraints.WEST;
        gbc_fieldSalaOrigem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldSalaOrigem.gridx = 2;
        gbc_fieldSalaOrigem.gridy = 2;
        getContentPane().add(fieldSalaOrigem, gbc_fieldSalaOrigem);

        lblSalaDeDestino = new JLabel("Sala de destino:");
        GridBagConstraints gbc_lblSalaDeDestino = new GridBagConstraints();
        gbc_lblSalaDeDestino.anchor = GridBagConstraints.EAST;
        gbc_lblSalaDeDestino.insets = new Insets(0, 0, 5, 5);
        gbc_lblSalaDeDestino.gridx = 1;
        gbc_lblSalaDeDestino.gridy = 3;
        getContentPane().add(lblSalaDeDestino, gbc_lblSalaDeDestino);

        SalaDestino = new JLabel("");
        GridBagConstraints gbc_SalaDestino = new GridBagConstraints();
        gbc_SalaDestino.anchor = GridBagConstraints.WEST;
        gbc_SalaDestino.insets = new Insets(0, 0, 5, 5);
        gbc_SalaDestino.gridx = 2;
        gbc_SalaDestino.gridy = 3;
        getContentPane().add(SalaDestino, gbc_SalaDestino);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 6;
        getContentPane().add(btnCancelar, gbc_btnCancelar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateMovimentacao(final MovimentacaoDTO movimentacao) {

        if (movimentacao == null) {
            throw new NullPointerException();
        }

        idMovimentacao = movimentacao.getId();
        bem = movimentacao.getBem();
        salaDestino = movimentacao.getSalaDestino();
        salaOrigem = movimentacao.getSalaOrigem();
        etapa = movimentacao.getEtapa();

        fieldEtapa.setText(movimentacao.getEtapaString());
        fieldBem.setText(movimentacao.getBem().getDescricao());
        SalaDestino.setText(movimentacao.getSalaDestino().getNome());
        fieldSalaOrigem.setText(movimentacao.getSalaOrigem().getNome());
        // comboBoxEventos

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
        final String messageCompleta = isAdicionar ? "Movimentação adicionada!"
                : "Movimentação alterada!";
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
    public MovimentacaoDTO getMovimentacao() {
        final MovimentacaoDTO movimentacao = new MovimentacaoDTO();
        movimentacao.setId(idMovimentacao);
        movimentacao.setEtapa(etapa);
        movimentacao.setSalaDestino(salaDestino);
        movimentacao.setSalaOrigem(salaOrigem);
        // movimentacao.setEventos(newEventos);
        movimentacao.setBem(bem);
        return movimentacao;
    }

}
