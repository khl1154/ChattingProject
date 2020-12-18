package com.clone.chat.dto;

import com.clone.chat.domain.ChatRoom;
import com.clone.chat.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDto {

    private Long id;
    private String originalFileName;
    private String fileName;
    private String filePath;
    private Long fileSize;

    public File toEntity() {
        return File.builder()
                .id(id)
                .originalFileName(originalFileName)
                .fileName(fileName)
                .filePath(filePath)
                .fileSize(fileSize)
                .build();
    }

    @Builder
    public FileDto(Long id, String originalFileName, String fileName, String filePath, Long fileSize) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
