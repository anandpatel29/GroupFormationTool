package CSCI5308.GroupFormationTool.QuestionManagerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestionDb;
import CSCI5308.GroupFormationTool.QuestionManager.Question;

@SpringBootTest
@SuppressWarnings("deprecation")
class QuestionTest {
	IQuestion question;

	@Test
	public void ConstructorTest() {
		question = new Question();
		assertTrue(question.getText().isEmpty());
		assertTrue(question.getTitle().isEmpty());
		assertTrue(question.getType().isEmpty());
		assertTrue(question.getQuestionId() == -1);

	}

	@Test
	public void getQuestionIdtest() {
		question = new Question();
		Assert.isTrue(question.getQuestionId() == -1);
		question.setQuestionId(100);
		Assert.isTrue(question.getQuestionId() == 100);

	}

	@Test
	public void setQuestionIdTest() {
		question = new Question();
		question.setQuestionId(100);
		Assert.isTrue(question.getQuestionId() == 100);
	}

	@Test
	public void getTitleTest() {
		question = new Question();
		Assert.isTrue(question.getTitle().isEmpty());
		question.setTitle("title");
		Assert.isTrue(question.getTitle().compareTo("title") == 0);
	}

	@Test
	public void setTitleTest() {
		question = new Question();
		question.setTitle("title");
		Assert.isTrue(question.getTitle().compareTo("title") == 0);
	}

	@Test
	public void getTypeTest() {
		question = new Question();
		Assert.isTrue(question.getType().isEmpty());
		question.setType("type");
		Assert.isTrue(question.getType().compareTo("type") == 0);
	}

	@Test
	public void setTypeTest() {
		question = new Question();
		question.setType("type");
		Assert.isTrue(question.getType().compareTo("type") == 0);
	}

	@Test
	public void getTextTest() {
		question = new Question();
		Assert.isTrue(question.getText().isEmpty());
		question.setText("text");
		Assert.isTrue(question.getText().compareTo("text") == 0);
	}

	@Test
	public void setTextTest() {
		question = new Question();
		question.setText("text");
		Assert.isTrue(question.getText().compareTo("text") == 0);
	}

	@Test
	public void getCreatedOnTest() {
		question = new Question();
		Assert.isTrue(question.getCreatedOn().equals(new Date(System.currentTimeMillis())));
	}

	@Test
	public void setCreatedOnTest() {
		question = new Question();
		Date date = new Date(System.currentTimeMillis());
		Assert.isTrue(question.getCreatedOn().equals(date));

	}

	@Test
	public void isTitleValid() {
		question = new Question();
		Assert.isTrue(question.isTitleValid("title"));
		Assert.isTrue(!question.isTitleValid(""));
		Assert.isTrue(!question.isTitleValid(null));
		Assert.isTrue(question.isTitleValid("titleofquestion"));
	}

	@Test
	public void isTypeValid() {
		question = new Question();
		Assert.isTrue(question.isTypeValid("type"));
		Assert.isTrue(!question.isTypeValid(""));
		Assert.isTrue(!question.isTypeValid(null));
		Assert.isTrue(question.isTypeValid("typeofquestion"));
	}

	@Test
	public void isTextValid() {
		question = new Question();
		Assert.isTrue(question.isTextValid("text"));
		Assert.isTrue(!question.isTextValid(""));
		Assert.isTrue(!question.isTextValid(null));
		Assert.isTrue(question.isTextValid("questiontext"));
	}

	@Test
	void createQuestion() {
		question = new Question();
		IQuestionDb questionDBMock = new QuestionDbMock();
		question.setTitle("abcdefghij");
		question.setText("abcdefghij");
		question.setType("numeric");
		question.setCreatedOn(new Date(System.currentTimeMillis()));
		Assert.isTrue(question.createQuestion(questionDBMock));
		question.setQuestionId(3);
		assertFalse(question.createQuestion(questionDBMock));
	}

}
