package tm.assessment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import tm.assessment.model.Tasks;
import tm.assessment.model.TeamSkill;
import tm.assessment.model.Teams;
import tm.assessment.service.TeamServiceImpl;
import tm.assessment.utility.CsvHelper;

class AssessmentApplicationTest {

	@Test
	public void WhenTeamIsProvided_ReturnSameInsertName() {
		TeamServiceImpl teamService = new TeamServiceImpl();
		var result = teamService.extractTeam(new File("../../../tm_assessment/assessment/load_data/team.csv"));
		assertEquals(result, true);
	}

	// test the csvHelper
	@Test
	public void WhenTeamCsvLoad() throws FileNotFoundException {
		var csvTeam = new CsvHelper();
		List<Teams> teams = csvTeam.csvToTeams(new File("../../../tm_assessment/assessment/load_data_test/team.csv"));
		assertEquals("TEAM_01", teams.get(0).getTeamId());
	}

	@Test
	public void WhenTaskCsvLoad() throws FileNotFoundException {
		var csvTeam = new CsvHelper();
		List<Tasks> task = csvTeam.csvToTasks(new File("../../../tm_assessment/assessment/load_data_test/task.csv"));		
		assertNotNull(task.get(0).getTaskId());
	}

	@Test
	public void WhenTeamSkillCsvLoad() throws FileNotFoundException {
		var csvTeam = new CsvHelper();
		List<TeamSkill> teamSkill = csvTeam.csvToTeamSkills(new File("../../../tm_assessment/assessment/load_data_test/team_skill.csv"));		
		assertNotNull(teamSkill.get(0).getSkill());
	}

}
