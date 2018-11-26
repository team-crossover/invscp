package com.github.nelsonwilliam.invscp.client.view;

import java.awt.event.ActionListener;
import java.util.List;

import com.github.nelsonwilliam.invscp.shared.model.dto.DepartamentoDTO;
import com.github.nelsonwilliam.invscp.shared.model.dto.FiltroBemDTO;

public interface FiltroBemView extends View {

    void addConfirmarListener(ActionListener listener);

    void updateFiltro(FiltroBemDTO filtro,
            final List<DepartamentoDTO> departamentos);

    void close();

    FiltroBemDTO getFiltro();
}
