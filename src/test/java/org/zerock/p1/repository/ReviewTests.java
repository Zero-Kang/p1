package org.zerock.p1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.entity.Review;

@SpringBootTest
public class ReviewTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testInsert() {

        for (long i = 90; i <= 100 ; i++) {

            Movie movie = Movie.builder().mno(i).build();

            for (int j = 0; j < 5; j++) {

                Review review = Review.builder()
                        .movie(movie)
                        .reviewText(i+j+"review...")
                        .score(j)
                        .build();

                reviewRepository.save(review);
            }

        }

    }
}
