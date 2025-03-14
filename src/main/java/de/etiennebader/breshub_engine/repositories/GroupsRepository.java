package de.etiennebader.breshub_engine.repositories;

import de.etiennebader.breshub_engine.entities.Group;
import de.etiennebader.breshub_engine.entities.User;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Hidden
@Repository
public interface GroupsRepository extends JpaRepository<Group, Long> {

    @Query("SELECT group FROM Group group WHERE group.name = :name")
    Optional<Group> findByName(@Param("name") String name);

    @Query("SELECT group FROM Group group")
    List<Group> findAllGroups();

    @Query("SELECT group FROM Group group WHERE group.id = :id")
    Optional<Group> findByID(@Param("id") Long id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Group g WHERE g.name = :name")
    Boolean existsByUsername(@Param("name") String name);
}
