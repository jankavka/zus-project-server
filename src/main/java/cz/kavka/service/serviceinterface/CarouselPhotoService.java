package cz.kavka.service.serviceinterface;

import cz.kavka.dto.CarouselPhotoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarouselPhotoService {

    /**
     * Uploads one or more photos and creates a CarouselPhotoEntity for each of them.
     * @param files photos to upload
     * @param names display name for each photo, matched to files by index; a missing or blank
     *              entry (or a shorter/absent array) falls back to that file's original filename
     * @return the created carousel photos
     * @throws IOException while a file can't be written to disk
     */
    List<CarouselPhotoDTO> uploadPhotos(MultipartFile[] files, String[] names) throws IOException;

    List<CarouselPhotoDTO> getAllPhotos();

    CarouselPhotoDTO editVisibility(Long id, boolean isHidden);

    CarouselPhotoDTO deletePhoto(Long id) throws IOException;
}
