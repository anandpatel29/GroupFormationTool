package CSCI5308.GroupFormationTool.Courses;

import CSCI5308.GroupFormationTool.AccessControl.IUser;
import CSCI5308.GroupFormationTool.AccessControl.User;
import CSCI5308.GroupFormationTool.ErrorHandling.ErrorMessage;
import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;

@Controller
public class CourseAdminController {
	private static final String ID = "id";
	private static final String TITLE = "title";
	private static final String INSTRUCTOR = "instructor";

	@GetMapping("/admin/course")
	public String course(Model model) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		List<ICourse> allCourses = courseDb.loadAllCourses();
		model.addAttribute("courses", allCourses);
		return "admin/course";
	}

	@GetMapping("/admin/course/courseresults")
	public String instructorAdmin(Model model,
			@RequestParam(name = ErrorMessage.ERROR_LABEL, required = false) List<String> failures) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		List<ICourse> allCourses = courseDb.loadAllCourses();
		model.addAttribute("courses", allCourses);
		model.addAttribute(ErrorMessage.ERROR_LABEL, failures);
		return "admin/course";
	}

	@GetMapping("/admin/assigninstructor")
	public String assignInstructor(Model model, @RequestParam(name = ID) long courseId) {
		ICoursePersistence courseDd = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		courseDd.loadCourseById(courseId, c);
		model.addAttribute("course", c);
		ICourseUserRelationshipPersistence courseUserRelationshipDb = SystemConfig.instance()
				.getCourseUserRelationshipDb();
		List<IUser> allUsersNotCurrentlyInstructors = courseUserRelationshipDb
				.findAllUsersWithoutCourseRole(Role.INSTRUCTOR, courseId);
		model.addAttribute("users", allUsersNotCurrentlyInstructors);
		return "admin/assigninstructor";
	}

	@GetMapping("/admin/deletecourse")
	public ModelAndView deleteCourse(@RequestParam(name = ID) long courseId) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		c.setId(courseId);
		c.delete(courseDb);
		ModelAndView mav = new ModelAndView("redirect:/admin/course");
		return mav;
	}

	@RequestMapping(value = "/admin/createcourse", method = RequestMethod.POST)
	public ModelAndView createCourse(@RequestParam(name = TITLE) String title) {
		ICoursePersistence courseDb = SystemConfig.instance().getCourseDb();
		ICourse c = new Course();
		c.setTitle(title);
		c.createCourse(courseDb);
		ModelAndView mav = new ModelAndView("redirect:/admin/course/courseresults");
		mav.addObject(ErrorMessage.ERROR_LABEL, c.getFailureResults());
		return mav;
	}

	@RequestMapping(value = "/admin/assigninstructor", method = RequestMethod.POST)
	public ModelAndView assignInstructorToCourse(@RequestParam(name = INSTRUCTOR) List<Integer> instructor,
			@RequestParam(name = ID) long courseID) {
		ICourse c = new Course();
		c.setId(courseID);
		Iterator<Integer> iter = instructor.iterator();
		ICourseUserRelationshipPersistence courseUserRelationshipDb = SystemConfig.instance()
				.getCourseUserRelationshipDb();
		while (iter.hasNext()) {
			IUser u = new User();
			u.setId(iter.next().longValue());
			courseUserRelationshipDb.enrollUser(c, u, Role.INSTRUCTOR);
		}
		ModelAndView mav = new ModelAndView("redirect:/admin/course");
		return mav;
	}
}