package com.github.nelsonwilliam.invscp.client.util;

import com.github.nelsonwilliam.invscp.client.view.ViewFactory.ViewImplementation;

public class ClientSettings {

    // TODO Ler estas configurações de um arquivo, em vez de serem hard-coded.

    public static ViewImplementation VIEW_IMPL = ViewImplementation.SWING;

    public static String SERVER_HOST = "localhost";

    public static int SERVER_PORT = 9898;

}
