package com.clone.chat.service;

import com.clone.chat.code.MD5Generator;
import com.clone.chat.dto.FileDto;
import com.clone.chat.repository.FileRepository;
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

    @Transactional
    public com.clone.chat.domain.File save(MultipartFile file){
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
        return fileRepository.save(fileDto.toEntity());
    }

    public com.clone.chat.domain.File findOne(Long id) {
        return fileRepository.getOne(id);
    }

}
