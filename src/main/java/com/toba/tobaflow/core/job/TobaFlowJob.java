package com.toba.tobaflow.core.job;

import com.toba.tobaflow.core.info.TobaFlowJobInfo;
import com.toba.tobaflow.core.manager.TobaFlowManager;
import com.toba.tobaflow.core.task.TobaJobTaskCenter;
import com.toba.tobaflow.core.utils.ScStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class TobaFlowJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        log.info("## Started TobaFlowJob["+Thread.currentThread().getName()+"] --->>> Current Time is " + ScStringUtils.getCurrentTime());
        TobaFlowManager tobaFlowManager = (TobaFlowManager) dataMap.get("tobaFlowManager");
        TobaFlowJobInfo jobInfo = (TobaFlowJobInfo) dataMap.get("jobInfo");
        log.info("jobInfo: "+jobInfo.getJobName());

        try {
            tobaFlowManager.getFlowJobQueue().put(jobInfo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        ExecutorService executorPool = Executors.newFixedThreadPool(1);
//
//        List<Callable<Map<String, Object>>> valStatusJobs = new ArrayList<Callable<Map<String, Object>>>();
//
//        TobaJobTaskCenter taskCenter = new TobaJobTaskCenter(jobInfo);
//        valStatusJobs.add(taskCenter);
//
//        List<Future<Map<String, Object>>> resultList = null;
//        try {
//            resultList = executorPool.invokeAll(valStatusJobs);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        for (Future<Map<String, Object>> future : resultList) {
//            try {
//                log.info("return:{}",future.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }

        log.info("## Ending.["+Thread.currentThread().getName()+"]...!! ["+ ScStringUtils.getCurrentTime()+"]");
//        executorPool.shutdown();
    }
}
