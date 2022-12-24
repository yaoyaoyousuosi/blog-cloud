package org.cloud.blog.admin.mapper;

import org.cloud.blog.admin.domain.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whj
 * @since 2022-12-22
 */
public interface TagMapper extends BaseMapper<Tag> {

    void deleteTagIdFormTagArticle(Integer id);
}
