package com.learn.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author: jingbiao li
 * @date: 2024/11/19 16:39
 * @Version: 1.0
 * @description:
 */
public class QuartzMain {

    public static void main(String[] args)throws SchedulerException {

        //创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");

        //创建一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3)
                        .repeatForever()).build();
        trigger.getJobDataMap().put("t2", "tv2");

        //创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .usingJobData("j1", "jv1")
                .withIdentity("myjob", "mygroup").build();
        job.getJobDataMap().put("j2", "jv2");

        //注册trigger并启动scheduler
        scheduler.scheduleJob(job,trigger);
        scheduler.start();

    }
}
