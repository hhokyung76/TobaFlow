package com.toba.tobaflow.core.utils;

import lombok.SneakyThrows;
import net.bytebuddy.agent.ByteBuddyAgent;

import java.io.File;

public class ByteBuddyAgentUtil {

    @SneakyThrows
    public static void main(String[] args) {
        java.lang.management.RuntimeMXBean runtime =
                java.lang.management.ManagementFactory.getRuntimeMXBean();
        java.lang.reflect.Field jvm = null;
        try {
            jvm = runtime.getClass().getDeclaredField("jvm");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        jvm.setAccessible(true);
        sun.management.VMManagement mgmt =
                null;
        try {
            mgmt = (sun.management.VMManagement) jvm.get(runtime);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        java.lang.reflect.Method pid_method =
                mgmt.getClass().getDeclaredMethod("getProcessId");
        pid_method.setAccessible(true);

        int pid = (Integer) pid_method.invoke(mgmt);
        System.out.println("pid: "+pid);
    }

    private static File AGENT_JAR = new File("/path/to/agent.jar");

    public static void addJarToClassPath(File jarFile) {
       // ByteBuddyAgent.attach(AGENT_JAR, String.valueOf(ProcessHandle.current().pid()), jarFile.getPath());
    }
}
