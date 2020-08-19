package antiesys.antiepidemic.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping(value = "/404")
    public String error_404() {
        return "error/error404";
    }
    /**
     * 500页面
     */
    @RequestMapping(value = "/500")
    public String error_500() {
        return "error/error500";
    }

}
