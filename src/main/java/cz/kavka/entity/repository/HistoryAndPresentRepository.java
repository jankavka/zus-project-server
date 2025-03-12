package cz.kavka.entity.repository;

import cz.kavka.entity.HistoryAndPresentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryAndPresentRepository extends JpaRepository<HistoryAndPresentEntity, Long> {
}
