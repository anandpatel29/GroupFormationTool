/**
 * 
 */
package CSCI5308.GroupFormationTool.EditCourseSurvey;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

@SpringBootTest
@SuppressWarnings("deprecation")
class EditCourseSurveyTest {

	@Test
	public void loadQuestionsForSurveyByInstructorId() {
		IEditCourseSurveyDb editCourseSurveyDbMock = new EditCourseSurveyDbMock();
		ArrayList<IQuestion> questions = editCourseSurveyDbMock.loadQuestionsForSurveyByInstructorId(837967L, 5309L);
		Assert.isTrue(questions.size() == 3);
		Assert.isTrue(questions.get(0).getQuestionId() == 103);
		Assert.isTrue(questions.get(1).getQuestionId() == 104);
		Assert.isTrue(questions.get(2).getQuestionId() == 105);
		questions.remove(questions);
		questions = editCourseSurveyDbMock.loadQuestionsForSurveyByInstructorId(837972L, 5310L);
		Assert.isTrue(questions.size() == 3);
		Assert.isTrue(questions.get(0).getQuestionId() == 203);
		Assert.isTrue(questions.get(1).getQuestionId() == 204);
		Assert.isTrue(questions.get(2).getQuestionId() == 205);
	}

	@Test
	public void loadQuestionsOfSurveyByCourseId() {
		IEditCourseSurveyDb editCourseSurveyDbMock = new EditCourseSurveyDbMock();
		ArrayList<IQuestion> questions = editCourseSurveyDbMock.loadQuestionsOfSurveyByCourseId(5309L);
		Assert.isTrue(questions.size() == 3);
		Assert.isTrue(questions.get(0).getQuestionId() == 100);
		Assert.isTrue(questions.get(1).getQuestionId() == 101);
		Assert.isTrue(questions.get(2).getQuestionId() == 102);
		questions.remove(questions);
		questions = editCourseSurveyDbMock.loadQuestionsOfSurveyByCourseId(5310L);
		Assert.isTrue(questions.size() == 3);
		Assert.isTrue(questions.get(0).getQuestionId() == 200);
		Assert.isTrue(questions.get(1).getQuestionId() == 201);
		Assert.isTrue(questions.get(2).getQuestionId() == 202);
	}

	@Test
	public void addQuestionToSurvey() {
		IEditCourseSurveyDb editCourseSurveyDbMock = new EditCourseSurveyDbMock();
		assertFalse(editCourseSurveyDbMock.addQuestionToSurvey(106, 5309L));
		assertFalse(editCourseSurveyDbMock.addQuestionToSurvey(102, 5309L));
		Assert.isTrue(editCourseSurveyDbMock.addQuestionToSurvey(104, 5309L));
		assertFalse(editCourseSurveyDbMock.addQuestionToSurvey(206, 5309L));
		assertFalse(editCourseSurveyDbMock.addQuestionToSurvey(202, 5309L));
		Assert.isTrue(editCourseSurveyDbMock.addQuestionToSurvey(204, 5310L));

	}

	@Test
	public void deleteQuestionFromSurvey() {
		IEditCourseSurveyDb editCourseSurveyDbMock = new EditCourseSurveyDbMock();
		assertFalse(editCourseSurveyDbMock.deleteQuestionFromSurvey(106));
		assertFalse(editCourseSurveyDbMock.deleteQuestionFromSurvey(206));
		Assert.isTrue(editCourseSurveyDbMock.deleteQuestionFromSurvey(102));
		Assert.isTrue(editCourseSurveyDbMock.deleteQuestionFromSurvey(202));
	}
}
