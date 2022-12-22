import React, {useEffect, useState} from 'react';
import axios from 'axios';
import jwt from 'jwt-decode';
import {Link} from "react-router-dom";

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
                        <Link to={"/song/" + song.id} className="song   " >Title: {song.title}</Link>
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