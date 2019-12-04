package application.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Photo {
    @Id
    private ObjectId id;
    private String name;
    private String description;
    private Binary imageFile;
}
