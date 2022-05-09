package com.toba.app.ignite;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureAnyOfTest {
    public static void log(String msg) {
        System.out.println(msg);
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log("starting future1");
                    return "Future1";
                });

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> {
                    log("starting future2");
                    return "Future2";
                });

        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(() -> {
                    log("starting future3");
                    return "Future3";
                });

        CompletableFuture.anyOf(future1, future2, future3)
                .thenAccept(s -> log("Result: " + s));
    }
}
