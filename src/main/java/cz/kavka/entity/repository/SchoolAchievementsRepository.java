package cz.kavka.entity.repository;

import cz.kavka.entity.SchoolAchievementsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SchoolAchievementsRepository extends JpaRepository<SchoolAchievementsEntity, Long> {

    @Query(value = "SELECT * FROM school_achievements WHERE school_year = :yearId", nativeQuery = true)
    List<SchoolAchievementsEntity> getAllAchievementsByYear(@Param("yearId") Long yearId);
}
