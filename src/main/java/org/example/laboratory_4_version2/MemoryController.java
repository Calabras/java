package org.example.laboratory_4_version2;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MemoryController implements IObserver {
    CpuModel cpuModel = BuildCpuModel.build();

    @FXML
    void initialize()
    {
        cpuModel.addObserver(this);
    }

    @FXML
    VBox memory;

    @Override
    public void event(CpuModel c){
        int[] mem = c.getMemory();
        TextFlow textFlow = new TextFlow();
        textFlow.setLineSpacing(-5);
        int columns = 5;
        int indexWidth = 2;
        int valueWidth  = 2;

        for (int i = 0; i < 1024; i++) {
            String index = String.format("%" + indexWidth + "d", i);
            String value = String.format("%" + valueWidth + "d", mem[i]);

            Text indexText = new Text(index + ": ");
            Text valueText = new Text(value);
            Text space = new Text("  ");

            if (mem[i] != 0) {
                valueText.setFill(Color.RED);
            }

            Font monoFont = Font.font("monospaced", 9);
            indexText.setFont(monoFont);
            valueText.setFont(monoFont);
            space.setFont(monoFont);

            textFlow.getChildren().addAll(indexText, valueText, space);

            if ((i + 1) % columns == 0) {
                textFlow.getChildren().add(new Text("\n"));
            }
        }
        VBox root = new VBox(textFlow);
        memory.getChildren().clear();
        memory.getChildren().add(root);
    }

    @Override
    public void event(ExecuterModel e) {}

    @Override
    public void event(ProgramModel m) {}
}
