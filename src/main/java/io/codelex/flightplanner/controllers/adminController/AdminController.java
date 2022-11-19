package io.codelex.flightplanner.controllers.adminController;

import io.codelex.flightplanner.domain.CorrectFlight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/flights")
    @ResponseBody
    synchronized public ResponseEntity<CorrectFlight> addFlight(@RequestBody CorrectFlight correctFlight) {
        return adminService.addFlight(correctFlight);
    }

    @DeleteMapping("/flights/{id}")
    synchronized public void deleteFlight(@PathVariable String id) {
        adminService.deleteFlight(id);
    }

    @GetMapping("/flights/{id}")
    @ResponseBody
    public ResponseEntity<CorrectFlight> fetchFlight(@PathVariable String id) {
        return adminService.fetchFlight(id);
    }


}
