package test.main.testex.controller;


import test.main.testex.entity.News;
import test.main.testex.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.main.testex.repositories.NewsRepository;
import test.main.testex.repositories.UserRepository;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/controller1")
public class StandartUserController {

    @Autowired
    NewsRepository newsRepository;

    @GetMapping(value = "/getNews")
    public ResponseEntity getNews(){
        System.out.println("im here!!!!!!!!!!!!!!!!!!");
        List<News> allNews = newsRepository.findAllId();
        HashMap<Object, Object> usersMap = new HashMap<>();
        for (News news: allNews) {
            usersMap.put(news.getTextNews(), news.getImg());
        }
        return ResponseEntity.ok(usersMap);
    }

    @GetMapping(value = "/getText")
    public void getText(){
        System.out.println("hello");
    }
}
