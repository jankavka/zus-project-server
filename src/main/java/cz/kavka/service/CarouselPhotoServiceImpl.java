package cz.kavka.service;

import cz.kavka.dto.CarouselPhotoDTO;
import cz.kavka.dto.mapper.CarouselPhotoMapper;
import cz.kavka.entity.CarouselPhotoEntity;
import cz.kavka.entity.repository.CarouselPhotoRepository;
import cz.kavka.service.serviceinterface.CarouselPhotoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CarouselPhotoServiceImpl implements CarouselPhotoService {

    private final String uploadDir;

    private final CarouselPhotoRepository carouselPhotoRepository;

    private final CarouselPhotoMapper carouselPhotoMapper;

    private final Object writeLock = new Object();

    @Autowired
    public CarouselPhotoServiceImpl(@Value("${carousel.upload-dir}") String uploadDir,
                                     CarouselPhotoRepository carouselPhotoRepository,
                                     CarouselPhotoMapper carouselPhotoMapper) {
        this.uploadDir = uploadDir;
        this.carouselPhotoRepository = carouselPhotoRepository;
        this.carouselPhotoMapper = carouselPhotoMapper;
    }

    @Transactional
    @Override
    public List<CarouselPhotoDTO> uploadPhotos(MultipartFile[] files, String[] names) throws IOException {
        List<CarouselPhotoDTO> saved = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];

            if (file.isEmpty()) {
                throw new IllegalArgumentException("File " + file.getOriginalFilename() + " is empty");
            }
            if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed");
            }

            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            synchronized (writeLock) {
                Files.createDirectories(uploadPath);
                try (InputStream stream = file.getInputStream()) {
                    Files.copy(stream, uploadPath.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            String name = names != null && i < names.length && names[i] != null && !names[i].isBlank()
                    ? names[i]
                    : stripExtension(file.getOriginalFilename());

            CarouselPhotoEntity entity = new CarouselPhotoEntity();
            entity.setName(name);
            entity.setPhotoUrl("/carousel-photos/" + uniqueFileName);
            entity.setHidden(false);

            saved.add(carouselPhotoMapper.toDTO(carouselPhotoRepository.save(entity)));
        }

        return saved;
    }

    private static String stripExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex > 0 ? fileName.substring(0, dotIndex) : fileName;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CarouselPhotoDTO> getAllPhotos() {
        return carouselPhotoRepository.findAll().stream().map(carouselPhotoMapper::toDTO).toList();
    }

    @Transactional
    @Override
    public CarouselPhotoDTO editVisibility(Long id, boolean isHidden) {
        CarouselPhotoEntity entity = carouselPhotoRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        entity.setHidden(isHidden);
        return carouselPhotoMapper.toDTO(carouselPhotoRepository.save(entity));
    }

    @Transactional
    @Override
    public CarouselPhotoDTO deletePhoto(Long id) throws IOException {
        CarouselPhotoEntity entity = carouselPhotoRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        String fileName = entity.getPhotoUrl().substring(entity.getPhotoUrl().lastIndexOf('/') + 1);
        Path filePath = Paths.get(uploadDir, fileName);

        synchronized (writeLock) {
            Files.deleteIfExists(filePath);
        }

        carouselPhotoRepository.delete(entity);
        return carouselPhotoMapper.toDTO(entity);
    }
}
