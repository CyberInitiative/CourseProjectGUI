package com.company.classes;

public class Device {

    private Resource[] resources;

    public Device(){
        int randomNumber = Utils.getRandomInteger(3,5);

        resources = new Resource[randomNumber];
        for(int i = 0; i < randomNumber; i++){
            resources[i] = new Resource(i);
        }
    }

    public Resource[] getResources() {
        return resources;
    }

    public Resource getResource(int resource){
        return resources[resource];
    }

    public synchronized int requestResource(){
        for(Resource resource : resources){
            if(resource.isIdle() == true){
                resource.setIdle(false);
                System.out.println("Found idle resource " + resource.getNumber());

                return resource.getNumber();
            }
        }
        return  -1;
    }

    public void doWork(int number){
        try {
            int sleepTime = Utils.getRandomInteger(10000,20000);
            System.out.println("Do work: " + number + " Sleep time: " +  sleepTime);
            Thread.sleep(sleepTime);
            System.out.println("Do work: " + number + " We are out");
            resources[number].setIdle(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String result = "[ ";
        for (Resource resource: resources){
            result +=  resource + " is "
                    + (resource.isIdle() ? "Idle" : "Busy") + "; ";
        }
        return result + "]";
    }
}
