package de.struktuhr.backgrounddemo.control;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DemoService {


    public String service() {
        return LocalTime.now().format(DateTimeFormatter.ISO_TIME);
    }
}
