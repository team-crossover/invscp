package com.github.nelsonwilliam.invscp.client.presenter;

import com.github.nelsonwilliam.invscp.client.view.View;

/**
 * Presenters são responsáveis pela ‘conexão’ entre a View e os Models. Cada Presenter é mapeado
 * diretamente a uma interface de View específica (&lt;V&gt;). <br>
 * <br>
 * Seu fluxo padrão é: inscrever-se na View para receber notificações sobre as interações de
 * usuário; para cada interação, fazer as atualizações apropriadas no Model; obter os dados
 * atualizados do Model e utilizar a interface da View para atualizar os dados que são exibidos.
 * <br>
 * <br>
 * Além disso, o Presenter pode controlar o fluxo da aplicação, decidindo quais Views exibir de
 * acordo com o estado atual.
 *
 * @param <V> Tipo da interface de View associada a este Presenter.
 */
public abstract class Presenter<V extends View> {

    protected final V view;

    public Presenter(final V view) {
        this.view = view;
    }

    public V getView() {
        return this.view;
    }

}
