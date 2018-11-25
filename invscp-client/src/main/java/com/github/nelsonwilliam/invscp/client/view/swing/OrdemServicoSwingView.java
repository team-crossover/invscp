package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.nelsonwilliam.invscp.client.view.OrdemServicoView;
import com.github.nelsonwilliam.invscp.shared.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.shared.model.enums.OSSituacaoEnum;

public class OrdemServicoSwingView extends JDialog implements OrdemServicoView {

    private static final long serialVersionUID = -9096527581893268073L;

    private final boolean isAdicionar;

    private Integer idOrdemServico;
    private BemDTO bem;
    private FuncionarioDTO funcionario;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldValor;
    private JLabel lblDataDeCadastro;
    private JLabel lblDataConclusao;
    private JLabel lblValor;
    private JLabel lblSituacao;
    private JLabel lblFuncionario;
    private JLabel lblBem;
    private JLabel fieldDataConclusao;
    private JLabel fieldSituacao;
    private JLabel fieldBem;
    private JLabel fieldFuncionario;
    private JLabel fieldDataCadastro;

    /**
     * @param ordemServico Ordens de serviço cujos valores serão exibidos
     *        inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova ordem de serviço (true) ou para atualização de uma
     *        ordem de serviço existente (false).
     */

    public OrdemServicoSwingView(final JFrame owner,
            final OrdemServicoDTO ordemServico, final boolean isAdicionar) {

        super(owner,
                isAdicionar ? "Adicionar ordem de serviço"
                        : "Alterar ordem de serviço",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateOrdemServico(ordemServico);
    }

    private void initialize() {
        setBounds(0, 0, 500, 250);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25 };
        gridBagLayout.rowHeights =
                new int[] { 25, 30, 15, 0, 15, 15, 15, 15, 0, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblDataDeCadastro = new JLabel("Data de cadastro:");
        final GridBagConstraints gbc_lblDataDeCadastro = new GridBagConstraints();
        gbc_lblDataDeCadastro.anchor = GridBagConstraints.EAST;
        gbc_lblDataDeCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeCadastro.gridx = 1;
        gbc_lblDataDeCadastro.gridy = 1;
        getContentPane().add(lblDataDeCadastro, gbc_lblDataDeCadastro);

        fieldDataCadastro = new JLabel("");
        final GridBagConstraints gbc_fieldDataCadastro = new GridBagConstraints();
        gbc_fieldDataCadastro.anchor = GridBagConstraints.WEST;
        gbc_fieldDataCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDataCadastro.gridx = 2;
        gbc_fieldDataCadastro.gridy = 1;
        getContentPane().add(fieldDataCadastro, gbc_fieldDataCadastro);

        lblDataConclusao = new JLabel("Data de conclusão:");
        final GridBagConstraints gbc_lblDataConclusao = new GridBagConstraints();
        gbc_lblDataConclusao.anchor = GridBagConstraints.EAST;
        gbc_lblDataConclusao.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataConclusao.gridx = 1;
        gbc_lblDataConclusao.gridy = 2;
        getContentPane().add(lblDataConclusao, gbc_lblDataConclusao);

        fieldDataConclusao = new JLabel();
        final GridBagConstraints gbc_fieldDataConclusao = new GridBagConstraints();
        gbc_fieldDataConclusao.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDataConclusao.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldDataConclusao.gridx = 2;
        gbc_fieldDataConclusao.gridy = 2;
        getContentPane().add(fieldDataConclusao, gbc_fieldDataConclusao);

        lblValor = new JLabel("Valor:");
        final GridBagConstraints gbc_lblValor = new GridBagConstraints();
        gbc_lblValor.insets = new Insets(0, 0, 5, 5);
        gbc_lblValor.anchor = GridBagConstraints.EAST;
        gbc_lblValor.gridx = 1;
        gbc_lblValor.gridy = 3;
        getContentPane().add(lblValor, gbc_lblValor);

        fieldValor = new JTextField();
        final GridBagConstraints gbc_fieldValor = new GridBagConstraints();
        gbc_fieldValor.insets = new Insets(0, 0, 5, 5);
        gbc_fieldValor.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldValor.gridx = 2;
        gbc_fieldValor.gridy = 3;
        getContentPane().add(fieldValor, gbc_fieldValor);
        fieldValor.setColumns(10);

        lblSituacao = new JLabel("Situação:");
        final GridBagConstraints gbc_lblSituacao = new GridBagConstraints();
        gbc_lblSituacao.anchor = GridBagConstraints.EAST;
        gbc_lblSituacao.insets = new Insets(0, 0, 5, 5);
        gbc_lblSituacao.gridx = 1;
        gbc_lblSituacao.gridy = 4;
        getContentPane().add(lblSituacao, gbc_lblSituacao);

        fieldSituacao = new JLabel("");
        final GridBagConstraints gbc_situacao = new GridBagConstraints();
        gbc_situacao.insets = new Insets(0, 0, 5, 5);
        gbc_situacao.fill = GridBagConstraints.HORIZONTAL;
        gbc_situacao.gridx = 2;
        gbc_situacao.gridy = 4;
        getContentPane().add(fieldSituacao, gbc_situacao);

        lblFuncionario = new JLabel("Responsável:");
        final GridBagConstraints gbc_lblFuncionario = new GridBagConstraints();
        gbc_lblFuncionario.anchor = GridBagConstraints.EAST;
        gbc_lblFuncionario.insets = new Insets(0, 0, 5, 5);
        gbc_lblFuncionario.gridx = 1;
        gbc_lblFuncionario.gridy = 5;
        getContentPane().add(lblFuncionario, gbc_lblFuncionario);

        fieldFuncionario = new JLabel("");
        final GridBagConstraints gbc_fieldFuncionario = new GridBagConstraints();
        gbc_fieldFuncionario.anchor = GridBagConstraints.WEST;
        gbc_fieldFuncionario.fill = GridBagConstraints.VERTICAL;
        gbc_fieldFuncionario.insets = new Insets(0, 0, 5, 5);
        gbc_fieldFuncionario.gridx = 2;
        gbc_fieldFuncionario.gridy = 5;
        getContentPane().add(fieldFuncionario, gbc_fieldFuncionario);

        lblBem = new JLabel("Bem:");
        final GridBagConstraints gbc_lblBem = new GridBagConstraints();
        gbc_lblBem.anchor = GridBagConstraints.EAST;
        gbc_lblBem.insets = new Insets(0, 0, 5, 5);
        gbc_lblBem.gridx = 1;
        gbc_lblBem.gridy = 6;
        getContentPane().add(lblBem, gbc_lblBem);

        fieldBem = new JLabel("");
        final GridBagConstraints gbc_fieldBem = new GridBagConstraints();
        gbc_fieldBem.anchor = GridBagConstraints.WEST;
        gbc_fieldBem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldBem.gridx = 2;
        gbc_fieldBem.gridy = 6;
        getContentPane().add(fieldBem, gbc_fieldBem);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 8;
        getContentPane().add(btnCancelar, gbc_btnCancelar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateOrdemServico(final OrdemServicoDTO ordemServico) {

        if (ordemServico == null) {
            throw new NullPointerException();
        }

        idOrdemServico = ordemServico.getId();
        bem = ordemServico.getBem();
        funcionario = ordemServico.getFuncionario();

        // Apenas pode alterar a OS se ela nao tiver CONCLUIDA
        final boolean isConcluida =
                ordemServico.getSituacao() == OSSituacaoEnum.CONCLUIDA;
        fieldValor.setEnabled(!isConcluida);
        btnConfirmar.setEnabled(!isConcluida);

        if (ordemServico.getDataCadastro() != null) {
            fieldDataCadastro.setText(ordemServico.getDataCadastro()
                    .format(DateTimeFormatter.ISO_DATE));
        }
        if (ordemServico.getDataConclusao() == null) {
            fieldDataConclusao.setText("Pendente");
        } else {
            fieldDataConclusao.setText(ordemServico.getDataConclusao()
                    .format(DateTimeFormatter.ISO_DATE));
        }
        if (ordemServico.getValor() != null) {
            fieldValor.setText(ordemServico.getValor().toString());
        }
        if (ordemServico.getSituacao() != null) {
            fieldSituacao
                    .setText(ordemServico.getSituacao().getTexto());
        }
        if (ordemServico.getFuncionario() != null) {
            fieldFuncionario.setText(ordemServico.getFuncionario().getNome());
        }
        if (ordemServico.getBem() != null) {
            fieldBem.setText(ordemServico.getBem().getDescricao());
        }

        if (ordemServico.getSituacao() != OSSituacaoEnum.CONCLUIDA) {
            final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
            gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
            gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
            gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
            gbc_btnConfirmar.gridx = 2;
            gbc_btnConfirmar.gridy = 8;
            getContentPane().add(btnConfirmar, gbc_btnConfirmar);
        } else {
            getContentPane().remove(btnConfirmar);
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
                ? "Ordem de serviço adicionada!"
                : "Ordem de serviço alterada!";
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
    public OrdemServicoDTO getOrdemServico() {
        final OrdemServicoDTO ordemServico = new OrdemServicoDTO();

        ordemServico.setId(idOrdemServico);
        ordemServico.setBem(bem);
        ordemServico.setFuncionario(funcionario);
        ordemServico
                .setSituacao(
                        OSSituacaoEnum.valueOfTexto(fieldSituacao.getText()));

        try {
            ordemServico.setDataCadastro(LocalDate.parse(
                    fieldDataCadastro.getText(),
                    DateTimeFormatter.ISO_DATE));
        } catch (final DateTimeParseException e) {
            showError("O formato da data de cadastro é inválido.");
            return null;
        }

        try {
            if (!fieldDataConclusao.getText().equals("Pendente")) {
            ordemServico.setDataConclusao(LocalDate.parse(
                    fieldDataConclusao.getText(), DateTimeFormatter.ISO_DATE));
            }
        } catch (final DateTimeParseException e) {
            showError("O formato da data de conclusão é inválido.");
            return null;
        }

        try {
            ordemServico.setValor(new BigDecimal(Double
                    .parseDouble(fieldValor.getText().replace(',', '.'))));
        } catch (final NumberFormatException e) {
            showError("O formato do valor é inválido.");
            return null;
        }

        return ordemServico;
    }

}
