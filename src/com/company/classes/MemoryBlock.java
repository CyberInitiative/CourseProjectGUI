package com.company.classes;

import java.util.Comparator;
//блок памяти
public class MemoryBlock {
    private int availableMemory;

    public static Comparator<MemoryBlock> byAvailableMemorySize = ((o1, o2) -> o2.availableMemory - o1.availableMemory);

    public MemoryBlock(int availableMemory){
        this.availableMemory = availableMemory;
    }

    public int getAvailableMemory() {
        return availableMemory;
    }

    public void setAvailableMemory(int availableMemory) {
        this.availableMemory = availableMemory;
    }

    @Override
    public String toString() {
        return "{" + availableMemory + '}';
    }
    /*
    int start;
    int end;

    @Override
    public String toString() {
        return "{" + start + ", " + end + '}';
    }

    public static Comparator<MemoryBlock> byEnd = ((o1, o2) -> o1.end - o2.end);

    public MemoryBlock(int start,int end){
        this.start = start;
        this.end = end;
    }

     */
}


