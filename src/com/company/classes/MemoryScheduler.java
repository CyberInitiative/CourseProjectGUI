package com.company.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MemoryScheduler {
    private static ArrayList<MemoryBlock> memoryBlocks = new ArrayList<>();
    private MemoryBlock biggestBlock;

    public MemoryBlock getBiggestBlock() {
        return biggestBlock;
    }

    public void setBiggestBlock(MemoryBlock biggestBlock) {
        this.biggestBlock = biggestBlock;
    }

    /*
    public void releaseMemoryBLock(MemoryBlock memoryBlock) {
        memoryBlocks.remove(memoryBlock);

        return;
    }
     */

    public MemoryBlock fillMemoryBLock(int memorySize) {
        //findFreeBlock(process);
        biggestBlock = Collections.max(memoryBlocks, Comparator.comparing(s -> s.getAvailableMemory()));

        if(memorySize <= biggestBlock.getAvailableMemory()){
            biggestBlock.setAvailableMemory(biggestBlock.getAvailableMemory() - memorySize);
            //process.setState(State.Ready);
            //String key = process.getName();
            //biggestBlock.keys.add(key);
            return biggestBlock;
        }
        return null;
    }

/*
    private int findFreeBlock(Process process) {
        biggestBlock = Collections.max(memoryBlocks, Comparator.comparing(s -> s.availableMemory));

        if(process.getMemory() <= biggestBlock.availableMemory){
            biggestBlock.availableMemory = biggestBlock.availableMemory - process.getMemory();
            process.setState(State.Ready);
            String key = process.getName();
            biggestBlock.keys.add(key);
        }
        return 0;
    }

    public void releaseBlock(){
        Process process = new Process();
        if(process.getState() == State.Terminated){
            for(int i = 0; i < memoryBlocks.size(); i++){
                for(int j = 0; j < biggestBlock.keys.size(); j++){
                    if(process.getName().equals(biggestBlock.keys.get(j)))
                    {
                        biggestBlock.availableMemory = biggestBlock.availableMemory + process.getMemory();
                        biggestBlock.keys.remove(i);
                    }
                }
            }
        }
    }



    private static int findFreeBlock(int size) {
        System.out.println(print());
        memoryBlocks.sort(MemoryBlock.byEnd);
        System.out.println(print());

        ArrayList<MemoryBlock> tempMemoryBlocks = new ArrayList<>();

        for(int i = 0; i < memoryBlocks.size()-1; i++){
            if (memoryBlocks.get(i+1).start-memoryBlocks.get(i).end > size) {
                MemoryBlock tempMemoryBlock = new MemoryBlock(memoryBlocks.get(i).end, memoryBlocks.get(i+1).start);
                tempMemoryBlocks.add(tempMemoryBlock);
                System.out.println(tempMemoryBlock);
            }
        }
        if (Configuration.memoryVolume-memoryBlocks.get(memoryBlocks.size()-1).end > size){
            MemoryBlock tempMemoryBlock = new MemoryBlock(memoryBlocks.get(memoryBlocks.size()-1).end, Configuration.memoryVolume);
            tempMemoryBlocks.add(tempMemoryBlock);
            System.out.println(tempMemoryBlock);
        }
        return  0;
    }
 */

    public void add(MemoryBlock memoryBlock){
        memoryBlocks.add(memoryBlock);
    }

    public MemoryBlock get(int i){
        return memoryBlocks.get(i);
    }

    public int getSize(){
        return memoryBlocks.size();
    }

    public static String print() {
        String result = "[";
        for(MemoryBlock memoryBlock : memoryBlocks){
            result+=memoryBlock+" ";
        }
        return result + "]";
    }



    @Override
    public String toString() {
        String result = "[ ";
        for (int i = 0; i < memoryBlocks.size(); i++){
            result+=memoryBlocks.get(i) + " ";
        }
        return result + "]";
    }
}