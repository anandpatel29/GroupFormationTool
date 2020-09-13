package CSCI5308.GroupFormationTool.QuestionManager;

import CSCI5308.GroupFormationTool.SystemConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShowQuestionController {

	private static final String QID = "qid";

	@GetMapping("/questionmanager/showquestion")
	public String question(Model model) {
		IQuestionDb questionDB = SystemConfig.instance().getQuestionDb();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String bannerID = authentication.getPrincipal().toString();
		List<IQuestion> allQuestions = questionDB.loadQuestionByID(bannerID);
		model.addAttribute("questions", allQuestions);
		return "questionmanager/showquestion";
	}

	@GetMapping("/questionmanager/deletequestion")
	public ModelAndView deleteQuestion(@RequestParam(name = QID) int questionID) {
		IQuestionDb questionDB = SystemConfig.instance().getQuestionDb();
		IQuestion q = new Question();
		q.setQuestionId(questionID);
		q.delete(questionDB);
		ModelAndView mav = new ModelAndView("redirect:/questionmanager/showquestion");
		return mav;
	}

}
