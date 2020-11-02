package us.mn.state.health.hrd.bodyart.mappers;

import org.mapstruct.*;
import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.domain.PaymentRepresentation;
import us.mn.state.health.hrd.bodyart.jpa.domain.Payment;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Service
public interface PaymentMapper {

    @Mappings({
            @Mapping(target="id", source="paymentId"),
            @Mapping(target="application.id", source="applicationId")
    })
    Payment toDatabase(PaymentRepresentation source);

    @InheritInverseConfiguration
    Payment fromDatabase(PaymentRepresentation source);

}
