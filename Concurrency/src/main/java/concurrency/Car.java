package main.java.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car {
    CyclicBarrier barrier = new CyclicBarrier(4);
    Lock lock = new ReentrantLock();
    AtomicInteger democratCount = new AtomicInteger();
    AtomicInteger republicanCount = new AtomicInteger();
    int counter = 0;


    public void ride(String type) throws BrokenBarrierException, InterruptedException {

        if(democratCount.get()+republicanCount.get() <= 4){
            //System.out.println(Thread.currentThread().getName()+"-Trying to ride.");
            boolean shouldWait = false;
            try{
                lock.lock();
                if(totalCount()<4){
                    if(totalCount() < 2 ){
                        incrementCount(type);
                        System.out.println("Block-1"+Thread.currentThread().getName()+",type="+get(type));
                        shouldWait = true;
                    }else if(democratCount.get() >0 && democratCount.get()%2 != 0 && type.equalsIgnoreCase("democrat")){
                       incrementCount(type);
                        System.out.println("Block-2"+Thread.currentThread().getName()+",type="+get(type));
                        shouldWait = true;
                    }else if(republicanCount.get() >0 && republicanCount.get()%2 != 0 && type.equalsIgnoreCase("republican")){
                        incrementCount(type);
                        System.out.println("Block-3"+Thread.currentThread().getName()+",type="+get(type));
                        shouldWait = true;
                    }else if(republicanCount.get() == 2 && democratCount.get() == 0 && type.equalsIgnoreCase("republican")){
                        incrementCount(type);
                        System.out.println("Block-4"+Thread.currentThread().getName()+",type="+get(type));
                        shouldWait = true;
                    }else if(democratCount.get() == 2 && republicanCount.get() == 0 && type.equalsIgnoreCase("democrat")){
                        incrementCount(type);
                        System.out.println("Block-5"+Thread.currentThread().getName()+",type="+get(type));
                        shouldWait = true;
                    }
                }
            }catch (Exception e){
                System.out.println("Exception occurred"+e);
            }finally {
                lock.unlock();
            }
            if(shouldWait){
                System.out.println(Thread.currentThread().getName()+"-waiting");
                barrier.await();
                System.out.println("democrat="+democratCount+",republicanCount= "+republicanCount);
                barrier.reset();
            }

        }else{
            System.out.println("No space is left in the cab");
        }
    }

    public void incrementCount(String type){
        if(type.equalsIgnoreCase("democrat"))
            democratCount.incrementAndGet();
        else
            republicanCount.incrementAndGet();
    }

    public void decrementCount(String type){
        if(type.equalsIgnoreCase("democrat"))
            democratCount.decrementAndGet();
        else
            republicanCount.decrementAndGet();
    }

    public int get(String type){
        if(type.equalsIgnoreCase("democrat"))
            return democratCount.get();
        else
           return  republicanCount.get();
    }


    public int totalCount(){
        return democratCount.get()+republicanCount.get();
    }

}

