package us.mn.state.health.hrd.bodyart.mappers;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.AttachmentRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.Attachment;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Service
public interface AttachmentMapper {

    @Mappings({
            @Mapping(target="id", source="attachmentId"),
            @Mapping(target="application.id", source="applicatonId"),
            @Mapping(target="attachmentType", source="type")
    })
    Attachment toDatabase(AttachmentRepresentation source);

    @InheritInverseConfiguration
    AttachmentRepresentation fromDatabase(Attachment source);

}
