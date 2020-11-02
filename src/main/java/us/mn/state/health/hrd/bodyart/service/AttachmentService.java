package us.mn.state.health.hrd.bodyart.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import us.mn.state.health.hrd.bodyart.domain.AttachmentRepresentation;
import us.mn.state.health.hrd.bodyart.domain.AttachmentType;
import us.mn.state.health.hrd.bodyart.jpa.domain.Application;
import us.mn.state.health.hrd.bodyart.jpa.domain.Attachment;
import us.mn.state.health.hrd.bodyart.jpa.repository.AttachmentRepository;
import us.mn.state.health.hrd.bodyart.mappers.AttachmentMapper;

import javax.inject.Inject;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class AttachmentService {

    @Value("${attachments.bucket-name}")
    private String bucketName;

    private static final int EXP_TIME_IN_MILLIS = 3_600_000;

    @Inject
    AttachmentRepository attachmentRepository;

    @Inject
    AttachmentMapper attachmentMapper;

    public AttachmentRepresentation getById(String id) {
        Attachment attachment =  attachmentRepository.findById(id).orElse(null);
        return attachment != null ? attachmentMapper.fromDatabase(attachment) : null;
    }

    public AttachmentRepresentation save(MultipartFile file, String type, String othType,
                                         LocalDate courseDate, String trainingPresenter) throws Exception {
        UUID id = UUID.randomUUID();
        putFile(id.toString(), file);
        Attachment model = new Attachment();
        model.setAttachmentType(AttachmentType.getByCode(type));
        model.setFilename(file.getOriginalFilename());
        model.setOtherAttachmentType(othType);
        model.setCourseDate(courseDate);
        model.setTrainingPresenter(trainingPresenter);

        model.setId(id.toString());
        model.setUrl(getPresignedUrlGet(id.toString()).toString()); //TODO - should not store in DB.
        attachmentRepository.save(model);
        return attachmentMapper.fromDatabase(model);
    }

    public List<Attachment> updateApplicationAssign(List<AttachmentRepresentation> attachments, Application app) {
        List<Attachment> attachmentList = new ArrayList<>();
        for (AttachmentRepresentation a: attachments) {
            Attachment attachment = attachmentRepository.findById(a.getAttachmentId()).orElse(null);
            if (attachment != null) {
                attachment.setApplication(app);
                attachmentRepository.save(attachment);
                attachmentList.add(attachment);
            } else {
                System.out.println("Attachment not found for id " + a);
            }
        }
        return attachmentList;
    }

    public String update(AttachmentRepresentation a) {
        return null;
    }

    public InputStream getFile(String id) {
        Optional<Attachment> results = attachmentRepository.findById(id);
        if (results.isPresent()) {
            Attachment attachment = results.get();
            S3Object s3object = buildS3Client().getObject(this.bucketName, attachment.getId());
            S3ObjectInputStream inputStream = s3object.getObjectContent();
            return inputStream;
        } else {
            return null;
        }
    }

    private void putFile(String fileName, MultipartFile file) {
        try {
            buildS3Client().putObject(this.bucketName, fileName, file.getInputStream(),
                    createObjectMetadata(file));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private ObjectMetadata createObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (file.getContentType() != null) {
            objectMetadata.setContentType(file.getContentType());
        }
        if (file.getSize() != 0) {
            objectMetadata.setContentLength(file.getSize());
        }
        return objectMetadata;
    }

    private AmazonS3 buildS3Client() {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_2)
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();
    }

    private URL getPresignedUrlGet(String fileName) throws Exception {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(this.bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(createExpirationTime());

        return buildS3Client().generatePresignedUrl(generatePresignedUrlRequest);
    }

    private Date createExpirationTime() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + EXP_TIME_IN_MILLIS;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

}
