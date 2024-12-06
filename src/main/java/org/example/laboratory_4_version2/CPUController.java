package org.example.laboratory_4_version2;

import javafx.fxml.FXML;

public class CPUController {
    ExecuterModel executerModel = BuildExecuterModel.build();

    @FXML
    void  start() {
        try {
            executerModel.run();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void run2() {
        try {
            executerModel.run2();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    void reset(){
        executerModel.reset();
    }
}
