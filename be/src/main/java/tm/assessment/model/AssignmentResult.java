package tm.assessment.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResult {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String team;
    private String task;
    private String skill;
    // private int numberOfTask;

    public AssignmentResult(String team, String task, String skill) {
        this.team = team;
        this.task = task;
        this.skill = skill;
        // this.numberOfTask = numberOfTask;
    }
}
