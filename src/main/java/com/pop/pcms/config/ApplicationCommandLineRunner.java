package com.pop.pcms.config;

import com.pop.pcms.msg.LiveMsgServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 应用工程启动后执行LiveMsgServer服务 执行websocket监听
 * Created by zhangjinye on 2017/3/8.
 */
@Component
public class ApplicationCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ApplicationCommandLineRunner.class);

    @Autowired
    private LiveMsgServer liveMsgServer;

    //定3个线程组成的线程池
    public static final ExecutorService es= Executors.newFixedThreadPool(3);

    @Override
    public void run(String... strings) throws Exception {
        //单独起一个线程去启动netty线程与端口监听
        es.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    liveMsgServer.runServer();
                } catch (Exception e) {
                    log.error("ApplicationStartup-runserver失败:{}", e);
                }
            }
        });
    }
}
