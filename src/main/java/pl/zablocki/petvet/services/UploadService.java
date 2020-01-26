package pl.zablocki.petvet.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class UploadService {

    private final String FOLDER;

    public UploadService() {
        this.FOLDER = "C:\\Users\\Hubii\\Desktop\\Platformy Oprogramowania\\petvet\\images\\";
    }

    public String saveImage(MultipartFile imageFile) {
        byte[] bytes;
        try {
            bytes = imageFile.getBytes();
            String fileName = imageFile.hashCode() + "." + StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
            Path path = Paths.get(FOLDER + fileName);
            Files.write(path, bytes, StandardOpenOption.CREATE);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
