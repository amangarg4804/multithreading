package com.aman.multithreading.scaler;

// Print numbers 1 to 100. Each number should be printed in its own thread.
public class PrintNumbersInSeparateThreads {
    public static void main(String[] args) {
        // each thread can run in different CPU core,hence can be executed parallel.
        // so we can't predict the order of printed numbers
        for(int i=1; i<=100; i++) {
            PrintNum printNum = new PrintNum(i);
            Thread t = new Thread(printNum);
            t.start();
        }
        System.out.println("All the numbers are printed");// this gets printed before all the numbers are printed because all number are being printed in different threads and this statement is running in main thread
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


