package com.clone.chat.service;

import com.clone.chat.dto.FileDto;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.service.aws.S3Service;
import com.clone.chat.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final S3Service s3Service;

    @Transactional
    public com.clone.chat.domain.File save(MultipartFile file){
        System.out.println("FileService.save111");
        String originalFileName = "";
        String fileName = "";
        String filePath = "";
        Long fileSize = 0L;

        try {
            fileName = new MD5Generator(file.getInputStream()).toString();
            originalFileName = file.getOriginalFilename();
            fileSize = file.getSize();
            filePath = s3Service.upload(file,fileName);

        } catch(Exception e){
            e.getStackTrace();
        }
        FileDto fileDto = new FileDto().builder()
                .originalFileName(originalFileName)
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
        return fileRepository.save(fileDto.toEntity());
    }

    public com.clone.chat.domain.File findOne(Long id) {
        return fileRepository.getOne(id);
    }

}
