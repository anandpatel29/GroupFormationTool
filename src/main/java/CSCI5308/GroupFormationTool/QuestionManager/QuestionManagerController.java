package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorMessage;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionManagerController {
	private static final String MCQONE = "Multiple choice choose one";
	private static final String MCQMULTIPLE = "Multiple choice choose multiple";

	@RequestMapping("/questionmanager/questionmanager")
	public String gotoQuestionManagerPage(Model model, @RequestParam("id") long courseID) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		courseDb.loadCourseById(courseID, c);
		model.addAttribute("course", c);
		return "questionmanager/questionmanager";
	}

	@RequestMapping("/questionmanager/createquestion")
	public String gotoCreateQuestionPage(Model model, @RequestParam("id") long courseID) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		courseDb.loadCourseById(courseID, c);
		model.addAttribute("course", c);
		model.addAttribute("numeric", false);
		return "questionmanager/createquestion";
	}

	@PostMapping("/questionmanager/submitquestion")
	public String gotoAnswerPage(Model model, @RequestParam("id") long courseId,
			@RequestParam("question") String question, @RequestParam("title") String title,
			@RequestParam("questionType") String questionType,
			@RequestParam(name = "optionText") ArrayList<String> optionText,
			@RequestParam(name = "storedAs") ArrayList<Integer> storedAs) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		IQuestion q = new Question();
		courseDb.loadCourseById(courseId, c);
		q.setTitle(title);
		q.setType(questionType);
		q.setText(question);
		model.addAttribute("course", c);
		List<String> failureList = new ArrayList<>();
		boolean isValidQuestionText = q.isTextValid(question);
		boolean isValidQuestionTitle = q.isTitleValid(title);
		boolean isValidQuestionType = q.isTypeValid(questionType);
		Map<String, Boolean> messageMap = new HashMap<String, Boolean>();
		messageMap.put(ErrorMessage.INVALID_QUESTION_TITLE_ERROR, isValidQuestionTitle);
		messageMap.put(ErrorMessage.INVALID_QUESTION_TXT_ERROR, isValidQuestionText);
		messageMap.put(ErrorMessage.INVALID_QUESTION_TYPE_ERROR, isValidQuestionType);

		for (String message : messageMap.keySet()) {
			if (!messageMap.get(message)) {
				failureList.add(message);
			}
		}

		IQuestionDb questionDb = SystemConfig.instance().getQuestionDb();
		ArrayList<IMultipleChoiceOptions> mco = new ArrayList<IMultipleChoiceOptions>();
		if (questionType.equals(MCQONE) || questionType.equals(MCQMULTIPLE)) {
			for (int i = 0; i < optionText.size(); i++) {
				boolean isOptionTextEmpty = optionText.get(i).isEmpty();
				boolean isStoredAsTextNull = null == storedAs.get(i);
				messageMap.put(ErrorMessage.EMPTY_OPTION_TEXT_ERROR, !isOptionTextEmpty);
				messageMap.put(ErrorMessage.INVALID_STORED_AS_ERROR, !isStoredAsTextNull);
				for (String message : messageMap.keySet()) {
					if (!messageMap.get(message)) {
						failureList.add(message);
					}
				}

				if (failureList.isEmpty()) {
					MultipleChoiceOptions mc = new MultipleChoiceOptions();
					mc.setDisplayText(optionText.get(i));
					System.out.println(storedAs.get(i));
					mc.setStoredAs(storedAs.get(i));
					mco.add(mc);
				} else {
					model.addAttribute(ErrorMessage.ERROR_LABEL, failureList);
					return "questionmanager/createquestion";
				}

			}
		}
		if (failureList.isEmpty()) {
			q.setMultipleChoiceOptions(mco);
			if (questionDb.createQuestion(q)) {
				return "questionmanager/questionmanager";
			} else {
				return "error";
			}
		} else {
			model.addAttribute(ErrorMessage.ERROR_LABEL, failureList);
			return "questionmanager/createquestion";
		}

	}

}
