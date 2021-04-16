package com.rafalleszko.locking;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;

public class Main {
    public static void main(String[] args) throws Exception {
        var config = new ClientConfig();
        config.getNetworkConfig().addAddress("hazelcast");
        var hazelcast = HazelcastClient.newHazelcastClient(config);
        var lock = hazelcast.getCPSubsystem().getLock("my-lock");

        while (true) {
            lock.lock();
            writetoSharedResource();
            lock.unlock();

            sleep();
        }
    }

    private static void writetoSharedResource() {
        System.out.println("Writing to a shared resource");
    }

    private static void sleep() throws InterruptedException {
        Thread.sleep(1000);
    }
}
