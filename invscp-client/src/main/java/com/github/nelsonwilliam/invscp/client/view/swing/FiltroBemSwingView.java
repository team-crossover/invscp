package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import com.github.nelsonwilliam.invscp.client.view.FiltroBemView;
import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FiltroBemDTO;

public class FiltroBemSwingView  extends JDialog implements FiltroBemView {

    private static final long serialVersionUID = -276032841299039698L;

    private JButton btnConfirmar;

    public FiltroBemSwingView(final JFrame owner) {
        super(owner, "Filtrar bens", ModalityType.APPLICATION_MODAL);
        initialize();
    }

    private void initialize() {
        setBounds(0, 0, 300, 200);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
        gridBagLayout.columnWeights =
                new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights =
                new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        getContentPane().setLayout(gridBagLayout);

        btnConfirmar = new JButton("Confirmar");
        final GridBagConstraints gbc_btnConfirmar = new GridBagConstraints();
        gbc_btnConfirmar.insets = new Insets(0, 0, 0, 5);
        gbc_btnConfirmar.gridx = 1;
        gbc_btnConfirmar.gridy = 2;
        getContentPane().add(btnConfirmar, gbc_btnConfirmar);
    }

    @Override
    public void addConfirmarListener(final ActionListener newListener) {
        btnConfirmar.addActionListener(newListener);
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
    public void updateFiltro(final FiltroBemDTO newFiltro,
            final List<DepartamentoDTO> newDepartamentos) {
        // TODO Auto-generated method stub
    }

    @Override
    public FiltroBemDTO getFiltro() {
        final FiltroBemDTO filtro = new FiltroBemDTO();
        // TODO Auto-generated method stub
        return filtro;
    }

}
