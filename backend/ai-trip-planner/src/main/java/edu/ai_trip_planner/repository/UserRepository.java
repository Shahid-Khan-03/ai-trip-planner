package edu.ai_trip_planner.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.ai_trip_planner.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {

Optional<User> findByEmail(String email);
   
boolean existsByEmail(String email);
}
