package test.main.testex.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "news")
@Component
@Getter
@Setter
@NoArgsConstructor
public class News {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "text_news")
    String textNews;

    @Column(name = "picture_news")
    String img;

    public News(String textNews, String img) {
        this.textNews = textNews;
        this.img = img;
    }
}
