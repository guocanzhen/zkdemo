package com.guocz.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guocz
 * @date 2022/8/31 10:02
 */
@RestController
@RequestMapping("zk")
@Log4j2
public class ZkController {

    @Autowired
    private CuratorFramework curatorFramework;

    @GetMapping("createNode")
    void createNode() throws Exception {
        // 添加持久节点
        String path = curatorFramework.create().forPath("/curator-node");
        log.info(String.format("curator create node :%s successfully.", path));

        // 添加临时序号节点,并赋值数据
        String path1 = curatorFramework.create()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curator-node", "some-data".getBytes());
        log.info(String.format("curator create node :%s successfully.", path1));

        // System.in.read()目的是阻塞客户端关闭，我们可以在这期间查看zk的临时序号节点
        // 当程序结束时候也就是客户端关闭的时候，临时序号节点会消失
        System.in.read();
    }

    /**
     * 获取节点
     *
     * @throws Exception
     */
    @GetMapping("testGetData")
    public void testGetData() throws Exception {
        // 在上面的方法执行后，创建了curator-node节点，但是我们并没有显示的去赋值
        // 通过这个方法去获取节点的值会发现，当我们通过Java客户端创建节点不赋值的话默认就是存储的创建节点的ip
        byte[] bytes = curatorFramework.getData().forPath("/curator-node");
        log.info(new String(bytes));
    }

    /**
     * 修改节点数据
     *
     * @throws Exception
     */
    @GetMapping("testSetData")
    public void testSetData() throws Exception {
        curatorFramework.setData().forPath("/curator-node", "changed!".getBytes());
        byte[] bytes = curatorFramework.getData().forPath("/curator-node");
        log.info(new String(bytes));
    }

    /**
     * 创建节点同时创建⽗节点
     *
     * @throws Exception
     */
    @GetMapping("testCreateWithParent")
    public void testCreateWithParent() throws Exception {
        String pathWithParent = "/node-parent/sub-node-1";
        String path = curatorFramework.create().creatingParentsIfNeeded().forPath(pathWithParent);
        log.info(String.format("curator create node :%s successfully.", path));
    }

    /**
     * 删除节点(包含子节点)
     *
     * @throws Exception
     */
    @GetMapping("testDelete")
    public void testDelete() throws Exception {
        String pathWithParent = "/node-parent";
        curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(pathWithParent);
    }
}
