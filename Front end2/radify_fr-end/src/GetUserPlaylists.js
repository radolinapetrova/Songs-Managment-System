import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './css/Playlist.css';
import {Link} from "react-router-dom";

export default function GetUserPlaylists() {
    const [playlists, setPlaylists] = useState([]);
    let decode = require('jwt-claims');



    useEffect(() => {
        getPlaylists();
    }, [])

    const getPlaylists = () => {

        const token = window.sessionStorage.getItem('token');

        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

        let claims = decode(token);


        if(claims.roles == "USER"){
            console.log("yassss")
            axios.get(`http://localhost:8080/playlists/user/${claims.id}`)
                .then(res => {
                    setPlaylists(res.data)
                }).catch(err => console.log(err))
        }
    }

    let playlistsArr = Array.from(playlists);

    const mapPlaylists = () => {
        return (
            <div className="userPl">
                <p className="title">Playlists</p>
                {playlistsArr.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <Link to={"/playlist/" + playlist.id} className="singlePlaylist" >{playlist.title}</Link>
                    </div>
                ))}
            </div>
        )
    }



    return (
        mapPlaylists()
    )
}