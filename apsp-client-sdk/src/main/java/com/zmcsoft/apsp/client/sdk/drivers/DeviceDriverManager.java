package com.zmcsoft.apsp.client.sdk.drivers;

import com.zmcsoft.apsp.client.core.Global;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhouhao
 * @since 1.0
 */
public class DeviceDriverManager {
    private static DeviceDrivers DEFAULT = new DefaultDeviceDrivers();

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    public static DeviceDrivers drivers() {
        try {
            lock.readLock().tryLock(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return DEFAULT;
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void register(DeviceDriverInitialization initialization) {
        Global.executorService.submit(() -> {
            try {
                lock.writeLock().tryLock(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            try {
                initialization.init(((DefaultDeviceDrivers) drivers()));
            } finally {
                lock.writeLock().unlock();
            }

        });
    }

    static {
        String[] drivers = {
                "com.zmcsoft.apsp.client.sdk.drivers.printer.DefaultPrinterDriver",
                "com.zmcsoft.apsp.client.sdk.drivers.camera.DefaultCameraDriver"
        };
        for (String driver : drivers) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
