package de.struktuhr.backgrounddemo.control;

import java.util.concurrent.BlockingQueue;

public class RequestObject {

    private final String payload;
    private final BlockingQueue<String> reponseQueue;

    public RequestObject(String payload, BlockingQueue<String> reponseQueue) {
        this.payload = payload;
        this.reponseQueue = reponseQueue;
    }

    public String getPayload() {
        return payload;
    }

    public BlockingQueue<String> getReponseQueue() {
        return reponseQueue;
    }
}
