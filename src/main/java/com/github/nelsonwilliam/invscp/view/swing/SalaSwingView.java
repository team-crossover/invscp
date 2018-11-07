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

import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.enums.TipoSalaEnum;
import com.github.nelsonwilliam.invscp.view.SalaView;

public class SalaSwingView extends JDialog implements SalaView {

    private static final long serialVersionUID = -4853835092579146336L;

    private final boolean isAdicionar;

    private Integer idSala;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JTextField fieldNome;
    private JComboBox<TipoSalaEnum> tipo;
    private JList<PredioDTO> listPredios;
    private JScrollPane scrollPredios;
    private JList<DepartamentoDTO> listDepartamentos;
    private JScrollPane scrollDepartamentos;

    /**
     * @param sala Salas cujos valores serão exibidos inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova sala (true) ou para atualização de uma sala existente
     *        (false).
     */
    public SalaSwingView(final JFrame owner, final SalaDTO sala,
            final boolean isAdicionar, final List<PredioDTO> predios,
            final List<DepartamentoDTO> departamentos) {

        super(owner, isAdicionar ? "Adicionar sala" : "Alterar sala",
                ModalityType.APPLICATION_MODAL);
        this.isAdicionar = isAdicionar;
        initialize();
        updateSala(sala, predios, departamentos);
    }

    private void initialize() {
        setBounds(0, 0, 500, 400);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 100, 0, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowHeights = new int[] { 30, 0, 0, 0, 0, 30, 0, 30, 0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0,
                0.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

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

        final JLabel lblTipo = new JLabel("Tipo:");
        final GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.gridx = 1;
        gbc_lblTipo.gridy = 2;
        getContentPane().add(lblTipo, gbc_lblTipo);

        tipo = new JComboBox<TipoSalaEnum>();
        tipo.setModel(
                new DefaultComboBoxModel<TipoSalaEnum>(TipoSalaEnum.values()));
        final GridBagConstraints gbc_tipo = new GridBagConstraints();
        gbc_tipo.insets = new Insets(0, 0, 5, 5);
        gbc_tipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_tipo.gridx = 2;
        gbc_tipo.gridy = 2;
        getContentPane().add(tipo, gbc_tipo);

        final ListCellRenderer<? super PredioDTO> predioListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 337686638148057981L;

            @Override
            public Component getListCellRendererComponent(final JList<?> list,
                    final Object value, final int index,
                    final boolean isSelected, final boolean cellHasFocus) {
                if (value == null) {
                    return super.getListCellRendererComponent(list, "Nenhum",
                            index, isSelected, cellHasFocus);
                } else {
                    final String nome = ((PredioDTO) value).getNome();
                    return super.getListCellRendererComponent(list, nome, index,
                            isSelected, cellHasFocus);
                }
            }
        };

        final ListCellRenderer<? super DepartamentoDTO> deptListRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 437686638148057981L;

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

        final JLabel lblPrdio = new JLabel("Prédio:");
        final GridBagConstraints gbc_lblPrdio = new GridBagConstraints();
        gbc_lblPrdio.anchor = GridBagConstraints.EAST;
        gbc_lblPrdio.insets = new Insets(0, 0, 5, 5);
        gbc_lblPrdio.gridx = 1;
        gbc_lblPrdio.gridy = 3;
        getContentPane().add(lblPrdio, gbc_lblPrdio);

        scrollPredios = new JScrollPane();
        final GridBagConstraints gbc_scrollPanePredios = new GridBagConstraints();
        gbc_scrollPanePredios.fill = GridBagConstraints.BOTH;
        gbc_scrollPanePredios.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPanePredios.gridx = 2;
        gbc_scrollPanePredios.gridy = 3;
        getContentPane().add(scrollPredios, gbc_scrollPanePredios);

        final JLabel lblDepartamento = new JLabel("DepartamentoDTO:");
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
        listDepartamentos.setModel(new DefaultListModel<DepartamentoDTO>());
        listDepartamentos.setCellRenderer(deptListRenderer);
        scrollDepartamentos
                .setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollDepartamentos.setViewportView(listDepartamentos);

        listPredios = new JList<PredioDTO>();
        listPredios.setModel(new DefaultListModel<PredioDTO>());
        listPredios.setCellRenderer(predioListRenderer);
        scrollPredios.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        scrollPredios.setViewportView(listPredios);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 6;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 6;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    @Override
    public void updateSala(final SalaDTO sala, final List<PredioDTO> predios,
            final List<DepartamentoDTO> departamentos) {
        if (sala == null) {
            throw new NullPointerException();
        }

        // O ID da sala sendo exibido é armazenado para que seja
        // possível
        // retorná-lo na hora de salvar a sala.
        idSala = sala.getId();

        fieldNome.setText(sala.getNome());
        tipo.setSelectedItem(sala.getTipo());

        // Exibe os predios na lista de predios e seleciona o atual, se
        // tiver, ou 'Nenhum', se não tiver.
        final PredioDTO predio = sala.getPredio();
        final DefaultListModel<PredioDTO> listPrediosModel = (DefaultListModel<PredioDTO>) listPredios
                .getModel();
        listPrediosModel.clear();
        if (predio == null) {
            listPrediosModel.add(0, null);
            listPredios.setSelectedIndex(0);
        } else {
            listPrediosModel.add(0, predio);
            listPredios.setSelectedIndex(0);
        }
        if (predios != null) {
            for (final PredioDTO p : predios) {
                if (predio == null || !p.getId().equals(predio.getId())) {
                    listPrediosModel.addElement(p);
                }
            }
        }

        // Exibe os departamentos na lista de departamentos e seleciona o atual,
        // se
        // tiver, ou 'Nenhum', se não tiver.
        final DepartamentoDTO departamento = sala.getDepartamento();
        final DefaultListModel<DepartamentoDTO> listDeptsModel = (DefaultListModel<DepartamentoDTO>) listDepartamentos
                .getModel();
        listDeptsModel.clear();
        if (departamento == null) {
            listDeptsModel.add(0, null);
            listDepartamentos.setSelectedIndex(0);
        } else {
            listDeptsModel.add(0, departamento);
            listDepartamentos.setSelectedIndex(0);
        }
        if (departamentos != null) {
            for (final DepartamentoDTO p : departamentos) {
                if (departamento == null
                        || !p.getId().equals(departamento.getId())) {
                    listDeptsModel.addElement(p);
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
        final String messageCompleta = isAdicionar ? "SalaDTO adicionada!"
                : "SalaDTO alterada!";
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
    public void close() {
        super.setVisible(false);
        super.dispose();
    }

    @Override
    public SalaDTO getSala() {
        final SalaDTO sala = new SalaDTO();
        sala.setId(idSala);
        sala.setNome(fieldNome.getText());
        sala.setTipo((TipoSalaEnum) tipo.getSelectedItem());
        sala.setPredio(listPredios.getSelectedValue() == null ? null
                : listPredios.getSelectedValue());
        sala.setDepartamento(listDepartamentos.getSelectedValue() == null ? null
                : listDepartamentos.getSelectedValue());
        return sala;
    }

}
