package us.mn.state.health.hrd.bodyart.mappers;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.QuestionRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.Question;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Service
public interface QuestionMapper {

    @Mappings({})
    Question toDatabase(QuestionRepresentation source);

    @InheritInverseConfiguration
    QuestionRepresentation fromDatabase(Question source);

}
