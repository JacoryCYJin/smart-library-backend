package io.github.jacorycyjin.smartlibrary.backend.service;

import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;
import io.github.jacorycyjin.smartlibrary.backend.form.BookSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;
import io.github.jacorycyjin.smartlibrary.backend.vo.BookDetailVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CommentVO;

/**
 * 图书服务
 * 
 * @author Jacory
 * @date 2025/12/31
 */
public interface BookService {
    /**
     * 搜索图书（包含分类和标签信息，支持分页）
     * 
     * @param searchForm 搜索条件（包含分页参数）
     * @return 分页图书列表（包含关联信息）
     */
    PageDTO<BookBO> searchBook(BookSearchForm searchForm);

    /**
     * 获取图书详情（包含完整分类层级、所有标签）
     * 
     * @param bookId 图书业务ID
     * @return 图书详情 VO
     */
    BookDetailVO getBookDetail(String bookId);

    /**
     * 获取图书的评论列表（分页）
     * 
     * @param bookId 图书业务ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页评论列表
     */
    PageDTO<CommentVO> getBookComments(String bookId, Integer pageNum, Integer pageSize);
}
    