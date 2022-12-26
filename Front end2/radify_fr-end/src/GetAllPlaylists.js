import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './css/Playlist.css';
import {Link} from "react-router-dom";



export default function GetAllPlaylists () {

    var decode = require('jwt-claims');

    const [playlists, setPlaylists] = useState([])
    const token = window.sessionStorage.getItem('token');


    useEffect(() => {
        if (token){

            axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
            const claims = decode(token);
            getSongs(claims.id)
        }
        else{
            let id = 0;
            getSongs(id)
        }

    }, [])


    function getSongs(id){
        axios.get(`http://localhost:8080/playlists/all/${id}`)
            .then(res => {
                setPlaylists(res.data)
            }).catch(err => console.log(err))


    }

    const mapPlaylists = () => {

        if (!playlists.length > 0){
            return (
                <div>
                    No playlists!
                </div>
            )
        }
        return (
            <div className="userPl">
                <p className="title">Playlists</p>
                {playlists.map((song) => (
                    <div key={song.id} className="playlist">
                        <Link to={"/playlist/" + song.id} className="singlePlaylist" >{song.title}</Link>
                    </div>
                ))}
            </div>
        )
    }


    return(
        mapPlaylists()
    )
}