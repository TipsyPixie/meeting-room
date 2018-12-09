package com.tipsypixie.meetingroom.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    @Length(max = 128)
    private String author;

    @Column(nullable = false)
    private LocalDateTime startsAt;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    @Column(nullable = false)
    private Integer repetitionCount;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public Reservation setRoom(String room) {
        this.room = room;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Reservation setAuthor(String author) {
        this.author = author;
        return this;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public Reservation setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
        return this;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public Reservation setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Reservation setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Reservation setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Integer getRepetitionCount() {
        return repetitionCount;
    }

    public Reservation setRepetitionCount(Integer repetitionCount) {
        this.repetitionCount = repetitionCount;
        return this;
    }
}
