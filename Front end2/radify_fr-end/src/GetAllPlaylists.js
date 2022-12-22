import React, {useEffect, useState} from 'react';
import axios from 'axios';
import jwt from 'jwt-decode';
import {Link} from "react-router-dom";



export default function GetAllPlaylists () {

    var decode = require('jwt-claims');

    const [playlists, setPlaylists] = useState([])
    const token = window.sessionStorage.getItem('token');


    useEffect(() => {
        if (window.sessionStorage.getItem('token')){

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
        return (
            <div className="playlists">
                <p>Playlists</p>
                {playlists.map((song) => (
                    <div key={song.id} className="songs">
                        <Link to={"/playlist/" + song.id} className="playlist" >Title: {song.title}</Link>
                    </div>
                ))}
            </div>
        )
    }


    return(
        mapPlaylists()
    )
}