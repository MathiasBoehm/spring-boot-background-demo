package de.struktuhr.backgrounddemo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundRunner implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(BackgroundRunner.class);

    private final DemoService demoService;

    private boolean stopped = false;

    public BackgroundRunner(DemoService demoService) {
        this.demoService = demoService;
    }


    @Override
    public void run() {
        while (!stopped) {
            // do stuff
            String result = demoService.service();
            log.info("BackgroundRunner do stuff with {}", result);

            try {
                Thread.sleep(2000);
            }
            catch(InterruptedException e) {
                log.warn("BackgroundRunner interrupted");
            }

        }

        log.info("BackgroundRunner stopped");
    }

    public void stop() {
        stopped = true;
    }
}
