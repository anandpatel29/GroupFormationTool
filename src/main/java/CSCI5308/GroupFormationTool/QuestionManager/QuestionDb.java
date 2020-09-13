package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.Database.CallStoredProcedure;
import CSCI5308.GroupFormationTool.Database.ICallStoredProcedure;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDb implements IQuestionDb {
	private Logger logger = Logger.getLogger(this.getClass());
	private static final String MCQone = "Multiple choice choose one";
	private static final String MCQmultiple = "Multiple choice choose multiple";

	public boolean createQuestion(IQuestion question) {
		ICallStoredProcedure procedure = null;
		int typeId = -1;
		try {
			procedure = new CallStoredProcedure("spLoadTypeIdFromType(?)");
			procedure.setParameter(1, question.getType());
			ResultSet rs = procedure.executeWithResults();
			if (rs.next()) {
				typeId = Integer.parseInt(rs.getString(1));
			}
			IUser u = CurrentUser.instance().getCurrentAuthenticatedUser();
			procedure = new CallStoredProcedure("spCreateQuestion(?,?,?,?,?)");
			procedure.setParameter(1, question.getTitle());
			procedure.setParameter(2, question.getText());
			procedure.setParameter(3, typeId);
			procedure.setParameter(4, question.getCreatedOn().toString());
			procedure.setParameter(5, u.getId());
			rs = null;
			rs = procedure.executeWithResults();
			if (rs.next()) {
				question.setQuestionId(rs.getLong(1));
			}

			if (question.getType().equals(MCQone) || question.getType().equals(MCQmultiple)) {
				ArrayList<IMultipleChoiceOptions> multipleChoiceOptions = question.getMultipleChoiceOptions();
				long questionId = question.getQuestionId();
				for (IMultipleChoiceOptions mco : multipleChoiceOptions) {
					createMultipleChoiceOptions(mco, questionId);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} catch (Exception genericException) {
			logger.error(genericException);
			return false;
		} finally {
			if (procedure != null) {
				procedure.cleanup();
			}
		}
		return true;
	}

	@Override
	public List<IQuestion> loadQuestionByID(String id) {
		List<IQuestion> questions = new ArrayList<IQuestion>();
		ICallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spLoadQuestionByID(?)");
			proc.setParameter(1, id);
			ResultSet results = proc.executeWithResults();
			if (null != results) {
				while (results.next()) {
					long qid = results.getLong(1);
					String title = results.getString(2);
					String text = results.getString(3);
					String typeid = results.getString(4);
					Date createdon = results.getDate(5);
					IQuestion q = new Question();
					q.setQuestionId(qid);
					q.setTitle(title);
					q.setText(text);
					q.setType(typeid);
					q.setCreatedOn(createdon);
					questions.add(q);
				}
			}
		} catch (SQLException e) {
			logger.error(e);
		} catch (Exception genericException) {
			logger.error(genericException);
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return questions;
	}

	@Override
	public boolean deleteQuestion(long id) {
		ICallStoredProcedure proc = null;
		try {
			proc = new CallStoredProcedure("spDeleteQuestion(?)");
			proc.setParameter(1, id);
			proc.execute();
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} catch (Exception genericException) {
			logger.error(genericException);
			return false;
		} finally {
			if (null != proc) {
				proc.cleanup();
			}
		}
		return true;
	}

	private boolean createMultipleChoiceOptions(IMultipleChoiceOptions mco, long questionId) {
		ICallStoredProcedure procedure = null;
		try {
			procedure = new CallStoredProcedure("spCreateMultipleChoiceOptions(?,?,?)");
			procedure.setParameter(1, questionId);
			procedure.setParameter(2, mco.getDisplayText());
			procedure.setParameter(3, mco.getStoredAs());
			procedure.execute();
			return true;
		} catch (SQLException e) {
			logger.error(e);
			return false;
		} catch (Exception genericException) {
			logger.error(genericException);
			return false;
		} finally {
			if (procedure != null) {
				procedure.cleanup();
			}
		}
	}

}
