package tm.assessment.component;

import java.io.File;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFile.Type;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import tm.assessment.service.AssignmentResultService;
import tm.assessment.service.TaskService;
import tm.assessment.service.TeamService;
import tm.assessment.service.TeamSkillService;

@Log4j2
@Component
public class MyFileChangeListener implements FileChangeListener {

    @Autowired
    TeamService teamService;

    @Autowired
    TaskService taskService;

    @Autowired
    TeamSkillService teamSkillService;

    @Autowired
    AssignmentResultService assignmentResultService;

    @Value("${import-path}")
    private String importPath;
    
    @Override
    public void onChange(Set<ChangedFiles> changeSet) {
        try {
            boolean teamStatus = false;
            boolean taskStatus = false;
            boolean teamSkillStatus = false;
            for(ChangedFiles cfiles : changeSet) {
                for(ChangedFile cfile: cfiles.getFiles()) {
                    // if( /* (cfile.getType().equals(Type.MODIFY) 
                    //      || cfile.getType().equals(Type.ADD)  
                    //      || cfile.getType().equals(Type.DELETE) ) && */ !isLocked(cfile.getFile().toPath())) {
                        
                    //     // System.out.println("Operation: " + cfile.getType() 
                    //     //   + " On file: "+ cfile.getFile().getName() + " is done");
                    //     //   System.out.println("../../tm_assessment/import/"+cfile.getFile().getName().toString());
                    //     log.info("insert file");
                    //     log.info("Operation: " + cfile.getType() + " on file:"+ cfile.getFile().getName());

                    // }    
                    log.info("Operation: " + cfile.getType() + " On file: "+ cfile.getFile().getName() + " is done");
                    if(cfile.getType().equals(Type.ADD)) {
                        
                        var file = new File(importPath + cfile.getFile().getName().toString());
                        switch (cfile.getFile().getName()) {                            
                            case "team.csv":                                                       
                                teamStatus = teamService.loadTeam(file);
                                log.info("loaded " + cfile.getFile().getName() +" file: " + teamStatus);                                    
                                break;
                            case "task.csv":                                                       
                                taskStatus = taskService.loadTask(file);
                                log.info("loaded " + cfile.getFile().getName() +" file: " + taskStatus);  
                                break;
                            case "team_skill.csv":                                                       
                                teamSkillStatus = teamSkillService.loadTeamSkill(file);
                                log.info("loaded " + cfile.getFile().getName() +" file: " + teamSkillStatus);
                                break;
                            default:
                        }
                    }
                }
            }

            if(teamStatus && teamSkillStatus && taskStatus) {
                var tasks = taskService.getAllTask();
                if(tasks != null) {
                    log.info("Start assigning task");
                    tasks.forEach(t -> {                                            
                        var savedRecord = assignmentResultService.saveAssignmentResult(t.getTaskId(), t.getSkill());
                        log.info("Task: " + savedRecord.getTask() + " Skill: " + savedRecord.getSkill() + " assigned to Team: " + savedRecord.getTeam());
                    });
                    log.info("End assigning task");
                } else {
                    log.info("Empty data no task assign!");
                }                
            }

        } catch (Exception ex) {
            log.error("Listener error :" + ex.getMessage(), ex);
        }        
    }

}
