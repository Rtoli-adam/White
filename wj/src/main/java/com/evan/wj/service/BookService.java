package com.evan.wj.service;

import com.evan.wj.mapper.BookMapper;
import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    CategoryService categoryService;

    public List<Book> list() {
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return bookMapper.findAll();
    }

    public void addOrUpdate(Book book) {
        bookMapper.save(book);
    }

    public void deleteById(int id) {
        bookMapper.deleteById(id);
    }

    public List<Book> listByCategory(int cid) {
        Category category = categoryService.get(cid);
//        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return bookMapper.findAllByCategory(category);
    }

    public List<Book> search(String keywords){
        return bookMapper.findAllByTitleLikeOrAuthorLike('%'+keywords+'%','%'+keywords+'%');
    }
}
