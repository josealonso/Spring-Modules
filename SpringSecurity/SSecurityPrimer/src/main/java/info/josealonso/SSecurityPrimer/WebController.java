package info.josealonso.SSecurityPrimer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/")
    public String publicPage() {
        return "This is a public page";
    }

    @GetMapping("/private")
    public String privatePage() {
        return "This is a PRIVATE page";
    }

}
