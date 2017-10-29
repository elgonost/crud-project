package com.crud.crudproject.Controllers;

import com.crud.crudproject.Models.Song;
import com.crud.crudproject.Models.User;
import com.crud.crudproject.Services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/songs")
public class SongController {
    @Autowired
    SongService songService;

    @Autowired
    public SongController(SongService songService){
        this.songService = songService;
    }

    //Show all entries
    @RequestMapping("/list")
    public String getSongs(Model model, HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("songList",songService.list(user.getId()));
        return "songList";
    }

    //Display the Add Song form
    @RequestMapping("/add")
    public String addSong(Model model){
        model.addAttribute("song",new Song());
        return "form";
    }

    //Save the new song into the database
    @PostMapping("/save")
    public String save(@Valid Song song, BindingResult bindingResult, Model model, HttpSession session){

        if(bindingResult.hasErrors()){
            return "form";
        } else {
            User user = (User) session.getAttribute("loggedInUser");
            song.setUserId(user.getId());
            Song savedSong = songService.save(song);
            return "redirect:/songs/list";
        }
    }

    //Update song information
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("song",songService.get(id));
        return "form";
    }

    //Delete song
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        songService.delete(id);
        redirectAttributes.addFlashAttribute("message","Song was deleted.");
        return "redirect:/songs/list";
    }
}
