package com.onurhizar.gamepass.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game extends BaseEntity {

    private String title;

    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("games")
    @Builder.Default
    private List<Category> categories = new LinkedList<>();

}
