package CSCI5308.GroupFormationTool.EditCourseSurvey;

import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.Database.ICallStoredProcedure;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.Question;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditCourseSurveyDb implements IEditCourseSurveyDb {
	private Logger logger = Logger.getLogger(this.getClass());

	public ArrayList<IQuestion> loadQuestionsForSurveyByInstructorId(long instructorId, long courseId){
		ArrayList<IQuestion> questions= new ArrayList<IQuestion>();
		ICallStoredProcedure procedure= null;
		try {
			procedure= new CallStoredProcedure("spLoadQuestionForSurveyByInstructorId(?,?)");
			procedure.setParameter(1, instructorId);
			procedure.setParameter(2, courseId);
			ResultSet rs=procedure.executeWithResults();
			while(rs.next()) {
				IQuestion q= new Question();
				q.setQuestionId(rs.getLong(1));
				q.setTitle(rs.getString(2));
				q.setText(rs.getString(3));
				q.setCreatedOn(rs.getDate(5));
				questions.add(q);
			}
		}catch (SQLException e) {
			logger.error(e);
		}
		catch (Exception genericException){
			logger.error(genericException);
		}
		return questions;
	}
	
	public ArrayList<IQuestion> loadQuestionsOfSurveyByCourseId(long courseId){
		ArrayList<IQuestion> questions= new ArrayList<IQuestion>();
		ICallStoredProcedure procedure= null;
		try {
			procedure= new CallStoredProcedure("sploadQuestionsOfSurveyByCourseId(?)");
			procedure.setParameter(1, courseId);
			ResultSet rs = procedure.executeWithResults();
			while(rs.next()) {
				IQuestion q= new Question();
				q.setQuestionId(rs.getLong(1));
				q.setTitle(rs.getString(2));
				q.setText(rs.getString(3));
				q.setCreatedOn(rs.getDate(5));
				questions.add(q);
			}
		}catch (SQLException e) {
			logger.error(e);
		}
		catch (Exception genericException){
			logger.error(genericException);
		}
		return questions;
	}
	
	public boolean addQuestionToSurvey(long questionId, long courseId) {
		ICallStoredProcedure procedure= null;
		try {
			procedure= new CallStoredProcedure("spAddQuestionToSurvey(?,?)");
			procedure.setParameter(1, questionId);
			procedure.setParameter(2, courseId);
			procedure.execute();
		}catch (SQLException e) {
			logger.error(e);
			return false;
		}
		catch (Exception genericException){
			logger.error(genericException);
			return false;
		}
		return true;
	}
	public boolean deleteQuestionFromSurvey(long questionId) {
		ICallStoredProcedure procedure= null;
		try 
		{
			procedure= new CallStoredProcedure("spDeleteQuestionFromSurvey(?)");
			procedure.setParameter(1, questionId);
			procedure.execute();
			
		}catch (SQLException e) {
			logger.error(e);
			return false;
		}
		catch (Exception genericException){
			logger.error(genericException);
			return false;
		}
		return true;
	}
}
