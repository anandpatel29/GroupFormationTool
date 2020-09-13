package CSCI5308.GroupFormationTool.QuestionManager;

import java.sql.Date;
import java.util.ArrayList;

public class Question implements IQuestion {
	private long questionId;
	private String title;
	private String text;
	private String type;
	private Date createdOn;
	private ArrayList<IMultipleChoiceOptions> multipleChoiceOptions;

	public Question() {
		setDefaults();
	}

	private void setDefaults() {
		long millis = System.currentTimeMillis();
		createdOn = new Date(millis);
		questionId = -1;
		title = "";
		text = "";
		type = "";
		multipleChoiceOptions = new ArrayList<IMultipleChoiceOptions>();
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public ArrayList<IMultipleChoiceOptions> getMultipleChoiceOptions() {
		return multipleChoiceOptions;
	}

	public void setMultipleChoiceOptions(ArrayList<IMultipleChoiceOptions> multipleChoiceOptions) {
		this.multipleChoiceOptions = multipleChoiceOptions;
	}

	public boolean isValidQuestion() {
		return this.questionId != -1;
	}

	public boolean createQuestion(IQuestionDb questionDB) {

		return questionDB.createQuestion(this);
	}

	public boolean isTypeValid(String type) {
		if (type == null || type.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isTextValid(String text) {
		if (text == null || text.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean isTitleValid(String title) {
		if (title == null || title.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean delete(IQuestionDb questionDB) {
		return questionDB.deleteQuestion(questionId);
	}

}
