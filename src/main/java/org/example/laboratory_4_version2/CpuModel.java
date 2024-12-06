package org.example.laboratory_4_version2;

import java.util.ArrayList;
import java.util.Arrays;

public class CpuModel implements ICpuObserver {

    private int[] registers = new int[4];
    private int[] memory = new int[1024];

    //текущая инструкция
    private String Instruction;

    public int[] getRegisters(){ return registers; }
    public int[] getMemory(){ return memory; }

    ArrayList<IObserver> allObserver = new ArrayList<>();
    void eventCall(){
        allObserver.forEach(action->action.event(this));
    }

    public void addObserver(IObserver e){
        allObserver.add(e);
        eventCall();
    }

    //очищение всех регистров и памяти
    public void ClearAll() {
        Instruction = null;
        Arrays.fill(memory, 0);
        Arrays.fill(registers, 0);
        eventCall();
    }

    //исполнение команды
    @Override
    public void exec(Command c) throws Exception {
        // Реализация команд
        Instruction = c.getCommand();
        switch (Instruction) {
            case "init":
                int address = Integer.parseInt(c.getArgs(0));
                int value = Integer.parseInt(c.getArgs(1));
                memory[address] = value;
                break;
            case "ld":
                int regIndex = getRegisterIndex(c.getArgs(0));
                address = Integer.parseInt(c.getArgs(1));
                registers[regIndex] = memory[address];
                break;
            case "st":
                regIndex = getRegisterIndex(c.getArgs(0));
                address = Integer.parseInt(c.getArgs(1));
                memory[address] = registers[regIndex];
                break;
            case "mv":
                int sourceRegIndex = getRegisterIndex(c.getArgs(0));
                int destRegIndex = getRegisterIndex(c.getArgs(1));
                registers[sourceRegIndex] = registers[destRegIndex];
                break;
            case "print":
                break;
            case "add":
                registers[3] = registers[0] + registers[1]; // d = a + b
                break;
            case "sub":
                registers[3] = registers[0] - registers[1]; // d = a - b
                break;
            case "mult":
                registers[3] = registers[0] * registers[1]; // d = a * b
                break;
            case "div":
                registers[3] = registers[0] / registers[1]; // d = a / b
                break;
            default:
                throw new IllegalArgumentException("Unknown command: " + c.getCommand());
        }
        eventCall();
    }


    private int getRegisterIndex(String register_name) throws Exception {
        return switch (register_name) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new Exception("Unknown register: " + register_name);
        };
    }
}


