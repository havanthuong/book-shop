package vn.demo.starter.consumer;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.demo.starter.config.ApplicationProperties;

import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements ICloudinaryService {

    private String cloudName;

    private String apiKey;

    private String apiSecret;

    private String folderName;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(ApplicationProperties properties) {
        this.cloudName = properties.getCloudinary().cloudName();

        this.apiKey = properties.getCloudinary().apiKey();

        this.apiSecret = properties.getCloudinary().apiSecret();

        this.folderName = properties.getCloudinary().folderName();
        this.cloudinary =  new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public String upload(MultipartFile file) {
        System.out.println(cloudName + " 1 " + apiKey + " 2 " + apiSecret + " 3 " + folderName);
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("folder", folderName);

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), param);
            String publicId = uploadResult.get("public_id").toString();

            String format = "jpg";
            Transformation transformation = new Transformation()
                    .width(200)
                    .height(200)
                    .crop("fit");

            String cloudUrl = cloudinary.url().secure(true).format(format)
                    .transformation(transformation)
                    .publicId(publicId)
                    .cloudName(cloudName)
                    .generate();

            logger.info("The user successfully uploaded the file: " + publicId);
            return cloudUrl;
        } catch (Exception ex) {
            logger.error("The user failed to load to Cloudinary the image file: " + file.getName());
            logger.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public void delete(String publicId) {
        try {
            Map<String, String> param = new HashMap<String, String>();
            param.put("folder", folderName);
            param.put("invalidate", "true");
            cloudinary.uploader().destroy(publicId, param);
        } catch (Exception ex) {
            logger.error("The user failed to remove to Cloudinary the image file: " + publicId);
            logger.error(ex.getMessage());
        }
    }
}
