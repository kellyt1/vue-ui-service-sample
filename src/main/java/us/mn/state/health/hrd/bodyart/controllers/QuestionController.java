package us.mn.state.health.hrd.bodyart.controllers;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import us.mn.state.health.hrd.bodyart.domain.QuestionRepresentation;
import us.mn.state.health.hrd.bodyart.service.QuestionService;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping(path = "/Questions", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class QuestionController {

    @Inject
    QuestionService questionService;

    @GetMapping(path = "/public")
    public List<QuestionRepresentation> getAllQuestions() {
        return questionService.findAll();
    }
}
