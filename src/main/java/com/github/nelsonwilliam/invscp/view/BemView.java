package com.github.nelsonwilliam.invscp.view;

import java.awt.event.ActionListener;
import java.util.List;

import com.github.nelsonwilliam.invscp.model.dto.BemDTO;
import com.github.nelsonwilliam.invscp.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.model.dto.GrupoMaterialDTO;
import com.github.nelsonwilliam.invscp.model.dto.SalaDTO;

public interface BemView extends View {
    // ----------------------------------------
    // Métodos para notificar ações do usuário.
    // ----------------------------------------

    void addConfirmarListener(ActionListener listener);

    // -------------------------------------------
    // Métodos para atualizar os valores exibidos.
    // -------------------------------------------

    /**
     * Atualiza todos os valores da janela para exibir os dados do bem passado.
     */
    void updateBem(BemDTO bem, List<DepartamentoDTO> departamentos,
            List<SalaDTO> salas, List<GrupoMaterialDTO> gruposMateriais);

    /**
     * Exibe uma mensagem de erro com a mensagem especificada. Por exemplo, pode
     * ser chamado para avisar que algum campo obrigatório foi deixado vazio.
     */
    void showError(String message);

    /**
     * Exibe uma mensagem de que o item foi alterado/adicionado com sucesso.
     */
    void showSucesso();

    void showInfo(String message);

    /**
     * Fecha a View.
     */
    void close();

    // ---------------------------------------------
    // Métodos para obter os valores de formulários.
    // ---------------------------------------------

    /**
     * Obtém o departamento com os valores atualmente exibidos no formulário que
     * o usuário pode ou não ter alterado.
     */
    BemDTO getBem();
}
