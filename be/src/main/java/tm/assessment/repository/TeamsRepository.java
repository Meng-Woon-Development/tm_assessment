package tm.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tm.assessment.model.Teams;


public interface TeamsRepository extends JpaRepository<Teams, String> {
    
}
