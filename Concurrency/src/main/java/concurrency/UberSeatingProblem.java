package main.java.concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberSeatingProblem {

    int democrat;
    int republican;

    ReentrantLock lock = new ReentrantLock();
    Semaphore dSemaphore = new Semaphore(0);
    Semaphore rSemaphore = new Semaphore(0);
    CyclicBarrier barrier = new CyclicBarrier(4);


    public static void main(String[] args) {
        runTest();
    }

    void seatDemocrat() throws InterruptedException, BrokenBarrierException {

            boolean rideLeader = false;
            lock.lock();
            democrat++;
            if(democrat == 4){
               dSemaphore.release(3);
               democrat-=4;
               rideLeader = true;
            }else if(democrat == 2 && republican>=2){
                dSemaphore.release(1);
                rSemaphore.release(2);
                democrat-=2;
                republican-=2;
                rideLeader = true;
            }else{
                lock.unlock();
                dSemaphore.acquire();
            }
            seated();
            barrier.await();
            if(rideLeader){
                drive();
                lock.unlock();
            }
    }

    void seatRepublican() throws InterruptedException, BrokenBarrierException {

        boolean rideLeader = false;
        lock.lock();
        republican++;
        if(republican == 4){
            rSemaphore.release(3);
            republican-=4;
            rideLeader = true;
        }else if(democrat >= 2 && republican==2){
            dSemaphore.release(2);
            rSemaphore.release(1);
            democrat-=2;
            republican-=2;
            rideLeader = true;
        }else{
            lock.unlock();
            rSemaphore.acquire();
        }
        seated();
        barrier.await();
        if(rideLeader){
            drive();
            lock.unlock();
        }
    }
    void seated(){
        System.out.println(Thread.currentThread().getName() +" seated");
    }

    void drive(){
        System.out.println("Cab Started");
    }

    public static void runTest(){
        final UberSeatingProblem uberSeatingProblem = new UberSeatingProblem();
        Set<Thread> allThreads = new HashSet<>();
        for(int i=0;i<14;i++){
            Thread dThread = new Thread(()->{
            try{
                    uberSeatingProblem.seatDemocrat();
            }catch(InterruptedException ie){
                System.out.println("We have a problem");
            } catch (BrokenBarrierException e) {
                throw new RuntimeException(e);
            }},"Democrat -"+i);

            Thread rThread = new Thread(()->{
                try{
                    uberSeatingProblem.seatRepublican();
                }catch(InterruptedException ie){
                    System.out.println("We have a problem");
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            },"Republican -"+i);
            allThreads.add(dThread);
            allThreads.add(rThread);
        }

        for(Thread t : allThreads)
            t.start();



    }
}
