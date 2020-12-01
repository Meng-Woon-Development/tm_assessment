package tm.assessment.utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tm.assessment.model.Tasks;
import tm.assessment.model.TeamSkill;
import tm.assessment.model.Teams;

@Log4j2
@Service
public class CsvHelper {

    // csv to Teams
    public List<Teams> csvToTeams(File filepath) throws FileNotFoundException {
    InputStream is = new FileInputStream(filepath); 
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

        var teams = new ArrayList<Teams>();

        Iterable<CSVRecord> csvRecords = csvParser.getRecords();

        for (CSVRecord csvRecord : csvRecords) {
            var team = new Teams(csvRecord.get("TEAM_ID"));
            teams.add(team);
        }
        return teams;    
        } catch (IOException e) {
            log.error("fail to parse" + filepath.getName() + " file: " + e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    // csv to Task
    public List<Tasks> csvToTasks(File filepath) throws FileNotFoundException {
        InputStream is = new FileInputStream(filepath); 
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
    
            var tasks = new ArrayList<Tasks>();
    
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
    
            for (CSVRecord csvRecord : csvRecords) {
                var task = new Tasks(csvRecord.get("TASK_ID"), csvRecord.get("SKILL"));
                tasks.add(task);
            }
            return tasks;    
        } catch (IOException e) {
            log.error("fail to parse" + filepath.getName() + " file: " + e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
    
    // csv to Task
    public List<TeamSkill> csvToTeamSkills(File filepath) throws FileNotFoundException {
        InputStream is = new FileInputStream(filepath); 
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
    
            var teamSkills = new ArrayList<TeamSkill>();
    
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
    
            for (CSVRecord csvRecord : csvRecords) {
                var teamSkill = new TeamSkill(csvRecord.get("TEAM_ID"), csvRecord.get("SKILL"));
                teamSkills.add(teamSkill);
            }
            return teamSkills;    
        } catch (IOException e) {
            log.error("fail to parse" + filepath.getName() + " file: " + e.getMessage());
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

}
