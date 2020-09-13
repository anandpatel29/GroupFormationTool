package CSCI5308.GroupFormationTool.Courses;

import java.util.List;

public interface IStudentCsvImport {
	public void parseCsvFile();

	public List<String> getSuccessResults();

	public List<String> getFailureResults();
}
