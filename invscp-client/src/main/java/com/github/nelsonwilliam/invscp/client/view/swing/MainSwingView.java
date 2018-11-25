package com.github.nelsonwilliam.invscp.client.view.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.nelsonwilliam.invscp.client.util.Client;
import com.github.nelsonwilliam.invscp.client.view.MainView;
import com.github.nelsonwilliam.invscp.client.view.MenuView;
import com.github.nelsonwilliam.invscp.client.view.View;

/**
 * Implementação em Swing da InicioView.
 */
public class MainSwingView extends JFrame implements MainView {

    private static final long serialVersionUID = 7053477840132102451L;

    private final MenuView menuView;
    private View selectedView;

    public MainSwingView(final MenuView menuView) {
        super();

        this.menuView = menuView;
        initialize();
        updateSelectedView(null);
    }

    private void initialize() {
        setTitle("InvSCP");
        setBounds(0, 0, 800, 600);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent event) {
                try {
                    Client.close();
                    dispose();
                } catch (final IOException e) {
                    e.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }
        });

        final GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 25, 100, 25, 500, 25 };
        gridBagLayout.rowHeights = new int[] { 25, 450, 25 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0 };
        gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0 };
        getContentPane().setLayout(gridBagLayout);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 1;
        gbc.gridy = 1;
        getContentPane().add((JPanel) menuView, gbc);
    }

    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
    }

    @Override
    public void updateSelectedView(final View selectedView) {
        if (this.selectedView != null) {
            this.selectedView.setVisible(false);
            getContentPane().remove((JPanel) this.selectedView);
        }

        this.selectedView = selectedView;
        if (selectedView != null) {
            final GridBagConstraints gbc_1 = new GridBagConstraints();
            gbc_1.anchor = GridBagConstraints.NORTH;
            gbc_1.fill = GridBagConstraints.BOTH;
            gbc_1.gridx = 3;
            gbc_1.gridy = 1;
            gbc_1.gridheight = 1;
            getContentPane().add((JPanel) selectedView, gbc_1);
            selectedView.setVisible(true);
        }

        revalidate();
        repaint();
    }

    @Override
    public void addCloseListener(final Consumer<WindowEvent> listener) {
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(final WindowEvent e)
            {
                listener.accept(e);
            }
        });
    }

}
