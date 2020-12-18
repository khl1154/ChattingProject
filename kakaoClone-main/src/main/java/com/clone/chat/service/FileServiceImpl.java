package com.clone.chat.service;

import com.clone.chat.dto.FileDto;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService{

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileDto save(MultipartFile file){
        String originalFileName = "";
        String fileName = "";
        String filePath = "";
        Long fileSize = 0L;

        try {
            if (file.isEmpty()) {
                fileName = originalFileName = "기본이미지.jpeg";
            } else {
                fileName = new MD5Generator(file.getInputStream()).toString();
                originalFileName = file.getOriginalFilename();
            }
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) new File(savePath).mkdir();

             fileSize = file.getSize();
            filePath = savePath + "\\" + fileName;
            file.transferTo(new File(filePath));

        } catch(Exception e){
            e.getStackTrace();
        }

        FileDto fileDto = new FileDto().builder()
                .originalFileName(originalFileName)
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
        Long fileId = fileRepository.save(fileDto.toEntity()).getId();
        fileDto.setId(fileId);

        return fileDto;
    }

    @Override
    public com.clone.chat.domain.File findOne(Long id) {
        return fileRepository.getOne(id);
    }

}
