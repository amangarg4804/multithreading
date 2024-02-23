package com.aman.multithreading.scaler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MergeSort {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> input = List.of(12, 15, 13, 56,89, 220, 100, 110);
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Integer>> future = es.submit(new MergeSortCallable(input));
        System.out.println(future.get());
    }

    private static final class MergeSortCallable implements Callable<List<Integer>> {

        private List<Integer> input;

        public MergeSortCallable(List<Integer> input) {
            this.input = input;
        }

        @Override
        public List<Integer> call() throws Exception {
            if(input.size()== 0) {
                return input;
            }
            if(input.size()==1) {
                return input;
            }
            int mid = input.size()/2; // {10,22,12} = 3/2 = 1
            // {5, 3} = 2/2= 1
            List<Integer> leftList = input.subList(0, mid);
            List<Integer> rightList = input.subList(mid, input.size());
            ExecutorService es = Executors.newFixedThreadPool(2);
            // sort both lists
            Future<List<Integer>> leftSortedFuture = es.submit(new MergeSortCallable(leftList));
            Future<List<Integer>> rightSortedFuture = es.submit(new MergeSortCallable(rightList));
            List<Integer> leftSorted = leftSortedFuture.get();
            List<Integer> rightSorted = rightSortedFuture.get();
            List<Integer> result = new ArrayList<>();
            int resultIndex =0;
            int leftIndex = 0;
            int rightIndex = 0;
            while (resultIndex < input.size()) {
                if(rightIndex == rightSorted.size()) {
                    result.add(leftSorted.get(leftIndex++));
                } else if(leftIndex == leftSorted.size() ) {
                    result.add(rightSorted.get(rightIndex++));
                }else if(leftSorted.get(leftIndex) < rightSorted.get(rightIndex)) {
                    result.add(leftSorted.get(leftIndex++));
                } else  {
                    result.add(rightSorted.get(rightIndex++));
                }
                resultIndex++;
            }
            return result;
        }
    }
}
