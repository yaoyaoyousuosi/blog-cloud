package org.cloud.blog.oss.service.impl;

import org.cloud.blog.oss.service.OssService;
import org.cloud.blog.oss.util.OSSClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadAvatar(MultipartFile file) {
        return OSSClientUtil.Upload(file, "avatar");
    }

    @Override
    public String uploadArticle(MultipartFile file) {
        return OSSClientUtil.Upload(file, "article");
    }

    @Override
    public String uploadBlogSources(MultipartFile file, String type) {
        return OSSClientUtil.Upload(file, type);
    }
}
