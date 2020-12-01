package tm.assessment.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tm.assessment.model.Tasks;
import tm.assessment.repository.TaskRepository;
import tm.assessment.utility.CsvHelper;
import tm.assessment.utility.FileUtility;

@Service
@Log4j2
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CsvHelper csvHelper;
    
    /**
     * 
     * @param is: InputStream
     */
    public boolean loadTask(File file) {
        log.info("loadTask start");
        try {
            var tasks = this.extractTask(file);
            var result = taskRepository.saveAll(tasks);
            if(result == null)   {
                log.info("loadTask empty data");
                return false;
            }                            
            FileUtility.deleteFile(file);
            return true;
        } catch(Exception ex) {            
            log.error("Error loadTask data " + ex.getMessage());    
            return false;
        }
        finally {
            log.info("loadTask data end");
        }        
    }

    /***
     * 
     */
    public List<Tasks> getAllTask() {        
        log.info("getAllTask start");
        try {
            return taskRepository.findAll();
        } catch(Exception ex) {            
            log.error("Error getAllTask data " + ex.getMessage());    
            return null;
        }
        finally {
            log.info("getAllTask data end");
        }  
    }
    
    /**
     * 
     * @param is InputStream
     * @return List<Teams>
     */
    private List<Tasks> extractTask(File file) {
        log.info("extractTask start");
        try {
            return csvHelper.csvToTasks(file);
        } catch(Exception ex) {
            log.error("Error extractTask: " + ex.getMessage());
            return null;
        } finally {
            log.info("extractTask end");
        }                
    }    
     
}
