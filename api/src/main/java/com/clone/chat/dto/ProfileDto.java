package com.clone.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String userId;
    private String filePath;
    private String nickName;
    private String statusMsg;

}
