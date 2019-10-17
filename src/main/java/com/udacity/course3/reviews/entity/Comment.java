package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Column(name = "text")
    @NotNull(message = "Text cannot be null")
    private String text;

    @Column(name = "created_by")
    @NotNull(message = "Created By cannot be null")
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Review getReview() { return review; }

    public void setReview(Review review) { this.review = review; }
}
