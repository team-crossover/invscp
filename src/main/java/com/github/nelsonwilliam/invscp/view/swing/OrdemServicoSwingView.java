package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.FuncionarioDTO;
import com.github.nelsonwilliam.invscp.model.dto.OrdemServicoDTO;
import com.github.nelsonwilliam.invscp.model.enums.OSsituacaoEnum;
import com.github.nelsonwilliam.invscp.view.OrdemServicoView;

public class OrdemServicoSwingView extends JDialog implements OrdemServicoView {

    private static final long serialVersionUID = -9096527581893268073L;

    private final boolean isAdicionar;

    private Integer idPredio;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldValor;
    private JLabel lblDataDeCadastro;
    private JLabel lblDataConclusao;
    private JLabel lblValor;
    private JLabel lblSituacao;
    private JLabel lblFuncionario;
    private JLabel lblBem;
    private JTextField fieldDataConclusao;
    private JComboBox<OSsituacaoEnum> situacao;
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
            final OrdemServicoDTO ordemServico, final boolean isAdicionar,
            final List<FuncionarioDTO> funcionario, List<BemDTO> bem) {

        super(owner,
                isAdicionar ? "Adicionar ordem de serviço"
                        : "Alterar ordem de serviço",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateOrdemServico(ordemServico, bem, funcionario);
    }

    private void initialize() {
        setBounds(0, 0, 500, 300);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 102, 0, 30 };
        gridBagLayout.rowHeights = new int[] { 30, 0, 0, 0, 0, 0, 15, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblDataDeCadastro = new JLabel("Data de cadastro:");
        GridBagConstraints gbc_lblDataDeCadastro = new GridBagConstraints();
        gbc_lblDataDeCadastro.anchor = GridBagConstraints.EAST;
        gbc_lblDataDeCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataDeCadastro.gridx = 1;
        gbc_lblDataDeCadastro.gridy = 0;
        getContentPane().add(lblDataDeCadastro, gbc_lblDataDeCadastro);

        fieldDataCadastro = new JLabel("");
        GridBagConstraints gbc_fieldDataCadastro = new GridBagConstraints();
        gbc_fieldDataCadastro.anchor = GridBagConstraints.WEST;
        gbc_fieldDataCadastro.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDataCadastro.gridx = 2;
        gbc_fieldDataCadastro.gridy = 0;
        getContentPane().add(fieldDataCadastro, gbc_fieldDataCadastro);

        lblDataConclusao = new JLabel("Data de conclusão:");
        GridBagConstraints gbc_lblDataConclusao = new GridBagConstraints();
        gbc_lblDataConclusao.anchor = GridBagConstraints.EAST;
        gbc_lblDataConclusao.insets = new Insets(0, 0, 5, 5);
        gbc_lblDataConclusao.gridx = 1;
        gbc_lblDataConclusao.gridy = 1;
        getContentPane().add(lblDataConclusao, gbc_lblDataConclusao);

        fieldDataConclusao = new JTextField();
        GridBagConstraints gbc_fieldDataConclusao = new GridBagConstraints();
        gbc_fieldDataConclusao.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDataConclusao.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldDataConclusao.gridx = 2;
        gbc_fieldDataConclusao.gridy = 1;
        getContentPane().add(fieldDataConclusao, gbc_fieldDataConclusao);
        fieldDataConclusao.setColumns(10);

        lblValor = new JLabel("Valor:");
        GridBagConstraints gbc_lblValor = new GridBagConstraints();
        gbc_lblValor.insets = new Insets(0, 0, 5, 5);
        gbc_lblValor.anchor = GridBagConstraints.EAST;
        gbc_lblValor.gridx = 1;
        gbc_lblValor.gridy = 2;
        getContentPane().add(lblValor, gbc_lblValor);

        fieldValor = new JTextField();
        final GridBagConstraints gbc_fieldValor = new GridBagConstraints();
        gbc_fieldValor.insets = new Insets(0, 0, 5, 5);
        gbc_fieldValor.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldValor.gridx = 2;
        gbc_fieldValor.gridy = 2;
        getContentPane().add(fieldValor, gbc_fieldValor);
        fieldValor.setColumns(10);

        lblSituacao = new JLabel("Situação:");
        GridBagConstraints gbc_lblSituacao = new GridBagConstraints();
        gbc_lblSituacao.anchor = GridBagConstraints.EAST;
        gbc_lblSituacao.insets = new Insets(0, 0, 5, 5);
        gbc_lblSituacao.gridx = 1;
        gbc_lblSituacao.gridy = 3;
        getContentPane().add(lblSituacao, gbc_lblSituacao);

        final ListCellRenderer<? super OSsituacaoEnum> situacaoListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 9190710738972824406L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((OSsituacaoEnum) value).getTexto();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        situacao = new JComboBox<OSsituacaoEnum>();
        situacao.setModel(new DefaultComboBoxModel<OSsituacaoEnum>(
                OSsituacaoEnum.values()));
        situacao.setRenderer(situacaoListRenderer);

        final GridBagConstraints gbc_situacao = new GridBagConstraints();
        gbc_situacao.insets = new Insets(0, 0, 5, 5);
        gbc_situacao.fill = GridBagConstraints.HORIZONTAL;
        gbc_situacao.gridx = 2;
        gbc_situacao.gridy = 3;
        getContentPane().add(situacao, gbc_situacao);

        lblFuncionario = new JLabel("Funcionario:");
        GridBagConstraints gbc_lblFuncionario = new GridBagConstraints();
        gbc_lblFuncionario.anchor = GridBagConstraints.EAST;
        gbc_lblFuncionario.insets = new Insets(0, 0, 5, 5);
        gbc_lblFuncionario.gridx = 1;
        gbc_lblFuncionario.gridy = 4;
        getContentPane().add(lblFuncionario, gbc_lblFuncionario);

        fieldFuncionario = new JLabel("");
        GridBagConstraints gbc_fieldFuncionario = new GridBagConstraints();
        gbc_fieldFuncionario.anchor = GridBagConstraints.WEST;
        gbc_fieldFuncionario.fill = GridBagConstraints.VERTICAL;
        gbc_fieldFuncionario.insets = new Insets(0, 0, 5, 5);
        gbc_fieldFuncionario.gridx = 2;
        gbc_fieldFuncionario.gridy = 4;
        getContentPane().add(fieldFuncionario, gbc_fieldFuncionario);

        lblBem = new JLabel("Bem:");
        GridBagConstraints gbc_lblBem = new GridBagConstraints();
        gbc_lblBem.anchor = GridBagConstraints.EAST;
        gbc_lblBem.insets = new Insets(0, 0, 5, 5);
        gbc_lblBem.gridx = 1;
        gbc_lblBem.gridy = 5;
        getContentPane().add(lblBem, gbc_lblBem);

        fieldBem = new JLabel("");
        GridBagConstraints gbc_fieldBem = new GridBagConstraints();
        gbc_fieldBem.anchor = GridBagConstraints.WEST;
        gbc_fieldBem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldBem.gridx = 2;
        gbc_fieldBem.gridy = 5;
        getContentPane().add(fieldBem, gbc_fieldBem);

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
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateOrdemServico(final OrdemServicoDTO ordemServico,
            final List<BemDTO> bens, final List<FuncionarioDTO> funcionarios) {

        if (ordemServico == null) {
            throw new NullPointerException();
        }

        // O ID do departamento sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar o departamento.
        idPredio = ordemServico.getId();

        fieldDataConclusao.setText(ordemServico.getDataConclusao().toString());
        fieldDataCadastro.setText(ordemServico.getDataConclusao().toString());
        fieldValor.setText(ordemServico.getValor().toString());
        situacao.setSelectedItem(ordemServico.getSituacao());
        fieldFuncionario.setText(ordemServico.getFuncionario().getNome());
        fieldBem.setText(ordemServico.getBem().getDescricao());

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
        ordemServico.setId(idPredio);
        // ordemServico.setBem(fieldBem.getText());
        // ordemServico.setFuncionario(fieldFuncionario.getText());
        ordemServico.setDataCadastro(LocalDate.parse(
                fieldDataCadastro.getText(), DateTimeFormatter.BASIC_ISO_DATE));
        ordemServico
                .setDataConclusao(LocalDate.parse(fieldDataConclusao.getText(),
                        DateTimeFormatter.BASIC_ISO_DATE));
        ordemServico.setValor(Float.valueOf(fieldValor.getText()));
        ordemServico.setSituacao((OSsituacaoEnum) situacao.getSelectedItem());

        return ordemServico;
    }

}
