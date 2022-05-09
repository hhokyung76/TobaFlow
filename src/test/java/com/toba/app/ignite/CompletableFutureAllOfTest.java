package com.toba.app.ignite;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureAllOfTest {
    public static void log(String msg) {
        System.out.println(msg);
    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture
                .supplyAsync(() -> "Future1");

        CompletableFuture<String> future2 = CompletableFuture
                .supplyAsync(() -> "Future2");

        CompletableFuture<String> future3 = CompletableFuture
                .supplyAsync(() -> "Future3");

        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(future1, future2, future3);

        log("get() : " + combinedFuture.get());
        log("future1.isDone() : " + future1.isDone());
        log("future2.isDone() : " + future2.isDone());
        log("future3.isDone() : " + future3.isDone());

        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" + "));
        log("Combined: " + combined);

    }
}
