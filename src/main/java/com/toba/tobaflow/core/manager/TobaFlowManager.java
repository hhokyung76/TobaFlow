package com.toba.tobaflow.core.manager;

import com.toba.tobaflow.core.info.TobaFlowJobInfo;
import com.toba.tobaflow.core.info.TobaTaskInfo;
import com.toba.tobaflow.core.job.TobaFlowJob;
import com.toba.tobaflow.core.job.TobaFlowJobRunner;
import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
@Component
public class TobaFlowManager {

    @Autowired
    private ApplicationContext context;

    private  Scheduler tobaFlowScheduler;

    private ExecutorService tobaFlowExecService;

    private ArrayBlockingQueue<TobaFlowJobInfo> flowJobQueue = new ArrayBlockingQueue<TobaFlowJobInfo>(300);

    /**
     * test 용
     */
    private List<TobaFlowJobInfo> makeFlowJobListForTest() {

        List<TobaFlowJobInfo> testJobList = new ArrayList<>();

        TobaFlowJobInfo flowJob = new TobaFlowJobInfo();
        flowJob.setJobId("test-111");
        flowJob.setJobName("testJob-111");
        flowJob.setJobDesc("테스트용 1111");
        flowJob.setJobCronExpr("*/30 * * * * ?");

        List<TobaTaskInfo> flowTaskQueueList = new ArrayList<>();

        //first task
        TobaTaskInfo taskInfo = new TobaTaskInfo();
        taskInfo.setTaskId("task-1");
        taskInfo.setTaskName("taskName-1");
        taskInfo.setTaskType("TBT_CMD");   // TBT_CMD, TBT_SSH, TBT_QRY, TBT_MQ
        taskInfo.setTaskDesc("ls -al");
        taskInfo.setTaskSeq(1);
        taskInfo.setJobId("jobId-111");
        flowTaskQueueList.add(taskInfo);

        //second task
        TobaTaskInfo taskInfo2 = new TobaTaskInfo();
        taskInfo2.setTaskId("task-2");
        taskInfo2.setTaskName("taskName-2");
        taskInfo2.setTaskType("TBT_CMD");
        // ls | awk ' BEGIN { ORS = ""; print "["; } { print "\/\@"$0"\/\@"; } END { print "]"; }' | sed "s^\"^\\\\\"^g;s^\/\@\/\@^\", \"^g;s^\/\@^\"^g"
        taskInfo2.setTaskDesc("mysql -u root -p ");
        taskInfo2.setTaskSeq(1);
        taskInfo2.setJobId("jobId-111");
        flowTaskQueueList.add(taskInfo2);

        flowJob.setTaskQueueList(flowTaskQueueList);

        testJobList.add(flowJob);

        return testJobList;

    }

    private void makeTobaFlowJobS(Scheduler flowScheduler) {
        List<TobaFlowJobInfo> testJobList = makeFlowJobListForTest();

        for (int ii = 0; ii < testJobList.size(); ii++) {
            TobaFlowJobInfo jobInfo = testJobList.get(ii);

            JobDetail tobaFlowJob = JobBuilder.newJob(TobaFlowJob.class)
                    .withIdentity(jobInfo.getJobName(), "TobaFlow1").build();

            // jobDetail에 설정 정보 세팅.
            JobDataMap jobDataMap = tobaFlowJob.getJobDataMap();
            jobDataMap.put("tobaFlowManager", this);
            jobDataMap.put("jobInfo", jobInfo);

            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger-"+jobInfo.getJobId(), "TobaFlow1")
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobInfo.getJobCronExpr()))
                    .build();

            try {
                // job trigger 등록.
                flowScheduler.scheduleJob(tobaFlowJob, trigger1);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }



    }

    @PostConstruct
    private void init() {
        this.tobaFlowExecService = Executors.newFixedThreadPool(200);

        TobaFlowJobRunner tobaFlowJobRunner = (TobaFlowJobRunner) context.getBean("tobaFlowJobRunner");
        tobaFlowJobRunner.setTobaFlowManager(this);
        tobaFlowExecService.submit(tobaFlowJobRunner);

        try {
            tobaFlowScheduler = new StdSchedulerFactory().getScheduler();

            makeTobaFlowJobS(tobaFlowScheduler);
            tobaFlowScheduler.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    private void finallize() {
        try {
            if (!tobaFlowExecService.isShutdown())
                tobaFlowExecService.shutdown();

            if (!tobaFlowScheduler.isShutdown())
                tobaFlowScheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
