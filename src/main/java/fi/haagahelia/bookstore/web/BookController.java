package fi.haagahelia.bookstore.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BookController {
    @GetMapping("/index")
    public String index() {
        return "Welcome to the bookstore!";
    }

}
