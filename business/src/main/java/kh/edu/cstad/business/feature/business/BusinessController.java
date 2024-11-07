package kh.edu.cstad.business.feature.business;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/categories")
public class BusinessController {

    @GetMapping
    Map<String, String> findAllCategories() {
        return Map.of(
                "Helloooo","sarandy"
        );
    }

}
