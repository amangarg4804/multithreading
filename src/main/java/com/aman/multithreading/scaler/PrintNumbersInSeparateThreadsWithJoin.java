package com.aman.multithreading.scaler;

import java.util.ArrayList;
import java.util.List;

public class PrintNumbersInSeparateThreadsWithJoin {
    public static void main(String[] args) throws InterruptedException {
        // each thread can run in different CPU core,hence can be executed parallel.
        // so we can't predict the order of printed numbers
        List<Thread> threads = new ArrayList<>();
        for(int i=1; i<=100; i++) {
            PrintNum printNum = new PrintNum(i);
            Thread t = new Thread(printNum);
            threads.add(t);
            t.start();
        }
        for(Thread t: threads) {
            t.join();
        }
        System.out.println("All the numbers are printed");
        //By using join, the above statement is guaranteed to be printed after all threads in threads list have executed.
    }

    private static class PrintNum implements Runnable {
        int num;

        public PrintNum(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            System.out.println(num);
        }
    }

}
