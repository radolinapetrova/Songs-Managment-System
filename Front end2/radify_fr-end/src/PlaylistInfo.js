import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import axios from 'axios';
import "./Playlist.css"
import decode from "jwt-claims";

export default function PlaylistInfo() {

    let {id} = useParams();
    const [playlist, setPlaylist] = useState({
        title: "",
        dateOfCreation: "",
        songs: [],
        creator: ""
    });


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


    function getPlaylistSongs(){
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
                {playlist.songs.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <p className="singlePlaylist">Title: {playlist.title}</p>
                    </div>
                ))}
            </div>
        )
    }

    function getPlaylistInfo() {
        return (
            <>
                <p>Playlist info</p>

                <p>Title: {playlist.title}</p>
                <p>Date: {playlist.dateOfCreation.substring(0, 10)}</p>
            </>

        )
    }


    function deletePlaylist(){
        axios.delete(`http://localhost:8080/playlists/${id}`)
            .then(res => console.log(res.data))
    }

    return (

        <>
            {getPlaylistInfo()}
            {mapSongs()}
            <button onClick={deletePlaylist}>Delete playlist</button>
        </>
    )

}