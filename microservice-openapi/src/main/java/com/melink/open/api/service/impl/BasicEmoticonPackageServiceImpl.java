package com.melink.open.api.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.melink.open.api.mapper.melink.BasicEmoticonPackageMapper;
import com.melink.open.api.model.BasicEmoticonPackage;
import com.melink.open.api.service.BasicEmoticonPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicEmoticonPackageServiceImpl implements BasicEmoticonPackageService {

    @Autowired
    private BasicEmoticonPackageMapper basicEmoticonPackageMapper;

    @Override
    public Page<BasicEmoticonPackage> findListByPagination(int p, int size) {
        PageHelper.startPage(p, size);
        Page<BasicEmoticonPackage> listByPagination = basicEmoticonPackageMapper.findListByPagination(p, size);
        return listByPagination;
    }

    @Override
    public BasicEmoticonPackage findbyId(String id) {
        return basicEmoticonPackageMapper.findbyId(id);
    }
}
