package us.mn.state.health.hrd.bodyart.mappers;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.EmployerRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.Employer;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Service
public interface EmployerMapper {

    @Mappings({
            @Mapping(target="id", source="employerId"),
            @Mapping(target="application.id", source="applicatonId")
    })
    Employer toDatabase(EmployerRepresentation source);

    @InheritInverseConfiguration
    EmployerRepresentation fromDatabase(Employer source);

}
