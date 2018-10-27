package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.github.nelsonwilliam.invscp.model.Localizacao;
import com.github.nelsonwilliam.invscp.view.LocalizacaoView;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import com.github.nelsonwilliam.invscp.model.UFEnum;

public class LocalizacaoSwingView extends JDialog implements LocalizacaoView {

	private static final long serialVersionUID = 5247921139046485177L;
	
	private final boolean isAdicionar;

	private Integer idLocalizacao;

	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JTextField fieldNome;
	private JTextField fieldCidade;
	private JLabel lblCidade;
	private JTextField fieldCEP;
	private JTextField fieldPais;
	private JLabel lblEndereco;
	private JLabel lblCep;
	private JLabel lblUf;
	private JLabel lblPais;
	private JTextField fieldEndereco;
	private JComboBox<UFEnum> comboBox;
	
	/**
	 * @param localizacao
	 *            Localizações cujos valores serão exibidos inicialmente.
	 * 
	 * @param isAdicionar
	 *            Indica se a janela que será exibida será para adição de uma nova
	 *            localização (true) ou para atualização de uma localização
	 *            existente (false).
	 */
	public LocalizacaoSwingView(Window owner, Localizacao localizacao, boolean isAdicionar) {
		super(owner, isAdicionar ? "Adicionar localizacao" : "Alterar localizacao", ModalityType.APPLICATION_MODAL);
		this.isAdicionar = isAdicionar;
		initialize();
		updateLocalizacao(localizacao);
	}

	private void initialize() {
		setBounds(150, 150, 422, 295);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 25, 102, 0, 25, 0 };
		gridBagLayout.rowHeights = new int[] {30, 25, 0, 0, 0, 0, 0, 0, 25, 30, 0};
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		
		lblEndereco = new JLabel("Endereco:");
		GridBagConstraints gbc_lblEndereco = new GridBagConstraints();
		gbc_lblEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndereco.anchor = GridBagConstraints.EAST;
		gbc_lblEndereco.gridx = 1;
		gbc_lblEndereco.gridy = 2;
		getContentPane().add(lblEndereco, gbc_lblEndereco);
		
		fieldEndereco = new JTextField();
		fieldEndereco.setColumns(10);
		GridBagConstraints gbc_txtfldEndereco = new GridBagConstraints();
		gbc_txtfldEndereco.insets = new Insets(0, 0, 5, 5);
		gbc_txtfldEndereco.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtfldEndereco.gridx = 2;
		gbc_txtfldEndereco.gridy = 2;
		getContentPane().add(fieldEndereco, gbc_txtfldEndereco);
		
		lblCep = new JLabel("CEP:");
		GridBagConstraints gbc_lblCep = new GridBagConstraints();
		gbc_lblCep.insets = new Insets(0, 0, 5, 5);
		gbc_lblCep.anchor = GridBagConstraints.EAST;
		gbc_lblCep.gridx = 1;
		gbc_lblCep.gridy = 3;
		getContentPane().add(lblCep, gbc_lblCep);
		
		fieldCEP = new JTextField();
		fieldCEP.setText((String) null);
		fieldCEP.setColumns(10);
		GridBagConstraints gbc_fieldCEP = new GridBagConstraints();
		gbc_fieldCEP.insets = new Insets(0, 0, 5, 5);
		gbc_fieldCEP.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldCEP.gridx = 2;
		gbc_fieldCEP.gridy = 3;
		getContentPane().add(fieldCEP, gbc_fieldCEP);
		
		lblCidade = new JLabel("Cidade:");
		GridBagConstraints gbc_lblCidade = new GridBagConstraints();
		gbc_lblCidade.insets = new Insets(0, 0, 5, 5);
		gbc_lblCidade.anchor = GridBagConstraints.EAST;
		gbc_lblCidade.gridx = 1;
		gbc_lblCidade.gridy = 4;
		getContentPane().add(lblCidade, gbc_lblCidade);
		
		fieldCidade = new JTextField();
		fieldCidade.setText((String) null);
		fieldCidade.setColumns(10);
		GridBagConstraints gbc_fieldCidade = new GridBagConstraints();
		gbc_fieldCidade.insets = new Insets(0, 0, 5, 5);
		gbc_fieldCidade.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldCidade.gridx = 2;
		gbc_fieldCidade.gridy = 4;
		getContentPane().add(fieldCidade, gbc_fieldCidade);
		
		lblUf = new JLabel("UF:");
		GridBagConstraints gbc_lblUf = new GridBagConstraints();
		gbc_lblUf.insets = new Insets(0, 0, 5, 5);
		gbc_lblUf.anchor = GridBagConstraints.EAST;
		gbc_lblUf.gridx = 1;
		gbc_lblUf.gridy = 5;
		getContentPane().add(lblUf, gbc_lblUf);
		
		comboBox = new JComboBox<UFEnum>();
		comboBox.setModel(new DefaultComboBoxModel<UFEnum>(UFEnum.values()));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 5;
		getContentPane().add(comboBox, gbc_comboBox);
		
		lblPais = new JLabel("Pais:");
		GridBagConstraints gbc_lblPais = new GridBagConstraints();
		gbc_lblPais.insets = new Insets(0, 0, 5, 5);
		gbc_lblPais.anchor = GridBagConstraints.EAST;
		gbc_lblPais.gridx = 1;
		gbc_lblPais.gridy = 6;
		getContentPane().add(lblPais, gbc_lblPais);
		
		fieldPais = new JTextField();
		fieldPais.setText((String) null);
		fieldPais.setColumns(10);
		GridBagConstraints gbc_fieldPais = new GridBagConstraints();
		gbc_fieldPais.insets = new Insets(0, 0, 5, 5);
		gbc_fieldPais.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldPais.gridx = 2;
		gbc_fieldPais.gridy = 6;
		getContentPane().add(fieldPais, gbc_fieldPais);

		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancelar.fill = GridBagConstraints.VERTICAL;
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 8;
		getContentPane().add(btnCancelar, gbc_btnCancelar);

		GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
		gbc_btnConfirmar.insets = new Insets(0, 0, 5, 5);
		gbc_btnConfirmar.anchor = GridBagConstraints.EAST;
		gbc_btnConfirmar.fill = GridBagConstraints.VERTICAL;
		gbc_btnConfirmar.gridx = 2;
		gbc_btnConfirmar.gridy = 8;
		getContentPane().add(btnConfirmar, gbc_btnConfirmar);
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}
	
	@Override
	public void addConfirmarListener(ActionListener listener) {
		// btnConfirmar.addActionListener(listener);
		
	}

	@Override
	public void updateLocalizacao(Localizacao localizacao) {
		if (localizacao == null) {
			throw new NullPointerException();
		}

		// O ID do departamento sendo exibido é armazenado para que seja possível
		// retorná-lo na hora de salvar o departamento.
		idLocalizacao = localizacao.getId();

		fieldNome.setText(localizacao.getNome());

	}

	@Override
	public void showError(String message) {
		String titulo = "Erro";
		String messageCompleta = "Erro: " + message;
		JOptionPane.showMessageDialog(this, messageCompleta, titulo, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showSucesso() {
		String titulo = "Sucesso";
		String messageCompleta = isAdicionar ? "Localizacao adicionada!" : "Localizacao alterada!";
		JOptionPane.showMessageDialog(this, messageCompleta, titulo, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void close() {
		super.setVisible(false);
		super.dispose();
	}

	@Override
	public Localizacao getLocalizacao() {
		Localizacao localizacao = new Localizacao();
		localizacao.setId(idLocalizacao);
		localizacao.setNome(fieldNome.getText());
		
		return localizacao;
	}

}
