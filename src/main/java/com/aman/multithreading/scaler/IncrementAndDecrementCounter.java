package com.aman.multithreading.scaler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IncrementAndDecrementCounter {
    public static void main(String[] args) throws InterruptedException {
        // counter
        // two parallel threads
        // one thread add 1 to 10000 to counter
        // another thread subtracts 1 to 100 from counter
        // expected final value =0
        Counter counter = new Counter();
        Adder adder = new Adder(counter);
        Subtracter subtracter = new Subtracter(counter);
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
        System.out.println(counter.value);
    }


}

class Counter {
    int value;

}
class Adder implements Runnable {
    Counter counter;

    public Adder(Counter counter) {
        this.counter = counter;
    }
    @Override
    public void run() {
        for(int i=0; i<10000; i++) {
            counter.value+= 1;
        }
    }
}

class Subtracter implements Runnable {
    Counter counter;

    public Subtracter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for(int i=0; i<10000; i++) {
            counter.value-= 1;
        }
    }
}
