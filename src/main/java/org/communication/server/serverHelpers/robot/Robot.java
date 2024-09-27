package org.communication.server.serverHelpers.robot;

import org.communication.server.serverHelpers.world.Config;
import org.communication.server.serverHelpers.world.World;
import org.communication.server.serverHelpers.commands.Command;
import org.communication.server.serverHelpers.obstacles.Mountain;
import org.communication.server.serverHelpers.obstacles.Obstacle;
import org.communication.server.serverHelpers.serverHandler.SimpleServer;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.*;


public class  Robot {
    private final Position TOP_LEFT = new Position(Config.getTopLeftX_world(), Config.getTopLeftY_world());
    private final Position BOTTOM_RIGHT = new Position(Config.getBottomRightX_world(), Config.getBottomRightY_world());
    public static final Position CENTRE = new Position(0,0);
    private State state;
    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;
    public boolean positionCheck;
    public boolean pathCheck;
    public boolean pathCheckPits;
    private int distance;
    private String robotType;
    public ArrayList<String> obstaclesNorth = new ArrayList<>();
    public ArrayList<String> obstaclesEast = new ArrayList<>();
    public ArrayList<String> obstaclesSouth = new ArrayList<>();
    public ArrayList<String> obstaclesWest = new ArrayList<>();
    public Map<String, Integer> obstacleSteps = new HashMap<>();
    public ArrayList<String> allObstacles = new ArrayList<>();
    private static final Object lock = new Object();
    /**
     * Constructs a new Robot with a given name and type. The Robot is placed at
     * a random position within the world.
     *
     * @param name The name of the Robot.
     * @param robotType The type of the Robot.
     */
    public Robot(String name, String robotType) {
        World world = World.getInstance();
        this.name = name;
        this.status = "NORMAL";
        this.position = randomPosition(this,world);;
        this.currentDirection = Direction.NORTH;
        this.robotType = robotType;
    }

    /**
     * Handles the given command by executing it.
     *
     * @param command The command to be executed.
     * @return True if the command was successfully executed, false otherwise.
     */
    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    /**
     * Updates the direction of the Robot based on the given heading.
     *
     * @param directionHeading The heading to update the direction (true for clockwise, false for counterclockwise).
     */
    public void updateDirection(boolean directionHeading) {
        switch (currentDirection) {
            case NORTH:
                currentDirection = directionHeading ? Direction.EAST : Direction.WEST;
                break;
            case EAST:
                currentDirection = directionHeading ? Direction.SOUTH : Direction.NORTH;
                break;
            case SOUTH:
                currentDirection = directionHeading ? Direction.WEST : Direction.EAST;
                break;
            case WEST:
                currentDirection = directionHeading ? Direction.NORTH : Direction.SOUTH;
                break;
        }
    }

    /**
     * Updates the position of the Robot by a given number of steps.
     *
     * @param nrSteps The number of steps to move.
     * @return True if the position was successfully updated, false otherwise.
     */
    public boolean updatePosition(int nrSteps){
        int newX = this.position.getX();
        int newY = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            newY = newY + nrSteps;
        }else if (Direction.SOUTH.equals(this.currentDirection)){
            newY = newY - nrSteps;
        }else if (Direction.WEST.equals(this.currentDirection)){
            newX = newX - nrSteps;
        }else if (Direction.EAST.equals(this.currentDirection)){
            newX = newX + nrSteps;
        }
        World world = World.getInstance();
        Position newPosition = new Position(newX, newY);
        positionCheck = world.isPositionBlocked(newPosition.getX(),newPosition.getY(), this);
        pathCheck = world.isPathBlocked(position.getX(),position.getY(),newPosition.getX(),newPosition.getY(), this);
        pathCheckPits = world.isPathBlockedPits(position.getX(), position.getY(), newPosition.getX(), newPosition.getY());

        if(positionCheck){
            return false;
        }
        if(pathCheck) {
            return false;
        }
        if (pathCheckPits){
            this.getState().setShields(0);
            endGame(this, "pit");
            return false;
        }
        if (newPosition.isIn(TOP_LEFT,BOTTOM_RIGHT)){
            this.position = newPosition;
            return true;
        }
        return false;
    }

    /**
     * Checks for obstacles in all directions within a given range.
     *
     * @param range The range to check for obstacles.
     */
    public void look(int range) {
        World world = World.getInstance();

        allObstacles.clear();
        obstacleSteps.clear();
        obstaclesNorth.clear();
        obstaclesEast.clear();
        obstaclesSouth.clear();
        obstaclesWest.clear();

        // Check North direction
        pathCheck = world.isPathBlocked(position.getX(), position.getY(), position.getX(), position.getY() + range, this);
        if (pathCheck) {
            for (Object obs : world.obstaclesLook) {

                if (obs instanceof Obstacle obstacle){
                    obstaclesNorth.add(String.format("North Obstacle at (%d, %d) to (%d, %d)",
                            obstacle.getX(), obstacle.getY(), obstacle.getX() + 4, obstacle.getY() + 4));
                    obstacleSteps.put("North_obstacle", obstacle.getY() - position.getY());
                }else if (obs instanceof Mountain mountain){
                    obstaclesNorth.add(String.format("North Mountain at (%d, %d) to (%d, %d)",
                            mountain.getX(), mountain.getY(), mountain.getX() + 4, mountain.getY() + 4));
                    obstacleSteps.put("North_mountain", mountain.getY() - position.getY());
                }else if (obs instanceof Robot robot){
                    obstaclesNorth.add(String.format("North Robot at (%d, %d)", robot.getPosition().getX(), robot.getPosition().getY()));
                    obstacleSteps.put("North_robot", robot.getPosition().getY() - position.getY());
                }
            }
        } else {
            obstaclesNorth.add("No Obstacles for North");
            obstacleSteps.put("North_none", 0);
        }
        world.obstaclesLook.clear();
        world.robotLook.clear();
        world.mountainLook.clear();

        // Check East direction
        pathCheck = world.isPathBlocked(position.getX(), position.getY(), position.getX() + range, position.getY(), this);

        if (pathCheck) {
            for (Object obs : world.obstaclesLook) {

                if (obs instanceof Obstacle obstacle) {
                    obstaclesEast.add(String.format("East Obstacle at (%d, %d) to (%d, %d)",
                            obstacle.getX(), obstacle.getY(), obstacle.getX() + 4, obstacle.getY() + 4));
                    obstacleSteps.put("East_obstacle", obstacle.getX() - position.getX());
                } else if (obs instanceof Mountain mountain) {
                    obstaclesNorth.add(String.format("East Mountain at (%d, %d) to (%d, %d)",
                            mountain.getX(), mountain.getY(), mountain.getX() + 4, mountain.getY() + 4));
                    obstacleSteps.put("East_mountain", mountain.getX() - position.getX());
                } else if (obs instanceof Robot robot) {
                    obstaclesEast.add(String.format("East Robot at (%d, %d)", robot.getPosition().getX(), robot.getPosition().getY()));
                    obstacleSteps.put("East_robot", robot.getPosition().getX() - position.getX());
                }
            }

        } else {
            obstaclesEast.add("No Obstacles for East");
            obstacleSteps.put("East_none", 0);
        }
        world.obstaclesLook.clear();
        world.robotLook.clear();
        world.mountainLook.clear();

        // Check South direction
        pathCheck = world.isPathBlocked(position.getX(), position.getY(), position.getX(), position.getY() - range, this);

        if (pathCheck) {
            for (Object obs : world.obstaclesLook) {
                if (obs instanceof Obstacle obstacle) {
                    obstaclesSouth.add(String.format("South Obstacle at (%d, %d) to (%d, %d)",
                            obstacle.getX(), obstacle.getY(), obstacle.getX() + 4, obstacle.getY() + 4));
                    obstacleSteps.put("South_obstacle", position.getY() - obstacle.getY());
                }else if (obs instanceof Mountain mountain){
                    obstaclesNorth.add(String.format("South Mountain at (%d, %d) to (%d, %d)",
                            mountain.getX(), mountain.getY(), mountain.getX() + 4, mountain.getY() + 4));
                    obstacleSteps.put("South_mountain", position.getY() - mountain.getY());
                }else if (obs instanceof Robot robot){
                    obstaclesSouth.add(String.format("South Robot at (%d, %d)", robot.getPosition().getX(), robot.getPosition().getY()));
                    obstacleSteps.put("South_robot", position.getY() - robot.getPosition().getY());
                }
            }
        } else {
            obstaclesSouth.add("No Obstacles for South");
            obstacleSteps.put("South_none", 0);
        }
        world.obstaclesLook.clear();
        world.robotLook.clear();
        world.mountainLook.clear();

        // Check West direction
        pathCheck = world.isPathBlocked(position.getX(), position.getY(), position.getX() - range, position.getY(), this);

        if (pathCheck) {
            for (Object obs : world.obstaclesLook) {
                if (obs instanceof Obstacle obstacle){
                    obstaclesWest.add(String.format("West Obstacle at (%d, %d) to (%d, %d)",
                            obstacle.getX(), obstacle.getY(), obstacle.getX() + 4, obstacle.getY() + 4));
                    obstacleSteps.put("West_obstacle", position.getX() - obstacle.getX());
                }else if (obs instanceof Mountain mountain){
                    obstaclesNorth.add(String.format("West Mountain at (%d, %d) to (%d, %d)",
                            mountain.getX(), mountain.getY(), mountain.getX() + 4, mountain.getY() + 4));
                    obstacleSteps.put("West_mountain", position.getX() - mountain.getX());
                }else if (obs instanceof Robot robot){
                    obstaclesWest.add(String.format("West Robot at (%d, %d)", robot.getPosition().getX(), robot.getPosition().getY()));
                    obstacleSteps.put("West_robot", position.getX() - robot.getPosition().getX());
                }
            }

        } else {
            obstaclesWest.add("No Obstacles for West");
            obstacleSteps.put("West_none", 0);
        }
        world.obstaclesLook.clear();
        world.robotLook.clear();
        world.mountainLook.clear();

        // Combine all obstacles into a single list to return
        allObstacles.addAll(obstaclesNorth);
        allObstacles.addAll(obstaclesEast);
        allObstacles.addAll(obstaclesSouth);
        allObstacles.addAll(obstaclesWest);
    }

    /**
     * Fires shots in the current direction, hitting any robot in the line of fire
     * within a distance of 5 units. The shot reduces the shield of the hit robot.
     *
     * @return The robot that was hit, or null if no robot was hit.
     */


    public Robot fireShots() {
        World world = World.getInstance();
        int targetX = this.position.getX();
        int targetY = this.position.getY();
        Robot hitRobot = null;

        // Calculate the target position based on the current direction
        if (this.currentDirection.equals(Direction.NORTH)) {
            targetY += 5;
        } else if (this.currentDirection.equals(Direction.SOUTH)) {
            targetY -= 5;
        } else if (this.currentDirection.equals(Direction.EAST)) {
            targetX += 5;
        } else if (this.currentDirection.equals(Direction.WEST)) {
            targetX -= 5;
        }

        // Check each robot in the world to see if they are within the target range
        Iterator<Robot> robotIterator = SimpleServer.robotObjects.iterator();
        while (robotIterator.hasNext()) {
            Robot robot = robotIterator.next();
            if (!robot.equals(this)) { // Ensure not checking against itself
                int distance = 0;

                if (this.currentDirection.equals(Direction.NORTH)) {
                    if (robot.getPosition().getX() == this.position.getX() && robot.getPosition().getY() > this.position.getY() && robot.getPosition().getY() <= targetY) {
                        distance = robot.getPosition().getY() - this.position.getY();
                    }
                } else if (this.currentDirection.equals(Direction.SOUTH)) {
                    if (robot.getPosition().getX() == this.position.getX() && robot.getPosition().getY() < this.position.getY() && robot.getPosition().getY() >= targetY) {
                        distance = this.position.getY() - robot.getPosition().getY();
                    }
                } else if (this.currentDirection.equals(Direction.EAST)) {
                    if (robot.getPosition().getY() == this.position.getY() && robot.getPosition().getX() > this.position.getX() && robot.getPosition().getX() <= targetX) {
                        distance = robot.getPosition().getX() - this.position.getX();
                    }
                } else if (this.currentDirection.equals(Direction.WEST)) {
                    if (robot.getPosition().getY() == this.position.getY() && robot.getPosition().getX() < this.position.getX() && robot.getPosition().getX() >= targetX) {
                        distance = this.position.getX() - robot.getPosition().getX();
                    }
                }
                this.setDistance(distance);

                if (distance > 0 && distance <= 5) {
                    hitRobot = robot;
                    int damage = 0;
                    if (distance == 1) {
                        damage = 5;
                    } else if (distance == 2) {
                        damage = 4;
                    } else if (distance == 3) {
                        damage = 3;
                    } else if (distance == 4) {
                        damage = 2;
                    } else if (distance == 5) {
                        damage = 1;
                    }

                    robot.getState().decrementShieldBy(damage);

                    endGame(robot, "shot");
                    hitRobot.setState(robot.getState());
                    if (robot.getState().getStatus().equals("DEAD")){
                        hitRobot = null;
                    }
                }
            }
        }
        // Decrement the shots for the firing robot
        this.getState().decrementShots();
        return hitRobot;

    }


    public void endGame(Robot robot, String killedMessage) {
        if (robot.getState().getShields() == 0) {
            Socket socket = SimpleServer.listOfRobotSockets.get(robot.getName());
            try (PrintStream out = new PrintStream(socket.getOutputStream())) {
                out.println(killedMessage);
                out.flush();

                synchronized (lock) {  // Synchronize on a dedicated lock object

                    synchronized (World.getInstance().obstacles) {  // Synchronize access to world.obstacles
                        List<Object> toRemove = new ArrayList<>();
                        for (Object obj : World.getInstance().obstacles) {
                            if (obj instanceof Robot bot && bot.getName().equals(robot.getName())) {
                                toRemove.add(obj);
                            }
                        }
                        World.getInstance().obstacles.removeAll(toRemove);
                    }

                    synchronized (SimpleServer.robotObjects) {  // Synchronize access to SimpleServer.robotObjects
                        List<Robot> toRemove = new ArrayList<>();
                        for (Robot bot : SimpleServer.robotObjects) {
                            if (bot.getName().equals(robot.getName())) {
                                toRemove.add(bot);
                            }
                        }
                        SimpleServer.robotObjects.removeAll(toRemove);
                    }
                    synchronized (SimpleServer.robotNames) {  // Synchronize access to SimpleServer.robotObjects
                        List<String> toRemove = new ArrayList<>();
                        for (String botName : SimpleServer.robotNames) {
                            if (botName.equals(robot.getName())) {
                                toRemove.add(botName);
                            }
                        }
                        SimpleServer.robotNames.removeAll(toRemove);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the coordinate position of the robot as a string.
     *
     * @return The coordinate position as a string.
     */
    public String coordinatePosition(){
        return "[" + this.position.getX() + "," + this.position.getY() + "]";
    }

    /**
     * Sets the status of the robot.
     *
     * @param status The new status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the name of the robot.
     *
     * @return The name of the robot.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the state of the robot.
     *
     * @return The state of the robot.
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the state of the robot.
     *
     * @param state The new state.
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * Gets the current direction of the robot.
     *
     * @return The current direction.
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Gets the status of the robot.
     *
     * @return The status of the robot.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the position of the robot.
     *
     * @param position The new position.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the position of the robot.
     *
     * @return The position.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the name of the robot.
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the distance to the obstacle in the current direction.
     *
     * @return The distance.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the distance to the obstacle in the current direction.
     *
     * @param distance The new distance.
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Gets the type of the robot.
     *
     * @return The robot type.
     */
    public String getRobotType() {
        return robotType;
    }

    /**
     * Places the robot at a random position within the world, ensuring the position is not blocked.
     *
     * @param robot The robot to place.
     * @param world The world in which to place the robot.
     * @return The random position.
     */
    public Position randomPosition(Robot robot, World world){
        Random random = new Random();
        int randomX = random.nextInt(0, 10);
        int randomY = random.nextInt(0,10);
        positionCheck = world.isPositionBlocked(randomX,randomY, robot);

        while (positionCheck){
            randomX = random.nextInt(0, 10);
            randomY = random.nextInt(0,10);
            positionCheck = world.isPositionBlocked(randomX,randomY, robot);
        }
        return new Position(randomX,randomY);
    }
}