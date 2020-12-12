package com.company.classes;

import java.util.Comparator;

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
}


