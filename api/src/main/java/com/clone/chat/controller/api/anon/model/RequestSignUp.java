package com.clone.chat.controller.api.anon.model;

import com.clone.chat.model.ModelBase;
import lombok.*;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class RequestSignUp extends ModelBase {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    @NotEmpty
    private String nickName;
    @NotEmpty
    private String phone;
    private String statusMsg;
    MultipartFile file;

    public boolean isFile() {
        if (null == this.file){
            return false;
        } else {
            return true;
        }
    }

    @Builder
    public RequestSignUp(@NotEmpty String id, @NotEmpty String password, @NotEmpty String nickName, @NotEmpty String phone, String statusMsg, MultipartFile file) {
        this.id = id;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
        this.statusMsg = statusMsg;
        this.file = file;
    }
}
