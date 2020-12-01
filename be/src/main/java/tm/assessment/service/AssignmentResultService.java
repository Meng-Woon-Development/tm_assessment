package tm.assessment.service;

import tm.assessment.model.AssignmentResult;

public interface AssignmentResultService {
    
    AssignmentResult saveAssignmentResult(AssignmentResult resource);

    AssignmentResult saveAssignmentResult(String task, String skill);

}
