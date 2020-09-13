package CSCI5308.GroupFormationTool.QuestionManagerTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import CSCI5308.GroupFormationTool.QuestionManager.IMultipleChoiceOptions;
import CSCI5308.GroupFormationTool.QuestionManager.MultipleChoiceOptions;

@SpringBootTest
@SuppressWarnings("deprecation")
class MultipleChoiceOptionsTest {

	@Test
	void testConstructor() {
		IMultipleChoiceOptions mco = new MultipleChoiceOptions();
		Assert.isTrue(mco.getDisplayText().equals(""));
		Assert.isTrue(mco.getStoredAs() == -1);
	}

	@Test
	public void getDisplayTextTest() {
		IMultipleChoiceOptions mco = new MultipleChoiceOptions();
		Assert.isTrue(mco.getDisplayText().equals(""));
		mco.setDisplayText("displaytext");
		Assert.isTrue(mco.getDisplayText().equals("displaytext"));
	}

	@Test
	public void setDisplayTextTest() {
		IMultipleChoiceOptions mco = new MultipleChoiceOptions();
		mco.setDisplayText("displaytext");
		Assert.isTrue(mco.getDisplayText().equals("displaytext"));
	}

	@Test
	public void getStoredAsTest() {
		IMultipleChoiceOptions mco = new MultipleChoiceOptions();
		Assert.isTrue(mco.getStoredAs() == -1);
		mco.setStoredAs(100);
		Assert.isTrue(mco.getStoredAs() == 100);
	}

	@Test
	public void setStoredAsTest() {
		IMultipleChoiceOptions mco = new MultipleChoiceOptions();
		mco.setStoredAs(100);
		Assert.isTrue(mco.getStoredAs() == 100);
	}

}
