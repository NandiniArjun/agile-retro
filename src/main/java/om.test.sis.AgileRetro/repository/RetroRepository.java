package com.test.sis.AgileRetro.repository;

import com.test.sis.AgileRetro.model.Retro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface RetroRepository extends JpaRepository<Retro, String> {
    @Transactional
    @Modifying
    @Query("update Retro set feedback = :item where name = :name AND VALUE(KEY(:feedback)) = :iName")
    void updateRetroFeedback(@Param("name") String name, @Param("item") String iName, Map<String, Map<String, String>> feedback, Map<String, String> item);
 }
