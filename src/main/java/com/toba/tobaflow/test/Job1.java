package com.toba.tobaflow.test;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Job1 implements Job{

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        System.out.println("Job1 --->>> Hello geeks! Time is " + new Date());
        System.out.println("dataMap name: "+dataMap.get("name"));
        System.out.println("dataMap age: "+dataMap.get("age"));

    }
}