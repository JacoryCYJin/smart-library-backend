package io.github.jacorycyjin.smartlibrary.backend.service;

import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;
import io.github.jacorycyjin.smartlibrary.backend.form.BookSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;

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
}
    