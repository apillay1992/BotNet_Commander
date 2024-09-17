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

public class World {


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

    }
}