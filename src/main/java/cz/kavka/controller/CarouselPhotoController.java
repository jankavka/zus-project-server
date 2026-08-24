package cz.kavka.controller;

import cz.kavka.dto.CarouselPhotoDTO;
import cz.kavka.service.serviceinterface.CarouselPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/carousel-photos")
public class CarouselPhotoController {

    private final CarouselPhotoService carouselPhotoService;

    @Autowired
    public CarouselPhotoController(CarouselPhotoService carouselPhotoService) {
        this.carouselPhotoService = carouselPhotoService;
    }

    @GetMapping
    public List<CarouselPhotoDTO> getAllPhotos() {
        return carouselPhotoService.getAllPhotos();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public List<CarouselPhotoDTO> uploadPhotos(@RequestParam("files") MultipartFile[] files,
                                                @RequestParam(value = "names", required = false) String[] names) throws IOException {
        return carouselPhotoService.uploadPhotos(files, names);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}/visibility")
    public CarouselPhotoDTO editVisibility(@PathVariable Long id, @RequestParam boolean isHidden) {
        return carouselPhotoService.editVisibility(id, isHidden);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public CarouselPhotoDTO deletePhoto(@PathVariable Long id) throws IOException {
        return carouselPhotoService.deletePhoto(id);
    }
}
