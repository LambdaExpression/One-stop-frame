package org.tcat.frame.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.tcat.frame.util.DateUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态定时器
 * Created by Lin on 2017/8/30.
 */
@Component("dynamicTimer")
public final class DynamicTimer extends ScheduledTaskRegistrar {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, ScheduledTask> scheduledTaskMap = new ConcurrentHashMap<>();

    /**
     * 任务最少提交执行时间（秒）
     */
    private static final int LEAST_SAVE_TIME_SECONDS = 2;

    /**
     * 添加定时任务
     *
     * @param task 任务
     * @param date 定时时间
     * @return turn/false
     */
    public boolean addCronTask(TimerRunnable task, Date date) {
        if (task == null || date.getTime() < DateUtils.addSeconds(new Date(), LEAST_SAVE_TIME_SECONDS).getTime()) {
            logger.error("添加定时任务 过期时间空或已过期 data={}", date);
            return false;
        }
        importScheduleCronTask(task, date);
        return true;
    }

    /**
     * 添加定时任务（秒）
     *
     * @param task   任务
     * @param amount 时间（秒）
     * @return turn/false
     */
    public boolean addCronTashSeconds(TimerRunnable task, int amount) {
        if (task == null || amount < LEAST_SAVE_TIME_SECONDS) {
            logger.error("添加定时任务 过期时间（秒）已过期 data={}", amount);
            return false;
        }
        Date date = DateUtils.addSeconds(new Date(), amount);
        System.out.println(DateUtils.format(date, DateUtils.CRON_FORMAT));
        importScheduleCronTask(task, date);
        return true;
    }

    /**
     * 添加定时任务（分钟）
     *
     * @param task   任务
     * @param amount 时间（分钟）
     * @return turn/false
     */
    public boolean addCronTashMinutes(TimerRunnable task, int amount) {
        if (task == null || amount < 1) {
            logger.error("添加定时任务 过期时间（分钟）已过期 data={}", amount);
            return false;
        }
        Date date = DateUtils.addMinutes(new Date(), amount);
        importScheduleCronTask(task, date);
        return true;
    }

    /**
     * 添加定时任务（小时）
     *
     * @param task   任务
     * @param amount 时间（小时）
     * @return turn/false
     */
    public boolean addCronTashHours(TimerRunnable task, int amount) {
        if (task == null || amount < 1) {
            logger.error("添加定时任务 过期时间（小时）已过期 data={}", amount);
            return false;
        }
        Date date = DateUtils.addHours(new Date(), amount);
        importScheduleCronTask(task, date);
        return true;
    }

    /**
     * 添加定时任务（日）
     *
     * @param task   任务
     * @param amount 时间（日）
     * @return turn/false
     */
    public boolean addCronTashDays(TimerRunnable task, int amount) {
        if (task == null || amount < 1) {
            logger.error("添加定时任务 过期时间（日）已过期 data={}", amount);
            return false;
        }
        Date date = DateUtils.addDays(new Date(), amount);
        importScheduleCronTask(task, date);
        return true;
    }

    private void importScheduleCronTask(TimerRunnable task, Date expression) {
        scheduledTaskMap.put(task.getTaskId()
                , super.scheduleCronTask(new CronTask(task, DateUtils.format(expression, DateUtils.CRON_FORMAT))));
    }

    public void cancel(String taskId) {
        ScheduledTask scheduledTask = scheduledTaskMap.get(taskId);
        if (scheduledTask != null) {
            scheduledTask.cancel();
            scheduledTaskMap.remove(taskId);
        }
    }

}
