package com.rafalleszko.locking;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;

public class Fenced {
    public static void main(String[] args) throws Exception {
        var config = new ClientConfig();
        config.getNetworkConfig().addAddress("hazelcast");
        var hazelcast = HazelcastClient.newHazelcastClient(config);
        var lock = hazelcast.getCPSubsystem().getLock("my-lock");

        while (true) {
            long token = lock.lockAndGetFence();
            writeToSharedResource(token);
            lock.unlock();
        }
    }

    private static void writeToSharedResource(long token) throws InterruptedException {
        System.out.println("Writing to a shared resource with token: " + token);
        Thread.sleep(1000);
        System.out.println("FINISHED");
    }

}
