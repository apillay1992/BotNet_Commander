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

}
