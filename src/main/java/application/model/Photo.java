package application.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class Photo {

    @Id
    private ObjectId id;
    private String name;
    private String description;
    @Nullable
    private Binary imageFile;
}
