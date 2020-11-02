package us.mn.state.health.hrd.bodyart.service;

import org.springframework.stereotype.Service;
import us.mn.state.health.hrd.bodyart.jpa.domain.ApplicationProperty;
import us.mn.state.health.hrd.bodyart.jpa.repository.ApplicationPropertyRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationPropertyService {

    @Inject
    private ApplicationPropertyRepository applicationPropertyRepository;

    private int STARTING_TRACKER_ID = 5000;
    private String NEXT_TRACKER_ID = "next.tracker.id";
    private String PREFIX_TRACKER_ID = "H";

    @Transactional
    public String getNextTrackingId() {
        Optional<ApplicationProperty> results =
                applicationPropertyRepository.findById(NEXT_TRACKER_ID);
        if (results.isPresent()) {
            ApplicationProperty property = results.get();
            Integer currentValue = Integer.parseInt(property.getValue());
            currentValue++;
            String nextTrackingId = PREFIX_TRACKER_ID + currentValue;
            property.setValue(String.valueOf(currentValue));
            applicationPropertyRepository.save(property);
            return nextTrackingId;
        } else {
            ApplicationProperty property = new ApplicationProperty();
            property.setKey(NEXT_TRACKER_ID);
            property.setValue(String.valueOf(STARTING_TRACKER_ID));
            applicationPropertyRepository.save(property);
            return PREFIX_TRACKER_ID + STARTING_TRACKER_ID;
        }
    }

    public String getProperty(String key) {
        Optional<ApplicationProperty> oApplicationProperty = applicationPropertyRepository.findById(key);
        if (oApplicationProperty.isPresent()) {
            return oApplicationProperty.get().getValue();
        } else {
            return null;
        }
    }

    @Transactional
    public ApplicationProperty save(ApplicationProperty payload) {
        Optional<ApplicationProperty> results = applicationPropertyRepository.findById(payload.getKey());
        if (results.isPresent()) {
            ApplicationProperty property = results.get();
            property.setValue((payload.getValue()));
            applicationPropertyRepository.save(property);
        } else {
            applicationPropertyRepository.save(payload);
        }
        return payload;
    }

    public Map<String, String> findByIds(List<String> keys) {
        Map<String, String> results = new HashMap<>();

        for (String key : keys) {
            Optional<ApplicationProperty> oProperty = applicationPropertyRepository.findById(key);
            if (oProperty.isPresent()) {
                results.put(key, oProperty.get().getValue());
            } else {
                results.put(key, "");
            }
        }

        return results;
    }

    @Transactional
    public void save(Map<String, String> payload) {
        for (Map.Entry<String, String> item : payload.entrySet()) {
            Optional<ApplicationProperty> oApplicationProperty =
                    applicationPropertyRepository.findById(item.getKey());
            if (oApplicationProperty.isPresent()) {
                ApplicationProperty property = oApplicationProperty.get();
                property.setValue((item.getValue()));
                applicationPropertyRepository.save(property);
            } else {
                ApplicationProperty property = new ApplicationProperty();
                property.setKey(item.getKey());
                property.setValue(item.getValue());
                applicationPropertyRepository.save(property);
            }
        }
    }
}
