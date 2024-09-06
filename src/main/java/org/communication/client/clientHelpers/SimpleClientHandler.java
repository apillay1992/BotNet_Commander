package org.communication.client.clientHelpers;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import org.communication.client.Client;
import org.communication.server.serverHelpers.robotModels.*;

import static org.communication.server.serverHelpers.headers.DisplayHeaders.*;
import static org.communication.server.serverHelpers.serverHandler.SimpleServer.validCommands;
import static org.communication.server.serverHelpers.world.Config.*;
//import static org.communication.server.serverHelpers.serverHandler.SimpleServer.validCommands;

/**
 * The SimpleClientHandler class handles client interactions, including
 * sending responses to the client, processing JSON data, and managing a list
 * of robot models.
 */
public class SimpleClientHandler {

    private PrintStream out;
    private Gson gson;
    private ArrayList<String> robotModels;
    private boolean robotLaunched;

    /**
     * Constructs a SimpleClientHandler with the specified PrintStream for output,
     * and initializes the Gson object and the list of robot models.
     *
     * @param out The PrintStream used for sending responses to the client.
     */
    public SimpleClientHandler(PrintStream out, Gson gson, ArrayList<String> robotModels) {
        this.out = out;
        this.gson = gson;
        this.robotModels = robotModels;
        this.robotLaunched = false;
    }

    /**
     * Handles user input commands from the scanner and processes them accordingly.
     * Commands include launching actions and handling valid commands based on input parts.
     */
//    public void handleUserInput(Scanner sc) {
//        while (Client.keepRunning) {
//            String userInput = sc.nextLine().toLowerCase();
//            String[] parts = userInput.split(" ");
//
//            if (userInput.startsWith("quit")) {
//                System.out.println(RED_BRIGHT + "Exiting game..." + RED_BRIGHT);
//                System.exit(0);
//            }
//
//            try {
//                if (!Client.repairing && !Client.reloading) {
//                    if (parts[0].equalsIgnoreCase("launch") && parts.length == 3) {
//                        handleLaunchCommand(parts);
//                    } else if(robotLaunched){
//                        if (validCommands.contains(parts[0])){
//                            handleValidCommand(parts);
//                        }else {
//                            displayInvalidCommandMessage(userInput);
//                        }
//                    }else {
//                        System.out.println(RED_BRIGHT + "Please launch a robot to start!" + RESET);
//                    }
//                } else {
//                    System.out.println(Client.reloading ? YELLOW_BRIGHT + "Busy Reloading..." + RESET : YELLOW_BRIGHT + "Busy Repairing..." + RESET);
//                }
//            } catch (Exception e) {
//                displayInvalidCommandMessage(userInput);
//            }
//        }
//    }




}
