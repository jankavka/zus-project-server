package cz.kavka.entity.repository;

import cz.kavka.entity.RequiredInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequiredInformationRepository extends JpaRepository<RequiredInformationEntity, Long> {
}
