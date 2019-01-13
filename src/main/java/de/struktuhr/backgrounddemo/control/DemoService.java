package de.struktuhr.backgrounddemo.control;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DemoService {


    public String service() {
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {

        }
        return LocalTime.now().format(DateTimeFormatter.ISO_TIME);
    }
}
