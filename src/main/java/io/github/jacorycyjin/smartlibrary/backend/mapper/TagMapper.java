package io.github.jacorycyjin.smartlibrary.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 标签 Mapper
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@Mapper
public interface TagMapper {

    /**
     * 根据图书ID列表批量查询标签信息
     * 
     * @param bookIds 图书业务ID列表
     * @return 标签信息列表（包含 bookId, tagId, tagName, tagType）
     */
    List<Map<String, Object>> selectTagsByBookIds(@Param("bookIds") List<String> bookIds);
}
