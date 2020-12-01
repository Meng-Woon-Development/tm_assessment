package tm.assessment.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tm.assessment.model.Teams;
import tm.assessment.repository.TeamsRepository;
import tm.assessment.utility.CsvHelper;
import tm.assessment.utility.FileUtility;

@Service
@Log4j2
public class TeamServiceImpl implements TeamService {
    
    @Autowired
    TeamsRepository teamsRepository;

    @Autowired
    CsvHelper csvHelper;

    public TeamServiceImpl() {
        
    }

    /**
     * 
     * @param is: InputStream
     */
    public boolean loadTeam(File file) {
        log.info("loadTeam start");
        try {
            var teams = this.extractTeam(file);
            var result = teamsRepository.saveAll(teams);
            if(result == null) {                
                log.info("loadTeam empty dta");
                return false;
            }                
            FileUtility.deleteFile(file);
            return true;            
        } catch(Exception ex) {            
            log.error("Error loadTeam: " + ex.getMessage());
            return false;            
        }
        finally {
            log.info("loadTeam end");
        }        
    }
    
    /**
     * 
     * @param is InputStream
     * @return List<Teams>
     */
    public List<Teams> extractTeam(File file) {
        log.info("extractTeam start");
        try {
            return csvHelper.csvToTeams(file);
        } catch(Exception ex) {
            log.error("Error extractTeam " + ex.getMessage());
            return null;
        } finally {
            log.info("extractTeam end");
        }                
    }
}
