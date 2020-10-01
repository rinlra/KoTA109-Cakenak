package Scheduler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SchedulerManager implements ServletContextListener{

	private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
    	ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
    	ZonedDateTime nextRun = now.withHour(0).withMinute(1).withSecond(0);
    	if(now.compareTo(nextRun) > 0)
    	    nextRun = nextRun.plusDays(1);

    	Duration duration = Duration.between(now, nextRun);
    	long initalDelay = duration.getSeconds();

    	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);            
    	scheduler.scheduleAtFixedRate(new DailyJob(),
    	    initalDelay,
    	    TimeUnit.DAYS.toSeconds(1),
    	    TimeUnit.SECONDS);
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new DailyJob(), 0, 1, TimeUnit.DAYS);
//        scheduler.scheduleAtFixedRate(new SomeHourlyJob(), 0, 1, TimeUnit.HOURS);
//        scheduler.scheduleAtFixedRate(new SomeQuarterlyJob(), 0, 15, TimeUnit.MINUTES);
//        scheduler.scheduleAtFixedRate(new SomeFiveSecondelyJob(), 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
	
}
