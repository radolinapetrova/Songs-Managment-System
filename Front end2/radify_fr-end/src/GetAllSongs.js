import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './css/Playlist.css';
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "./auth/AuthProvider";

export default function GetAllSongs() {

    const {auth, claims} = useAuth();
    const [songs, setSongs] = useState([])
    const [song, setSong] = useState(null)

    useEffect(() => {

        getSongs()

    }, [])

    let navigate = useNavigate()
    const token = window.sessionStorage.getItem('token');

    function saveSong(id) {
        sessionStorage.removeItem('song');
        sessionStorage.setItem('song', id)
    }

    const getSong = async (e) => {
        e.preventDefault()
        setSong(e.target.value);
       saveSong(e.target.value)

        navigate("/playlists")
    }



    const mapSongs = () => {

        if ( auth && claims.roles[0] === 'USER') {
            return (<div className="group">
                <p className="category">Songs</p>
                {songs.map((song) => (<div key={song.id} className="single">
                    <Link to={"/song/" + song.id} className="title">{song.title}</Link>
                    <button value={song.id} onClick={getSong} className="button">+</button>
                </div>))}
            </div>)
        } else {
            if (auth){
            return (<div className="group">
                <p className="category">Songs</p>
                {songs.map((song) => (<div key={song.id} className="single">
                    <Link to={"/song/" + song.id} className="title">{song.title}</Link>
                </div>))}
            </div>)}
            else{
                return (<div className="group">
                    <p className="category">Songs</p>
                    {songs.map((song) => (<div key={song.id} className="single">
                        <Link to={"/song/" + song.id} className="title">{song.title}</Link>

                    </div>))}
                </div>)
            }

        }
    }


    function getSongs() {
        console.log("herreee")
        axios.get('http://localhost:8080/songs/all')
            .then(res => {
                 setSongs(res.data)
            }).catch(err => console.log(err))
console.log(songs)

    }


    return (
        mapSongs()
    )


}