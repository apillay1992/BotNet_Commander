package org.communication.server.serverHelpers.headers;

import com.google.gson.*;
import org.communication.server.serverHelpers.robot.Position;
import org.communication.server.serverHelpers.world.World;
import org.communication.server.serverHelpers.obstacles.BottomLessPit;
import org.communication.server.serverHelpers.obstacles.Lake;
import org.communication.server.serverHelpers.obstacles.Mountain;
import org.communication.server.serverHelpers.obstacles.Obstacle;
import org.communication.server.serverHelpers.serverHandler.SimpleServer;
import org.communication.server.serverHelpers.robot.Robot;

import java.util.ArrayList;
import java.util.Iterator;

public class DisplayHeaders {

    // High Intensity Colour
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String RESET = "\033[0m";  // Text Reset
    public static ArrayList<Position> launchingSpots = new ArrayList<>();

    /**
     * Displays the world commands menu.
     */
    public static void viewMenu(){
        System.out.println(RED_BRIGHT + "\nWorld Commands" + RESET);
        System.out.println(BLUE_BRIGHT +"'quit'" + RESET + YELLOW_BRIGHT +" - Disconnects all robots and ends the world " + RESET);
        System.out.println(BLUE_BRIGHT +"'robots'" + RESET + YELLOW_BRIGHT +" - Lists all robots including the robot's name and state"+ RESET);
        System.out.println(BLUE_BRIGHT +"'view'" + RESET + YELLOW_BRIGHT +" - displays all the available/acceptable commands"+ RESET);
        System.out.println(BLUE_BRIGHT +"'dump'" + RESET + YELLOW_BRIGHT +" - Displays a representation of the worlds state\n"+ RESET);
    }

    /**
     * Displays the header for the robot connection.
     */
    public static void displayHeaderRobot(){
        System.out.println(RED_BRIGHT + "Connection Successful...\uD83D\uDE0A" + RESET);
        System.out.println(BLUE_BRIGHT + "\n*************************************************" + RESET);
        System.out.println(YELLOW_BRIGHT +"  🤖✨ Welcome to the Amazing Robot World! ✨🤖" + RESET);
        System.out.println(BLUE_BRIGHT + "*************************************************" + RESET);
    }

    /**
     * Displays the general header for the game.
     */
    public static void displayHeader(){
        System.out.println(BLUE_BRIGHT + "\n**********************************************************************" + RESET);
        System.out.println(YELLOW_BRIGHT+ "            🤖✨ Welcome to the Amazing Robot World! ✨🤖" + RESET);
        System.out.println(BLUE_BRIGHT + "**********************************************************************" + RESET);
    }

    /**
     * Displays a message indicating the server is running and waiting for connections.
     */
    public static void displayWaitingForConnections(){
        System.out.println(RED_BRIGHT + "\t\tServer running & waiting for client connections." + RESET);
        System.out.println(BLUE_BRIGHT + "----------------------------------------------------------------------" + RESET);
    }

    /**
     * Displays the help menu with available commands.
     */
    public static void helpMenu() {
        System.out.println(RED_BRIGHT + "\nHelp Menu" + RESET);
        System.out.println(BLUE_BRIGHT + "'launch'" +RESET + YELLOW_BRIGHT +" - launch a new robot into the world"+ RESET);
        System.out.println(BLUE_BRIGHT +"'look'" +RESET + YELLOW_BRIGHT +" - Allows your robot to look around"+ RESET);
        System.out.println(BLUE_BRIGHT +"'state'" +RESET + YELLOW_BRIGHT +"- View the current state of your robot\n"+ RESET);
        System.out.println(BLUE_BRIGHT +"'forward'"+RESET + YELLOW_BRIGHT +" - move the robot forward e.g forward 10 "+ RESET);
        System.out.println(BLUE_BRIGHT +"'back'" +RESET + YELLOW_BRIGHT +"- move the robot backwards e.g back 50"+ RESET);
        System.out.println(BLUE_BRIGHT +"'turn'"+RESET + YELLOW_BRIGHT + " - turn the robot either left or right e.g turn left"+ RESET);
        System.out.println(BLUE_BRIGHT +"'fire'" +RESET + YELLOW_BRIGHT +" - shoot your shot"+ RESET);
    }

    /**
     * Displays the configuration menu for setting the world size.
     */
    public static void configMenu(){
        System.out.println(RED_BRIGHT + "\nLet's start by configuring the world size:"+ RESET);
        System.out.println(RED_BRIGHT + "Please choose an index below:" + RESET);
        System.out.println(YELLOW_BRIGHT + "*Hint*" + RESET + RED_BRIGHT + " - These are coordinates representing (x,y) values" + RESET);
        System.out.println(BLUE_BRIGHT + "1) (-100, 100) X (100, -100) -" + RESET + YELLOW_BRIGHT+ " EASY\n" +RESET +
                BLUE_BRIGHT +"2) (-200, 200) X (200, -200) -"+ RESET + YELLOW_BRIGHT+ " MEDIUM\n" + RESET +
                BLUE_BRIGHT +"3) (-300, 300) X (300, -300) -" + RESET + YELLOW_BRIGHT+ " HARD" + RESET ) ;
    }

    /**
     * Displays the initial game menu.
     */
    public static void displayMenu() {

        System.out.println(YELLOW_BRIGHT + "                   Welcome to your new robot world " +
                "\n        Crafted with a default size of 200 clicks by 200 clicks.\n" +
                "     Each robot has a standard 50-clicks-per-direction view radius!\n" +RESET);

        System.out.println(YELLOW_BRIGHT + "                    Want to customize your world? \n" +
                "               Type 'config' to tweak the settings, or\n" +
                "          Press 'Enter' to embark on your exciting adventure! \n" +
                "                          Press 'q' to quit" + RESET);
    }

    /**
     * Displays the robot statistics and available characters.
     */
    public static void displayRobotStats() {
        System.out.println(YELLOW_BRIGHT + "   \uD83D\uDE0AGet ready to start this exciting journey\uD83D\uDE0A\n" + RESET);
        System.out.println(YELLOW_BRIGHT + "launch a robot to start the game: " + RESET);
        System.out.println(YELLOW_BRIGHT + "\nChoose your Robot Character:" + RESET);
        System.out.println(YELLOW_BRIGHT + "Robot Stats:" + RESET);
        displayRobotStats("1) Blaze", 16, 20);
        displayRobotStats("2) Demolisher", 14, 20);
        displayRobotStats("3) Reaper", 15, 15);
        displayRobotStats("4) Venom", 20, 16);
        displayRobotStats("5) Warpath", 18, 16);
        System.out.println(YELLOW_BRIGHT + "\nTo join the game enter e.g 'launch reaper james'\n" + RESET);
    }

    /**
     * Displays the statistics for a specific robot.
     *
     * @param robotName The name of the robot.
     * @param shield The shield value of the robot.
     * @param shots The number of shots the robot has.
     */
    public static void displayRobotStats(String robotName, int shield, int shots) {
        System.out.println(RED_BRIGHT + robotName + ":" + RESET);
        System.out.println(BLUE_BRIGHT + "   Shield: " + RESET + generateVisualRepresentation(shield, '▧'));
        System.out.println(BLUE_BRIGHT + "   Shots:  " + RESET + generateVisualRepresentation(shots, '✪'));

    }

    /**
     * Generates a visual representation of a value using a specified symbol.
     *
     * @param value The value to be represented.
     * @param symbol The symbol used for the representation.
     * @return A string representing the visual representation.
     */
    public static String generateVisualRepresentation(int value, char symbol) {
        StringBuilder visualRepresentation = new StringBuilder();
        for (int i = 0; i < value; i++) {
            visualRepresentation.append(symbol);
        }
        return visualRepresentation.toString();
    }

    /**
     * Displays the obstacles and robots present in the world.
     */
    public static void displayObstaclesAndRobots(){
        World world = World.getInstance();
        System.out.println(RED_BRIGHT + "The world contains these types of obstacles:" + RESET);
        for (Object obs : world.obstacles){
            if (obs instanceof Obstacle obstacle) {
                String printObstacle =String.format(BLUE_BRIGHT + "- At position " + obstacle.getX() + ", " +  obstacle.getY() + " (to " +  (obstacle.getX()+4) + ", " + (obstacle.getY()+4) +") there is a " +RESET + YELLOW_BRIGHT + "obstacle"+ RESET);
                System.out.println(printObstacle);
            }else if (obs instanceof Mountain mountain) {
                String printObstacle =String.format(BLUE_BRIGHT +"- At position " + mountain.getX() + ", " +  mountain.getY() + " (to " +  (mountain.getX()+4) + ", " + (mountain.getY()+4) +") there is a "+RESET + YELLOW_BRIGHT + "mountain" + RESET);
                System.out.println(printObstacle);
            }else if (obs instanceof Lake lake) {
                String printObstacle =String.format(BLUE_BRIGHT +"- At position " + lake.getX() + ", " +  lake.getY() + " (to " +  (lake.getX()+4) + ", " + (lake.getY()+4) +") there is a "+RESET + YELLOW_BRIGHT + "lake" + RESET);
                System.out.println(printObstacle);
            }else if (obs instanceof BottomLessPit bottomLessPit){
                String printObstacle =String.format(BLUE_BRIGHT +"- At position " + bottomLessPit.getX() + ", " +  bottomLessPit.getY() + " (to " +  (bottomLessPit.getX()+4) + ", " + (bottomLessPit.getY()+4) +") there is a "+RESET + YELLOW_BRIGHT + "bottomless pit" + RESET);
                System.out.println(printObstacle);
            }
        }
        System.out.println(RED_BRIGHT + "\nNumber of robots in the world : " +RESET + YELLOW_BRIGHT + SimpleServer.robotObjects.size() + RESET);
        if (!SimpleServer.robotObjects.isEmpty()) {
            for (Robot robot: SimpleServer.robotObjects) {
                System.out.println(BLUE_BRIGHT + "Bot " + RESET + YELLOW_BRIGHT +  robot.getName() + RESET + BLUE_BRIGHT +" is at " +RESET + YELLOW_BRIGHT + robot.coordinatePosition() + RESET  + BLUE_BRIGHT +" facing "+ RESET+ YELLOW_BRIGHT + robot.getCurrentDirection()+ RESET);
            }
        }
    }

    /**
     * Displays the list of robots with their details.
     */
    public static void listRobots() {
        System.out.println(RED_BRIGHT + "  ***List of Robots*** " + RESET);
        Iterator<Robot> iterator = SimpleServer.robotObjects.iterator();

        while (iterator.hasNext()) {
            Robot robot = iterator.next();

            System.out.println(BLUE_BRIGHT +"Name          :" + RESET + YELLOW_BRIGHT+" <" + robot.getName().toUpperCase() + ">\n"+RESET +
                    BLUE_BRIGHT + "Position      :" + RESET + YELLOW_BRIGHT +" <"  + robot.coordinatePosition() + ">\n" + RESET +
                    BLUE_BRIGHT + "Direction     :" + RESET + YELLOW_BRIGHT +" <"  + robot.getCurrentDirection() + ">\n" + RESET +
                    BLUE_BRIGHT + "Shields       :" + RESET + YELLOW_BRIGHT +" <"  + robot.getState().getShields() + ">\n"+ RESET +
                    BLUE_BRIGHT + "Shots         :" + RESET + YELLOW_BRIGHT +" <"  + robot.getState().getShots() + ">\n" + RESET +
                    BLUE_BRIGHT + "Status        :" + RESET + YELLOW_BRIGHT +" <"  + robot.getState().getStatus() + ">\n" +RESET );
        }
    }

    /**
     * Displays the server response parsed from JSON format.
     *
     * @param serverResponse The server response in JSON format.
     */
    public static void displayServerResponse(String serverResponse) {
        if (serverResponse == null || serverResponse.trim().isEmpty()) {
            System.out.println("Received an empty or null JSON response.");
            return;
        }

        try {
            Gson gson = new Gson();
            JsonElement jsonElement = gson.fromJson(serverResponse, JsonElement.class);

            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Unpack and print data with safety checks
                if (jsonObject.has("result")) {
                    String result = jsonObject.get("result").getAsString();
                    System.out.println(RED_BRIGHT + "Result: " + RESET + YELLOW_BRIGHT + result + RESET);
                }

                JsonObject data = jsonObject.has("data") ? jsonObject.getAsJsonObject("data") : null;
                JsonObject state = jsonObject.has("state") ? jsonObject.getAsJsonObject("state") : null;

                if (data != null) {
                    System.out.println(RED_BRIGHT + "Data:" + RESET);
                    if (data.has("message")) {
                        System.out.println(BLUE_BRIGHT + "  Message    :  " + RESET + YELLOW_BRIGHT +  data.get("message").getAsString() + RESET);
                    }
                    if (data.has("object")) {
                        JsonArray objects = data.getAsJsonArray("object");
                        System.out.println(RED_BRIGHT + "  Objects:" + RESET);
                        for (JsonElement objElement : objects) {
                            JsonObject obj = objElement.getAsJsonObject();
                            String direction = obj.has("direction") ? obj.get("direction").getAsString() : "N/A";
                            String type = obj.has("type") ? obj.get("type").getAsString() : "N/A";
                            int distance = obj.has("distance") ? obj.get("distance").getAsInt() : -1;
                            System.out.println(BLUE_BRIGHT + "    Direction: " + RESET + YELLOW_BRIGHT + direction + BLUE_BRIGHT + ", Type: " + RESET + YELLOW_BRIGHT+ type + BLUE_BRIGHT +", Distance: "+ RESET + YELLOW_BRIGHT + distance + RESET);
                        }
                    }
                    // Other data fields you might want to display
                    if (data.has("repair")) {
                        System.out.println(BLUE_BRIGHT + "  Repair     :  " + RESET + YELLOW_BRIGHT + data.get("repair").getAsString() + RESET);
                    }
                    if (data.has("shields")) {
                        System.out.println(BLUE_BRIGHT +"  Shields    :  "+ RESET + YELLOW_BRIGHT + data.get("shields").getAsInt()+ RESET);
                    }
                    if (data.has("reload")) {
                        System.out.println(BLUE_BRIGHT +"  Reload     :  " + RESET + YELLOW_BRIGHT+ data.get("reload").getAsString()+ RESET);
                    }
                    if (data.has("visibility")) {
                        System.out.println(BLUE_BRIGHT +"  Visibility :  "+ RESET + YELLOW_BRIGHT + data.get("visibility").getAsString()+ RESET);
                    }
                    if (data.has("direction")){
                        System.out.println(BLUE_BRIGHT +"  Direction   :  "+ RESET + YELLOW_BRIGHT + data.get("direction").getAsString()+ RESET);
                    }
                    if (data.has("position")) {
                        System.out.println(BLUE_BRIGHT +"  Position   :  "+ RESET + YELLOW_BRIGHT + data.get("position").getAsString()+ RESET);
                    }
                    if (data.has("distance")){
                        System.out.println(BLUE_BRIGHT +"  Distance   :  "+ RESET + YELLOW_BRIGHT + data.get("distance").getAsString()+ RESET);
                    }
                    if (data.has("robot")){
                        System.out.println(BLUE_BRIGHT + "  Name       :  " + RESET + YELLOW_BRIGHT + data.get("robot").getAsString() + RESET);
                    }
                    if (data.has("state")) {
                        System.out.println(RED_BRIGHT + "  State:" + RESET);
                        JsonObject dataState = data.getAsJsonObject("state");
                        if (dataState.has("position")) {
                            System.out.println(BLUE_BRIGHT +"    Position   :  "+ RESET + YELLOW_BRIGHT + dataState.get("position").getAsString()+ RESET);
                        }
                        if (dataState.has("direction")) {
                            System.out.println(BLUE_BRIGHT +"    Direction  :  "+ RESET + YELLOW_BRIGHT + dataState.get("direction").getAsString()+ RESET);
                        }
                        if (dataState.has("shields")) {
                            System.out.println(BLUE_BRIGHT +"    Shields    :  "+ RESET + YELLOW_BRIGHT + dataState.get("shields").getAsInt()+ RESET);
                        }
                        if (dataState.has("shots")) {
                            System.out.println(BLUE_BRIGHT +"    Shots      :  "+ RESET + YELLOW_BRIGHT + dataState.get("shots").getAsInt()+ RESET);
                        }
                        if (dataState.has("status")) {
                            System.out.println(BLUE_BRIGHT +"    Status     :  "+ RESET + YELLOW_BRIGHT + dataState.get("status").getAsString()+ RESET);
                        }
                    }


                }

                if (state != null) {
                    System.out.println(RED_BRIGHT +"State:" + RESET);
                    if (state.has("position")) {
                        System.out.println(BLUE_BRIGHT +"  Position   :  "+ RESET + YELLOW_BRIGHT + state.get("position").getAsString()+ RESET);
                    }
                    if (state.has("direction")) {
                        System.out.println(BLUE_BRIGHT +"  Direction  :  "+ RESET + YELLOW_BRIGHT + state.get("direction").getAsString()+ RESET);
                    }
                    if (state.has("shields")) {
                        if (state.get("shields").getAsInt() != 0) {
                            System.out.println(BLUE_BRIGHT +"  Shields    :  "+ RESET + YELLOW_BRIGHT + state.get("shields").getAsInt()+ RESET);
                        }
                    }
                    if (state.has("shots")) {
                        System.out.println(BLUE_BRIGHT +"  Shots      :  "+ RESET + YELLOW_BRIGHT + state.get("shots").getAsInt()+ RESET);
                    }
                    if (state.has("status")) {
                        System.out.println(BLUE_BRIGHT +"  Status     :  "+ RESET + YELLOW_BRIGHT + state.get("status").getAsString()+ RESET);
                    }
                }
            } else {
                System.out.println(serverResponse);
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
        }
    }

}
