package org.example.laboratory_4_version2;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class StatsController implements IObserver {

    ProgramModel programModel = BuildProgramModel.build();
    Font monoFont = Font.font("monospaced", 12);

    @FXML
    void initialize()
    {
        programModel.addObserver(this);
    }

    @FXML
    VBox stats;

    @Override
    public void event(ProgramModel m) {
        stats.getChildren().clear();
        Text st = new Text(m.MostPopularInstruction());
        st.setFont(monoFont);
        stats.getChildren().add(new VBox(st));
    }

    @Override
    public void event(CpuModel c) {
    }

    @Override
    public void event(ExecuterModel e) {
    }
}
