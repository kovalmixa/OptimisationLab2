package edu.dnu.fpm.pz.db.Repositories;

import edu.dnu.fpm.pz.db.Entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {}
