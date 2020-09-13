package CSCI5308.GroupFormationTool.EditCourseSurvey;

import java.util.ArrayList;
import java.util.HashMap;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.Question;

public class EditCourseSurveyDbMock implements IEditCourseSurveyDb {
	private HashMap<Long, ArrayList<Long>> surveyQuestion = new HashMap<Long, ArrayList<Long>>();
	private HashMap<Long, Long> courseSurvey = new HashMap<Long, Long>();
	private HashMap<Long, Long> courseInstructor = new HashMap<Long, Long>();
	private HashMap<Long, ArrayList<Long>> instructorQuestion = new HashMap<Long, ArrayList<Long>>();

	public EditCourseSurveyDbMock() {
		ArrayList<Long> questionsInSurvey = new ArrayList<Long>();
		courseSurvey.put(5309L, 1L);
		courseInstructor.put(5309L, 837967L);
		questionsInSurvey.add(100L);
		questionsInSurvey.add(101L);
		questionsInSurvey.add(102L);
		surveyQuestion.put(1L, questionsInSurvey);
		questionsInSurvey = new ArrayList<Long>();
		questionsInSurvey.add(100L);
		questionsInSurvey.add(101L);
		questionsInSurvey.add(102L);
		questionsInSurvey.add(103L);
		questionsInSurvey.add(104L);
		questionsInSurvey.add(105L);
		instructorQuestion.put(837967L, questionsInSurvey);

		courseSurvey.put(5310L, 2L);
		courseInstructor.put(5310L, 837972L);
		questionsInSurvey = new ArrayList<Long>();
		questionsInSurvey.add(200L);
		questionsInSurvey.add(201L);
		questionsInSurvey.add(202L);
		surveyQuestion.put(2L, questionsInSurvey);
		questionsInSurvey = new ArrayList<Long>();
		questionsInSurvey.add(200L);
		questionsInSurvey.add(201L);
		questionsInSurvey.add(202L);
		questionsInSurvey.add(203L);
		questionsInSurvey.add(204L);
		questionsInSurvey.add(205L);
		instructorQuestion.put(837972L, questionsInSurvey);
	}

	public ArrayList<IQuestion> loadQuestionsForSurveyByInstructorId(long instructorId, long courseId) {
		ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
		if (courseSurvey.keySet().contains(courseId)) {
			long sid = courseSurvey.get(courseId);
			ArrayList<Long> instructorQuestions = instructorQuestion.get(instructorId);
			ArrayList<Long> surveyQuestionList = surveyQuestion.get(sid);
			for (int i = 0; i < instructorQuestions.size(); i++) {
				if (!surveyQuestionList.contains(instructorQuestions.get(i))) {
					Question q = new Question();
					q.setQuestionId(instructorQuestions.get(i));
					questions.add(q);
				}
			}

			return questions;
		} else {
			return null;
		}

	}

	public ArrayList<IQuestion> loadQuestionsOfSurveyByCourseId(long courseId) {

		ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
		if (courseSurvey.keySet().contains(courseId)) {
			long sid = courseSurvey.get(courseId);
			ArrayList<Long> questionList = surveyQuestion.get(sid);
			for (int i = 0; i < questionList.size(); i++) {
				Question q = new Question();
				q.setQuestionId(questionList.get(i));
				questions.add(q);
			}
			return questions;
		} else {
			return null;
		}

	}

	public boolean addQuestionToSurvey(long questionId, long courseId) {
		if (!instructorQuestion.get(courseInstructor.get(courseId)).contains(questionId)) {
			return false;
		}
		if (surveyQuestion.get(courseSurvey.get(courseId)).contains(questionId)) {
			return false;
		}
		long sid = courseSurvey.get(courseId);
		ArrayList<Long> questionInSurvey = surveyQuestion.get(sid);
		questionInSurvey.add(questionId);
		surveyQuestion.put(sid, questionInSurvey);
		return (surveyQuestion.get(sid).contains(questionId));

	}

	public boolean deleteQuestionFromSurvey(long questionId) {
		int flag = 0;
		for (long surveyId : surveyQuestion.keySet()) {
			if (surveyQuestion.get(surveyId).contains(questionId)) {
				surveyQuestion.get(surveyId).remove(questionId);
				flag = 1;
				if (surveyQuestion.get(surveyId).size() == 0) {
					courseSurvey.remove(surveyId);
				}
			}
		}
		if (flag == 0) {
			return false;
		}
		for (long surveyId : surveyQuestion.keySet()) {
			if (surveyQuestion.get(surveyId).contains(questionId)) {
				return false;
			}
		}
		return true;

	}
}
