package tm.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import tm.assessment.model.AssignmentResult;
import tm.assessment.service.AssignmentResultService;

@RestController
@RequestMapping("/api/assignment")
@Log4j2
@CrossOrigin
public class AssignmentResultController {
    
    @Autowired
    AssignmentResultService assignmentService;

    @PostMapping("/save")
    public ResponseEntity<AssignmentResult> postAsync(@RequestBody AssignmentResult resource) {
        log.info("postAsync start");
        try {
            var res = assignmentService.saveAssignmentResult(resource);
            return ResponseEntity.ok(res);            
        }catch(Exception ex) {
            log.error("postAsync error", resource);
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(resource);
        }finally{
            log.info("postAsync end", resource);
        }

    }
}
