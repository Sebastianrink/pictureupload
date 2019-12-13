package application.services;

import application.model.Photo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    PhotoRepo photoRepo;
    //TODO: GRIDFS benutzen wenn Files > 16MB
    public ObjectId store(MultipartFile file) throws IOException {
        Photo photo = new Photo();
        photo.setName(LocalDateTime.now().toString());
        photo.setImageFile(
          new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.save(photo);

        return photo.getId();
    }

    public Optional<Photo> getPhotoById(String photoId) {
        return photoRepo.findById(photoId);
    }

    public ObjectId store(Photo photo) {
        photo = photoRepo.save(photo);
        return photo.getId();
    }
}
