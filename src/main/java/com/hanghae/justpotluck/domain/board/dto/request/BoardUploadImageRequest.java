package com.hanghae.justpotluck.domain.board.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardUploadImageRequest {
    private Long boardId;
    private List<MultipartFile> images = new ArrayList<>();
}
