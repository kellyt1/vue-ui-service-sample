package us.mn.state.health.hrd.bodyart.service;

import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.QuestionRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.Question;
import us.mn.state.health.hrd.bodyart.jpa.repository.QuestionRepository;
import us.mn.state.health.hrd.bodyart.mappers.QuestionMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Inject
    QuestionRepository questionRepository;

    @Inject
    QuestionMapper questionMapper;

    public List<QuestionRepresentation> findAll() {
        List<Question> questions = (List<Question>) questionRepository.findAll();
        if (questions.size() > 0 ) {
            return questions.stream().map(q -> questionMapper.fromDatabase(q)).collect(Collectors.toList());
        } else {
            return null;
        }
    }

}
