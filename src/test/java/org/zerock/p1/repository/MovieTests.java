package org.zerock.p1.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.entity.MovieImage;

import java.util.Arrays;

@SpringBootTest
public class MovieTests {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieImageRepository movieImageRepository;

    @Test
    public void testInsertMovies() {

        for (int i = 0; i < 100 ; i++) {

            Movie movie = Movie.builder().title("Movie---"+i).build();

            Movie insertedMovie = movieRepository.save(movie);


            for (int j = 0; j < 2; j++) {

                MovieImage movieImage = MovieImage.builder()
                        .movie(insertedMovie)
                        .fname(i+"---"+j+"file.jpg")
                        .build();

                movieImageRepository.save(movieImage);
            }
        }
    }

    @Test
    public void testSearch(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());


        Page<Object[]> result = movieRepository.getList1("3", pageable);


        result.get().forEach(arr -> {
            System.out.println(Arrays.toString(arr));
        });

    }

}
