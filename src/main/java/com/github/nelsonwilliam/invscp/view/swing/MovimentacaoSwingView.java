package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.EventoMovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.LocalizacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.MovimentacaoDTO;
import com.github.nelsonwilliam.invscp.model.dto.PredioDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;
import com.github.nelsonwilliam.invscp.model.enums.EtapaMovEnum;
import com.github.nelsonwilliam.invscp.util.Client;
import com.github.nelsonwilliam.invscp.view.MovimentacaoView;

public class MovimentacaoSwingView extends JDialog implements MovimentacaoView {

    private static final long serialVersionUID = 2636535013435748325L;

    private final boolean isAdicionar;

    private Integer idMovimentacao;
    private BemDTO bem;
    private SalaDTO salaOrigem;
    private EtapaMovEnum etapa;
    private List<EventoMovimentacaoDTO> eventos;
    private String numGuiaTransporte;

    private LocalizacaoDTO selectedDestinoLoca;
    private PredioDTO selectedDestinoPredio;
    private SalaDTO selectedDestinoSala;

    private JButton btnConfirmar;
    private JButton btnCancelar;
    private JLabel lblEtapa;
    private JLabel lblBem;
    private JLabel fieldEtapa;
    private JLabel lblOrigem;
    private JLabel lblDestino;
    private JLabel lblLocalizao;
    private JLabel lblPrdio;
    private JLabel lblSala;
    private JLabel lblLocalizao_1;
    private JLabel lblPrdio_1;
    private JLabel lblSala_1;
    private JLabel fieldOrigemLoca;
    private JLabel fieldOrigemPredio;
    private JLabel fieldOrigemSala;
    private JLabel fieldBem;
    private JComboBox<LocalizacaoDTO> comboBoxDestinoLoca;
    private JComboBox<PredioDTO> comboBoxDestinoPredio;
    private JComboBox<SalaDTO> comboBoxDestinoSala;
    private JLabel lblNoGuiaTransporte;
    private JLabel fieldGuiaTransporte;
    private JLabel lblTipo;
    private JLabel fieldTipo;
    private JLabel lblDepartamento;
    private JLabel fieldDeptOrigem;
    private JLabel fieldDeptDestino;
    private JLabel lblDepartamento_1;
    private JLabel lblCidade;
    private JLabel fieldCidadeOrigem;
    private JLabel lblCidade_1;
    private JLabel fieldCidadeDestino;

    /**
     * @param movimentacao Movimentações cujos valores serão exibidos
     *        inicialmente.
     * @param isAdicionar Indica se a janela que será exibida será para adição
     *        de uma nova ordem de serviço (true) ou para atualização de uma
     *        movimentação existente (false).
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
        setBounds(0, 0, 500, 500);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 30, 102, 0, 30 };
        gridBagLayout.rowHeights = new int[] { 30, 0, 0, 0, 0, 30, 0, 0, 0, 0,
                0, 30, 0, 0, 0, 0, 0, 30, 0, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.addActionListener(arg0 -> {
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener((final ActionEvent e) -> {
            close();
        });

        lblEtapa = new JLabel("Etapa:");
        final GridBagConstraints gbc_lblEtapa = new GridBagConstraints();
        gbc_lblEtapa.anchor = GridBagConstraints.EAST;
        gbc_lblEtapa.insets = new Insets(0, 0, 5, 5);
        gbc_lblEtapa.gridx = 1;
        gbc_lblEtapa.gridy = 1;
        getContentPane().add(lblEtapa, gbc_lblEtapa);

        fieldEtapa = new JLabel("");
        final GridBagConstraints gbc_fieldEtapa = new GridBagConstraints();
        gbc_fieldEtapa.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldEtapa.insets = new Insets(0, 0, 5, 5);
        gbc_fieldEtapa.gridx = 2;
        gbc_fieldEtapa.gridy = 1;
        getContentPane().add(fieldEtapa, gbc_fieldEtapa);

        lblBem = new JLabel("Bem:");
        final GridBagConstraints gbc_lblBem = new GridBagConstraints();
        gbc_lblBem.anchor = GridBagConstraints.EAST;
        gbc_lblBem.insets = new Insets(0, 0, 5, 5);
        gbc_lblBem.gridx = 1;
        gbc_lblBem.gridy = 2;
        getContentPane().add(lblBem, gbc_lblBem);

        fieldBem = new JLabel("");
        final GridBagConstraints gbc_fieldBem = new GridBagConstraints();
        gbc_fieldBem.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldBem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldBem.gridx = 2;
        gbc_fieldBem.gridy = 2;
        getContentPane().add(fieldBem, gbc_fieldBem);

        lblTipo = new JLabel("Tipo:");
        final GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
        gbc_lblTipo.gridx = 1;
        gbc_lblTipo.gridy = 3;
        getContentPane().add(lblTipo, gbc_lblTipo);

        fieldTipo = new JLabel("");
        final GridBagConstraints gbc_fieldTipo = new GridBagConstraints();
        gbc_fieldTipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldTipo.insets = new Insets(0, 0, 5, 5);
        gbc_fieldTipo.gridx = 2;
        gbc_fieldTipo.gridy = 3;
        getContentPane().add(fieldTipo, gbc_fieldTipo);

        lblNoGuiaTransporte = new JLabel("Nº da guia transporte:");
        final GridBagConstraints gbc_lblNoGuiaTransporte =
                new GridBagConstraints();
        gbc_lblNoGuiaTransporte.insets = new Insets(0, 0, 5, 5);
        gbc_lblNoGuiaTransporte.gridx = 1;
        gbc_lblNoGuiaTransporte.gridy = 4;
        getContentPane().add(lblNoGuiaTransporte, gbc_lblNoGuiaTransporte);

        fieldGuiaTransporte = new JLabel("");
        final GridBagConstraints gbc_fieldGuiaTransporte =
                new GridBagConstraints();
        gbc_fieldGuiaTransporte.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldGuiaTransporte.insets = new Insets(0, 0, 5, 5);
        gbc_fieldGuiaTransporte.gridx = 2;
        gbc_fieldGuiaTransporte.gridy = 4;
        getContentPane().add(fieldGuiaTransporte, gbc_fieldGuiaTransporte);

        lblOrigem = new JLabel("Origem");
        lblOrigem.setHorizontalAlignment(SwingConstants.CENTER);
        lblOrigem.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
        final GridBagConstraints gbc_lblOrigem = new GridBagConstraints();
        gbc_lblOrigem.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblOrigem.gridwidth = 2;
        gbc_lblOrigem.insets = new Insets(0, 0, 5, 5);
        gbc_lblOrigem.gridx = 1;
        gbc_lblOrigem.gridy = 5;
        getContentPane().add(lblOrigem, gbc_lblOrigem);

        lblLocalizao = new JLabel("Localização:");
        final GridBagConstraints gbc_lblLocalizao = new GridBagConstraints();
        gbc_lblLocalizao.anchor = GridBagConstraints.EAST;
        gbc_lblLocalizao.insets = new Insets(0, 0, 5, 5);
        gbc_lblLocalizao.gridx = 1;
        gbc_lblLocalizao.gridy = 6;
        getContentPane().add(lblLocalizao, gbc_lblLocalizao);

        fieldOrigemLoca = new JLabel();
        final GridBagConstraints gbc_comboBoxOrigemLoca =
                new GridBagConstraints();
        gbc_comboBoxOrigemLoca.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxOrigemLoca.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxOrigemLoca.gridx = 2;
        gbc_comboBoxOrigemLoca.gridy = 6;
        getContentPane().add(fieldOrigemLoca, gbc_comboBoxOrigemLoca);

        lblPrdio = new JLabel("Prédio:");
        final GridBagConstraints gbc_lblPrdio = new GridBagConstraints();
        gbc_lblPrdio.anchor = GridBagConstraints.EAST;
        gbc_lblPrdio.insets = new Insets(0, 0, 5, 5);
        gbc_lblPrdio.gridx = 1;
        gbc_lblPrdio.gridy = 7;
        getContentPane().add(lblPrdio, gbc_lblPrdio);

        fieldOrigemPredio = new JLabel();
        final GridBagConstraints gbc_comboBoxOrigemPredio =
                new GridBagConstraints();
        gbc_comboBoxOrigemPredio.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxOrigemPredio.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxOrigemPredio.gridx = 2;
        gbc_comboBoxOrigemPredio.gridy = 7;
        getContentPane().add(fieldOrigemPredio, gbc_comboBoxOrigemPredio);

        lblSala = new JLabel("Sala:");
        final GridBagConstraints gbc_lblSala = new GridBagConstraints();
        gbc_lblSala.anchor = GridBagConstraints.EAST;
        gbc_lblSala.insets = new Insets(0, 0, 5, 5);
        gbc_lblSala.gridx = 1;
        gbc_lblSala.gridy = 8;
        getContentPane().add(lblSala, gbc_lblSala);

        fieldOrigemSala = new JLabel();
        final GridBagConstraints gbc_comboBoxOrigemSala =
                new GridBagConstraints();
        gbc_comboBoxOrigemSala.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxOrigemSala.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxOrigemSala.gridx = 2;
        gbc_comboBoxOrigemSala.gridy = 8;
        getContentPane().add(fieldOrigemSala, gbc_comboBoxOrigemSala);

        lblDepartamento = new JLabel("Departamento:");
        final GridBagConstraints gbc_lblDepartamento = new GridBagConstraints();
        gbc_lblDepartamento.anchor = GridBagConstraints.EAST;
        gbc_lblDepartamento.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartamento.gridx = 1;
        gbc_lblDepartamento.gridy = 9;
        getContentPane().add(lblDepartamento, gbc_lblDepartamento);

        fieldDeptOrigem = new JLabel("");
        final GridBagConstraints gbc_fieldDeptOrigem = new GridBagConstraints();
        gbc_fieldDeptOrigem.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldDeptOrigem.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDeptOrigem.gridx = 2;
        gbc_fieldDeptOrigem.gridy = 9;
        getContentPane().add(fieldDeptOrigem, gbc_fieldDeptOrigem);

        lblCidade = new JLabel("Cidade:");
        final GridBagConstraints gbc_lblCidade = new GridBagConstraints();
        gbc_lblCidade.anchor = GridBagConstraints.EAST;
        gbc_lblCidade.insets = new Insets(0, 0, 5, 5);
        gbc_lblCidade.gridx = 1;
        gbc_lblCidade.gridy = 10;
        getContentPane().add(lblCidade, gbc_lblCidade);

        fieldCidadeOrigem = new JLabel();
        final GridBagConstraints gbc_lblC = new GridBagConstraints();
        gbc_lblC.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblC.insets = new Insets(0, 0, 5, 5);
        gbc_lblC.gridx = 2;
        gbc_lblC.gridy = 10;
        getContentPane().add(fieldCidadeOrigem, gbc_lblC);

        lblDestino = new JLabel("Destino");
        lblDestino.setHorizontalAlignment(SwingConstants.CENTER);
        lblDestino.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
        final GridBagConstraints gbc_lblDestino = new GridBagConstraints();
        gbc_lblDestino.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblDestino.gridwidth = 2;
        gbc_lblDestino.insets = new Insets(0, 0, 5, 5);
        gbc_lblDestino.gridx = 1;
        gbc_lblDestino.gridy = 11;
        getContentPane().add(lblDestino, gbc_lblDestino);

        lblLocalizao_1 = new JLabel("Localização:");
        final GridBagConstraints gbc_lblLocalizao_1 = new GridBagConstraints();
        gbc_lblLocalizao_1.anchor = GridBagConstraints.EAST;
        gbc_lblLocalizao_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblLocalizao_1.gridx = 1;
        gbc_lblLocalizao_1.gridy = 12;
        getContentPane().add(lblLocalizao_1, gbc_lblLocalizao_1);

        final ListCellRenderer<? super LocalizacaoDTO> localizacaoListRenderer =
                new DefaultListCellRenderer() {
                    private static final long serialVersionUID =
                            337686638048057981L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Nenhuma", index, isSelected, cellHasFocus);
                        } else {
                            final String nome =
                                    ((LocalizacaoDTO) value).getNome();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        comboBoxDestinoLoca = new JComboBox<LocalizacaoDTO>();
        comboBoxDestinoLoca.setRenderer(localizacaoListRenderer);
        comboBoxDestinoLoca.addActionListener(e -> {
            final Object novoItem = comboBoxDestinoLoca.getSelectedItem();
            if (selectedDestinoLoca == null) {
                if (novoItem == null) {
                    return;
                } else {
                    updateDestinoLocalizacao((LocalizacaoDTO) novoItem);
                }
            } else {
                if (novoItem == null) {
                    updateDestinoLocalizacao(null);
                } else {
                    if (!selectedDestinoLoca.equals(novoItem)) {
                        updateDestinoLocalizacao((LocalizacaoDTO) novoItem);
                    }
                }
            }
        });

        final GridBagConstraints gbc_comboBoxDestinoLoca =
                new GridBagConstraints();
        gbc_comboBoxDestinoLoca.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxDestinoLoca.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxDestinoLoca.gridx = 2;
        gbc_comboBoxDestinoLoca.gridy = 12;
        getContentPane().add(comboBoxDestinoLoca, gbc_comboBoxDestinoLoca);

        lblPrdio_1 = new JLabel("Prédio:");
        final GridBagConstraints gbc_lblPrdio_1 = new GridBagConstraints();
        gbc_lblPrdio_1.anchor = GridBagConstraints.EAST;
        gbc_lblPrdio_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblPrdio_1.gridx = 1;
        gbc_lblPrdio_1.gridy = 13;
        getContentPane().add(lblPrdio_1, gbc_lblPrdio_1);

        final ListCellRenderer<? super PredioDTO> predioListRenderer =
                new DefaultListCellRenderer() {
                    private static final long serialVersionUID =
                            437686638048057981L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Nenhum", index, isSelected, cellHasFocus);
                        } else {
                            final String nome = ((PredioDTO) value).getNome();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        comboBoxDestinoPredio = new JComboBox<PredioDTO>();
        comboBoxDestinoPredio.setRenderer(predioListRenderer);
        comboBoxDestinoPredio.addActionListener(e -> {
            final Object novoItem = comboBoxDestinoPredio.getSelectedItem();
            if (selectedDestinoPredio == null) {
                if (novoItem == null) {
                    return;
                } else {
                    updateDestinoPredio((PredioDTO) novoItem);
                }
            } else {
                if (novoItem == null) {
                    updateDestinoPredio(null);
                } else {
                    if (!selectedDestinoPredio.equals(novoItem)) {
                        updateDestinoPredio((PredioDTO) novoItem);
                    }
                }
            }
        });

        final GridBagConstraints gbc_comboBoxDestinoPredio =
                new GridBagConstraints();
        gbc_comboBoxDestinoPredio.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxDestinoPredio.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxDestinoPredio.gridx = 2;
        gbc_comboBoxDestinoPredio.gridy = 13;
        getContentPane().add(comboBoxDestinoPredio, gbc_comboBoxDestinoPredio);

        lblSala_1 = new JLabel("Sala:");
        final GridBagConstraints gbc_lblSala_1 = new GridBagConstraints();
        gbc_lblSala_1.anchor = GridBagConstraints.EAST;
        gbc_lblSala_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblSala_1.gridx = 1;
        gbc_lblSala_1.gridy = 14;
        getContentPane().add(lblSala_1, gbc_lblSala_1);

        final ListCellRenderer<? super SalaDTO> salaListRenderer =
                new DefaultListCellRenderer() {
                    private static final long serialVersionUID =
                            437686638048057981L;

                    @Override
                    public Component getListCellRendererComponent(
                            final JList<?> list, final Object value,
                            final int index, final boolean isSelected,
                            final boolean cellHasFocus) {
                        if (value == null) {
                            return super.getListCellRendererComponent(list,
                                    "Nenhuma", index, isSelected, cellHasFocus);
                        } else {
                            final String nome = ((SalaDTO) value).getNome();
                            return super.getListCellRendererComponent(list,
                                    nome, index, isSelected, cellHasFocus);
                        }
                    }
                };

        comboBoxDestinoSala = new JComboBox<SalaDTO>();
        comboBoxDestinoSala.setRenderer(salaListRenderer);
        comboBoxDestinoSala.addActionListener(e -> {
            final Object novoItem = comboBoxDestinoSala.getSelectedItem();
            if (selectedDestinoSala == null) {
                if (novoItem == null) {
                    return;
                } else {
                    updateDestinoSala((SalaDTO) novoItem);
                }
            } else {
                if (novoItem == null) {
                    updateDestinoSala(null);
                } else {
                    if (!selectedDestinoSala.equals(novoItem)) {
                        updateDestinoSala((SalaDTO) novoItem);
                    }
                }
            }
        });
        final GridBagConstraints gbc_comboBoxDestinoSala =
                new GridBagConstraints();
        gbc_comboBoxDestinoSala.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxDestinoSala.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxDestinoSala.gridx = 2;
        gbc_comboBoxDestinoSala.gridy = 14;
        getContentPane().add(comboBoxDestinoSala, gbc_comboBoxDestinoSala);

        lblDepartamento_1 = new JLabel("Departamento:");
        final GridBagConstraints gbc_lblDepartamento_1 =
                new GridBagConstraints();
        gbc_lblDepartamento_1.anchor = GridBagConstraints.EAST;
        gbc_lblDepartamento_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblDepartamento_1.gridx = 1;
        gbc_lblDepartamento_1.gridy = 15;
        getContentPane().add(lblDepartamento_1, gbc_lblDepartamento_1);

        fieldDeptDestino = new JLabel("");
        final GridBagConstraints gbc_fieldDeptDestino =
                new GridBagConstraints();
        gbc_fieldDeptDestino.fill = GridBagConstraints.HORIZONTAL;
        gbc_fieldDeptDestino.insets = new Insets(0, 0, 5, 5);
        gbc_fieldDeptDestino.gridx = 2;
        gbc_fieldDeptDestino.gridy = 15;
        getContentPane().add(fieldDeptDestino, gbc_fieldDeptDestino);

        lblCidade_1 = new JLabel("Cidade:");
        final GridBagConstraints gbc_lblCidade_1 = new GridBagConstraints();
        gbc_lblCidade_1.anchor = GridBagConstraints.EAST;
        gbc_lblCidade_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblCidade_1.gridx = 1;
        gbc_lblCidade_1.gridy = 16;
        getContentPane().add(lblCidade_1, gbc_lblCidade_1);

        fieldCidadeDestino = new JLabel();
        final GridBagConstraints gbc_lblC_1 = new GridBagConstraints();
        gbc_lblC_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblC_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblC_1.gridx = 2;
        gbc_lblC_1.gridy = 16;
        getContentPane().add(fieldCidadeDestino, gbc_lblC_1);

        final GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.anchor = GridBagConstraints.WEST;
        gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
        gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 18;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        btnConfirmar = new JButton("Confirmar");
        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
        gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
        gbc_btnConfirmar.gridx = 2;
        gbc_btnConfirmar.gridy = 18;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener listener) {
        btnConfirmar.addActionListener(listener);
    }

    private void updateDestinoLocalizacao(final LocalizacaoDTO localizacao) {
        selectedDestinoLoca = localizacao;

        // TODO Não deveria estar usando o Client diretamente. Usar o Presenter
        // para a comunicação com Model...

        // Atualiza a lista de prédios
        final List<PredioDTO> predios =
                localizacao == null ? new ArrayList<PredioDTO>()
                        : Client.requestGetPrediosByLocalizacao(localizacao);
        predios.add(0, null);
        final PredioDTO[] prediosArray = new PredioDTO[predios.size()];
        predios.toArray(prediosArray);
        final DefaultComboBoxModel<PredioDTO> prediosModel =
                new DefaultComboBoxModel<PredioDTO>(prediosArray);
        comboBoxDestinoPredio.setModel(prediosModel);
        comboBoxDestinoPredio.setSelectedIndex(0);
    }

    private void updateDestinoPredio(final PredioDTO predio) {
        selectedDestinoPredio = predio;

        // TODO Não deveria estar usando o Client diretamente. Usar o Presenter
        // para a comunicação com Model...

        // Atualiza a lista de salas
        final List<SalaDTO> salas = predio == null ? new ArrayList<SalaDTO>()
                : Client.requestGetSalasByPredio(predio);
        salas.add(0, null);
        final SalaDTO[] salasArray = new SalaDTO[salas.size()];
        salas.toArray(salasArray);
        final DefaultComboBoxModel<SalaDTO> salasModel =
                new DefaultComboBoxModel<SalaDTO>(salasArray);
        comboBoxDestinoSala.setModel(salasModel);
        comboBoxDestinoSala.setSelectedIndex(0);
    }

    private void updateDestinoSala(final SalaDTO sala) {
        selectedDestinoSala = sala;

        if (salaOrigem == null || salaOrigem.getDepartamento() == null
                || sala == null || sala.getDepartamento() == null) {
            fieldTipo.setText("Desconhecido");
            fieldDeptDestino.setText("Desconhecido");
            fieldCidadeDestino.setText("Desconhecida");
            fieldGuiaTransporte
                    .setText(numGuiaTransporte == null ? "Desconhecido"
                            : numGuiaTransporte);
            return;
        }

        if (salaOrigem.getDepartamento().getId()
                .equals(sala.getDepartamento().getId())) {
            fieldTipo.setText("Interna");
        } else {
            fieldTipo.setText("Externa");
        }

        if (salaOrigem.getPredio().getLocalizacao().getCidade()
                .equals(sala.getPredio().getLocalizacao().getCidade())) {
            fieldGuiaTransporte.setText(
                    numGuiaTransporte == null ? "Nenhum" : numGuiaTransporte);
        } else {
            fieldGuiaTransporte
                    .setText(numGuiaTransporte == null ? "Será gerado"
                            : numGuiaTransporte);
        }

        fieldDeptDestino.setText(sala == null ? "Desconhecido"
                : sala.getDepartamento().getNome());
        fieldCidadeDestino.setText(sala == null ? "Desconhecida"
                : sala.getPredio().getLocalizacao().getCidade() + " - "
                        + sala.getPredio().getLocalizacao().getUf().toString());
    }

    @Override
    public void updateMovimentacao(final MovimentacaoDTO movimentacao) {
        if (movimentacao == null) {
            throw new NullPointerException();
        }

        comboBoxDestinoLoca.setEnabled(isAdicionar);
        comboBoxDestinoPredio.setEnabled(isAdicionar);
        comboBoxDestinoSala.setEnabled(isAdicionar);
        btnConfirmar.setEnabled(isAdicionar);

        idMovimentacao = movimentacao.getId();
        bem = movimentacao.getBem();
        etapa = movimentacao.getEtapa();
        salaOrigem = movimentacao.getSalaOrigem();
        eventos = movimentacao.getEventos();
        numGuiaTransporte = movimentacao.getNumGuiaTransporte();

        fieldEtapa.setText(etapa.getTexto());
        fieldBem.setText(bem.getDescricao());
        fieldOrigemLoca
                .setText(salaOrigem.getPredio().getLocalizacao().getNome());
        fieldOrigemPredio.setText(salaOrigem.getPredio().getNome());
        fieldOrigemSala.setText(salaOrigem.getNome());
        fieldGuiaTransporte.setText(
                numGuiaTransporte == null ? "Nenhum" : numGuiaTransporte);
        fieldDeptOrigem.setText(salaOrigem.getDepartamento().getNome());
        fieldCidadeOrigem.setText(salaOrigem.getPredio().getLocalizacao()
                .getCidade() + " - "
                + salaOrigem.getPredio().getLocalizacao().getUf().toString());

        // TODO Não deveria estar usando o Client diretamente. Usar o Presenter
        // para a comunicação com Model...

        // Atualiza a lista de localizações
        final List<LocalizacaoDTO> locas = Client.requestGetLocalizacoes();
        locas.add(0, null);
        final LocalizacaoDTO[] locasArray = new LocalizacaoDTO[locas.size()];
        locas.toArray(locasArray);
        final DefaultComboBoxModel<LocalizacaoDTO> locasModel =
                new DefaultComboBoxModel<LocalizacaoDTO>(locasArray);
        comboBoxDestinoLoca.setModel(locasModel);
        comboBoxDestinoLoca.setSelectedIndex(0);

        final SalaDTO salaDestino = movimentacao.getSalaDestino();
        if (salaDestino != null) {
            comboBoxDestinoLoca
                    .setSelectedItem(salaDestino.getPredio().getLocalizacao());
            comboBoxDestinoPredio.setSelectedItem(salaDestino.getPredio());
            comboBoxDestinoSala.setSelectedItem(salaDestino);
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
        movimentacao.setBem(bem);
        movimentacao.setEventos(eventos);
        movimentacao.setNumGuiaTransporte(numGuiaTransporte);
        movimentacao.setSalaOrigem(salaOrigem);
        movimentacao.setSalaDestino(
                (SalaDTO) comboBoxDestinoSala.getSelectedItem());
        return movimentacao;
    }

}
