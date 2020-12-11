package com.company.classes;

public class Core extends Resource {

    public Core(int number) {
        super(number);
    }

    @Override
    public String toString() {
        return "core #" + this.resourceNumber;
    }
}
