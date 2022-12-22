import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './Playlist.css';
import {Link} from "react-router-dom";

var decode = require('jwt-claims');


export default function GetAllSongs() {

    const [songs, setSongs] = useState([])

    useEffect(() => {

        getSongs()

    }, [])


    const mapSongs = () => {
        return (
            <div className="userPl">
                <p className="title">Songs</p>
                {songs.map((song) => (
                    <div key={song.id} className="playlist">
                        <Link to={"/song/" + song.id} className="singlePlaylist" >{song.title}</Link>
                    </div>
                ))}
            </div>
        )
    }

    function getSongs(){
        axios.get('http://localhost:8080/songs')
            .then(res => {
                setSongs(res.data)
            }).catch(err => console.log(err))


    }


    return (
        
        mapSongs()
    )



}