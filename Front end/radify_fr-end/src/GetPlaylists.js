import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './Playlist.css';

export default function GetPlaylists() {
    const [playlists, setPlaylists] = useState([]);
    const [songs, setSongs] = useState([]);

    useEffect(() => {
        getPlaylists();
    }, [])

    const getPlaylists = () => {
        // axios.get('http://localhost:8080/playlists/1')
        //     .then(res => {
        //         setPlaylists(res.data)
        //     }).catch(err => console.log(err))
    }

    const getSongs = () => {
        // axios.get('http://localhost:8080/songs/2')
        //     .then(res => {
        //         setSongs(res.data.songs)
        //         console.log(res.data.songs)
        //     }).catch(err => console.log(err))
    }
 
    let playlistsArr = Array.from(playlists);

    const mapPlaylists = () => {
        return (
            <div className="userPl">
                <p>Playlists</p>
                {playlistsArr.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <div className="playlist" onClick={function (e) {
                            getSongs();
                        }}>Title: {playlist.title}</div>
                    </div>
                ))}
            </div>
        )
    }

    return (
        mapPlaylists()
    )
}