import React, {useEffect, useState} from 'react';
import {useParams, useNavigate, Link} from "react-router-dom";
import axios from 'axios';
import "./Playlist.css";
import decode from "jwt-claims";


export default function PlaylistInfo() {

    let decode = require('jwt-claims');
    const token = window.sessionStorage.getItem('token');
    let claims = decode(token);

    let {id} = useParams();
    const [playlist, setPlaylist] = useState({
        title: "",
        dateOfCreation: "",
        songs: [],
        creator: ""
    });

    const navigate = useNavigate();


    useEffect(() => {
        getPlaylist()
        getPlaylistSongs()
    }, [])


    function getPlaylist() {
        const token = window.sessionStorage.getItem('token');

        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}


        axios.get(`http://localhost:8080/playlists/${id}`)
            .then(res => {
                setPlaylist(prevState => ({
                    ...prevState,
                    title: res.data.title,
                    dateOfCreation: res.data.dateOfCreation,
                    creator: res.data.creator
                }))

            }).catch(err => console.log(err))


    }


    function getPlaylistSongs() {
        axios.get(`http://localhost:8080/songs/playlist/${id}`)
            .then(res => {
                setPlaylist(prevState => ({
                    ...prevState,
                    songs: res.data

                }))

            }).catch(err => console.log(err))
    }


    const mapSongs = () => {
        console.log(playlist)
        return (
            <div className="songs">
                <p>Playlist songs</p>
                {playlist.songs.map((song) => (
                    <div key={song.id} className="playlist">
                        <Link to={"/song/" + song.id} className="singlePlaylist">{song.title}</Link>
                    </div>
                ))}
            </div>
        )
    }

    function getPlaylistInfo() {
        return (
            <>
                <p className="title">Playlist info</p>

                <p>Title: {playlist.title}</p>
                <p>Date: {playlist.dateOfCreation.substring(0, 10)}</p>
            </>

        )
    }


    function deletePlaylist() {
        if (claims.id == playlist.creator.id) {
            axios.delete(`http://localhost:8080/playlists/${id}`)
                .then(res => console.log(res.data))

            navigate('/');
        } else {
           alert("No..");
        }

    }

    return (

        <div className="info">
            {getPlaylistInfo()}
            {mapSongs()}

            <button onClick={deletePlaylist}>Delete playlist</button>
        </div>
    )

}