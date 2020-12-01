package tm.assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tm.assessment.model.Tasks;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, String> {
    
}
