package org.example.laboratory_4_version2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainViewController implements IObserver {
    ProgramModel programModel = BuildProgramModel.build();

    @FXML
    void initialize()
    {
        programModel.addObserver(this);

        try {
            AddCommandController pc1 = new AddCommandController();
            FXMLLoader fxmlLoader1 = new FXMLLoader(
                    appMain.class.getResource("AddCommandView.fxml"));

            MemoryController pc2 = new MemoryController();
            FXMLLoader fxmlLoader2 = new FXMLLoader(
                    appMain.class.getResource("MemoryView.fxml"));

            CPUController pc3 = new CPUController();
            FXMLLoader fxmlLoader3 = new FXMLLoader(
                    appMain.class.getResource("CPUView.fxml"));

            RegistersController pc4 = new RegistersController();
            FXMLLoader fxmlLoader4 = new FXMLLoader(
                    appMain.class.getResource("RegistersView.fxml"));

            StatsController pc5 = new StatsController();
            FXMLLoader fxmlLoader5 = new FXMLLoader(
                    appMain.class.getResource("StatisticsView.fxml"));

            Pane pane1 = fxmlLoader1.load();
            Pane pane2 = fxmlLoader2.load();
            Pane pane3 = fxmlLoader3.load();
            Pane pane4 = fxmlLoader4.load();
            Pane pane5 = fxmlLoader5.load();

            main.add(pane1, 1, 1); //добавление команды
            main.add(pane2, 2, 1); //память
            main.add(pane3, 1,0); //кнопки действия
            main.add(pane4, 2,0);
            main.add(pane5, 2, 2); //статистика

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    GridPane main;

    @FXML
    GridPane myCommands;

    @Override
    public void event(ProgramModel model) {
        myCommands.getChildren().clear();
        for (Command c : model) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(
                        appMain.class.getResource("CommandView.fxml"));
                Pane pane = fxmlLoader.load();
                CommandViewController pc = fxmlLoader.getController();
                pc.setCommand(c);
                myCommands.addColumn(0, pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void event(CpuModel c) {}

    @Override
    public void event(ExecuterModel e) {}
}
