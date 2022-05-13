package com.toba.tobaflow.core.manager;

import com.toba.tobaflow.test.job.Job1;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;


public class TobaFlowManagerTest {

    private  Scheduler toboFlowScheduler;

    public Scheduler getToboFlowScheduler() {
        return toboFlowScheduler;
    }

    public void setToboFlowScheduler(Scheduler toboFlowScheduler) {
        this.toboFlowScheduler = toboFlowScheduler;
    }

    @PostConstruct
    private void init() {
        try {
            toboFlowScheduler = new StdSchedulerFactory().getScheduler();

            JobDetail job1 = JobBuilder.newJob(Job1.class)
                    .withIdentity("job1", "group1").build();

            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();

            toboFlowScheduler.start();
            toboFlowScheduler.scheduleJob(job1, trigger1);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void log() {

    }

    public static void main(String[] args) {
        try {
            JobDetail job1 = JobBuilder.newJob(Job1.class)
                    .withIdentity("job1", "group1").build();

            JobDataMap jobDataMap = job1.getJobDataMap();
            jobDataMap.put("name", "goodName");
            jobDataMap.put("age", 29);

            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("cronTrigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();

            Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
            scheduler1.start();
            scheduler1.scheduleJob(job1, trigger1);



            Thread.sleep(100000);

            scheduler1.shutdown();
//            scheduler2.shutdown();
//            scheduler3.shutdown();
//            scheduler4.shutdown();
//            scheduler5.shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
