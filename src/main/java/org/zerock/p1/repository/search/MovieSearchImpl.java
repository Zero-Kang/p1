package org.zerock.p1.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.entity.QMovie;
import org.zerock.p1.entity.QMovieImage;
import org.zerock.p1.entity.QReview;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class MovieSearchImpl extends QuerydslRepositorySupport implements MovieSearch {

    public MovieSearchImpl(){
        super(Movie.class);
    }

    @Override
    public Page<Object[]> getList1(String keyword, Pageable pageable) {


        QMovie movie = QMovie.movie;
        QMovieImage movieImage = QMovieImage.movieImage;
        QReview review = QReview.review;

        JPQLQuery<Movie> query = from(movie);
        query.leftJoin(movieImage).on(movieImage.movie.eq(movie));
        query.leftJoin(review).on(review.movie.eq(movie));


        if(keyword !=null) {

            query.where(movie.title.contains(keyword));
        }


        query.groupBy(movie);

        JPQLQuery<Tuple> tupleJPQLQuery = query.select(movie.mno, movie.title, movieImage.ino,movieImage.fname, review.rno.countDistinct() );


        getQuerydsl().applyPagination(pageable, tupleJPQLQuery);


        List<Tuple> result = tupleJPQLQuery.fetch();

        long count = tupleJPQLQuery.fetchCount();

        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());

        return new PageImpl<>(resultArr,pageable,count);
    }
}
