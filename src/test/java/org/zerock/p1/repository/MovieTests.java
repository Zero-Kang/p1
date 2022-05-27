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

import javax.transaction.Transactional;
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


        Page<Object[]> result = movieRepository.getList1(null, pageable);


        result.get().forEach(arr -> {
            System.out.println(Arrays.toString(arr));
        });

    }


    ///-----------------------------------------------bidirectional

    @Test
    public void testInsertMoviesBidirectional () {

        for (int i = 0; i < 100 ; i++) {

            Movie movie = Movie.builder().title("Movie---"+i).build();

            for (int j = 0; j < 2; j++) {

                MovieImage movieImage = MovieImage.builder()
                        .fname(i+"---"+j+"file.jpg")
                        .build();

                movie.addImage(movieImage);
            }

            movieRepository.save(movie);
        }
    }

    @Transactional
    @Test
    public void testSearch2(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());


        Page<Movie> result = movieRepository.getList2(null, pageable);


        result.get().forEach(movie -> {
            System.out.println(movie.getMno());
            System.out.println(movie.getImageSet());
            System.out.println("--------------------------------");
        });

    }

    @Transactional
    @Test
    public void testSearch3(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());


        Page<Object[]> result = movieRepository.getList3(null, pageable);


        result.get().forEach(arr -> {

            Movie movie = (Movie)arr[0];

            System.out.println(movie.getMno());
            System.out.println(movie.getImageSet());
            System.out.println("REPLYCOUNT: "+arr[1]);
            System.out.println("--------------------------------");
        });

    }

}
