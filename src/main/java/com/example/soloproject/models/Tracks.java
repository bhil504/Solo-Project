package com.example.soloproject.models;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tracks")
public class Tracks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient // This field won't be persisted
    private MultipartFile file;

    @NotEmpty(message = "Title is required!")
    @Size(min = 3, max = 30, message = "Title must be between 3 and 30 characters")
    private String title;

    @NotEmpty(message = "Genre is required!")
    @Size(min = 1, max = 30, message = "Genre must be between 1 and 30 characters!")
    private String genre;

    @NotBlank(message = "Lyrics are required!")
    @Lob
    private String lyrics;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "file_data", columnDefinition="LONGBLOB")
    private byte[] fileData;

    @Column(updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Tracks() {}

    public Tracks(Long id, String title, String genre, String lyrics, String fileName, User user) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.lyrics = lyrics;
        this.fileName = fileName;
        this.user = user;
    }
    
 // Validating file type and size
    @AssertTrue(message = "Only image and audio files (jpg, png, mp3, wav) are allowed, and must be less than 16MB.")
    public boolean isValidFile() {
        if (file == null || file.isEmpty()) return true; // Allow no file to be uploaded
        List<String> allowedTypes = Arrays.asList("image/jpeg", "image/png", "audio/mpeg", "audio/wav");
        return file.getSize() <= 16 * 1024 * 1024 && allowedTypes.contains(file.getContentType());
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    
    //Getters And Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

    
}
