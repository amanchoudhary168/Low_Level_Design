package main.java.concurrency;

import java.util.concurrent.BrokenBarrierException;
/**
 * A conference is held at some place, after the conference is over people in the conference has to take the cab.
 * There are two type of people in the conference 1.Democrat 2.Republican.
 * The cab ride can have all Democrats or Republicans or two Democrats and two Republicans.
 * **/

public class UberConcurrencyProblem{

    public static void main(String[] args) throws InterruptedException {
        Car c = new Car();
        for(int i=0;i<5;i++){
            Thread t = new Thread(()->{
                try {

                    c.ride("democrat");
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },"democrat-"+i) ;
            Thread t1= new Thread(()->{
                try {

                    c.ride("republican");
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            },"republican-"+i) ;
            t1.start();
            t.start();
        }

    }


}
