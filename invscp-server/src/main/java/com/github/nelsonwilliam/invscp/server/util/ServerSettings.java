package com.github.nelsonwilliam.invscp.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyException;
import java.util.Properties;

import com.github.nelsonwilliam.invscp.shared.util.Relatorios;
import com.github.nelsonwilliam.invscp.shared.util.Resources;

public class ServerSettings {

    private static int serverPort;

    private static String databaseHost;

    private static String databasePort;

    private static String databaseName;

    private static String databaseUser;

    private static String databasePassword;

    private static Integer databaseVersion;

    private static boolean logRequests;

    public static void readSettings() throws IOException, KeyException {
        final File file = new File("server.properties");
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
                Resources.readResource("properties/server-default.properties");
        Relatorios.salvar(file.getPath(), defaultProp);
    }

    private static void readFile(final File file)
            throws KeyException, IOException {

        final FileInputStream stream = new FileInputStream(file);
        final Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        if (properties.containsKey("serverPort")) {
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: serverPort");
        }

        if (properties.containsKey("databaseHost")) {
            databaseHost = properties.getProperty("databaseHost");
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: databaseHost");
        }

        if (properties.containsKey("databasePort")) {
            databasePort = properties.getProperty("databasePort");
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: databasePort");
        }

        if (properties.containsKey("databaseName")) {
            databaseName = properties.getProperty("databaseName");
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: databaseName");
        }

        if (properties.containsKey("databaseUser")) {
            databaseUser = properties.getProperty("databaseUser");
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: databaseUser");
        }

        if (properties.containsKey("databasePassword")) {
            databasePassword = properties.getProperty("databasePassword");
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: databasePassword");
        }

        if (properties.containsKey("databaseVersion")) {
            databaseVersion =
                    Integer.parseInt(properties.getProperty("databaseVersion"));
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: databaseVersion");
        }

        if (properties.containsKey("logRequests")) {
            logRequests = properties.getProperty("logRequests").toLowerCase()
                    .equals("true") ? true : false;
        } else {
            throw new KeyException(
                    "Configuração faltando no arquivo de configurações: logRequests");
        }
    }

    /**
     * Obtém o valor atual de serverPort
     *
     * @return O valor atual de serverPort.
     */
    public static final int getServerPort() {
        return serverPort;
    }

    /**
     * Obtém o valor atual de databaseHost
     *
     * @return O valor atual de databaseHost.
     */
    public static final String getDatabaseHost() {
        return databaseHost;
    }

    /**
     * Obtém o valor atual de databasePort
     *
     * @return O valor atual de databasePort.
     */
    public static final String getDatabasePort() {
        return databasePort;
    }

    /**
     * Obtém o valor atual de databaseName
     *
     * @return O valor atual de databaseName.
     */
    public static final String getDatabaseName() {
        return databaseName;
    }

    /**
     * Obtém o valor atual de databaseUser
     *
     * @return O valor atual de databaseUser.
     */
    public static final String getDatabaseUser() {
        return databaseUser;
    }

    /**
     * Obtém o valor atual de databasePassword
     *
     * @return O valor atual de databasePassword.
     */
    public static final String getDatabasePassword() {
        return databasePassword;
    }

    /**
     * Obtém o valor atual de databaseVersion
     *
     * @return O valor atual de databaseVersion.
     */
    public static final Integer getDatabaseVersion() {
        return databaseVersion;
    }

    /**
     * Obtém o valor atual de logRequests
     *
     * @return O valor atual de logRequests.
     */
    public static final boolean isLogRequests() {
        return logRequests;
    }

}
