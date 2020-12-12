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

    public int getAvailableMemory(){
        int availableMemory = 0;
        for(var memoryBlock : memoryBlocks){
            availableMemory += memoryBlock.getAvailableMemory();
        }
        return availableMemory;
    }

    public MemoryBlock fillMemoryBLock(int memorySize) {
        biggestBlock = Collections.max(memoryBlocks, Comparator.comparing(s -> s.getAvailableMemory()));

        if(memorySize <= biggestBlock.getAvailableMemory()){
            biggestBlock.setAvailableMemory(biggestBlock.getAvailableMemory() - memorySize);
            return biggestBlock;
        }
        return null;
    }

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