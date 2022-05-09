package com.toba.app.ignite;

import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static void log(String msg) {
        System.out.println(LocalTime.now() + " ("
                + Thread.currentThread().getName() + ") " +  msg);
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        CompletableFuture<String> future
//                = new CompletableFuture<>();
//        Executors.newCachedThreadPool().submit(() -> {
//            Thread.sleep(2000);
//            future.complete("Finished");
//            return null;
//        });
//
//        log(future.get());


//        CompletableFuture<String> future1
//                = CompletableFuture.supplyAsync(() -> "Future1");
//
//        CompletableFuture<String> future2 = future1.thenApply(
//                s -> s + " + Future2");
//
//        log("future1.get(): " + future1.get());
//        log("future2.get(): " + future2.get());

        CompletableFuture<String> future31 = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"))
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " Java"));

        log(future31.get());

        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "Future1")
                .thenApply((s) -> {
                    log("Starting future1");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "!";
                });

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "Future2")
                .thenApply((s) -> {
                    log("Starting future2");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "!";
                });

        future1.thenCombine(future2, (s1, s2) -> s1 + " + " + s2)
                .thenAccept((s) -> log(s));


        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(() -> "future3")
                .thenApplyAsync((s) -> {
                    log("Starting future3");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "!";
                });

        CompletableFuture<String> future4 = CompletableFuture
                .supplyAsync(() -> "Future4")
                .thenApplyAsync((s) -> {
                    log("Starting future4");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + "!";
                });

        future3.thenCombine(future4, (s1, s2) -> s1 + " + " + s2)
                .thenAccept((s) -> log(s));

        Thread.sleep(5000);
    }
}
