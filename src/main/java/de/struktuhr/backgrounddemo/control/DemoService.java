package de.struktuhr.backgrounddemo.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DemoService {

    private final Logger log = LoggerFactory.getLogger(DemoService.class);

    public String service() {
        log.info("service");
        return LocalTime.now().format(DateTimeFormatter.ISO_TIME);
    }
}
