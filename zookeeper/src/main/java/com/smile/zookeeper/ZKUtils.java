package com.smile.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.security.acl.AclEntry;
import java.util.concurrent.CountDownLatch;

/**
 * @作者 liutao
 * @时间 2020/3/7 14:38
 * @描述 原生客户端
 */
public class ZKUtils implements Watcher {


    private CountDownLatch downLatch = new CountDownLatch(1);
    /**
     * 节点的名称
     */
    private static final String PATH = "/leader";
    private static final int SESSION_OUT_TIME = 10000;
    private static final String CONNECTION = "127.0.0.1:2181";

    private ZooKeeper zk = null;

    /**
     * 创建链接
     * @param connection
     * @param sessionTime
     */
    public void createConnection(String connection, int sessionTime){
        this.clearConnection();
        try {
            zk = new ZooKeeper(connection, sessionTime, this);
            downLatch.await();
        } catch (InterruptedException e) {
            System.out.println("连接失败....");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("连接失败....");
            e.printStackTrace();
        }
    }

    /**
     * 释放资源，关闭链接
     */
    public void clearConnection(){
        if(null != this.zk){
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个节点
     * @param path
     * @param data
     */
    public boolean createNode(String path, String data){
        try {
            this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("创建节点：" + path + " 内容：" + data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 读取节点内容
     * @param path
     */
    public void readData(String path){
        try {
            System.out.println("读取到的数据：" + new String(this.zk.getData(path, true, null)));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新节点信息
     * @param path
     * @param data
     */
    public void setNode(String path, String data){
        try {
            this.zk.setData(path, data.getBytes(), -1);
            System.out.println("更新的节点：" + path + " 内容 ： " + data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点信息
     * @param path
     */
    public void deleteNode(String path){
        try {
            this.zk.delete(path, -1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用监听
     * @param watchedEvent
     */
    public void process(WatchedEvent watchedEvent) {
        if(Event.KeeperState.SyncConnected ==  watchedEvent.getState()){
            System.out.println("客户端连接成功");
            downLatch.countDown();
        }
    }

    public static void main(String[] args) {
        ZKUtils ut = new ZKUtils();
        ut.createConnection(CONNECTION, SESSION_OUT_TIME);
        if(ut.createNode(PATH, "这个是我第一个创建的节点")){
            System.out.println("-------------------------------------");
            ut.readData(PATH);
            System.out.println("-------------------------------------");
            ut.setNode(PATH, "更新后的数据信息");
            ut.readData(PATH);
            System.out.println("-------------------------------------");
            ut.deleteNode(PATH);
            ut.clearConnection();
        }
    }
}
