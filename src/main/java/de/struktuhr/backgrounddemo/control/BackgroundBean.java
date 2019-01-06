package de.struktuhr.backgrounddemo.control;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BackgroundBean {

    private final static Logger log = LoggerFactory.getLogger(BackgroundBean.class);

    @Autowired
    private DemoService demoService;

    private BackgroundRunner backgroundRunner;

    @EventListener
    public void handleApplicationStarted(ApplicationStartedEvent event) {
        log.info("Application Started");
        backgroundRunner = new BackgroundRunner(demoService);
        new Thread(backgroundRunner).start();
    }

    @EventListener
    public void handleContextClosed(ContextClosedEvent event) {
        log.info("Context Closed");
        backgroundRunner.stop();
    }

}
