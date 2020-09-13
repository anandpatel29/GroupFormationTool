package CSCI5308.GroupFormationTool.QuestionManagerTest;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import CSCI5308.GroupFormationTool.QuestionManager.IMultipleChoiceOptions;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionDb;
import CSCI5308.GroupFormationTool.QuestionManager.MultipleChoiceOptions;
import CSCI5308.GroupFormationTool.QuestionManager.Question;

public class QuestionDbMock implements IQuestionDb {
	private ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
	private IMultipleChoiceOptions choice = new MultipleChoiceOptions();
	private ArrayList<IMultipleChoiceOptions> choices = new ArrayList<IMultipleChoiceOptions>();
	private Date date = new Date(System.currentTimeMillis());
	private Question question = new Question();

	public QuestionDbMock() {
		question.setQuestionId(1);
		question.setTitle("title1");
		question.setText("text1");
		question.setType("MCQ one1");
		question.setCreatedOn(date);
		choice.setDisplayText("abc1");
		choice.setStoredAs(1);
		choices.add(choice);
		choice.setDisplayText("def1");
		choice.setStoredAs(2);
		choices.add(choice);
		question.setMultipleChoiceOptions(choices);
		questions.add(question);
		choices.removeAll(choices);
		question.setQuestionId(2);
		question.setTitle("title2");
		question.setText("text2");
		question.setType("MCQ one2");
		question.setCreatedOn(date);
		questions.add(question);
		choice.setDisplayText("abc2");
		choice.setStoredAs(1);
		choices.add(choice);
		choice.setDisplayText("def1");
		choice.setStoredAs(2);
		choices.add(choice);
		question.setMultipleChoiceOptions(choices);
		questions.add(question);
	}

	public boolean createQuestion(IQuestion question1) {
		if (question1.getQuestionId() >= 0) {
			return false;
		}
		question1.setQuestionId(3);
		questions.add(question1);

		for (IQuestion q : questions) {
			if (q.getQuestionId() == question1.getQuestionId()) {
				return true;
			}
		}
		return false;

	}

	@Override
	public List<IQuestion> loadQuestionByID(String id) {
		return null;
	}

	@Override
	public boolean deleteQuestion(long id) {
		boolean success = false;
		for (IQuestion q : questions) {
			if (q.getQuestionId() == id) {
				questions.remove(q);
				success = true;
				break;
			}
		}
		return success;
	}

}
