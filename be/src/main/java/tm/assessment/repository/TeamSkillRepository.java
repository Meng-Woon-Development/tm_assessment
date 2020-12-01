package tm.assessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tm.assessment.model.TeamSkill;

@Repository
public interface TeamSkillRepository extends JpaRepository<TeamSkill, String> {
    List<TeamSkill> findBySkill(String skill);
}
