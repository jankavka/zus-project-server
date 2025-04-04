package cz.kavka.entity.repository;

import cz.kavka.entity.SchoolManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolManagementRepository extends JpaRepository<SchoolManagementEntity, Long> {
}
