package com.lyq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestForConcurrent {
    public static long run(HelloService helloService, int threadCnt, int taskCnt) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss:SSS z");
        Date beginDate = new Date(System.currentTimeMillis());
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < threadCnt; i++) {
            threadList.add(new Thread(() -> {
                Hello h = new Hello("111", "222");
                for (int j = 0; j < taskCnt; j++) {
                    helloService.hello(h);
                }
            }));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Date dateEnd = new Date(System.currentTimeMillis());
        return dateEnd.getTime() - beginDate.getTime();
    }
}
