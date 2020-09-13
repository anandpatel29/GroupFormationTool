package CSCI5308.GroupFormationTool.QuestionManager;

import java.util.List;

public interface IQuestionDb {
	public boolean createQuestion(IQuestion question);

	public List<IQuestion> loadQuestionByID(String id);

	public boolean deleteQuestion(long id);

}
