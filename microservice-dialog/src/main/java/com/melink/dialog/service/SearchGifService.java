package com.melink.dialog.service;

import com.melink.sop.api.models.dialog.mathingGIF.MathingGifsInfo;

import java.util.List;

public interface SearchGifService {
    List<MathingGifsInfo> searchGifs();

    List<MathingGifsInfo> searchGifsByText(String q);
}
