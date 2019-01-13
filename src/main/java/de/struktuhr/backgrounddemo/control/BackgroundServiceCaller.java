package de.struktuhr.backgrounddemo.control;

import de.struktuhr.backgrounddemo.entity.Demo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class BackgroundServiceCaller {

    private final static Logger log = LoggerFactory.getLogger(BackgroundServiceCaller.class);

    @Autowired
    private BlockingQueue<RequestObject> inputQueue;

    public String call(Demo demo) {
        String result;
        try {
            final RequestObject req = RequestObjectHelper.createRequest(demo.message);
            inputQueue.put(req);

            // Wait for response
            result = req.getReponseQueue().take();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("call result = {}", result);

        return result;
    }

}
