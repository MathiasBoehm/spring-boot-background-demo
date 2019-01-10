package de.struktuhr.backgrounddemo.boundary;

import de.struktuhr.backgrounddemo.control.BackgroundServiceCaller;
import de.struktuhr.backgrounddemo.entity.Demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FrontController {

    @Autowired
    private BackgroundServiceCaller backgroundServiceCaller;

    @PostMapping(value = "process")
    public String process(@RequestBody Demo demo) {
        return backgroundServiceCaller.call(demo);
    }

}
