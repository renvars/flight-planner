package io.codelex.flightplanner.controllers.testingController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testing-api")
public class TestingController {


    private final TestingService testingService;

    @Autowired
    public TestingController(TestingService testingService) {
        this.testingService = testingService;
    }


    @PostMapping("/clear")
    @ResponseStatus(code = HttpStatus.OK)
    public void clear() {
        testingService.clear();
    }


}
