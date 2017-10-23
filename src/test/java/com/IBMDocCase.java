package com;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by zengjie on 17/9/7.
 */
public class IBMDocCase {

    public static void main(String[] arg){
        System.out.print("Get set...");
        IntStream.range(0, 4).forEach(i -> System.out.print(i + "..."));


        System.out.println("Get set...");

        IntStream.rangeClosed(0,4).forEach(value -> System.out.print(value+ "..."));


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        IntStream.range(0, 5)
                .forEach(i ->
                        executorService.submit(new Runnable() {
                            public void run() {
                                System.out.println("Running task " + i);
                            }
                        }));

        IntStream.range(0, 5)
                .forEach(i ->
                        executorService.submit(() -> System.out.println("Running task " + i)));
        long a=92233720368547751L;
        executorService.shutdown();

    }
}
