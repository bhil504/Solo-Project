package com.example.soloproject.services;

import com.example.soloproject.models.Tracks;
import com.example.soloproject.repositories.TracksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TracksService {

    @Autowired
    private TracksRepository tracksRepository;

    public TracksService(TracksRepository tracksRepository) {
    	this.tracksRepository = tracksRepository;
    }
    
    // Create a new track
    public Tracks createTrack(Tracks track, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            track.setFileData(file.getBytes()); // Set the file data as byte[]
            track.setFileName(file.getOriginalFilename()); // Set the file name
        }
        return tracksRepository.save(track);
    }

    // Retrieve all tracks
    public List<Tracks> alltracks() {
        return tracksRepository.findAll();
    }

    // Retrieve a track by ID
    public Tracks getTracksByID(Long id) {
        Optional<Tracks> track = tracksRepository.findById(id);
        return track.orElse(null); // Handle track not found
    }

    // Update an existing track
    public Tracks updateTrack(Long id, Tracks track, MultipartFile file) throws IOException {
        Tracks existingTrack = tracksRepository.findById(id).orElse(null);

        if (existingTrack != null) {
            // Update fields
            existingTrack.setTitle(track.getTitle());
            existingTrack.setGenre(track.getGenre());
            existingTrack.setLyrics(track.getLyrics());

            if (file != null && !file.isEmpty()) {
                existingTrack.setFileData(file.getBytes());
                existingTrack.setFileName(file.getOriginalFilename());
            }

            return tracksRepository.save(existingTrack); // Save updated track
        }
        return null; // Handle case where track is not found
    }


    

    public List<Tracks> getAllTracks() {
        return tracksRepository.findAll();
    }

    public Optional<Tracks> getTrackByID(Long id) {
        return tracksRepository.findById(id); // Return Optional for better handling
    }

    public void deleteTrack(Long id) {
        tracksRepository.deleteById(id);
    }
    
    public void saveTrack(Tracks track, MultipartFile file) throws IOException {
        // Convert the MultipartFile to byte[] and set it in the track entity
        track.setFileData(file.getBytes());

        // Save the track to the database
        tracksRepository.save(track);
    }
    
    public void saveTrackWithFileData(Tracks track, MultipartFile file) throws IOException {
        if (track == null) {
            throw new IllegalArgumentException("Track object cannot be null");
        }

        if (file != null && !file.isEmpty()) {
            // Optional: Validate the file size before processing
            if (file.getSize() > 16 * 1024 * 1024) { // 16 MB
                throw new IOException("File size exceeds the maximum limit of 16 MB.");
            }

            // Convert the file to byte[]
            byte[] fileBytes = file.getBytes();
            track.setFileData(fileBytes);

            // Optional: Set the file name
            track.setFileName(file.getOriginalFilename());
        } else {
            // Handle case where file is null or empty
            throw new IOException("File must not be empty");
        }

        // Save the track to the database
        tracksRepository.save(track);
    }

    // Other service methods can be added as needed
}