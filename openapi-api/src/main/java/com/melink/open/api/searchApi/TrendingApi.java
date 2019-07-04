package com.melink.open.api.searchApi;

import com.melink.sop.api.models.OpenApiV2ListResponse;
import com.melink.sop.api.models.open.modelinfos.OpenEmoticion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface TrendingApi {
    @RequestMapping("/not/textinfo/trending")
    OpenApiV2ListResponse<OpenEmoticion> notTextInfoTrending(
            @RequestParam(value = "ssl_res", required = false, defaultValue = "false") final boolean sslRes,
            @RequestParam(value = "p", required = false, defaultValue = "1") Integer p,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size
    );
}
