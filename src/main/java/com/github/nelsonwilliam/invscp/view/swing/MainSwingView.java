package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.github.nelsonwilliam.invscp.view.MainView;
import com.github.nelsonwilliam.invscp.view.MenuView;
import com.github.nelsonwilliam.invscp.view.View;

/**
 * Implementação em Swing da InicioView.
 */
public class MainSwingView extends JFrame implements MainView {

	private static final long serialVersionUID = 7053477840132102451L;

	private final MenuView menuView;
	private View selectedView;

	public MainSwingView(MenuView menuView) {
		super();

		this.menuView = menuView;
		initialize();
		updateSelectedView(null);
	}

	private void initialize() {
		setBounds(100, 100, 640, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add((JPanel) menuView, BorderLayout.WEST);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
	}

	@Override
	public void updateSelectedView(View selectedView) {
		if (this.selectedView != null) {
			this.selectedView.setVisible(false);
			getContentPane().remove((JPanel) this.selectedView);
		}

		this.selectedView = selectedView;
		if (selectedView != null) {
			getContentPane().add((JPanel) selectedView, BorderLayout.CENTER);
			selectedView.setVisible(true);
		}

		revalidate();
		repaint();
	}

}
