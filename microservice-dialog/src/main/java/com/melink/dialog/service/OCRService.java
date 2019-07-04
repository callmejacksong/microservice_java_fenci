package com.melink.dialog.service;

import org.springframework.web.multipart.MultipartFile;

public interface OCRService {
    String getTextByImage(MultipartFile image);

    String getTextByGif(MultipartFile gif);
}
