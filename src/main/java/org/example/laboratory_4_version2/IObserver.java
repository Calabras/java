package org.example.laboratory_4_version2;

public interface IObserver {
    void event(ProgramModel m);
    void event(CpuModel c);
    void event(ExecuterModel e);
}
