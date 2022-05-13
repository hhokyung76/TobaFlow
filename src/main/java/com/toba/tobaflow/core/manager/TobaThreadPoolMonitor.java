package com.toba.tobaflow.core.manager;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class TobaThreadPoolMonitor {

    @Autowired
    private TobaFlowManager tobaFlowManager;

    private ThreadPoolExecutor monitoredThreadPool = null;

    private final String poolName = "TobaFlowExecServicePool";

    @Scheduled(cron = "*/10 * * * * *")
    private void printThreadPoolMonitor() {
        monitoredThreadPool = (ThreadPoolExecutor) tobaFlowManager.getTobaFlowExecService();
        int activeCount = this.monitoredThreadPool.getActiveCount();
        int corePoolSize = this.monitoredThreadPool.getCorePoolSize();
        int largestPoolSize = this.monitoredThreadPool.getLargestPoolSize();
        int maximumPoolSize = this.monitoredThreadPool.getMaximumPoolSize();
        int queueTaskSize = this.monitoredThreadPool.getQueue().size();
        long taskCount = this.monitoredThreadPool.getTaskCount();

        log.info("\n## thread pool name = {}\n## largest pool size = {}\n## peak number of active threads = {}\n## peak number of queue tasks = {}\n## number of core threads = {}\n## maximum number of threads = {}\n## total number of tasks executed = {}",
                poolName, largestPoolSize, activeCount, queueTaskSize, corePoolSize, maximumPoolSize);
//        this.lastTaskCount = taskCount;
//        if (this.monitoredThreadPool.isTerminated()) {
//            ThreadPoolMonitor.remove(this.poolName, this.monitoredThreadPool);
//        }
    }
}
