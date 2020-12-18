package com.clone.chat.repository;


import com.clone.chat.domain.File;
import com.clone.chat.dto.FileDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
