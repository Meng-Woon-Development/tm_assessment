package tm.assessment.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tm.assessment.model.AssignmentResult;
import tm.assessment.model.TeamSkill;
import tm.assessment.repository.AssignmentResultRepository;
import tm.assessment.repository.TeamSkillRepository;

/**
 * after load the data into table
 * then create record base on team and team skill
 */


@Service
@Log4j2
public class AssignmentReulstServiceImpl implements AssignmentResultService {

    @Autowired
    AssignmentResultRepository assignmentResultRepository;

    @Autowired
    TeamSkillRepository teamSkillRepository;
    
    /**
     * saveAssignmentResult method for REST controller
     */
    public AssignmentResult saveAssignmentResult(AssignmentResult resource) {
       log.info("saveAssignmentResult start"); 
        try {
            var res = this.roundRobin(resource.getTask(), resource.getSkill());
            return res;
        } catch (Exception ex) {            
            log.error("Error saveAssignmentResult: " + ex.getMessage()); 
            return null;
        } finally {
            log.info("saveAssignmentResult end"); 
        }
    }

    /**
     * saveAssignmentResult method for file load
     */
    public AssignmentResult saveAssignmentResult(String task, String skill) {
        log.info("saveAssignmentResult start"); 
        try {
            var res = this.roundRobin(task, skill);
            return res;
        } catch (Exception ex) {            
            log.error("Error saveAssignmentResult: " + ex.getMessage()); 
            return null;
        } finally {
            log.info("saveAssignmentResult end"); 
        }
    }

    /**
     * 
     */
    private AssignmentResult roundRobin(String task, String skill) {

        log.info("roundRobin start");
        try {
            var teamSkills = teamSkillRepository.findBySkill(skill);
            var listOfAssignedTasks = assignmentResultRepository.findAll();
            var ls = new ArrayList<AssignmentResult>();
            for(TeamSkill ts : teamSkills) {
                // var assignment = assignmentResultRepository.findByTeamAndSkill(ts.getTeamId(), ts.getSkill());            
                var assignment = listOfAssignedTasks
                                    .stream()
                                    .filter(x -> x.getTeam().equals(ts.getTeamId()))
                                    .filter(x -> x.getSkill().equals(ts.getSkill()))
                                    .findFirst()
                                    .orElse(null);
                                    // .map(a -> {
                                    //     return new AssignmentResult(a.getTeam(), a.getTask(), 
                                    //     a.getSkill(), a.getNumberOfTask());
                                    // }).orElse(null);
                                    
                                    
                if( assignment == null) {
                    //  insert record and return
                    var entity = new AssignmentResult(ts.getTeamId(), task, skill, 1);
                    return assignmentResultRepository.save(entity);
                } 
                ls.add(assignment);
            }
            
            // get the lowest no. of assigned task
            var result = ls.stream()
                        .sorted(Comparator.comparingInt(AssignmentResult::getNumberOfTask))
                        .findFirst()
                        .get()
                        .getId();
            
            var assignTeam = assignmentResultRepository.findById(result).orElse(null);
            assignTeam.setNumberOfTask(assignTeam.getNumberOfTask() + 1);

            return assignmentResultRepository.save(assignTeam);

        } catch (Exception e) {
            log.error("Error roundRobin: " + e.getMessage());
            return null;
        } finally {
            log.info("roundRobin end");
        }
    } 
    
}
