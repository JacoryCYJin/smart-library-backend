package io.github.jacorycyjin.smartlibrary.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 图书分类关联 Mapper
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@Mapper
public interface BookCategoryRelMapper {

    /**
     * 根据图书ID列表批量查询分类关联信息
     * 
     * @param bookIds 图书业务ID列表
     * @return 关联信息列表（包含 bookId, categoryId）
     */
    List<Map<String, Object>> selectByBookIds(@Param("bookIds") List<String> bookIds);
}
