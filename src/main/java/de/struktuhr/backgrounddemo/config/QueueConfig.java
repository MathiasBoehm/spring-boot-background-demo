package de.struktuhr.backgrounddemo.config;

import de.struktuhr.backgrounddemo.control.RequestObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Configuration
public class QueueConfig {

    @Bean
    public BlockingQueue<RequestObject> inputQueue() {
        return new ArrayBlockingQueue<>(10);
    }
}
