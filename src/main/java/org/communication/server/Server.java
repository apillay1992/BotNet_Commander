package org.communication.server;

import org.communication.server.serverHelpers.serverHandler.MultiServerHandler;
import org.communication.server.serverHelpers.serverHandler.SimpleServer;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.communication.server.serverHelpers.headers.DisplayHeaders.*;
import static org.communication.server.serverHelpers.headers.DisplayHeaders.displayWaitingForConnections;

/**
 * The main server class responsible for initializing the server, handling user input,
 * and managing client connections.
 */public class Server {

    public static boolean flag;
    public static List<Socket> socketList = new ArrayList<>();

    /**
     * Main method to start the server.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException If an I/O error occurs while creating the server socket or handling connections.
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        displayHeader();
        displayMenu();

        // Create a handler for managing multiple server-related tasks
        MultiServerHandler handler = new MultiServerHandler(socketList);

        handler.handleWorldConfiguration(sc);

        // Open a server socket on the predefined port
        ServerSocket serverSocket = new ServerSocket(SimpleServer.PORT);
        displayWaitingForConnections();
        flag = false;

        // Start a separate thread to handle user input while accepting client connections
        handler.startUserInputThread(sc);
        handler.acceptClientConnections(serverSocket);
    }
}
