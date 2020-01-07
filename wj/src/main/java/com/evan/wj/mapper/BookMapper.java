package com.evan.wj.mapper;

import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookMapper extends JpaRepository<Book, Integer> {
    List<Book> findAllByCategory(Category category);
//    @Query(value = "select * from book b where b.title like ?1 or b.author like ?2", nativeQuery = true)
    List<Book> findAllByTitleLikeOrAuthorLike(String keyword1, String keyword2);
}
