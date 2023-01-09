package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.PlaylistService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnauthorizedAction;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.domain.Playlist;
import com.example.radify_be.persistence.PlaylistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {


    private final PlaylistRepo repo;

    @Override
    public Playlist getPlaylistSongs(Integer playlistId) {
        return null;
    }

    @Override
    public Playlist createPlaylist(Playlist playlist) throws UnsuccessfulAction, InvalidInputException {

        Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]+$");
        Matcher match = pattern.matcher(playlist.getTitle());

        if (!match.matches()){
            throw new InvalidInputException();
        }

        Playlist pl = null;

        pl = repo.save(playlist);

        if (pl == null) {
            throw new UnsuccessfulAction();
        }
        return pl;
    }

    @Override
    public boolean ExistsByCreatorId(Integer id){
        return repo.ExistsByCreatorId(id);
    }


    @Override
    public List<Playlist> getUserPlaylists(Integer id) {

        boolean exists = ExistsByCreatorId(id);

        if(!exists){
            return new ArrayList<Playlist>();
        }
        return repo.getAllByUserId(id);
    }

    @Override
    public void deletePlaylist(Integer playlist, Integer user) throws UnauthorizedAction, UnsuccessfulAction {
        if (repo.findById(playlist).getCreator().getId() != user) {
            throw new UnauthorizedAction();
        }
        repo.deleteById(playlist);

        //check if the playlist was successfully deleted
        if (repo.existsById(playlist)) {
            throw new UnsuccessfulAction();
        }
    }

    @Override
    public Playlist addSongToPlaylist(Integer playlist, Integer song, Integer user) throws UnauthorizedAction, UnsuccessfulAction {

        if (repo.findById(playlist).getCreator().getId() != user) {
            throw new UnauthorizedAction();
        }
        Playlist result = repo.addSong(playlist, song);

        //check if the song was successfully added
        if (!result.getSongs().stream().anyMatch(s -> s.getId() == song)) {
            throw new UnsuccessfulAction();
        }
        return result;
    }

    @Override
    public Playlist removeSongsFromPlaylist(Integer playlist, Integer song, Integer user) throws UnauthorizedAction, UnsuccessfulAction {
        if (repo.findById(playlist).getCreator().getId() != user) {
            throw new UnauthorizedAction();
        }
        Playlist result = repo.deleteSong(playlist, song);

        //check if the song was successfully deleted
        if (result.getSongs().stream().anyMatch(s -> s.getId() == song)) {
            throw new UnsuccessfulAction();
        }
        return result;
    }

    @Override
    public Playlist updatePlaylistInfo(Playlist pl, Integer user) throws UnauthorizedAction , UnsuccessfulAction{
        if (pl.getCreator().getId() != user) {
            throw new UnauthorizedAction();
        }

        Playlist pl2 = repo.save(pl);

        if (pl2 != pl){
            throw new UnsuccessfulAction();
        }
        return pl2;
    }

    @Override
    public Playlist findById(Integer id) {
        return repo.findById(id);
    }


    @Override
    public List<Playlist> getAllPublicAndUser(Integer id) {
        return repo.getAllPublicAndUser(id);
    }

    @Override
    public List<Playlist> getAllByTitle(Integer id, String title) {

        return repo.findByTitle(title, id);
    }
}
