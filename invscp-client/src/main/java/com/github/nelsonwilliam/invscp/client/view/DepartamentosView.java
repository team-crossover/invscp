package com.github.nelsonwilliam.invscp.client.view;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;

import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FuncionarioDTO;

public interface DepartamentosView extends View {

    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addAdicionarDepartamentoListener(ActionListener listener);

    void addGerarRelatorioListener(ActionListener listener);

    void addDeletarDepartamentosListener(ActionListener listener);

    /**
     * Adiciona o Listener para quando o usuário informar que quer alterar um
     * departamento. Espera-se que quando este evento for invocado, haja
     * exatamente UM departamento selecionado (obtível através do método
     * getSelectedDepartamentosIds.
     */
    void addAlterarDepartamentoListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    void updateDepartamentos(List<DepartamentoDTO> departamentos,
            FuncionarioDTO usuario);

    void showError(String message);

    void showSucesso(String message);

    void showInfo(String message);

    void showConfirmacao(String message, Consumer<Boolean> responseCallback);

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém uma lista com os IDs de todos os departamentos atualmente
     * selecionados na tabela.
     */
    List<Integer> getSelectedDepartamentosIds();

}
