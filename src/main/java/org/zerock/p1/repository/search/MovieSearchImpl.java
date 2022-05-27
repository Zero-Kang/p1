package org.zerock.p1.repository.search;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.p1.entity.Movie;
import org.zerock.p1.entity.QMovie;
import org.zerock.p1.entity.QMovieImage;
import org.zerock.p1.entity.QReview;
import static com.querydsl.sql.SQLExpressions.select;
import static com.querydsl.sql.SQLExpressions.selectFrom;

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
//
//
//        if(keyword !=null) {
//
//            query.where(movie.title.contains(keyword));
//        }

        QMovie movie1 = QMovie.movie;

        JPQLQuery<Movie> movieJPQLQuery = from(movie1);
        getQuerydsl().applyPagination(pageable, movieJPQLQuery);

        query.where(movie.in(movieJPQLQuery.fetch()));

        query.groupBy(movie);

        JPQLQuery<Tuple> tupleJPQLQuery = query.select(movie.mno, movie.title, movieImage.ino,movieImage.fname, review.rno.countDistinct() );


        //getQuerydsl().applyPagination(pageable, tupleJPQLQuery);


        List<Tuple> result = tupleJPQLQuery.fetch();

        long count = tupleJPQLQuery.fetchCount();

        List<Object[]> resultArr = result.stream().map(res -> res.toArray()).collect(Collectors.toList());

        return new PageImpl<>(resultArr,pageable,count);
        //return null;
    }

    @Override
    public Page<Movie> getList2(String keyword, Pageable pageable) {

        QMovie movie = QMovie.movie;
        QMovieImage movieImage = QMovieImage.movieImage;

        JPQLQuery<Movie> query = from(movie);

        query.leftJoin(movie.imageSet, movieImage);



        getQuerydsl().applyPagination(pageable, query);

        query.groupBy(movie);

        List<Movie> movies = query.fetch();

        long count = query.fetchCount();



        return new PageImpl<>(movies, pageable, count);
    }

    @Override
    public Page<Object[]> getList3(String keyword, Pageable pageable) {

        QMovie movie = QMovie.movie;
        QMovieImage movieImage = QMovieImage.movieImage;
        QReview review = QReview.review;

        JPQLQuery<Movie> query = from(movie);

        query.leftJoin(movie.imageSet, movieImage);
        query.leftJoin(review).on(review.movie.eq(movie));


        getQuerydsl().applyPagination(pageable, query);

        query.groupBy(movie);

        JPQLQuery<Tuple> tuples = query.select(movie, review.countDistinct());

        List<Object[]> data = tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

        long count = query.fetchCount();

        return new PageImpl<>(data, pageable, count);
    }
}







