package com.evan.wj.controller;

import com.evan.wj.pojo.Book;
import com.evan.wj.pojo.Search;
import com.evan.wj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Random;

@RestController
public class LibraryController {

    @Autowired
    BookService bookService;

    @CrossOrigin
    @GetMapping("api/books")
    public List<Book> list(){
        return bookService.list();
    }

    @CrossOrigin
    @PostMapping("api/books")
    public Book addOrUpdate(@RequestBody Book book){
        bookService.addOrUpdate(book);
        return book;
    }

    @CrossOrigin
    @PostMapping("api/delete")
    public void delete(@RequestBody Book book){
        bookService.deleteById(book.getId());
    }

    @CrossOrigin
    @GetMapping("api/categories/{cid}/books")
    public List<Book> listByCategory(@PathVariable("cid")int cid){
        if (cid != 0){
            return bookService.listByCategory(cid);
        } else {
            return list();
        }
    }

    @CrossOrigin
    @PostMapping("api/search")
    public List<Book> searchResult(@RequestBody Search search){
        String keywords = search.getKeywords();
        if ("".equals(search.getKeywords())){
            return bookService.list();
        }else{
            System.out.println(bookService.search(search.getKeywords()));
            return bookService.search(search.getKeywords());
        }
    }

    @CrossOrigin
    @PostMapping("api/covers")
    public String coversUpload(MultipartFile file){
        String folder = "D:/White/img";
        File imageFolder = new File(folder);
        File f = new File(imageFolder,getRandom(6)+file.getOriginalFilename().substring(file.getOriginalFilename().length()-4));
        if (!f.getParentFile().exists())
            f.getParentFile().mkdir();
        try{
            file.transferTo(f);
            System.out.println(f.getName());
            String imgUrl = "http://localhost:8443/api/file/"+f.getName();
            return imgUrl;
        }catch (Exception e){
            return "";
        }
    }



    //生成指定长度随机字符串的代码
    public String getRandom(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i<length; i++){
            int num = random.nextInt(base.length());
            sb.append(base.charAt(num));
        }
        return sb.toString();
    }
}
