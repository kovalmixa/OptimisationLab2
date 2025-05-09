package edu.dnu.fpm.pz.db.Services;

import edu.dnu.fpm.pz.db.Entities.ActivityLog;
import edu.dnu.fpm.pz.db.Repositories.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityLogService {
    @Autowired
    private ActivityLogRepository repo;
    public List<ActivityLog> getAll() { return repo.findAll(); }
    public ActivityLog save(ActivityLog log) { return repo.save(log); }
}