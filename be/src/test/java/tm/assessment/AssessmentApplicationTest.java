package tm.assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.junit.jupiter.api.Test;

import tm.assessment.service.TeamServiceImpl;

class AssessmentApplicationTest {

	@Test
	public void WhenTeamIsProvided_ReturnSameInsertName() {		
		TeamServiceImpl teamService = new TeamServiceImpl();		
		var result = teamService.extractTeam(new File("../../../tm_assessment/assessment/load_data/team.csv"));
		assertEquals(result, true);
	}

	// test the csvHelper

}
