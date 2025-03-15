package cz.kavka.entity.repository;

import cz.kavka.entity.BasicDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasicDataRepository extends JpaRepository<BasicDataEntity, Long> {
}
