package com.aman.multithreading.scaler.adderSubtractorWithSemaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class IncrementAndDecrementCounterThreadSafe {
    public static void main(String[] args) throws InterruptedException {
        // counter
        // two parallel threads
        // one thread add 1 to 10000 to counter
        // another thread subtracts 1 to 100 from counter
        // expected final value =0
        Counter counter = new Counter();
        Semaphore semaphore = new Semaphore(1); // A semaphore with 1 permit is a mutex
        Adder adder = new Adder(counter, semaphore);
        Subtracter subtracter = new Subtracter(counter, semaphore);
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.submit(adder);
        es.submit(subtracter);
//        synchronized (adderReturn) {
// wait can't be called without synchronized.
// The video was calling wait() on adderReturn and subReturn but it doesn't work for us. It throws IllegalMonitorStateException.
        // And when we use synchronized, the main thread starts waiting. Wait() keeps on waiting infinitely waiting for notify() call.


//            adderReturn.wait();
//        }
//        synchronized (subReturn){
//            subReturn.wait();
//        }
        es.awaitTermination(5, TimeUnit.SECONDS); // awaitTermination is a blocking call. waiting for 5 seconds to complete the submitted tasks.
        es.shutdown(); // if we don't add a shutdown call, the program never finishes.
        System.out.println(counter.getValue());
    }



}
 class Counter {
    private int value;

     public int getValue() {
         return value;
     }

     public Counter setValue(int value) {
         this.value = value;
         return this;
     }
 }

 class Adder implements Runnable {
    Counter counter;
    Semaphore semaphore;

    public Adder(Counter counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
//                semaphore.release(); // Note that a semaphore can be released by this thread although it was acquired by
//                another thread.
//                If we add this line, we don't get 0 result
                semaphore.acquire();
                int val = counter.getValue();
                counter.setValue(val+1);
                semaphore.release();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

 class Subtracter implements Runnable {
    Counter counter;
    Semaphore semaphore;

    public Subtracter(Counter counter, Semaphore semaphore) {
        this.counter = counter;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            try {
                semaphore.acquire();
                int val = counter.getValue();
                counter.setValue(val-1);
                semaphore.release();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}