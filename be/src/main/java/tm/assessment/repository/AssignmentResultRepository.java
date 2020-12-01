package tm.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tm.assessment.model.AssignmentResult;

@Repository
public interface AssignmentResultRepository extends JpaRepository<AssignmentResult, Long> {
    
    AssignmentResult findByTeam(String team);

    AssignmentResult findByTeamAndSkill(String team, String skill);

    List<AssignmentResult> findByTeamInAndSkillIn(List<String> teams, List<String> skills);
}
