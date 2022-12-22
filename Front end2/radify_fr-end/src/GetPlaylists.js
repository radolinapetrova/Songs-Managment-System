import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './Playlist.css';
import jwt from 'jwt-decode';
import Playlist from "./Playlist";
import {useSearchParams, Link} from "react-router-dom";

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

        let claims = decode(token);


        if(claims.roles == "USER"){
            axios.get(`http://localhost:8080/playlists/user/${claims.id}`)
                .then(res => {
                    setPlaylists(res.data)
                }).catch(err => console.log(err))
        }



    }

    const getSongs = (id) => {
        axios.get(`http://localhost:8080/songs/playlist/${id}`)
            .then(res => {
                setSongs(res.data)
                console.log(res.data)
                mapSongs()
            }).catch(err => console.log(err))



    }

    function get(id){
        console.log("here")
        console.log(id)
        return (
            <Playlist id = {id}/>
        )
    }

    let playlistsArr = Array.from(playlists);

    const mapPlaylists = () => {
        return (
            <div className="userPl">
                <p>Playlists</p>
                {playlistsArr.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <Link to={"/playlist/" + playlist.id} className="singlePlaylist" >Title: {playlist.title}</Link>
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