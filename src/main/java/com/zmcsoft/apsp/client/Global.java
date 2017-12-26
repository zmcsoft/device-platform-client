package com.zmcsoft.apsp.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Global {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*8);


}
