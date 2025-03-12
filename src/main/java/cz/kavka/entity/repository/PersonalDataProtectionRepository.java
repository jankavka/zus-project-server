package cz.kavka.entity.repository;

import cz.kavka.entity.PersonalDataProtectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDataProtectionRepository extends JpaRepository<PersonalDataProtectionEntity, Long> {
}
