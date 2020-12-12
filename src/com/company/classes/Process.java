package com.company.classes;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Process{
    private int id;
    private String name;
    private int priority;
    private int time; //число работы в тактах
    private int memory; //кол-во памяти
    private int arrivalTime; //время захода
    private int burstTime; // Общее время, необходимое центральному процессору для выполнения всего процесса (не включает в себя время ожидания)
    private int idleTime;
    private int runTime;
    //private int startWaitTime;
    private State state;
    private MemoryBlock memoryBlock;
    private Core core;
    private Device device;
    private int resourceId = -1;
    private Timer timer = new Timer();
    //private int maxInitAttempts = 3;

    public Process(int id) {
        this.id = id;
        this.memory = Utils.getRandomInteger(10,Configuration.memoryVolume/4);  //кол-во памяти, требуемое для процесса
        this.priority = Utils.getRandomInteger( Configuration.maxPriority);
        this.time = Utils.getRandomInteger(10,100);
        this.arrivalTime = TactGenerator.getTime();
        this.burstTime = 0;
        this.name = "PID" + this.id;
        this.state = State.New;
    }
    /*
    public Process(int id, String name, int priority, int time, int memory) {
        this.id = id;
        this.name = "P" + this.id;
        this.priority = priority;
        this.time = time;
        this.memory = memory;
        this.arrivalTime = TactGenerator.getTime();
        this.burstTime = 0;
        this.state = State.New;
    }

    public Process() {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.time = time;
        this.memory = memory;
        this.arrivalTime = TactGenerator.getTime();
        this.burstTime = 0;
        this.state = State.New;
    }
 */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getTime() {
        return time;
    }

    public int getMemory() {
        return memory;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public State getState() {
        return state;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setState(State state) {
        this.state = state;
        if(state == State.Running){
            startTimer();
        }
        else if(state == State.Terminated){
            stopTimer();
            core.setIdle(true);
            core = null;
            releaseMemory();
        }
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

    public MemoryBlock getMemoryBlock() {
        return memoryBlock;
    }

    public void setMemoryBlock(MemoryBlock memoryBlock) {
        this.memoryBlock = memoryBlock;
    }

    public void releaseMemory(){
        int memory = memoryBlock.getAvailableMemory();
        this.memoryBlock.setAvailableMemory(memory += this.memory);
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    /*
    public int getMaxInitAttempts() {
        return maxInitAttempts;
    }

    public void setMaxInitAttempts() {
        this.maxInitAttempts--;
    }
    */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getResourceId() {
        return resourceId;
    }
    /*
    public float getCpuUtil()
    {
        return burstTime + idleTime != 0 ? (float)burstTime / (burstTime + idleTime) : 0;
    }
    */
    private void startTimer2(){
        int requestDeviceTime = Utils.getRandomInteger(0,time / 2);
        var me = this;

        TimerTask repeatedTask = new TimerTask() {
            int startWaitTime = 0;

            public void run() {
                /*
                if(burstTime >= requestDeviceTime && state == State.Running){
                    state = State.Waiting;
                    startWaitTime = TactGenerator.getTime();
                }

                if(resourceId == -1) {
                    resourceId = device.requestResource();
                    if (resourceId != -1) {
                        device.doWork(resourceId);
                        idleTime += (TactGenerator.getTime() - startWaitTime);
                        state = State.Running;
                        stopTimer();
                        resourceId = -1;
                    } else
                        System.out.println(new Date() + " Process " + me.name + " is waiting for resource");
                }
                */

                if(burstTime >= requestDeviceTime && state == State.Running){
                    state = State.Waiting;
                    startWaitTime = TactGenerator.getTime();
                    int resourceNumber = device.requestResource();
                    if(resourceNumber != -1){
                        device.doWork(resourceNumber);
                        idleTime = TactGenerator.getTime() - startWaitTime;
                        state = State.Running;
                        stopTimer();
                    }
                    else
                        System.out.println(new Date() + " Process " + me.name + " is waiting for resource");
                }
                else if(state == State.Waiting)
                {
                    idleTime = TactGenerator.getTime() - startWaitTime;
                }

            }
        };
        int timerPeriod = Configuration.TG_PERIOD / Configuration.TIMER_FREQ;
        timer.schedule(repeatedTask, timerPeriod, timerPeriod);
    }

    private void startTimer(){
        int requestDeviceTime = Utils.getRandomInteger(0,time / 2);
        var me = this;

        TimerTask repeatedTask = new TimerTask() {
            int startWaitTime = 0;

            public void run() {

                if(burstTime >= requestDeviceTime && state == State.Running){
                    state = State.Waiting;
                    startWaitTime = TactGenerator.getTime();
                    int resourceNumber = device.requestResource();
                    if(resourceNumber != -1){

                        Thread workerThread = new Thread(() -> {
                            device.doWork(resourceNumber);
                            state = State.Running;
                            stopTimer();
                        });
                        workerThread.start();

                    }
                    else
                        System.out.println(new Date() + " Process " + me.name + " is waiting for resource");
                }
                else if(state == State.Waiting)
                {
                    idleTime = TactGenerator.getTime() - startWaitTime;
                }

            }
        };
        int timerPeriod = Configuration.TG_PERIOD / Configuration.TIMER_FREQ;
        timer.schedule(repeatedTask, timerPeriod, timerPeriod);
    }

    private void stopTimer(){
        timer.cancel();
    }

    @Override
    public String toString() {
        return id +
                "{ PID='" + name + '\'' +
                ", priority=" + priority +
                ", time=" + time +
                ", memory=" + memory +
                ", timeIn=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", state=" + state +
                (core != null ? (", core number=" + core.getNumber()) : "") +
                '}' + "\n";
    }

    //public static Comparator<Process> byPriority = ((o1, o2) -> o1.priority - o2.priority);
    //public static Comparator<Process> byArrivalTime = ((o1, o2) -> o1.arrivalTime - o2.arrivalTime);
}
