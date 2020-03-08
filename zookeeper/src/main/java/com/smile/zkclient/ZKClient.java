package com.smile.zkclient;

import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @作者 liutao
 * @时间 2020/3/8 8:53
 * @描述 zkClient客户端
 */
public class ZKClient {

    private static final String SERVER_ADDR = "127.0.0.1:2181";
    private static final int SESSION_TIMEOUT = 10000; // 10S

    public static void main(String[] args) {
        ZkClient client = new ZkClient(SERVER_ADDR, SESSION_TIMEOUT);
        // 创建节点和删除节点
        // 添加一个临时节点
        // createEphemeralSequential : 临时带序号的节点
        client.createEphemeral("/temp");
//        // 创建一个持久节点
//        // createPersistentSequential ： 持久带序号的节点
        client.createPersistent("/super/aaa/bbbbb", true);
        // 删除 一个没有子节点的节点
        client.delete("/temp");
        // 遍历删除节点以及节点下的所有子节点
        client.deleteRecursive("/super");

        //--------------------给节点添加内容-------------------
        client.createPersistent("/supper","supper父类节点");
        client.createPersistent("/supper/aaaaa", "/supper/aaaaa子节点信息");
        client.createPersistent("/supper/bbbbb", "/supper/bbbbb子节点信息");

        List<String> list =  client.getChildren("/supper");
        if(list != null && list.size() > 0){
            for(String p : list){
                String node = "/supper/" + p;
                System.out.println("节点：" + node + " 节点内容：" + client.readData(node).toString());
            }
        }

        //-----------------更新节点数据--------------------
        System.out.println("是否存在/supper/aaaaa节点：" + client.exists("/supper/aaaaa"));
        client.writeData("/supper/aaaaa", "/supper/aaaaa子节点信息更新");
        System.out.println("更新数据：" + client.readData("/supper/aaaaa"));

        client.close();
    }


}
