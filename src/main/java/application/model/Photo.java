package application.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Getter
@Setter
public class Photo {

    @Id
    private ObjectId id;
    private String name;
    private String description;
    private Binary imageFile;
}
