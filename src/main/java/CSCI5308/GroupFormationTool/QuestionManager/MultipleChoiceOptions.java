package CSCI5308.GroupFormationTool.QuestionManager;

public class MultipleChoiceOptions implements IMultipleChoiceOptions {
	private String displayText;
	private int storedAs;

	public MultipleChoiceOptions() {
		this.displayText = "";
		this.storedAs = -1;
	}

	public String getDisplayText() {
		return displayText;
	}

	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	public int getStoredAs() {
		return storedAs;
	}

	public void setStoredAs(int storedAs) {
		this.storedAs = storedAs;
	}
	
	public boolean isEmptyOrNull() {
		return (this.displayText==null || this.displayText.isEmpty());
	}

}
