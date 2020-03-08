package com.smile.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.junit.Test;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/8 13:19
 * @描述 zkClient的监听
 * 结论：
 *  可以对父节点，子节点进行监听，不能对孙子节点进行监听，若要对孙子节点进行监听，则需要在子节点创建成功后，对子节点进行监听
 *  对节点进行操作，才可以触发监听，比如创建，修改，删除；对节点读取，判断节点是否存在则不会触发该节点的监听
 */
public class ZKClientWatcher {

    private static final String SERVER_ADDR = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 10000; // 10S

    @Test
    public void parentNodeWatcher() throws InterruptedException {
        //ZkClient zkClient = new ZkClient(SERVER_ADDR, SESSION_TIMEOUT);
        // 一般推荐使用下面这种方式
        ZkClient zkClient = new ZkClient(new ZkConnection(SERVER_ADDR), SESSION_TIMEOUT);
        zkClient.subscribeDataChanges("/listener", new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("更新数据: 节点 -- " + s + " 更新的数据：" + o);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("删除数据: 节点 -- " + s );
            }
        });

        zkClient.createPersistent("/listener", "测试父类的监听");

        System.out.println("读取的数据：" + zkClient.readData("/listener").toString());

        zkClient.writeData("/listener", "测试父类的监听 -- 更新数据");
        Thread.sleep(1000);
        zkClient.delete("/listener");
        Thread.sleep(3000);
        zkClient.close();
    }


    @Test
    public void childrenNodeWatcher() throws InterruptedException {
        //ZkClient zkClient = new ZkClient(SERVER_ADDR, SESSION_TIMEOUT);
        // 一般推荐使用下面这种方式
        ZkClient zkClient = new ZkClient(new ZkConnection(SERVER_ADDR), SESSION_TIMEOUT);

        // 对父节点下的子节点进行监听 ， 无法对孙子节点进行监听，若是需要进行孙子节点监听需要进行方法抽取，将节点改为子节点
        // 即创建子节点成功后，对子节点进行监听
        zkClient.subscribeChildChanges("/listener", new IZkChildListener() {
            public void handleChildChange(String s, List<String> list) throws Exception {
                System.out.println("父类节点：" + s);
                System.out.println("子节点：" + list);
            }
        });

        // 判断该节点是否已经存在
        if(zkClient.exists("/listener")){
            // 判断该节点下是否存在子节点
            if(zkClient.getChildren("/listener").size() > 0){
                // 存在子节点，遍历删除节点
                zkClient.deleteRecursive("/listener");
            }else{
                zkClient.delete("/listener");
            }
        }
        zkClient.createPersistent("/listener", "测试父类的监听");
        System.out.println("-------------创建父节点------------");
        Thread.sleep(500);
        zkClient.createPersistent("/listener/aaaa", "测试子节点的监听");
        System.out.println("-------------创建子节点------------");
        Thread.sleep(500);
        zkClient.createPersistent("/listener/aaaa/bbbb", "测试对孙子节点的监听");
        System.out.println("-------------创建孙子节点------------");

        Thread.sleep(1000);
        // zkClient.delete("/listener"); 会报错 ， 需要使用便利删除节点
        if(zkClient.getChildren("/listener").size() > 0){
            Thread.sleep(1000);
            System.out.println("----------查看该节点是否存在子节点----------");
            zkClient.deleteRecursive("/listener");
            System.out.println("----------遍历删除节点----------");
        }else{
            zkClient.delete("/listener");
            System.out.println("----------删除节点----------");
        }
        Thread.sleep(3000);
        zkClient.close();
    }

}
