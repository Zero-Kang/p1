package org.zerock.p1.entity;


import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String fname;

    @ManyToOne

    @BatchSize(size = 10)
    private Movie movie;

    private int ord;

    public void fixOrd(int ord){
        this.ord = ord;
    }

    public void fixMovie(Movie movie){
        this.movie = movie;
    }
}
