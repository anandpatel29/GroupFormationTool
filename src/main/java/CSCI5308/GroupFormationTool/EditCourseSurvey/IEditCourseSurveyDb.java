package CSCI5308.GroupFormationTool.EditCourseSurvey;

import java.util.ArrayList;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

public interface IEditCourseSurveyDb {
	public ArrayList<IQuestion> loadQuestionsForSurveyByInstructorId(long instructorId, long courseId);

	public ArrayList<IQuestion> loadQuestionsOfSurveyByCourseId(long courseId);

	public boolean addQuestionToSurvey(long questionId, long courseId);

	public boolean deleteQuestionFromSurvey(long questionId);

}
