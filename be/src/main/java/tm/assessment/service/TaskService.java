package tm.assessment.service;

import java.io.File;
import java.util.List;

import tm.assessment.model.Tasks;

public interface TaskService {
    boolean loadTask(File is);

    List<Tasks> getAllTask();
}
