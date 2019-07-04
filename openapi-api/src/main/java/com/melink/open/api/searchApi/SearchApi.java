package com.melink.open.api.searchApi;

import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface SearchApi {

    @GetMapping("/only/search")
    OpenApiV2ListResponse<OpenEmoticion> onlySearch(
            @RequestParam("q") String q,
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes
    );

    @GetMapping("/gifs/not/textinfo/search")
    OpenApiV2ListResponse<OpenEmoticion> searchEmojiNotTextInfo(
            @RequestParam("q") String q,
            @RequestParam("size") Integer size
    );
}
