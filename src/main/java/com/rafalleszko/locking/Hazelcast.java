package com.rafalleszko.locking;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;

public class Hazelcast {
    public static void main(String[] args) throws Exception {
        var config = new ClientConfig();
        config.getNetworkConfig().addAddress("hazelcast");
        var hazelcast = HazelcastClient.newHazelcastClient(config);
        var lock = hazelcast.getCPSubsystem().getLock("my-lock");

        while (true) {
            lock.lock();
            writeToSharedResource();
            lock.unlock();
        }
    }

    private static void writeToSharedResource() throws InterruptedException {
        System.out.println("Writing to a shared resource...");
        Thread.sleep(1000);
        System.out.println("FINISHED");
    }

}
