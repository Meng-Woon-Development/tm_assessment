package tm.assessment.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tm.assessment.model.TeamSkill;
import tm.assessment.model.Teams;
import tm.assessment.repository.TeamSkillRepository;
import tm.assessment.utility.CsvHelper;
import tm.assessment.utility.FileUtility;

@Service
@Log4j2
public class TeamSkillServiceImpl implements TeamSkillService {
    
    @Autowired
    TeamSkillRepository teamSkillRepository;

    @Autowired
    CsvHelper csvHelper;    

    /**
     * 
     * @param is: InputStream
     */
    public boolean loadTeamSkill(File file) {
        log.info("loadTeamSkill start");
        try {
            var teamSkills = this.extractTeamSkills(file);
            var result = teamSkillRepository.saveAll(teamSkills);
            if(result == null) {
                log.info("loadTeamSkill empty dta");
                return false;
            }                         
            FileUtility.deleteFile(file);
            return true;
        } catch(Exception ex) {            
            log.error("Error loadTeamSkill: " + ex.getMessage());
            return false;
        }
        finally {
            log.info("loadTeamSkill end");
        }        
    }
    
    /**
     * 
     * @param is InputStream
     * @return List<Teams>
     */
    private List<TeamSkill> extractTeamSkills(File file) {
        log.info("extractTeamSkills start");
        try {
            return csvHelper.csvToTeamSkills(file);
        } catch(Exception ex) {
            log.error("Error extractTeamSkills " + ex.getMessage());
            return null;
        } finally {
            log.info("extractTeamSkills end");
        }                
    }
}
