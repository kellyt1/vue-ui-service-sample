package us.mn.state.health.hrd.bodyart.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import us.mn.state.health.hrd.bodyart.domain.AttachmentRepresentation;
import us.mn.state.health.hrd.bodyart.domain.AttachmentType;
import us.mn.state.health.hrd.bodyart.service.AttachmentService;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/Attachments")
@Validated
public class AttachmentController {

    @Inject
    private AttachmentService attachmentService;

    @PostMapping(value = "/public", consumes = { "multipart/form-data" })
    public AttachmentRepresentation upload(@RequestParam MultipartFile file,
                                           @RequestParam String type,
                                           @RequestParam(required = false) String othType,
                                           @RequestParam(required = false) String courseDate,
                                           @RequestParam(required = false) String trainingPresenter) throws Exception {

        LocalDate ldCourseDate = null;
        if (StringUtils.isNotEmpty(courseDate)) {
            ldCourseDate = LocalDate.parse(courseDate, DateTimeFormatter.ISO_DATE);
        }
        AttachmentRepresentation attachment =
                attachmentService.save(file, type, othType, ldCourseDate, trainingPresenter);
        return attachment;
    }

    @GetMapping("/public/{id}")
    public InputStream download(@PathVariable String id) throws IOException {
        return attachmentService.getFile(id);
    }

    @GetMapping(path = "/public/types")
    public List<AttachmentType> getNonPoaAttachmentTypes() {
        return AttachmentType.nonPoaValues();
    }

    @GetMapping(path = "/public/poaTypes")
    public List<AttachmentType> getPoaAttachmentTypes() {
        return AttachmentType.poaValues();
    }
}
