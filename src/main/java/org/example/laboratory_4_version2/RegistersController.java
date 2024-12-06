package org.example.laboratory_4_version2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegistersController implements IObserver {
    CpuModel cpuModel = BuildCpuModel.build();

    @FXML
    void initialize()
    {
        cpuModel.addObserver(this);
    }

    @FXML
    Label registerA;
    @FXML
    Label registerB;
    @FXML
    Label registerC;
    @FXML
    Label registerD;

    @Override
    public void event(CpuModel c) {
        registerA.setText(String.valueOf(c.getRegisters()[0]));
        registerB.setText(String.valueOf(c.getRegisters()[1]));
        registerC.setText(String.valueOf(c.getRegisters()[2]));
        registerD.setText(String.valueOf(c.getRegisters()[3]));
    }

    @Override
    public void event(ExecuterModel e) {
    }

    @Override
    public void event(ProgramModel m) {
    }
}
