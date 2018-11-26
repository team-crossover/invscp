package com.github.nelsonwilliam.invscp.server.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;

import com.github.nelsonwilliam.invscp.shared.util.Request;
import com.github.nelsonwilliam.invscp.shared.util.Response;

/**
 * Classe que é instanciada para lidar com cada cliente conectado ao servidor.
 * Sua função é receber as requisições do cliente e responder com os objetos
 * apropriados.
 */
public class ClientHandler extends Thread {

    /**
     * Responsável por lidar com os pedidos recebidos.
     */
    private final Server server;
    private final Socket socket;
    private final int clientNumber;

    public ClientHandler(final Socket socket, final int clientNumber) {
        server = new Server();
        this.socket = socket;
        this.clientNumber = clientNumber;

        System.out.println("Conexão estabelecida com o cliente #" + clientNumber
                + " em " + socket);
    }

    @Override
    public void run() {
        try {
            final ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream());
            final ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());

            out.writeObject("Conexão estabelecida com o servidor, cliente #"
                    + clientNumber + ".");

            while (true) {
                final Object input = in.readObject();
                if (input != null && input instanceof Request) {
                    final Request request = (Request) input;
                    if (ServerSettings.LOG_REQUESTS) {
                        System.out
                                .println(LocalTime.now() + ": #" + clientNumber
                                        + " requested "
                                + request.getTipo());
                    }

                    final Response response = server.receive(request);
                    out.writeObject(response);
                } else {
                    final Response response =
                            new Response(new IllegalArgumentException(
                                    "Pedido vazio ou em formato inválido."));
                    out.writeObject(response);
                }
            }
        } catch (final EOFException e) {

        } catch (final Exception e) {
            System.out.println(
                    "Erro ao lidar com o cliente # " + clientNumber + ":");
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (final IOException e) {
                System.out.println("Não foi possível fechar um socket.");
            }
            System.out.println("Conexão com #" + clientNumber + " encerrada.");
        }
    }

}
