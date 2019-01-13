package de.struktuhr.backgrounddemo.control;

import java.util.concurrent.BlockingQueue;

public class RequestObject {

    private final boolean stopped;
    private final String payload;
    private final BlockingQueue<String> reponseQueue;

    public RequestObject(String payload, BlockingQueue<String> reponseQueue) {
        this(false, payload, reponseQueue);
    }

    public RequestObject(boolean stopped, String payload, BlockingQueue<String> reponseQueue) {
        this.stopped = stopped;
        this.payload = payload;
        this.reponseQueue = reponseQueue;
    }

    public boolean isStopped() {
        return stopped;
    }

    public String getPayload() {
        return payload;
    }

    public BlockingQueue<String> getReponseQueue() {
        return reponseQueue;
    }
}
