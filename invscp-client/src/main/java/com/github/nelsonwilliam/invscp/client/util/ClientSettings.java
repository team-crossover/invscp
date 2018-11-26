package com.github.nelsonwilliam.invscp.client.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyException;
import java.util.Properties;

import com.github.nelsonwilliam.invscp.client.view.ViewFactory.ViewImplementation;
import com.github.nelsonwilliam.invscp.shared.util.Relatorios;
import com.github.nelsonwilliam.invscp.shared.util.Resources;

public class ClientSettings {

    private static String serverHost;

    private static int serverPort;

    private static ViewImplementation viewImpl = ViewImplementation.SWING;

    public static void readSettings() throws IOException, KeyException {
        final File file = new File("client.properties");
        if (!file.isFile()) {
            createDefault(file);
            System.out.println("Arquivo de configurações padrão criado em "
                    + file.getAbsolutePath());
        }

        readFile(file);
        System.out
                .println("Configurações obtidas do arquivo de configuração em "
                        + file.getAbsolutePath());
    }

    private static void createDefault(final File file) throws IOException {
        final String defaultProp =
                Resources.readResource("properties/client-default.properties");
        Relatorios.salvar(file.getPath(), defaultProp);
    }

    private static void readFile(final File file)
            throws KeyException, IOException {

        final FileInputStream stream = new FileInputStream(file);
        final Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        if (properties.containsKey("serverHost")) {
            serverHost = properties.getProperty("serverHost");
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: serverHost");
        }

        if (properties.containsKey("serverPort")) {
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: serverPort");
        }

        if (properties.containsKey("viewImpl")) {
            viewImpl = ViewImplementation
                    .valueOf(properties.getProperty("viewImpl"));
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: viewImpl");
        }
    }

    /**
     * Obtém o valor atual de viewImpl
     *
     * @return O valor atual de viewImpl.
     */
    public static final ViewImplementation getViewImpl() {
        return viewImpl;
    }

    /**
     * Obtém o valor atual de serverHost
     *
     * @return O valor atual de serverHost.
     */
    public static final String getServerHost() {
        return serverHost;
    }

    /**
     * Obtém o valor atual de serverPort
     *
     * @return O valor atual de serverPort.
     */
    public static final int getServerPort() {
        return serverPort;
    }

}
