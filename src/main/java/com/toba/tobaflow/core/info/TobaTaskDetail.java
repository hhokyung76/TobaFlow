package com.toba.tobaflow.core.info;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *      * Task
         *      * jobId
         *      * taskId
         *      * taskName
         *      * taskDesc
         *      * taskSeq 순번
         *      * taskType (ssh, cmd, mq, query) TBT_CMD, TBT_SSH, TBT_QRY, TBT_MQ
 *      **/
@Getter
@Setter
@ToString
public class TobaTaskDetail implements Serializable {
    private String jobId;
    private String taskId;
    private String taskName;
    private String taskDesc;
    private int taskSeq;
    private String taskType; // (ssh, cmd, mq, query)

}
