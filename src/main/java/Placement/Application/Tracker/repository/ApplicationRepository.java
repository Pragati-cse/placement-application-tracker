package Placement.Application.Tracker.repository;

import Placement.Application.Tracker.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

}