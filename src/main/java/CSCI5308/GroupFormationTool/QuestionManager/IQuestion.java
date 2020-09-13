package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Date;
import java.util.ArrayList;

public interface IQuestion {
	public long getQuestionId();

	public void setQuestionId(long questionId);

	public String getTitle();

	public void setTitle(String title);

	public String getText();

	public void setText(String text);

	public String getType();

	public void setType(String type);

	public Date getCreatedOn();

	public void setCreatedOn(Date createdOn);

	public ArrayList<IMultipleChoiceOptions> getMultipleChoiceOptions();

	public void setMultipleChoiceOptions(ArrayList<IMultipleChoiceOptions> multipleChoiceOptions);

	public boolean isValidQuestion();

	public boolean createQuestion(IQuestionDb questionDB);

	public boolean isTypeValid(String type);

	public boolean isTextValid(String text);

	public boolean isTitleValid(String title);

	public boolean delete(IQuestionDb questionDB);
}
