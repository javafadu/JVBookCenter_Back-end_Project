package com.library.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// All arg a gerek yok
public class ImageSavedResponse extends LResponse {

    private String imageId;
    public ImageSavedResponse(String imageId, String message, boolean success) {
        super(success,message);
        this.imageId=imageId;
    }

}