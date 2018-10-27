package com.github.nelsonwilliam.invscp.view.swing;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import com.github.nelsonwilliam.invscp.model.Predio;
import com.github.nelsonwilliam.invscp.view.PrediosView;

public class PrediosSwingView extends JPanel implements PrediosView {

    private static final long serialVersionUID = 5043497852044311548L;

    @Override
    public void addAdicionarPredioListener(ActionListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addDeletarPrediosListener(ActionListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addAlterarPredioListener(ActionListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updatePredio(List<Predio> predio) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Integer> getSelectedPrediosIds() {
        // TODO Auto-generated method stub
        return null;
    }

}
