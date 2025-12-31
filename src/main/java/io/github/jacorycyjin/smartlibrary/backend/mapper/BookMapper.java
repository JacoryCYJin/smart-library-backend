package io.github.jacorycyjin.smartlibrary.backend.mapper;

import io.github.jacorycyjin.smartlibrary.backend.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 图书 Mapper（只负责 Book 表）
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@Mapper
public interface BookMapper {

    /**
     * 搜索图书（只返回图书基本信息）
     * 
     * @param params 查询参数
     * @return 图书列表
     */
    List<Book> searchBook(Map<String, Object> params);

    /**
     * 根据图书业务ID查询单本图书
     * 
     * @param bookId 图书业务ID
     * @return 图书信息
     */
    Book selectByBookId(@Param("bookId") String bookId);
}

