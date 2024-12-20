package org.communication.server.serverHelpers.commands;

import org.communication.server.serverHelpers.world.Config;
import org.communication.server.serverHelpers.robot.Robot;

/**
 * Represents a command to make a robot look around.
 * Extends the Command class and overrides the execute method to handle the look action.
 */
public class Look extends Command {

    //Executes the look command on the target robot.
    @Override
    public boolean execute(Robot target) {
        target.look(Config.getVisibility()); // Make the robot look using configured visibility
        return false; // Return false indicating the command execution result
    }

    //Constructs a look command with the specified argument.
    public Look(String argument){
        super("look", argument);
    }

}
