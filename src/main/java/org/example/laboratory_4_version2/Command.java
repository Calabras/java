package org.example.laboratory_4_version2;

import java.sql.PreparedStatement;
import java.util.Arrays;

public class Command {
    private String command;
    private String[] args;
    private int ID=-1;

    @Override
    public String toString() {
        return "\n" + command + " " +  String.join(" ", args);
    }


    public Command(int ID, String command, String... args) {
        this.ID = ID;
        this.command = command;
        this.args = args;
    }

    public Command(String command, String... args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public String getArgs(int index) {
        if (args.length != 0)
            return args[index];
        return "";
    }

    public void setID(int ID) {
        this.ID=ID;
    }

    public int getID() {
        return ID;
    }
}
