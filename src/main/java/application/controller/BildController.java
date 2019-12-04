package application.controller;

import application.model.Photo;
import application.services.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping(path = "/picture")
public class BildController {

    @Autowired
    StorageService storageService;
    private final Log log = LogFactory.getLog(getClass());

    @GetMapping(path = "/test")
    public String Test(){
        return "Success";
    }

    @GetMapping(path = "/{id}")
    public Object getPhoto(@PathVariable ("id") String photoId){
        Optional<Photo> photoById = storageService.getPhotoById(photoId);
        int returnValue = HttpStatus.SC_NOT_FOUND;
        //TODO: HTTP Status in der Response auch auf 404 umbauen
        if(photoById.isPresent())
        {
            returnValue = HttpStatus.SC_OK;
        }
        return returnValue;
    }

    @PostMapping("/picture")
    public int uploadSinglePicture(){

        return HttpStatus.SC_CREATED;
    }

    @PostMapping("/pictures")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                                     RedirectAttributes redirectAttributes) throws IOException {
        log.info(String.format("upload-start /pictures/ (%s bytes)",file.getSize()));
        ObjectId store = storageService.store(file);

        return "redirect:/photos/" + store;
    }


}
