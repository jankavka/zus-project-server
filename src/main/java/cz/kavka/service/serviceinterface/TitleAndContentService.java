package cz.kavka.service.serviceinterface;

import cz.kavka.dto.TitleAndContentDTO;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface TitleAndContentService {

    Map<String, TitleAndContentDTO> getContent() throws IOException;

    Optional<TitleAndContentDTO> getSection(String key) throws IOException;

    Map<String, TitleAndContentDTO> updateContent(String key, TitleAndContentDTO titleAndContentDTO) throws IOException;
}
