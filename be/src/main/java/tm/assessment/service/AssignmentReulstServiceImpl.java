package tm.assessment.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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
                var assignment = listOfAssignedTasks
                                    .stream()
                                    .filter(x -> x.getTeam().equals(ts.getTeamId()))
                                    .filter(x -> x.getSkill().equals(ts.getSkill()))
                                    .findFirst()
                                    .orElse(null);

                //  insert record and return
                if( assignment == null) {                    
                    var entity = new AssignmentResult(ts.getTeamId(), task, skill);
                    return assignmentResultRepository.save(entity);
                } 
                ls.add(assignment);
            }
            
            // group the assignment list
            Map<String, Long> counted = ls.stream()
                    .collect(Collectors.groupingBy(AssignmentResult::getTeam, Collectors.counting()));
            // get the lowest
            Map<String, Long> finalMap = new LinkedHashMap<>();

            // sort the team
            counted.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue())
                    .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));

            var team = finalMap.keySet().iterator().next();
            var assignTeam = new AssignmentResult(team, task, skill);
            return assignmentResultRepository.save(assignTeam);

        } catch (Exception e) {
            log.error("Error roundRobin: " + e.getMessage());
            return null;
        } finally {
            log.info("roundRobin end");
        }
    } 
    
}
