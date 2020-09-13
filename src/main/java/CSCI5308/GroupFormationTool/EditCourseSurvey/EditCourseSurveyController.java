package CSCI5308.GroupFormationTool.EditCourseSurvey;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import CSCI5308.GroupFormationTool.SystemConfig;
import CSCI5308.GroupFormationTool.AccessControl.CurrentUser;
import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.Courses.Course;
import CSCI5308.GroupFormationTool.Courses.ICourse;
import CSCI5308.GroupFormationTool.Courses.ICoursePersistence;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorMessage;
import CSCI5308.GroupFormationTool.QuestionManager.IQuestion;

@Controller
public class EditCourseSurveyController {
	private Logger logger = Logger.getLogger(this.getClass());
	private static final String ID = "id";
	private static final String QID = "qid";

	@GetMapping("/editcoursesurvey/editcoursesurvey")
	public String editCourseSurveyPage(Model model, @RequestParam(name = ID) long courseId) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		courseDb.loadCourseById(courseId, c);
		model.addAttribute("course", c);
		return "editcoursesurvey/editcoursesurvey";
	}

	@GetMapping("/editcoursesurvey/viewquestioninsurvey")
	public String viewQuestionInSurveyPage(Model model, @RequestParam(name = ID) long courseId) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		IEditCourseSurveyDb surveyDb = SystemConfig.instance().getSurveyDb();
		ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
		questions = surveyDb.loadQuestionsOfSurveyByCourseId(courseId);
		ICourse c = new Course();
		courseDb.loadCourseById(courseId, c);
		model.addAttribute("course", c);
		model.addAttribute("questions", questions);
		return "editcoursesurvey/viewquestioninsurvey";
	}

	@GetMapping("/editcoursesurvey/addquestiontosurvey")
	public String addQuestionToSurveyPage(Model model, @RequestParam(name = ID) long courseId) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		IEditCourseSurveyDb surveyDb = SystemConfig.instance().getSurveyDb();
		ArrayList<IQuestion> questions = new ArrayList<IQuestion>();
		IUser user = CurrentUser.instance().getCurrentAuthenticatedUser();
		questions = surveyDb.loadQuestionsForSurveyByInstructorId(user.getId(), courseId);
		ICourse c = new Course();
		courseDb.loadCourseById(courseId, c);
		model.addAttribute("course", c);
		model.addAttribute("questions", questions);
		return "editcoursesurvey/addquestiontosurvey";
	}

	@GetMapping("/editcoursesurvey/addquestion")
	public ModelAndView addQuestionToSurvey(Model model, @RequestParam(name = QID) long questionId,
			@RequestParam(name = ID) long courseId) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		courseDb.loadCourseById(courseId, c);
		IEditCourseSurveyDb surveyDb = new EditCourseSurveyDb();
		if (surveyDb.addQuestionToSurvey(questionId, courseId)) {
			ModelAndView mav = new ModelAndView("redirect:/editcoursesurvey/addquestiontosurvey");
			mav.addObject("id", c.getId());
			return mav;
		} else {
			ModelAndView mav = new ModelAndView("redirect:/editcoursesurvey/addquestiontosurvey");
			mav.addObject("id", c.getId());
			mav.addObject(ErrorMessage.ERROR_LABEL, ErrorMessage.QUESTION_ALREADY_EXIST);
			logger.info(ErrorMessage.QUESTION_ALREADY_EXIST_LOG);
			return mav;
		}
	}

	@GetMapping("/editcoursesurvey/deletequestion")
	public ModelAndView deleteQuestionFromSurvey(Model model, @RequestParam(name = QID) long questionId,
			@RequestParam(name = ID) long courseID) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		courseDb.loadCourseById(courseID, c);

		IEditCourseSurveyDb surveyDb = new EditCourseSurveyDb();
		if (surveyDb.deleteQuestionFromSurvey(questionId)) {
			ModelAndView mav = new ModelAndView("redirect:/editcoursesurvey/viewquestioninsurvey");
			mav.addObject("id", c.getId());
			return mav;
		} else {
			logger.info(ErrorMessage.QUESTION_ALREADY_DELETED_LOG);
			ModelAndView mav = new ModelAndView("redirect:/editcoursesurvey/viewquestioninsurvey");
			mav.addObject("id", c.getId());
			mav.addObject(ErrorMessage.ERROR_LABEL, ErrorMessage.QUESTION_ALREADY_DELETED);
			return mav;
		}
	}

}
