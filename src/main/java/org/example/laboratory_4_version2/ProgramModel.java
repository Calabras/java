package org.example.laboratory_4_version2;

import java.util.*;

public class ProgramModel implements Iterable<Command> {

    DAO_class dao = BuildDAO.getDAO();

    public ArrayList<Command> getAllCommands(){
        return dao.getAllCommands();
    }

    public int countCommands() { return dao.countCommands(); }

    public ProgramModel(){
        if (dao.getAllCommands().isEmpty()) {
//            dao.addCommand(new Command("init", "10", "20"));
//            dao.addCommand(new Command("init", "11", "25"));
//            dao.addCommand(new Command("init", "12", "5"));
//            dao.addCommand(new Command("ld", "a", "10"));
//            dao.addCommand(new Command("ld", "b", "11"));
//            dao.addCommand(new Command("ld", "c", "12"));
//            dao.addCommand(new Command("add"));
//            dao.addCommand(new Command("print"));
//            dao.addCommand(new Command("mv", "a", "d"));
//            dao.addCommand(new Command("mv", "b", "c"));
//            dao.addCommand(new Command("div"));
//            dao.addCommand(new Command("print"));
        }
        eventCall();
    }

    ArrayList<IObserver> allObserver = new ArrayList<>();
    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver observer){
        allObserver.add(observer);
        eventCall();
    }

    public void addCommand(Command c){
        dao.addCommand(c);
        eventCall();
    }

    public int getNumCommand(Command c){
        return dao.getNumCommand(c);
    }

    public void removeCommand(Command c) {
        dao.removeCommand(c);
        eventCall();
    }

    public void moveCommandDown(Command c) throws Exception {
       dao.moveCommandDown(c);
    }

    public void moveCommandUp(Command c) throws Exception {
        dao.moveCommandUp(c);
    }


    //выводим инструкции по популярности
    public String MostPopularInstruction() {
        Map<String, Integer> commandCount = new HashMap<>();
        for (Command command : dao.getAllCommands()) {
            if (command == null) continue;
            String command_string = command.getCommand();
            commandCount.put(command_string, commandCount.getOrDefault(command_string, 0) + 1);
        }

        String mostPopularCommand = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : commandCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostPopularCommand = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        List<Map.Entry<String, Integer>> sortedCommands = new ArrayList<>(commandCount.entrySet());
        sortedCommands.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        int i = 0;
        StringBuilder table = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedCommands) {
            String cell = String.format("%" + 5 + "s: " + "%" + 2 + "d\n", entry.getKey(), entry.getValue());
            table.append(cell);
        }
        return table.toString();
    }

    @Override
    public Iterator<Command> iterator() {
        return dao.iterator();
    }
}
