package com.library.controller;

import com.library.domain.ImageFile;
import com.library.dto.ImageFileDTO;
import com.library.dto.response.ImageSavedResponse;
import com.library.dto.response.LResponse;
import com.library.dto.response.ResponseMessage;
import com.library.service.ImageFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Rest api lerimi yerlestirecegim
@RequestMapping("/files") // base path belirledik
@AllArgsConstructor
public class ImageFileController {

    private ImageFileService imageFileService;
    // ImageFileService injection, bunu yapinca yukariya @AllArgsConstructor da eklememiz gerekiyor

    // image i upload eden bir method yazalim
    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ImageSavedResponse> uploadFile(@RequestParam("file") MultipartFile file ){

        // MultipartFile : bize body degil, multipart yapi gelecek
        // icinde image in ismi, data nin oldugu, byte olarak yapi


        String imageId= imageFileService.saveImage(file);

        ImageSavedResponse response=new ImageSavedResponse();

        response.setImageId(imageId);
        response.setMessage(ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return ResponseEntity.ok(response);

    }

    // image leri getirebilmek icin arac bilgileri gelirken image bilgileri de gelmesi gerekiyor.
    @GetMapping("/download/{id}")
    public ResponseEntity<byte []> getImageFile(@PathVariable String id){
        ImageFile imageFile= imageFileService.getImageById(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+
                imageFile.getName()).body(imageFile.getData());

        // download eden taraf filename olarak download etsin, header bilgisinden okudu
        // CONTENT_DISPOSITIO ile download edebiliyoruz ya da byte kodlarını görebiliyoruz.
    }

    // image display
    @GetMapping("/display/{id}")
    public ResponseEntity<byte []> displayFile(@PathVariable String id){
        ImageFile imageFile= imageFileService.getImageById(id);

        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageFile.getData(),header,HttpStatus.OK);
    }

    // butun image dosyalarini almak icin method
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") // admin tarafindan getirilebilsin
    public ResponseEntity<List<ImageFileDTO>> getAllImages() {
        List<ImageFileDTO> imageList = imageFileService.getAllImages();

        // return ResponseEntity.ok(imageList);

        return ResponseEntity.status(HttpStatus.OK).body(imageList);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // admin tarafindan getirilebilsin
    public ResponseEntity<Map<String,String>> deleteImageWithId(@PathVariable String id) {
        Map<String,String> deleteImageMap = new HashMap<>();

        String deletedImage = imageFileService.deleteImage(id);

        deleteImageMap.put("message", "Image Successfully Deleted");
        deleteImageMap.put("status", "true");

        return new ResponseEntity<>(deleteImageMap, HttpStatus.OK);


    }








}