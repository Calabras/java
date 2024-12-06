package org.example.laboratory_4_version2;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DAO_class implements Iterable<Command> {
    ArrayList<Command> myCommands = new ArrayList<>();

    DAO_class()
    {
        connect();
        updateList();
    }

    Connection c;

    //подключение
    void connect()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(
                    "jdbc:sqlite:program.db");
            System.out.println("Opened database successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //обновление листа команд
    protected void updateList(){
        myCommands.clear();
        String query = "SELECT * FROM AllCommands";
        try (Statement st = c.createStatement();
             ResultSet r = st.executeQuery(query)) {
            while (r.next()) {
                Command command = new Command(
                        r.getInt("ID"),
                        r.getString("CommandName"),
                        r.getString("Argument1"),
                        r.getString("Argument2")
                );
                myCommands.add(command);
            }
        } catch (SQLException e) {
            System.out.println("Error comList: " + e.getMessage());
        }
    }

    //добавление команды
    public void addCommand(Command command){
        int maxPosition = getMaxPosition();
        int newPosition = maxPosition + 1;

        String sql = "INSERT INTO AllCommands(CommandName, Argument1, Argument2, Position) VALUES (?,?,?,?)";
        try (PreparedStatement pst = c.prepareStatement(sql)) {

            pst.setString(1, command.getCommand());
            pst.setString(2, command.getArgs(0));
            pst.setString(3, command.getArgs(1));
            pst.setInt(4, newPosition);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error addCom: " + ex.getMessage());
        }
        updateList();
    }

    //удаление команды
    public void removeCommand(Command command) {
        String sql = "DELETE FROM AllCommands WHERE ID = ?";
        try (PreparedStatement pst = c.prepareStatement(sql)) {
            pst.setInt(1, command.getID());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Ошибка при удалении команды: " + ex.getMessage());
        }
        updateList();
    }


//    //движение вниз
//    public void moveCommandUp(Command command) {
//        try {
//            int currentIndex = myCommands.indexOf(command)+1;
//            int currentID = command.getID();
//
//            int leftPos = myCommands.indexOf(command)-1+1;
//            int leftID = myCommands.get(myCommands.indexOf(command)-1).getID();
//
//            PreparedStatement pst = c.prepareStatement(
//                    "UPDATE AllCommands SET Position = ? WHERE ID = ?"
//            );
//
//            pst.setInt(1, currentIndex);
//            pst.setInt(2, leftID);
//            pst.executeUpdate();
//
//            PreparedStatement pst1 = c.prepareStatement(
//                    "UPDATE AllCommands SET Position = ? WHERE ID = ?"
//            );
//
//            pst1.setInt(1, leftPos);
//            pst1.setInt(2, currentID);
//            pst1.executeUpdate();
//        }
//        catch (SQLException ex) {
//            System.out.println("Mode up:" + ex.getMessage());
//        }
//
//        updateList();
//    }

    public void moveCommandDown(Command command){//достаточно лишь свапнуть id записей в таблице

        int tmp = command.getID();
        command.setID(myCommands.get(myCommands.indexOf(command)+1).getID());
        myCommands.get(myCommands.indexOf(command)+1).setID(tmp);

        updateDB();
        updateList();
    }

    public void moveCommandUp(Command command){//достаточно лишь свапнуть id записей в таблице

        int tmp = command.getID();
        command.setID(myCommands.get(myCommands.indexOf(command)-1).getID());
        myCommands.get(myCommands.indexOf(command)-1).setID(tmp);

        updateDB();//выгрузили новые данные в бд
        updateList();
    }

    protected void updateDB(){
        try{
            PreparedStatement pst = c.prepareStatement("" +
                    "UPDATE AllCommands SET CommandName = ?, Argument1 = ?," +
                    "Argument2 = ? WHERE ID = ?");
            for(Command com: myCommands){
                pst.setString(1, com.getCommand());
                pst.setString(2, com.getArgs(0));
                pst.setString(3, com.getArgs(1));
                pst.setInt(4, com.getID());
                pst.addBatch(); //добавляем запрос по конкретной команде в пакет
            }
            pst.executeBatch(); //выполняем пакет запросов
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


//    //движение вверх
//    public void moveCommandDown(Command command) {
//        try {
//            int curPos = myCommands.indexOf(command)+1;
//            int curID = command.getID();
//
//            int rightPos = myCommands.indexOf(command)+1+1;
//            int rightID = myCommands.get(myCommands.indexOf(command)+1).getID();
//
//            PreparedStatement pst = c.prepareStatement(
//                    "UPDATE AllCommands SET Position = ? WHERE ID = ?"
//            );
//
//            pst.setInt(1, curPos);
//            pst.setInt(2, rightID);
//            pst.executeUpdate();
//
//            PreparedStatement pst1 = c.prepareStatement(
//                    "UPDATE AllCommands SET Position = ? WHERE ID = ?"
//            );
//
//            pst1.setInt(1, rightPos);
//            pst1.setInt(2, curID);
//            pst1.executeUpdate();
//        }
//        catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        updateList();
//    }

    //получаем максимальную текущую позицию
    private int getMaxPosition() {
        int maxPosition = 0;
        String sql = "SELECT MAX(Position) FROM AllCommands";
        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                maxPosition = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error maxPos: " + e.getMessage());
        }
        return maxPosition;
    }

    public int countCommands() { return myCommands.size(); }

    public int getNumCommand(Command c){
        return myCommands.indexOf(c);
    }

    @NotNull
    @Override
    public Iterator<Command> iterator() {
        return myCommands.iterator();
    }

    public ArrayList<Command> getAllCommands(){
        return myCommands;
    }

}
