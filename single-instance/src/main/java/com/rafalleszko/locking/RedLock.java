package com.rafalleszko.locking;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedLock {
    public static void main(String[] args) throws InterruptedException {
        var lock = new RedissonRedLock(
                client("redis-1").getLock("lock1"),
                client("redis-2").getLock("lock2"),
                client("redis-3").getLock("lock3")
        );

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
