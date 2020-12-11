package com.company.classes;
import java.util.TimerTask;

public class TactGenerator extends TimerTask {
    public static int getTime() {
        return time;
    }

    public static void incTime(int tact)
    {
        time+=tact;
    }

    public static void incTime()
    {
        time++;
    }

    private static int time;

    @Override
    public void run() {
        //while (true)
        {
            /*
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

             */
            incTime();
        }
    }
}
