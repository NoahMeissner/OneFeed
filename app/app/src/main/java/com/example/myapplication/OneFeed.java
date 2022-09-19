package com.example.myapplication;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OneFeed extends Application {
    public ExecutorService executorService = Executors.newFixedThreadPool(4);
}
