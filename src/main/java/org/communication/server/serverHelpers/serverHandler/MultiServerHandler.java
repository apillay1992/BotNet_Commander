//package org.communication.server.serverHelpers.serverHandler;
//
//import org.communication.server.Server;
//import org.communication.server.serverHelpers.world.Config;
//import java.net.*;
//import java.io.*;
//import java.util.List;
//import java.util.Scanner;
//
//import static org.communication.server.serverHelpers.headers.DisplayHeaders.*;
//import static org.communication.server.serverHelpers.serverHandler.SimpleServer.*;
//import static org.communication.server.serverHelpers.serverHandler.SimpleServer.robotObjects;
//
//public class MultiServerHandler {
//
//    private PrintStream out;
//    private List<Socket> socketList;
//
//    /**
//     * Handles multiple client connections and user interactions for the server.
//     */
//    public MultiServerHandler(List<Socket> socketList) {
//        this.socketList = socketList;
//    }
//
//    /**
//     * Constructs a MultiServerHandler with a list of connected sockets.
//     *
//     */
//    public void handleWorldConfiguration(Scanner sc) {
//        String userConfig = sc.nextLine();
//
//        if (userConfig.equalsIgnoreCase("config")) {
//            configMenu();
//            getUserChoiceUpdateCoordinates(sc); // Update coordinates based on user input
//            getUserChoiceUpdateVisibility(sc); // Update visibility based on user input
//
//        } else if (userConfig.equalsIgnoreCase("q")) {
//            System.exit(0);
//        }
//    }
//
//    /**
//     * Starts a user input thread to handle interactive commands and operations.
//     *
//     * @param sc The Scanner object to read user input.
//     */
//    public void startUserInputThread(Scanner sc) {
//        Thread userInputThread = new Thread(() -> {
//            System.out.println(YELLOW_BRIGHT + "Enter 'view' for available commands." + RESET);
//            while (true) {
//                String userInput = sc.nextLine();
//
//                if (userInput.equals("robots")) {
//                    if (robotObjects.isEmpty()) {
//                        System.out.println(RED_BRIGHT + "Sorry, there aren't any robots in the world at the moment!" + RESET);
//                    } else {
//                        listRobots();
//                        System.out.println();
//                    }
//                } else if (userInput.equals("dump")) {
//                    displayObstaclesAndRobots();
//                } else if (userInput.equalsIgnoreCase("view")) {
//                    viewMenu();
//                } else if (userInput.equals("quit")) {
//                    Server.flag = true;
//                    terminateConnections(); // Terminate server connections
//                    sc.close();
//                    System.exit(0);
//                } else {
//                    System.out.println(YELLOW_BRIGHT + "Sorry I did not understand '" + userInput + "'. Enter 'view' for assistance." +RESET);
//                }
//            }
//        });
//        userInputThread.start();
//    }
//
//    //Accepts incoming client connections and handles them in separate threads.
//    public void acceptClientConnections(ServerSocket serverSocket) {
//        while (true) {
//            try {
//                Socket socket = serverSocket.accept();
//                socketList.add(socket);
//                // Create a new task (SimpleServer instance) to handle the client connection
//                Runnable serverTask = new SimpleServer(socket);
//                // Start a new thread to execute the server task, unless termination flag is set
//                if (!Server.flag) {
//                    new Thread(serverTask).start();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Terminates all active client connections by sending a "quit" command to each connected client.
//     * Closes the output stream associated with each socket after sending the command.
//     */
//    private void terminateConnections() {
//        for (Socket eachSocket : socketList) {
//            try (PrintStream out = new PrintStream(eachSocket.getOutputStream())) {
//                out.println("quit");
//                out.flush();
//            } catch (IOException e) {
//                System.out.println(RED_BRIGHT + "Terminating players..." + RESET);
//            }
//        }
//
//        /**
//         * Closes all active sockets in the socketList, terminating client connections.
//         * Prints stack traces for any IOException encountered during socket closure.
//         */
//        for (Socket eachSocket : socketList) {
//            try {
//                eachSocket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Prompts the user to choose a configuration option to update world coordinates based on input.
//     * Handles user input to set specific coordinate values in the Config class for the world's boundaries.
//     */
//    private void getUserChoiceUpdateCoordinates(Scanner sc) {
//        while (true) {
//            try {
//                String input = sc.nextLine();
//                // Update coordinates based on user input
//                if (input.equals("1")) {
//                    Config.setTopLeftX_world(-100);
//                    Config.setTopLeftY_world(100);
//                    Config.setBottomRightX_world(100);
//                    Config.setBottomRightY_world(-100);
//                    break;
//                }else if (input.equals("2")) {
//                    Config.setTopLeftX_world(-200);
//                    Config.setTopLeftY_world(200);
//                    Config.setBottomRightX_world(200);
//                    Config.setBottomRightY_world(-200);
//                    break;
//                }else if (input.equals("3")){
//                    Config.setTopLeftX_world(-300);
//                    Config.setTopLeftY_world(300);
//                    Config.setBottomRightX_world(300);
//                    Config.setBottomRightY_world(-300);
//                    break;
//                } else {
//                    System.out.println(RED_BRIGHT + "Oops! I think you made a mistake. Let's try again!" + RESET);
//                }
//            } catch (NumberFormatException e) {
//                System.out.println(RED_BRIGHT + "Oops! I think you made a mistake. Let's try again!" + RESET);
//            }
//        }
//    }
//
//
//    /**
//     * Prompts the user to enter a visibility constraint for robots and updates the configuration accordingly.
//     * Handles user input to set the visibility constraint in the Config class.
//     */
//    private void getUserChoiceUpdateVisibility(Scanner sc) {
//        System.out.println(RED_BRIGHT + "Enter your robot's visibility constraint e.g. 50 " + RESET);
//        System.out.println(YELLOW_BRIGHT + "*Hint*" + RESET + RED_BRIGHT + " - This will determine how far each robot can see." + RESET);
//        while (true) {
//            String input = sc.nextLine();
//            try {
//                int newVisibility = Integer.parseInt(input);
//                Config.setVisibility(newVisibility); // Update visibility in the Config class
//                break; // Exit the loop after setting the visibility
//            } catch (NumberFormatException e) {
//                System.out.println(RED_BRIGHT + "Oops! I think you made a mistake. Let's try again!" + RESET);
//            }
//        }
//    }
//
//}
