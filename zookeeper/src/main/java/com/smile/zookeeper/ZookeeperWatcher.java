package com.smile.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @作者 liutao
 * @时间 2020/3/7 15:46
 * @描述
 */
public class ZookeeperWatcher implements Watcher {

    private AtomicInteger count = new AtomicInteger(0);
    private CountDownLatch downLatch = new CountDownLatch(1);

    private String value = System.currentTimeMillis() + "";


    public void process(WatchedEvent watchedEvent) {
        // 连接状态
        Watcher.Event.KeeperState state = watchedEvent.getState();
        //事件类型
        Event.EventType type = watchedEvent.getType();
        // 节点
        String path = watchedEvent.getPath();

        System.out.println("收到Watcher的通知");
        System.out.println("事件类型：" + type);
        System.out.println("监听的节点：" + path);

        String watcherLog = "[Watcher-->" + count.incrementAndGet() + "]";

        if(Event.KeeperState.SyncConnected == state){
            // 连接成功
            if(Event.EventType.None == type){
                System.out.println("链接成功");
                downLatch.countDown();
            }else if(Event.EventType.NodeCreated == type){
                System.out.println("创建一个节点" + path);
                // 对这个节点进行监听
            }else if(Event.EventType.NodeDataChanged == type){
                System.out.println("更新一个节点" + path);
                // 对这个节点进行监听
            }else if(Event.EventType.NodeChildrenChanged == type){
                System.out.println("更新子节点");
                // 对这个节点进行监听
            }else if(Event.EventType.NodeDeleted == type){
                System.out.println("删除一个节点");
                // 对这个节点进行监听
            }
        }else if(Event.KeeperState.Disconnected == state){
            System.out.println("关闭连接");
        }else if(Event.KeeperState.AuthFailed == state){
            System.out.println("授权失败");
        }else if(Event.KeeperState.Expired == state){
            System.out.println("会话超时");
        }
    }
}
