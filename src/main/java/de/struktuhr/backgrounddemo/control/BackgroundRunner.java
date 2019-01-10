package de.struktuhr.backgrounddemo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.concurrent.BlockingQueue;

public class BackgroundRunner implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(BackgroundRunner.class);

    private final DemoService demoService;
    private BlockingQueue<RequestObject> queue;

    private boolean stopped = false;

    public BackgroundRunner(DemoService demoService, BlockingQueue<RequestObject> queue) {
        this.demoService = demoService;
        this.queue = queue;
    }


    @Override
    public void run() {
        while (!stopped) {
            try {
                // This call blocks
                final RequestObject req = queue.take();

                Thread.sleep(5000);

                log.info("Process Message  {}", req.getPayload());

                String result = req.getPayload() + " processed at " + demoService.service();

                req.getReponseQueue().put(result);

            } catch (InterruptedException e) {
                log.warn("BackgroundRunner interrupted");
            }

        }

        log.info("BackgroundRunner stopped");
    }

    public void stop() {
        stopped = true;
    }
}
