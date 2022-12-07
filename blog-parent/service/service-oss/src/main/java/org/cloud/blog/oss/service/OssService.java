package org.cloud.blog.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadAvatar(MultipartFile file);

    String uploadArticle(MultipartFile file);
}
