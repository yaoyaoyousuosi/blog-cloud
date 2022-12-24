package org.cloud.blog.admin.service;

import org.cloud.blog.admin.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.cloud.blog.admin.util.PageUtil;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
public interface TagService extends IService<Tag> {

    Map<String, Object> tagList(PageUtil pageUtil);

    Boolean deleteTagById(Integer id);

    Boolean add(Tag tag);

    Boolean updateTag(Tag tag);
}
