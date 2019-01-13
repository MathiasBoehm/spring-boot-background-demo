package de.struktuhr.backgrounddemo.control;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RequestObjectHelper {

    public static RequestObject createRequest(String payload) {
        final BlockingQueue<String> responseQueue = new ArrayBlockingQueue<>(1);
        return new RequestObject(payload, responseQueue);
    }

    public static RequestObject createStopRequest() {
        final BlockingQueue<String> responseQueue = new ArrayBlockingQueue<>(1);
        return new RequestObject(true, "stop", responseQueue);

    }
}
