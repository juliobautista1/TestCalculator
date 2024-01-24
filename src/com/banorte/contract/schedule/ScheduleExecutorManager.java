package com.banorte.contract.schedule;

import java.util.Calendar;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MRIOS
 */
public class ScheduleExecutorManager {
    private static final Logger logger = Logger.getLogger(ScheduleExecutorManager.class.getName());
    private static final ScheduledThreadPoolExecutor scheduleService = new ScheduledThreadPoolExecutor(1);
    private static final Map<String,ScheduleTask> scheduleMap = new Hashtable<String,ScheduleTask>();
    
            
    private ScheduleExecutorManager() {
    	
    }

    public static void start() {
        logger.info("Starting ScheduleExecutorManager...");

    }

    public static void shutdown() {
        logger.info("Shutting down ScheduleExecutorManager...");
        scheduleService.shutdown();
    }

    public static void schedule(String name,Runnable task, long delay) {
        if(!scheduleMap.containsKey(name)){
            ScheduleTask scheduleTask = new ScheduleTask(name,task);
 //         scheduleService.scheduleAtFixedRate(scheduleTask, delay, 24 * 60 * 60, TimeUnit.SECONDS); // Uncomment for real schedule every 24 Hours
            ScheduledFuture future = scheduleService.scheduleAtFixedRate(scheduleTask, delay, 24 * 60 * 60, TimeUnit.SECONDS);
            scheduleTask.setScheduledFuture(future);
            scheduleService.setCorePoolSize(scheduleService.getCorePoolSize() + 1);
            scheduleMap.put(name, scheduleTask);
        }
    }

    public static void execute(Runnable runnable){
        scheduleService.execute(runnable);
    }

    public static void scheduleByHour(String name,Runnable runnable, int hour,int minute) {
    	logger.setLevel(Level.WARNING);
    	//TimeZone.setDefault(TimeZone.getTimeZone("GMT-06:00"));
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
        Calendar calendar = Calendar.getInstance();        
        logger.info("Actual Time "+(new java.sql.Timestamp(calendar.getTime().getTime())));
        logger.info("Default Time Zone"+(TimeZone.getDefault()));
//        logger.info("Time Zone"+(calendar.getTimeZone()));
        
        if(hour<=calendar.get(Calendar.HOUR_OF_DAY)){
            if(hour<calendar.get(Calendar.HOUR_OF_DAY) || minute<=calendar.get(Calendar.MINUTE)){
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        logger.info("Schedule every 24Hrs starting at "+(new java.sql.Timestamp(calendar.getTimeInMillis())));
        schedule(name,runnable,(calendar.getTimeInMillis()-System.currentTimeMillis())/1000);
    }
    
    public static void executeReProgramacion(String name,Runnable runnable, ScheduledFuture<?> scheduledFuture, int hour,int minute) {
    	logger.setLevel(Level.WARNING);
    	//TimeZone.setDefault(TimeZone.getTimeZone("GMT-06:00"));
        TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
        Calendar calendar = Calendar.getInstance();        
        logger.info("Actual Time "+(new java.sql.Timestamp(calendar.getTime().getTime())));
        logger.info("Default Time Zone"+(TimeZone.getDefault()));
//        logger.info("Time Zone"+(calendar.getTimeZone()));
        
        if(hour<=calendar.get(Calendar.HOUR_OF_DAY)){
            if(hour<calendar.get(Calendar.HOUR_OF_DAY) || minute<=calendar.get(Calendar.MINUTE)){
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        logger.info("Schedule every 24Hrs starting at "+(new java.sql.Timestamp(calendar.getTimeInMillis())));
        //schedule(name,runnable,(calendar.getTimeInMillis()-System.currentTimeMillis())/1000);
        long delay=(calendar.getTimeInMillis()-System.currentTimeMillis())/1000;
        
        if(scheduleMap.containsKey(name)){
        	scheduledFuture.cancel(true);
        	//System.out.println("esta hecho: "+scheduledFuture.isDone());
        	//System.out.println("se cancelo: "+scheduledFuture.isCancelled());

            ScheduleTask scheduleTask = new ScheduleTask(name,runnable);
 //         scheduleService.scheduleAtFixedRate(scheduleTask, delay, 24 * 60 * 60, TimeUnit.SECONDS); // Uncomment for real schedule every 24 Hours
            ScheduledFuture<?> future = scheduleService.scheduleAtFixedRate(scheduleTask, delay, 24 * 60 * 60, TimeUnit.SECONDS);
            scheduleTask.setScheduledFuture(future);
            //scheduleService.setCorePoolSize(scheduleService.getCorePoolSize() + 1);
            scheduleMap.put(name, scheduleTask);
        }
		/*//PARA PRUEBAS
		 * System.out.println("INFO scheduleService RESCHEDULE - "+name);
		 * System.out.println("scheduleService.getActiveCount(): "+scheduleService.
		 * getActiveCount());
		 * System.out.println("scheduleService.getCompletedTaskCount(): "
		 * +scheduleService.getCompletedTaskCount());
		 * System.out.println("scheduleService.getCorePoolSize(): "+scheduleService.
		 * getCorePoolSize());
		 * System.out.println("scheduleService.getLargestPoolSize(): "+scheduleService.
		 * getLargestPoolSize());
		 * System.out.println("scheduleService.getMaximumPoolSize(): "+scheduleService.
		 * getMaximumPoolSize());
		 * System.out.println("scheduleService.getPoolSize(): "+scheduleService.
		 * getPoolSize());
		 * System.out.println("scheduleService.getTaskCount(): "+scheduleService.
		 * getTaskCount());
		 */
    }

    public static Collection<ScheduleTask> getScheduleTask() {
//        BlockingQueue<Runnable> queue = scheduleService.getQueue();
        return scheduleMap.values();
    }

//    public static void main(String[] args) {
//        System.out.println("Ejecutando Main...");
//        Runnable task1 = new Runnable() {
//            public void run() {
//                System.out.println("Ejecutando Tarea 1..." + (new java.sql.Timestamp(System.currentTimeMillis())));
//            }
//        };
//        Runnable task2 = new Runnable() {
//            public void run() {
//                System.out.println("Ejecutando Tarea 2..." + (new java.sql.Timestamp(System.currentTimeMillis())));
//            }
//        };
//        ScheduleExecutorManager.scheduleByHour("Tarea 1",task1,10,47);
//        ScheduleExecutorManager.scheduleByHour("Tarea 2",task2,2,38);
//        try {
//            System.out.println("Durmiendo 3 Mins.");
//            Thread.sleep(2*60*1000);
////            LayoutOperations layOut = LayoutOperations.getInstance();
////            layOut.startSend(false, true);
////            layOut.startRecipe(false);
//            System.out.println("Deteneiendo App.");
//            ScheduleExecutorManager.shutdown();
//            System.out.println("Durmiendo 1 Mins.");
//            Thread.sleep(1*60*1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ScheduleExecutorManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}