package com.crud.crudproject.Services;

import com.crud.crudproject.Models.Song;
import com.crud.crudproject.Repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    public List<Song> list(){
        return songRepository.findAll();
    }

    public Song save(Song song){
        return songRepository.save(song);
    }

    public Song get(Long id){
        return songRepository.getOne(id);
    }

    public void delete(Long id){
        songRepository.delete(id);
    }
}
