package com.toba.tobaflow.core.task;

import com.toba.tobaflow.core.info.TobaFlowJobInfo;
import com.toba.tobaflow.core.info.TobaTaskInfo;
import com.toba.tobaflow.core.utils.ScStringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

@Setter
@Slf4j
public class TobaJobTaskCenter implements Callable<Map<String, Object>> {

    private TobaFlowJobInfo flowJobInfo;

    public TobaJobTaskCenter(TobaFlowJobInfo flowJobInfo) {
        this.flowJobInfo = flowJobInfo;
    }

    @Override
    public Map<String, Object> call()  {
        log.info("called at "+ ScStringUtils.getCurrentTime());
        Map<String, Object> returnVal = new HashMap<String, Object>();

        List<TobaTaskInfo> taskList = flowJobInfo.getTaskQueueList();

        ArrayBlockingQueue<TobaTaskInfo> taskQueue = new ArrayBlockingQueue<TobaTaskInfo>(taskList.size());
        for (int ii = 0; ii < taskList.size(); ii++) {
            TobaTaskInfo taskInfoTemp = taskList.get(ii);
            try {
                taskQueue.put(taskInfoTemp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int index = 0;
        while(index < taskList.size()) {
            TobaTaskInfo taskInfo = null;
            try {
                taskInfo = taskQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            index++;
            log.info("taskInfo: "+taskInfo.toString());

            if (taskInfo.getTaskType().equals("TBT_CMD")) {
                String cmd = taskInfo.getTaskDesc();

                executeProcess(taskInfo);
            }
        }
        return null;
    }

    private String executeProcess(TobaTaskInfo taskInfo) {
        String[] cmdList = taskInfo.getTaskDesc().split(" ");
        List<String> command = Arrays.asList(cmdList); //new ArrayList<>();
        //command.add("ls");
       // command.add("-al");

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = null;
        try {
            log.info("run command: " + StringUtils.join(command));


            process = processBuilder.start();

            BufferedReader outReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = outReader.readLine()) != null) {
                log.info(line);
            }
            while ((line = errorReader.readLine()) != null) {
                log.error(line);
            }

            process.getErrorStream().close();
            process.getInputStream().close();
            process.getOutputStream().close();

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            process.destroy();
        }

        return null;

    }
}
