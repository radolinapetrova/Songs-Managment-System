import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './Playlist.css';
import jwt from 'jwt-decode';

export default function GetPlaylists() {
    const [playlists, setPlaylists] = useState([]);
    const [songs, setSongs] = useState([]);
    var decode = require('jwt-claims');



    useEffect(() => {
        getPlaylists();
    }, [])

    const getPlaylists = () => {

        const token = window.sessionStorage.getItem('token');

        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

        var claims = decode(token);

        axios.get(`http://localhost:8080/playlists/user/${claims.id}`)
            .then(res => {
                setPlaylists(res.data)
            }).catch(err => console.log(err))
    }

    const getSongs = (id) => {
        axios.get(`http://localhost:8080/songs/playlist/${id}`)
            .then(res => {
                setSongs(res.data)
                console.log(res.data)
                mapSongs()
            }).catch(err => console.log(err))



    }

    let playlistsArr = Array.from(playlists);

    const mapPlaylists = () => {
        return (
            <div className="userPl">
                <p>Playlists</p>
                {playlistsArr.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <div className="playlist" onClick={function (e) {
                            getSongs(playlist.id);
                        }}>Title: {playlist.title}</div>
                    </div>
                ))}
            </div>
        )
    }
    const mapSongs = () => {
        return (
            <div className="userPl">
                <p>Playlist songs</p>
                {songs.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <div className="playlist">Title: {playlist.title}</div>
                    </div>
                ))}
            </div>
        )
    }

    return (
        mapPlaylists()
    )
}