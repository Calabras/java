package org.example.laboratory_4_version2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CommandViewController implements IObserver {
    ProgramModel programModel = BuildProgramModel.build();
    ExecuterModel executerModel = BuildExecuterModel.build();
    Command c;

    public void setCommand(Command c){
        this.c = c;
        command.setText(c.getCommand());
        argument1.setText(c.getArgs(0));
        argument2.setText(c.getArgs(1));
    }

    @FXML
    Label command;
    @FXML
    Label argument1;
    @FXML
    Label argument2;

    @FXML
    void initialize() { executerModel.addObserver(this); }

    @FXML
    void remove() { programModel.removeCommand(c); }

    @FXML
    void moveDown() throws Exception {
        try {
            programModel.moveCommandDown(c);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void moveUp() throws Exception {
        try {
            programModel.moveCommandUp(c);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void event(ProgramModel m) {}

    @Override
    public void event(CpuModel c) {}

    @Override
    public void event(ExecuterModel e) {
        if (c != null) {
            if (e.getCounter() == programModel.getNumCommand(c))
                setColor(Color.RED);
            else
                setColor(Color.BLACK);
        }
    }

    private void setColor(Color color){
        command.setTextFill(color);
    }
}
