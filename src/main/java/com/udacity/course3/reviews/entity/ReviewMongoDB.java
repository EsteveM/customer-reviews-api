package com.udacity.course3.reviews.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "reviews")
public class ReviewMongoDB {

    @Id
    private Integer id;
    private String title;
    private String text;
    private String createdBy;
    // EMG - For a review of the use of embedded documents with Spring Data and MongoDB, see
    // https://lankydan.dev/2017/05/29/embedded-documents-with-spring-data-and-mongodb
    private List<CommentMongoDB> comments;

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

    public List<CommentMongoDB> getComments() { return comments; }

    public void setComments(List<CommentMongoDB> comments) {
        this.comments = comments;
    }
}
