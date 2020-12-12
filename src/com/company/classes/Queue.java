package com.company.classes;

import java.util.*;

//Общая очередь всех процессов. Все новые процессы попадают сюда.
public class Queue {
    private ArrayList<Process> queue;
    private int lastID;

    public Queue() {
        this.queue = new ArrayList<>();
        this.lastID = 1;
    }

    public Process get(int i) {
        return queue.get(i);
    }

    public int getSize() {
        return queue.size();
    }

    public void add(Process process) {
        this.queue.add(process);
    }

    public void add() {
        Process process = new Process(this.lastID++);

        this.add(process);
    }

    public void add(final int N) {
        for (int i = 0; i < N; i++) {
            /*
            try {
                Thread.sleep(Utils.getRandomInteger(500, 3000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
            this.add();
        }
    }

    /*
    public void countReadyAndRunningProcesses(){
       for(int i = 0; i < queue.size(); i++){
           if(queue.get(i).getState() == State.Ready || queue.get(i).getState() == State.Running){
               counterReadyAndRunningProcesses++;
           }
       }
    }
     */

    public void remove(int i){
        this.queue.remove(i);
    }

    public void remove(Process process){
        this.queue.remove(process);
    }

    public synchronized void sortByPriorityAndArrivalTime(State state){
        Process[] sortedQueue = this.queue.toArray(new Process[0]);
        int temp;
        String stemp;
        for(int i = 0; i < sortedQueue.length; i++){
            if(sortedQueue[i].getState() != state.Ready)
                continue;
            for(int j = 0; j <sortedQueue.length - i - 1; j++){

                if(sortedQueue[j].getArrivalTime() > sortedQueue[j + 1].getArrivalTime()){
                    temp = sortedQueue[j].getArrivalTime();
                    sortedQueue[j].setArrivalTime(sortedQueue[j+1].getArrivalTime());
                    sortedQueue[j + 1].setArrivalTime(temp);

                    temp = sortedQueue[j].getBurstTime();
                    sortedQueue[j].setBurstTime(sortedQueue[j+1].getBurstTime());
                    sortedQueue[j + 1].setBurstTime(temp);

                    temp = sortedQueue[j].getPriority();
                    sortedQueue[j].setPriority(sortedQueue[j+1].getPriority());
                    sortedQueue[j + 1].setPriority(temp);

                    stemp = sortedQueue[j].getName();
                    sortedQueue[j].setName(sortedQueue[j+1].getName());
                    sortedQueue[j + 1].setName(stemp);
                }
                if(sortedQueue[j].getArrivalTime() == sortedQueue[j+1].getArrivalTime()){
                    if(sortedQueue[j].getPriority() > sortedQueue[j+1].getPriority()){
                        temp = sortedQueue[j].getArrivalTime();
                        sortedQueue[j].setArrivalTime(sortedQueue[j+1].getArrivalTime());
                        sortedQueue[j + 1].setArrivalTime(temp);

                        temp = sortedQueue[j].getBurstTime();
                        sortedQueue[j].setBurstTime(sortedQueue[j+1].getBurstTime());
                        sortedQueue[j + 1].setBurstTime(temp);

                        temp = sortedQueue[j].getPriority();
                        sortedQueue[j].setPriority(sortedQueue[j+1].getPriority());
                        sortedQueue[j + 1].setPriority(temp);

                        stemp = sortedQueue[j].getName();
                        sortedQueue[j].setName(sortedQueue[j+1].getName());
                        sortedQueue[j + 1].setName(stemp);
                    }
                }
            }
        }
    }
/*
    public int dataCounter(Queue queue){
        int counter = 0;
        for(int i = 0; i < queue.getSize(); i++){
            counter++;
        }
        return counter;
    }
 */

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public int getCountByState(State state){
        int count = 0;
        for(int i = 0 ; i < queue.size(); i++){
            if(queue.get(i).getState() == state)
                count++;
        }
        return count;
    }

    public int getUsedMemory(){
        int usedMemory = 0;
        for (int i = 0; i < queue.size(); i++){
            usedMemory += queue.get(i).getMemory();
        }
        return usedMemory;
    }

    @Override
    public String toString() {
        String result = "";
        for (Process process : queue) {
            result += process;
        }
        return result;
    }
}
