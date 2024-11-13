package lk.anan.ri.dataviewer.service;

import lk.anan.ri.dataviewer.model.FileEntity;
import lk.anan.ri.dataviewer.repository.FileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FileEntityService {

    @Autowired
    private FileEntityRepository repository;

    @Transactional(readOnly = true)
    public List<FileEntity> getAllFiles() {
        return repository.findByDeletedFalseOrderByDateCreatedDesc();
    }

    @Transactional(readOnly = true)
    public List<FileEntity> getFilesByDateCreated(LocalDateTime dateCreated) {
        return repository.findByDeletedFalseAndDateCreatedOrderByDateCreatedDesc (dateCreated);
    }

    @Transactional
    public FileEntity createFile(FileEntity fileEntity) {
        return repository.save(fileEntity);
    }

    @Transactional
    public FileEntity updateFile(Long id, FileEntity fileDetails) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            FileEntity updatedFile = fileEntity.get();
            updatedFile.setPath(fileDetails.getPath());
            updatedFile.setDatatype(fileDetails.getDatatype());
            updatedFile.setDevelopers(fileDetails.getDevelopers());
            updatedFile.setLead(fileDetails.getLead());
            updatedFile.setConfirmedBy(fileDetails.getConfirmedBy());
            updatedFile.setApprovedBy(fileDetails.getApprovedBy());
            updatedFile.setConfirmed(fileDetails.isConfirmed());
            updatedFile.setApproved(fileDetails.isApproved());
            updatedFile.setDeleted(fileDetails.isDeleted());
            return repository.save(updatedFile);
        } else {
            throw new RuntimeException("File not found with id " + id);
        }
    }

    @Transactional
    public FileEntity confirmFile(Long id, String confirmedBy) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            FileEntity updatedFile = fileEntity.get();
            updatedFile.setConfirmedBy(confirmedBy);
            updatedFile.setConfirmed(true);
            return repository.save(updatedFile);
        } else {
            throw new RuntimeException("File not found with id " + id);
        }
    }

    @Transactional
    public FileEntity approveFile(Long id, String approvedBy) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            FileEntity updatedFile = fileEntity.get();
            updatedFile.setApprovedBy(approvedBy);
            updatedFile.setApproved(true);
            return repository.save(updatedFile);
        } else {
            throw new RuntimeException("File not found with id " + id);
        }
    }

    @Transactional
    public void deleteFile(Long id) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            FileEntity entity = fileEntity.get();
            entity.setDeleted(true);
            repository.save(entity);
        } else {
            throw new RuntimeException("File not found with id " + id);
        }
    }

    @Transactional
    public FileEntity getFileById(Long id) {
        Optional<FileEntity> fileEntity = repository.findById(id);
        if (fileEntity.isPresent()) {
            return fileEntity.get();
        } else {
            throw new RuntimeException("File not found with id " + id);
        }
    }
}