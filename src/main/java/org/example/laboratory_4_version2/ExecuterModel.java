package org.example.laboratory_4_version2;
import java.util.ArrayList;

public class ExecuterModel {
    CpuModel cpuModel = BuildCpuModel.build();
    ProgramModel programModel = BuildProgramModel.build();

    private boolean finish = false;
    private boolean start = false;
    private int counter = 0;

    public int getCounter() { return counter; }

    public boolean getFinish(){ return finish;}
    public boolean getStart(){ return start;}

    ArrayList<IObserver> allObserver = new ArrayList<>();
    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }
    public void addObserver(IObserver e){
        allObserver.add(e);
        eventCall();
    }

    //полностью
    public void run() throws Exception {
        counter = 0;
        start = false;
        finish = false;
        for (int i = 0; counter < programModel.dao.getAllCommands().size(); i++) {
            cpuModel.exec(programModel.dao.getAllCommands().get(i));
            counter++;
        }
        finish = true;
        counter--;
        eventCall();
    }

    //по командам
    public void run2() throws Exception {
        if (!finish){
            if (counter < programModel.dao.getAllCommands().size()){
                cpuModel.exec(programModel.dao.getAllCommands().get(counter));
                start = true;
                counter++;
            }
            if (counter == programModel.dao.getAllCommands().size()){
                start = false;
                finish = true;
                counter--;
            }
        }
        else
            throw new Exception("Program is end");
        eventCall();
    }

    public void reset() {
        finish = false;
        start = false;
        counter = 0;
        cpuModel.ClearAll();
        eventCall();
    }
}