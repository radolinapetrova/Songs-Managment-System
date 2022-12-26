import React, {useEffect, useState} from 'react';
import {useParams, useNavigate, Link} from "react-router-dom";
import axios from 'axios';
import "./css/Playlist.css";
import {useAuth} from "./auth/AuthProvider";

export default function PlaylistInfo() {

    const {claims} = useAuth();
    let {id} = useParams();


    const data = ({
        userId: null,
        playlistId: id,
        songId: null
    })

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

    function getSongs(){
        axios.get(`http://localhost:8080/songs/playlist/${id}`)
            .then(res => {
                setPlaylist(prevState => ({
                    ...prevState,
                    songs: res.data

                }))

            }).catch(err => console.log(err))
    }

    function getPlaylistSongs() {
        if (sessionStorage.getItem('song')) {

            data.songId = sessionStorage.getItem('song')
            data.userId = claims.id
            try{
                axios.put("http://localhost:8080/playlists", data).then
                (getSongs)
                sessionStorage.removeItem('song')
            }
            catch(err){
                if (err.response.status === 401){
                    alert("why again????")
                }
            }

        }
        else{
            getSongs()
        }
    }


    const mapSongs = () => {
        console.log(playlist)
        return (
            <div className="songs">
                <p>Playlist songs</p>
                {playlist.songs.map((song) => (
                    <div key={song.id} className="playlist">
                        <Link to={"/song/" + song.id} className="singlePlaylist">{song.title}</Link>
                        <button value={song.id} onClick={deleteSong}>-</button>
                    </div>
                ))}
            </div>
        )
    }

    const deleteSong = async (e) => {
        e.preventDefault()
        data.songId = e.target.value
        data.userId = claims.id
        console.log(data)
        axios.put('http://localhost:8080/playlists/remove', data)
            .then(getSongs)
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
            axios.delete('http://localhost:8080/playlists', {data: {userId: claims.id, playlistId: id}})
                .then(res => console.log(res.data))

            navigate('/playlists');
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