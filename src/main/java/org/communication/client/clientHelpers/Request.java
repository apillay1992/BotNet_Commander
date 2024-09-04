package org.communication.client.clientHelpers;

import com.google.gson.Gson;
import org.communication.server.serverHelpers.robot.Robot;

public class Request {
    private String robotName;
    private String command;
    private String[] arguments;
    private Robot robot;

    //Constructs an empty Request.
    public Request(){
    }

    /**
     * Constructs a new Request with the specified robot name and command.
     *
     * @param robotName The name of the robot.
     * @param command The command to be executed.
     */
    public Request(String robotName, String command){
        this.robotName = robotName;
        this.command = command;
    }
}
