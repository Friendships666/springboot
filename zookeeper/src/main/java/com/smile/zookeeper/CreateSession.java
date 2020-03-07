package com.smile.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @作者 liutao
 * @时间 2020/3/7 15:18
 * @描述
 */
public class CreateSession {

    public static final String SERVER = "127.0.0.1:2181";

    public static final int SESSION_TIMEOUT = 30000;

    @Test
    public void createConnet(){
        try {
            ZooKeeper zk = new ZooKeeper(SERVER,SESSION_TIMEOUT,null);
            System.out.println(zk.getState());
            zk.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static CountDownLatch downLatch = new CountDownLatch(1);

    @Test
    public void createConnetWatcher()  {
        try {
            ZooKeeper zk = new ZooKeeper(SERVER, SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if(watchedEvent.getState() == Event.KeeperState.SyncConnected){
                        System.out.println("链接已经成功");
                        downLatch.countDown();
                    }
                }
            });
            downLatch.await();
            System.out.println(zk.getState());
            zk.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
