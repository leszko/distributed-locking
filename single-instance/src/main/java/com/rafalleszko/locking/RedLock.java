package com.rafalleszko.locking;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedLock {
    public static void main(String[] args) throws InterruptedException {
        var redis1 = client("redis-1");
        var redis2 = client("redis-2");
        var redis3 = client("redis-3");

        var lock = new RedissonRedLock(redis1.getLock("lock1"), redis2.getLock("lock2"), redis3.getLock("lock3"));

        while (true) {
            lock.lock();
            writetoSharedResource();
            lock.unlock();

            sleep();
        }
    }

    private static RedissonClient client(String server) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + server + ":6379");
        return Redisson.create(config);
    }

    private static void writetoSharedResource() {
        System.out.println("Writing to a shared resource");
    }

    private static void sleep() throws InterruptedException {
        Thread.sleep(1000);
    }
}
