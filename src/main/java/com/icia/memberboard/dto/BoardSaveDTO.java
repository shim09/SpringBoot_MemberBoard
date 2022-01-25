package com.icia.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {

    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private MultipartFile boardImage;
    private String boardImageName;
    private LocalDateTime boardDate;
}
