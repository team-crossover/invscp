package com.github.nelsonwilliam.invscp.util;

/**
 * Classe que é instanciada para lidar com cada cliente conectado ao servidor.
 * Sua função é receber as requisições do cliente (feitas em {@link Client}) e
 * responder com os objetos apropriados.
 */
public class ClientHandler extends Thread {

    // TODO Por enquanto não é utilizado pois o cliente-servidor não foi
    // implementado. Futuramente, a classe main do projeto servidor deve ser
    // capaz de instanciar uma nova ClientHandler para cada cliente conectado, e
    // mantê-lo recebendo requisições até que o cliente finalize a conexão ou
    // seja atingido um timeout.

}
