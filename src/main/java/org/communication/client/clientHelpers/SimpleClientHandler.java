package org.communication.client.clientHelpers;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import org.communication.client.Client;
import org.communication.server.serverHelpers.robotModels.*;

import static org.communication.server.serverHelpers.headers.DisplayHeaders.*;
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
}
