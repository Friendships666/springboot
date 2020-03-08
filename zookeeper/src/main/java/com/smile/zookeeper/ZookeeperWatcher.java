package com.smile.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
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

    private static final String SERVER_IP_PORT = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 30000;
    private static final String ROOT_NODE = "/zkclient";
    private static final String ROOT_CLIEND_NODE = "/zkclient/childer";

    private static ZookeeperWatcher watcher = null;

    private ZooKeeper zk = null;

    /**
     * 创建一个连接
     * @param server
     * @param sessionTime
     * @return
     */
    public boolean createConnection(String server, int sessionTime){
        this.closeConnection();
        try {
            zk = new ZooKeeper(server, sessionTime, this);
            System.out.println("创建一个连接成功");
            downLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }


    public void createNode(String nodeName, String data){
        try {
            // zk中的Watcher都是一次性的，所以每次创建都需要进行重新监听
            this.exists(nodeName, true);
            // 节点，节点内容，权限，节点类型
            // 节点类型： PERSISTENT：持久节点 PERSISTENT_SEQUENTIAL：持久带序号
            // EPHEMERAL：临时节点  EPHEMERAL_SEQUENTIAL：临时带序号
            this.zk.create(nodeName, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断指定节点是否存在
     * @param nodeName 节点路径
     */
    public Stat exists(String nodeName, boolean needWatch) {
        try {
            return this.zk.exists(nodeName, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 更新节点数据信息
     * @param nodeName
     * @param data
     * @return
     */
    public boolean writeNode(String nodeName, String data){
        try {
            this.zk.setData(nodeName, data.getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 读取节点信息
     * @param nodeName
     * @param needWatcher 是否进行监听
     * @return
     */
    public String readNodeInfo(String nodeName, boolean needWatcher){
        try {
            return new String(this.zk.getData(nodeName, needWatcher, null));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取节点信息
     * @param nodeName
     * @param needWatcher 是否进行监听
     * @return
     */
    public List<String> getChildrens(String nodeName, boolean needWatcher){
        try {
            return this.zk.getChildren(nodeName, needWatcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除节点数据
     * @param nodeName
     * @return
     */
    public boolean deleteNode(String nodeName){
        try {
            if(this.exists(nodeName, false) != null) {
                this.zk.delete(nodeName, -1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 关闭连接
     */
    public void closeConnection(){
        if(this.zk != null){
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



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

        System.out.println("----------------------------------");
    }


    public static void main(String[] args) {
        watcher = new ZookeeperWatcher();
        if(watcher.createConnection(SERVER_IP_PORT, SESSION_TIMEOUT)){
            // 连接成功
            // 设置节点信息
            watcher.createNode(ROOT_NODE, "这是原生客户端的用法");
            // 读取节点信息
            watcher.readNodeInfo(ROOT_NODE, true);
            // 更新数据
            watcher.writeNode(ROOT_NODE, "进行了数据的更新");
            // 再次进行监听
            watcher.readNodeInfo(ROOT_NODE, true);
            // 创建一个节点下的子节点信息
            watcher.createNode(ROOT_CLIEND_NODE, "这是一个子节点信息");
            // 读取节点信息
            watcher.readNodeInfo(ROOT_CLIEND_NODE, true);
            watcher.closeConnection();
        }
    }
}
