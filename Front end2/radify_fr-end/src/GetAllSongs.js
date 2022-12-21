import React, {useEffect, useState} from 'react';
import axios from 'axios';
import jwt from 'jwt-decode';

var decode = require('jwt-claims');


export default function GetAllSongs() {

    const [songs, setSongs] = useState([])

    useEffect(() => {

        getSongs()

    }, [])


    const mapSongs = () => {
        return (
            <div className="songs">
                <p>Songs</p>
                {songs.map((song) => (
                    <div key={song.id} className="playlist">
                        <div className="song">Title: {song.title}</div>
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