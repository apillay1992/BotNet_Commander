package org.communication.server.serverHelpers.serverHandler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.*;
import org.communication.client.clientHelpers.Request;
import org.communication.server.serverHelpers.obstacles.*;
import org.communication.server.serverHelpers.world.Config;
import org.communication.server.serverHelpers.world.World;
import org.communication.server.serverHelpers.commands.Command;
import org.communication.server.serverHelpers.commands.Fire;
import org.communication.server.serverHelpers.response.Response;
import org.communication.server.serverHelpers.robot.Robot;
import org.communication.server.serverHelpers.robot.State;
import org.communication.server.serverHelpers.robotModels.*;

import static org.communication.server.serverHelpers.headers.DisplayHeaders.*;

public class SimpleServer implements Runnable {

    /**
     * Default port number for the server.
     */
    public static final int PORT = 8000;
    private final BufferedReader in;
    private final PrintStream out;
    public static ArrayList<String> robotNames = new ArrayList<>(); // ArrayList to store robot names
    public static ArrayList<String> validCommands = new ArrayList<>(Arrays.asList("forward", "back", "look", "turn", "state", "fire", "orientation", "reload", "repair")); //ArrayList to store robots valid commands
    public static ArrayList<String> turns = new ArrayList<>(Arrays.asList("left", "right")); //ArrayList to store robots valid commands
    public static CopyOnWriteArrayList<Robot> robotObjects = new CopyOnWriteArrayList<>();
    public static Map<String, Socket> listOfRobotSockets = new HashMap<>();
    Gson gson = new Gson();
    public Socket socket;

    /**
     * Constructor for initializing the server with a socket.
     *
     * @param socket The socket used for communication.
     * @throws IOException If there's an issue with socket input or output streams.
     */
    public SimpleServer(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Main execution logic of the server.
     */
    public void run() {
        World world = World.getInstance();
        Robot robot = null;
        String messageFromClient;

        try{
            while ((messageFromClient = in.readLine()) != null) {

                try{
                    Request request = gson.fromJson(messageFromClient, Request.class);

                    if (request.getCommand().equals("launch")) {
                        String robotName = request.getRobotName();
                        String robotType = request.getArguments()[0];
                        if (!robotNames.contains(robotName)) {
                            robot = new Robot(robotName,robotType);
                            robot.setName(robotName);
                            request.setRobot(robotName);
                            robotNames.add(robotName);
                            int shield = Integer.parseInt(request.getArguments()[1]);
                            int shots = Integer.parseInt(request.getArguments()[2]);
                            System.out.println(RED_BRIGHT + "\n" + request.getRobotName() + " joined the game..." + " at position " + robot.getPosition() + " " + RESET);
                            out.println(sendResponsetoClient(robot, gson, shield, shots));
                            robotObjects.add(robot);
                            world.obstacles.add(robot);
                            listOfRobotSockets.put(robotName, socket);
                        }

                        else {
                            String jsonToClient = errorResponse(robot, gson, "ERROR", "Too many of you in this world");
                            out.println(jsonToClient);
                        }


                    }else if (validCommands.contains(request.getCommand()) && !request.getCommand().equals("look") && !request.getCommand().equals("state") && !request.getCommand().equals("fire") && !request.getCommand().equals("orientation") && !request.getCommand().equals("reload") && !request.getCommand().equals("repair")) {

                        try {
                            if (!turns.contains(request.getArguments()[0])) {
                                String newRobotCommand = request.getCommand() + " " + request.getArguments()[0];
                                Command command = Command.create(newRobotCommand);
                                robot.handleCommand(command);
                                String jsonToClient = successfulResponse(robot, gson, "OK", robot.getState().getShields(), robot.getState().getShots());
                                out.println(jsonToClient);

                            } else if (turns.contains(request.getArguments()[0])) {
                                String newRobotCommand = request.getArguments()[0];
                                Command command = Command.create(newRobotCommand);
                                robot.handleCommand(command);
                                String jsonToClient = successfulResponse(robot, gson, "OK", robot.getState().getShields(), robot.getState().getShots());
                                out.println(jsonToClient);

                            } else {
                                String errorResponse = errorResponse(robot, gson, "ERROR", "Could not parse arguments");
                                out.println(errorResponse);
                            }

                        } catch (IllegalArgumentException | NullPointerException e) {
                            if (e instanceof ConcurrentModificationException){
                                System.out.println(RED_BRIGHT + "A player has been terminated..." + RESET);
                            }
                            String errorResponse = errorResponse(robot, gson, "ERROR", "Could not parse arguments.");
                            out.println(errorResponse);

                        }

                    }else if (validCommands.contains(request.getCommand()) && request.getCommand().equals("fire") && request.getArguments()==null){
                        Command command = Command.create(request.getCommand());
                        robot.handleCommand(command);

                        if (Fire.damagedRobot != null) {
                            // Robot hit something, send hit response
                            String jsonToClient = sendFireResponseHit(Fire.damagedRobot, robot, gson, robot.getState().getShields(), robot.getState().getShots());
                            out.println(jsonToClient);
                        } else {
                            // Robot missed, send miss response
                            String jsonToClient = sendFireResponseMiss(gson, robot.getState().getShots());
                            out.println(jsonToClient);
                        }

                    }else if (request.getCommand().equals("look") && validCommands.contains("look")) {
                        String newRobotCommand = request.getCommand();
                        Command command = Command.create(newRobotCommand);
                        robot.handleCommand(command);
                        String jsonToClient = successfulLookResponse(robot, gson, "OK", robot.getState().getShields(), robot.getState().getShots());
                        out.println(jsonToClient);

                    }else if (request.getCommand().equals("state") && validCommands.contains("state")) {
                        assert robot != null;
                        String jsonToClient = sendStateResponseToClient(robot, gson, robot.getState().getShields(), robot.getState().getShots());
                        out.println(jsonToClient);

                    }else if (request.getCommand().equals("orientation") && validCommands.contains("orientation")) {
                        assert robot != null;
                        String jasonToClient = sendOrientationResponseToClient(robot, gson);
                        out.println(jasonToClient);

                    }else if (request.getCommand().equals("reload") && validCommands.contains("reload")){
                        assert robot != null;
                        String jasonToClient = reloadResponse(robot, gson);
                        out.println(jasonToClient);

                    }else if (request.getCommand().equals("repair") && validCommands.contains("repair")){
                        assert robot != null;
                        String jasonToClient = repairResponse(robot, gson);
                        out.println(jasonToClient);

                    }else {
                        String errorResponse = errorResponse(robot, gson, "ERROR", "Could not parse arguments");
                        out.println(errorResponse);
                    }

                }catch (IllegalArgumentException | NullPointerException e) {
                    String errorResponse = errorResponse(robot, gson, "ERROR", "Could not parse arguments.");
                    out.println(errorResponse);

                }catch (JsonSyntaxException e) {
                    System.out.println("invalid json received!");
                }
            }

        }catch (IOException e){
            if (e instanceof SocketException){
                System.out.println(RED_BRIGHT + "A player has been terminated... bye" + RESET);
            }else {
                e.printStackTrace();
            }
        }

    }

}
