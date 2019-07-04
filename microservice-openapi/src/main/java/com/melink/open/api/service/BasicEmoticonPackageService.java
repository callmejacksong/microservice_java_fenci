package com.melink.open.api.service;

import com.github.pagehelper.Page;
import com.melink.open.api.model.BasicEmoticonPackage;

public interface BasicEmoticonPackageService {
    Page<BasicEmoticonPackage> findListByPagination(int p, int size);

    BasicEmoticonPackage findbyId(String id);
}
