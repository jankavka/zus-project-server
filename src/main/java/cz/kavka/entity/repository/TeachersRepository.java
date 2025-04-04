package cz.kavka.entity.repository;

import cz.kavka.entity.TeachersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepository extends JpaRepository<TeachersEntity, Long> {
}
