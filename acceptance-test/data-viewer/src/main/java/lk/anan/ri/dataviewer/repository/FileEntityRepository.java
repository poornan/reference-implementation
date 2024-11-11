package lk.anan.ri.dataviewer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.anan.ri.dataviewer.model.FileEntity;

public interface FileEntityRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByDeletedFalse();
}