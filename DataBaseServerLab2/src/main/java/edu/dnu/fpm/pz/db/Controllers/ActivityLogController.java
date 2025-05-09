package edu.dnu.fpm.pz.db.Controllers;

import edu.dnu.fpm.pz.db.Entities.ActivityLog;
import edu.dnu.fpm.pz.db.Services.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityLogController {
    @Autowired private ActivityLogService service;

    @GetMapping public List<ActivityLog> getAll() { return service.getAll(); }
    @PostMapping public ResponseEntity<ActivityLog> create(@RequestBody ActivityLog log) {
        return ResponseEntity.ok(service.save(log));
    }
}
