package org.zerock.p1.entity;


import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "movie")
    @Builder.Default

    @BatchSize(size =100)
    private Set<MovieImage> imageSet = new HashSet<>();

    public void addImage(MovieImage movieImage){

        movieImage.fixOrd(this.imageSet.size());
        movieImage.fixMovie(this);
        this.imageSet.add(movieImage);
    }



}
