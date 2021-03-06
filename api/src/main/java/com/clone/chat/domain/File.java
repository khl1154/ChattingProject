package com.clone.chat.domain;

import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends ModelBase implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String fileName;

    @JsonView({JsonViewApi.class})
    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize;

    @Builder
    public File(Long id, String originalFileName, String fileName, String filePath, Long fileSize) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
