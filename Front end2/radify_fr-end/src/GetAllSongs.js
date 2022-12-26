import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './css/Playlist.css';
import {Link, useNavigate} from "react-router-dom";


export default function GetAllSongs() {

    const [songs, setSongs] = useState([])
    const [song, setSong] = useState(null)

    useEffect(() => {

        getSongs()

    }, [])

    let navigate = useNavigate()

    function saveSong(id) {
        sessionStorage.removeItem('song');
        sessionStorage.setItem('song', id)
    }

    const getSong = async (e) => {
        e.preventDefault()
        setSong(e.target.value);
       saveSong(e.target.value)
        navigate("/playlists")
    }


    const mapSongs = () => {
        return (
            <div className="userPl">{
                <form>
                    <p className="title">Songs</p>
                    {songs.map((song) => (
                        <div key={song.id} className="playlist">
                            <Link to={"/song/" + song.id} className="singlePlaylist">{song.title}</Link>
                            <button value={song.id} onClick={getSong}>+</button>
                        </div>
                    ))}
                </form>}
            </div>
        )
    }

    function getSongs() {
        axios.get('http://localhost:8080/songs/all')
            .then(res => {
                setSongs(res.data)
            }).catch(err => console.log(err))


    }


    return (
        mapSongs()
    )


}