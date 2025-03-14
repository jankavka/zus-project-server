package cz.kavka.dto.mapper;

import cz.kavka.dto.ArticleDTO;
import cz.kavka.entity.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleDTO toDTO(ArticleEntity articleEntity);

    ArticleEntity toEntity (ArticleDTO articleDTO);
}
