package com.crud.crudproject.Repositories;

import com.crud.crudproject.Models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByUserId(Long userId);
}
