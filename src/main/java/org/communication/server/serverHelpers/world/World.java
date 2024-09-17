package org.communication.server.serverHelpers.world;

import org.communication.server.serverHelpers.obstacles.BottomLessPit;
import org.communication.server.serverHelpers.obstacles.Lake;
import org.communication.server.serverHelpers.obstacles.Mountain;
import org.communication.server.serverHelpers.obstacles.Obstacle;
import org.communication.server.serverHelpers.robot.Position;
import org.communication.server.serverHelpers.robot.Robot;
import java.util.ArrayList;
import java.util.Random;

// ... (fields and constructor omitted for brevity)

/**
 * Represents the game world with obstacles and robots.
 * Responsible for managing the positions of obstacles and robots,
 * and checking for collisions and blocked paths.
 */
public class World {
    private static final Position TOP_LEFT = new Position(Config.getTopLeftX_world(), Config.getTopLeftY_world());
    private static final Position BOTTOM_RIGHT = new Position(Config.getBottomRightX_world(), Config.getBottomRightY_world());
    private static org.communication.server.serverHelpers.world.World instance;
    private Obstacle obstacle;
    private Mountain mountain;
    private Lake lake;
    private BottomLessPit bottomLessPit;
    public ArrayList<Object> obstacles = new ArrayList<>();
    public ArrayList<Object> obstaclesLook = new ArrayList<>();
    public ArrayList<Robot> robotLook = new ArrayList<>();
    public ArrayList<Mountain> mountainLook = new ArrayList<>();

    private World() {
        Random random = new Random();
        int numOfObstacles = random.nextInt(7, 11); // increase bound to 10
        int idx = 0;

        while (idx < numOfObstacles) {
            Object newObstacle = createRandomObstacle(random);
            if (isCollidingWithExistingObstacles(newObstacle)) {
                continue;
            }
            obstacles.add(newObstacle);
            idx++;
        }
    }

    /**
     * Creates a random obstacle based on the given Random object.
     *
     * @param random The Random object used to generate random values.
     * @return A new obstacle object (Obstacle, Mountain, Lake, or BottomLessPit).
     */
    private Object createRandomObstacle(Random random) {
        int obstacleType = random.nextInt(4);
        return switch (obstacleType) {
            case 1 -> new Mountain(random.nextInt(TOP_LEFT.getX() - 5,BOTTOM_RIGHT.getX() - 5), random.nextInt(BOTTOM_RIGHT.getY() - 5,TOP_LEFT.getY() - 5));
            case 2 -> new Lake(random.nextInt(TOP_LEFT.getX() - 5,BOTTOM_RIGHT.getX() - 5), random.nextInt(BOTTOM_RIGHT.getY() - 5,TOP_LEFT.getY() - 5));
            case 3 -> new BottomLessPit(random.nextInt(TOP_LEFT.getX() - 5,BOTTOM_RIGHT.getX() - 5), random.nextInt(BOTTOM_RIGHT.getY() - 5,TOP_LEFT.getY() - 5));
            default -> new Obstacle(random.nextInt(TOP_LEFT.getX() - 5,BOTTOM_RIGHT.getX() - 5), random.nextInt(BOTTOM_RIGHT.getY() - 5,TOP_LEFT.getY() - 5));
        };
    }

    /**
     * Checks if a new obstacle collides with any existing obstacles.
     *
     * @param newObstacle The new obstacle to be checked.
     * @return True if the new obstacle collides with any existing obstacle, false otherwise.
     */
    private boolean isCollidingWithExistingObstacles(Object newObstacle) {
        for (Object obs : obstacles) {
            switch (getObstacleType(obs)) {
                case "Obstacle":
                    Obstacle existingObstacle = (Obstacle) obs;
                    if (isWithinRange(newObstacle, existingObstacle)) {
                        return true;
                    }
                    break;
                case "Mountain":
                    Mountain existingMountain = (Mountain) obs;
                    if (isWithinRange(newObstacle, existingMountain)) {
                        return true;
                    }
                    break;
                case "Lake":
                    Lake existingLake = (Lake) obs;
                    if (isWithinRange(newObstacle, existingLake)) {
                        return true;
                    }
                    break;
                case "BottomLessPit":
                    BottomLessPit existingBottomLessPit = (BottomLessPit) obs;
                    if (isWithinRange(newObstacle, existingBottomLessPit)) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * Returns the type of the obstacle as a String.
     *
     * @param obstacle The obstacle object.
     * @return The type of the obstacle ("Obstacle", "Mountain", "Lake", "BottomLessPit", or "Unknown").
     */
    private String getObstacleType(Object obstacle) {
        if (obstacle instanceof Obstacle) return "Obstacle";
        if (obstacle instanceof Mountain) return "Mountain";
        if (obstacle instanceof Lake) return "Lake";
        if (obstacle instanceof BottomLessPit) return "BottomLessPit";
        return "Unknown";
    }

    /**
     * Checks if the new obstacle is within the range (4 units) of the existing obstacle.
     *
     * @param newObstacle     The new obstacle.
     * @param existingObstacle The existing obstacle.
     * @return True if the new obstacle is within the range of the existing obstacle, false otherwise.
     */
    private boolean isWithinRange(Object newObstacle, Object existingObstacle) {
        int newX = getX(newObstacle);
        int existingX = getX(existingObstacle);
        return (newX <= existingX + 4) && (newX >= existingX - 4);
    }

    /**
     * Returns the X coordinate of the given obstacle.
     *
     * @param obstacle The obstacle object.
     * @return The X coordinate of the obstacle, or -1 if the obstacle type is unknown.
     */
    private int getX(Object obstacle) {
        if (obstacle instanceof Obstacle) return ((Obstacle) obstacle).getX();
        if (obstacle instanceof Mountain) return ((Mountain) obstacle).getX();
        if (obstacle instanceof Lake) return ((Lake) obstacle).getX();
        if (obstacle instanceof BottomLessPit) return ((BottomLessPit) obstacle).getX();
        return -1;
    }

    /**
     * Returns the singleton instance of the World class.
     *
     * @return The instance of the World class.
     */
    public static World getInstance() {
        if (instance == null) {
            synchronized (World.class) {
                if (instance == null) {
                    instance = new World();
                }
            }
        }
        return instance;
    }

    /**
     * Checks if the given position (x, y) is blocked by an obstacle or a robot.
     *
     * @return True if the position is blocked, false otherwise.
     */
    public Obstacle getObstacle() {
        return obstacle;
    }

    /**
     * Checks if the given position (x, y) is blocked by a bottomless pit.
     *
     * @param x The X coordinate of the position.
     * @param y The Y coordinate of the position.
     * @return True if the position is blocked by a bottomless pit, false otherwise.
     */
    public boolean isPositionBlocked(int x, int y, Robot currentRobot) {
        for (Object obs : obstacles) {
            if (obs instanceof Obstacle obstacle) {
                // Check if the position (x, y) is within the obstacle's area
                if (x >= obstacle.getX() && x <= obstacle.getX() + 4 &&
                        y >= obstacle.getY() && y <= obstacle.getY() + 4) {
                    obstaclesLook.add(new Obstacle(obstacle.getX(), obstacle.getY()));
                    return true; // Position is blocked by an obstacle
                }
            }else if (obs instanceof Mountain mountain) {
                // Check if the position (x, y) is within the mountains area
                if (x >= mountain.getX() && x <= mountain.getX() + 4 &&
                        y >= mountain.getY() && y <= mountain.getY() + 4) {
                    obstaclesLook.add(new Mountain(mountain.getX(), mountain.getY()));
                    return true; // Position is blocked by a mountain
                }

            }else if (obs instanceof Lake lake){
                // Check if the position (x, y) is within the lake area
                if (x >= lake.getX() && x <= lake.getX() + 4 &&
                        y >= lake.getY() && y <= lake.getY() + 4) {
                    obstaclesLook.add(new Lake(lake.getX(),lake.getY()));
                    return true; // Position is blocked by a lake
                }
            }else if (obs instanceof Robot robotObj){
                if (x == robotObj.getPosition().getX() && y == robotObj.getPosition().getY() && !robotObj.getName().equals(currentRobot.getName())){
                    obstaclesLook.add(robotObj);
                    return true;
                }
            }
        }
        return false; // Position is not blocked by any obstacle
    }

    /**
     * Checks if the path between two positions (x1, y1) and (x2, y2) is blocked by an obstacle or a robot.
     *
     * @param x1          The X coordinate of the first position.
     * @param y1          The Y coordinate of the first position.
     * @param x2          The X coordinate of the second position.
     * @param y2          The Y coordinate of the second position.
     * @param currentRobot The current robot being checked.
     * @return True if the path is blocked, false otherwise.
     */
    public boolean isPathBlocked(int x1, int y1, int x2, int y2, Robot currentRobot) {
        if (x1 == x2) {
            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
                if (isPositionBlocked(x1, i, currentRobot)) {
                    return true; // Path is blocked by an obstacle
                }
            }
        } else if (y1 == y2) {
            for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++) {
                if (isPositionBlocked(j, y1, currentRobot)) {
                    return true; // Path is blocked by an obstacle
                }
            }
        }
        return false; // Path is not blocked by any obstacle
    }

    /**
     * Checks if the path between two positions (x1, y1) and (x2, y2) is blocked by a bottomless pit..
     * @return True if the path is blocked by a bottomless pit, false otherwise.
     */
    public Boolean isPositionBlockedPits(int x, int y) {
        for (Object obs : obstacles) {
            if (obs instanceof BottomLessPit bottomLessPit) {
                // Check if the position (x, y) is within the bottomless pit area
                if (x >= bottomLessPit.getX() && x <= bottomLessPit.getX() + 4 &&
                        y >= bottomLessPit.getY() && y <= bottomLessPit.getY() + 4) {
                    return true; // Position is blocked by a bottomless pit area
                }
            }
        }
        return false;
    }

    /**
     * Checks if the path between two positions (x1, y1) and (x2, y2) is blocked by a bottomless pit.
     *
     * @param x1 The X coordinate of the first position.
     * @param y1 The Y coordinate of the first position.
     * @param x2 The X coordinate of the second position.
     * @param y2 The Y coordinate of the second position.
     * @return True if the path is blocked by a bottomless pit, false otherwise.
     */
    public boolean isPathBlockedPits(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
                if (isPositionBlockedPits(x1, i)) {
                    return true; // Path is blocked by a bottomless pit
                }
            }
        } else if (y1 == y2) {
            for (int j = Math.min(x1, x2); j <= Math.max(x1, x2); j++) {
                if (isPositionBlockedPits(j, y1)) {
                    return true; // Path is blocked by a bottomless pit
                }
            }
        }
        return false; // Path is not blocked by any bottomless pit
    }

    /**
     * Returns the top-left position of the world.
     *
     * @return The top-left position of the world.
     */
    public Position getTopLeft() {
        return TOP_LEFT;
    }

    /**
     * Returns the bottom-right position of the world.
     *
     * @return The bottom-right position of the world.
     */
    public Position getBottomRight() {
        return BOTTOM_RIGHT;
    }

    /**
     * Checks if the given position is within the boundaries of the world.
     *
     * @param position The position to be checked.
     * @return True if the position is within the world boundaries, false otherwise.
     */
    public boolean isInWorld(Position position) {
        return position.isIn(TOP_LEFT, BOTTOM_RIGHT);
    }



}
