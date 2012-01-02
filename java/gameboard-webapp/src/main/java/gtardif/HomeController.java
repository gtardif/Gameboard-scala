package gtardif;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	@RequestMapping("/home.html")
	public ModelAndView showHomePage() {
		return new ModelAndView("/home");
	}
}