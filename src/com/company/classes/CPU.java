package com.company.classes;

public class CPU {

    private Core[] cores;

    public CPU(final int coresNumber){
        cores = new Core[coresNumber];
        for(int i = 0; i < coresNumber; i++){
            cores[i] = new Core(i);
        }
    }

    public Core[] getCores() {
        return cores;
    }

    public void setCores(Core[] cores) {
        this.cores = cores;
    }

    public Core getCore(int core){
        return cores[core];
    }

    @Override
    public String toString() {
        String result = "[ ";
        for (Core core : cores){
            result +=  core + " is "
                    + (core.isIdle() ? "Idle" : "Busy") + "; ";
        }
        return result + "]";
    }
}
