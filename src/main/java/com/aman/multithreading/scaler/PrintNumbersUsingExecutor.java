package com.aman.multithreading.scaler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrintNumbersUsingExecutor {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);//here we use only 1 thread so the order if printing is guaranteed
        for(int i=1; i<= 100; i++) {
            PrintNum printNum = new PrintNum(i);
            es.submit(printNum);
        }
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
