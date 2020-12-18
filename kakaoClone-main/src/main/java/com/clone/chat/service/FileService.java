package com.clone.chat.service;

import com.clone.chat.domain.File;
import com.clone.chat.dto.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    public FileDto save(MultipartFile file);
    public File findOne(Long id);
}
