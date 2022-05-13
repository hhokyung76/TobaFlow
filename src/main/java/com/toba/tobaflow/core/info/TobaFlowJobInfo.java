package com.toba.tobaflow.core.info;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * /**
 *      * JOB 은
 *      * jobId
 *      * jobName
 *      * jobDesc
 *      * jobCronExpr
 *      *
 *      * @param args
 */
@Getter
@Setter
@ToString
public class TobaFlowJobInfo implements Serializable {
    private String jobId;
    private String jobName;
    private String jobDesc;
    private String jobCronExpr;

    private List<TobaTaskInfo> taskQueueList;

}
