package io.github.jacorycyjin.smartlibrary.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import io.github.jacorycyjin.smartlibrary.backend.service.BookService;
import io.github.jacorycyjin.smartlibrary.backend.common.response.Result;
import io.github.jacorycyjin.smartlibrary.backend.bo.BookBO;
import io.github.jacorycyjin.smartlibrary.backend.vo.BookPublicVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.BookDetailVO;
import io.github.jacorycyjin.smartlibrary.backend.vo.CommentVO;
import io.github.jacorycyjin.smartlibrary.backend.form.BookSearchForm;
import io.github.jacorycyjin.smartlibrary.backend.common.dto.PageDTO;
import io.github.jacorycyjin.smartlibrary.backend.common.vo.PageVO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 图书控制器
 * 
 * @author Jacory
 * @date 2025/12/31
 */
@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;

    /**
     * 搜索图书（支持多条件查询和分页）
     * 
     * @param searchForm 查询条件（包含分页参数：pageNum, pageSize）
     * @return 分页图书列表
     */
    @PostMapping("/search")
    public Result<PageVO<BookPublicVO>> searchBook(@RequestBody(required = false) BookSearchForm searchForm) {
        if (searchForm == null) {
            searchForm = new BookSearchForm(); // 如果前端没传参数，就创建一个空的查询表单，代表查所有
        }
        
        // 调用 Service 层获取分页数据
        PageDTO<BookBO> pageDTO = bookService.searchBook(searchForm);
        
        // 转换 BO -> VO
        List<BookPublicVO> bookVOs = pageDTO.getList().stream()
                .map(BookPublicVO::fromBO)
                .toList();
        
        // 构建分页 VO
        PageVO<BookPublicVO> pageVO = new PageVO<>(pageDTO, bookVOs);
        
        return Result.success(pageVO);
    }

    /**
     * 获取图书详情（包含完整分类层级、所有标签，不包含评论）
     * 
     * @param bookId 图书业务ID
     * @return 图书详情
     */
    @GetMapping("/{bookId}")
    public Result<BookDetailVO> getBookDetail(@PathVariable String bookId) {
        BookDetailVO detailVO = bookService.getBookDetail(bookId);
        return Result.success(detailVO);
    }

    /**
     * 获取图书的评论列表（分页，包含用户信息）
     * 
     * @param bookId 图书业务ID
     * @param pageNum 页码（默认1）
     * @param pageSize 每页大小（默认10）
     * @return 分页评论列表
     */
    @GetMapping("/{bookId}/comments")
    public Result<PageVO<CommentVO>> getBookComments(
            @PathVariable String bookId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        PageDTO<CommentVO> pageDTO = bookService.getBookComments(bookId, pageNum, pageSize);
        PageVO<CommentVO> pageVO = new PageVO<>(pageDTO, pageDTO.getList());
        
        return Result.success(pageVO);
    }
}
