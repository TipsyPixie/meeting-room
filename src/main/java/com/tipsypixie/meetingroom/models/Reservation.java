package com.tipsypixie.meetingroom.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Length(max = 128)
    private String author;

    @Column(nullable = false)
    private Time startsAt;

    @Column(nullable = false)
    private Time endsAt;

    @Column
    @CreationTimestamp
    private Timestamp createdAt;

    @Column
    @UpdateTimestamp
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Time getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Time startsAt) {
        this.startsAt = startsAt;
    }

    public Time getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Time endsAt) {
        this.endsAt = endsAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
