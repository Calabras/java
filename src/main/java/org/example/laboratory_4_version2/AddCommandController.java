package org.example.laboratory_4_version2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Objects;

public class AddCommandController {
    ProgramModel programModel = BuildProgramModel.build();

    @FXML
    void add() throws Exception {
        if (InstructionList.getValue() != null) {
            if (((InstructionList.getValue().equals("init") || InstructionList.getValue().equals("ld")
                    || InstructionList.getValue().equals("st") || InstructionList.getValue().equals("mv"))
                    && (!Objects.equals(argument1.getText(), "") && !Objects.equals(argument2.getText(), ""))) ||
                    ((InstructionList.getValue().equals("print") || InstructionList.getValue().equals("add")
                            || InstructionList.getValue().equals("sub") || InstructionList.getValue().equals("mult")
                            || InstructionList.getValue().equals("div")))) {

                programModel.addCommand(new Command((String)InstructionList.getValue(), argument1.getText(), argument2.getText()));
            }
            else
                System.out.println("Введите корректные параметры!");
        }
        else
            System.out.println("Выберите команду!");
    }

    @FXML
    ComboBox InstructionList;
    @FXML
    TextField argument1;
    @FXML
    TextField argument2;
    @FXML
    Button AddCommand;

    @FXML
    void initialize()
    {
        InstructionList.getItems().addAll("init", "ld", "st", "mv", "print", "add", "sub", "mult", "div");
        argument1.setDisable(true);
        argument2.setDisable(true);

        InstructionList.setOnAction(event -> {
            String selectedCommand = (String) InstructionList.getValue();
            argument1.setText("");
            argument2.setText("");

            if (selectedCommand.equals("print") || selectedCommand.equals("add") || selectedCommand.equals("sub") ||
                    selectedCommand.equals("div") || selectedCommand.equals("mult")) {
                argument1.setDisable(true);
                argument2.setDisable(true);
            } else {
                argument1.setDisable(false);
                argument2.setDisable(false);
            }
        });
    }
}
