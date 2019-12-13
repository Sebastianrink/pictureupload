package application.controller;

import application.model.Photo;
import application.services.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping(path = "/picture")
public class BildController {

    @Autowired
    StorageService storageService;
    private final Log log = LogFactory.getLog(getClass());

    @GetMapping(path = "/test")
    public String Test() {
        return "Success";
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Resource> getPhoto(@PathVariable("id") String photoId) {
        Optional<Photo> photoById = storageService.getPhotoById(photoId);
        //TODO: Beim Upload Dateityp speichern und mit an den Dateinamen hängen
        return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photoById.get().getName() + "\".jpg")
                    .body(new ByteArrayResource(photoById.get().getImageFile().getData()));
    }

    @PostMapping
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        log.info(String.format("upload-start /picture/ (%s bytes)", file.getSize()));
        ObjectId store = storageService.store(file);
        //Spring ServletUri Componentbuilder
        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(store).toUri();
        log.info(String.format("upload finished %s", location));
        return ResponseEntity.created(location).build();

    }
}
