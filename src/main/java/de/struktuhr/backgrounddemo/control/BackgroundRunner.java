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

                if (req.isStopped()) {
                    log.info("Got stop signal. Initiate stopping");
                    stopped = true;
                }
                else {
                    log.info("Process Message  {}", req.getPayload());
                    String result = demoService.service(req.getPayload());
                    req.getReponseQueue().put(result);
                }

            } catch (InterruptedException e) {
                log.warn("BackgroundRunner interrupted");
            }
        }

        log.info("BackgroundRunner stopped");
    }
}
