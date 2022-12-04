import React, {useEffect, useState} from 'react';
import axios from 'axios';
import jwt from 'jwt-decode';

var decode = require('jwt-claims');

export default function GetAllPlaylists () {

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
                        <div className="song">Title: {song.title}</div>
                    </div>
                ))}
            </div>
        )
    }


    return(
        mapPlaylists()
    )
}