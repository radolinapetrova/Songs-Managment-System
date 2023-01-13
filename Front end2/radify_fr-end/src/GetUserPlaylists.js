import React, {useEffect, useState} from 'react';
import axios from 'axios';
import './css/Playlist.css';
import {Link} from "react-router-dom";
import {useAuth} from "./auth/AuthProvider";

export default function GetUserPlaylists() {
    const [playlists, setPlaylists] = useState([]);
    const [data, setData] = useState({
        title: "",
        isPublic: false,
        userId: 0
    })



    const {claims} = useAuth();

    useEffect(() => {

        const token = window.sessionStorage.getItem('token');
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

        setData(prevState => ({...prevState, userId: claims.id}))

        console.log("get the playlists")
        getPlaylists();

    }, [])

    function getPlaylists () {

        if (claims.roles[0] == "USER") {
            console.log("yassss")
            axios.get(`http://localhost:8080/playlists/user/${claims.id}`)
                .then(res => {
                    setPlaylists(res.data)
                    console.log(res.data)
                }).catch(err => console.log(err))
        }


    }

    let playlistsArr = Array.from(playlists);

    const mapPlaylists = () => {

        return (
            <div className="group">
                <p className="category">Playlists</p>
                {playlistsArr.map((playlist) => (
                    <div key={playlist.id} className="single">
                        <Link to={"/playlist/" + playlist.id} className="title">{playlist.title}</Link>
                    </div>
                ))}
            </div>

        )
    }

    const createPlaylist = () => {
        return (
            <div className="group">

                <form className="createPlaylist">
                    <div>
                        <label>Title </label>
                        <input type="text" id="title_input"
                               onChange={(e) => setData(prevState => ({...prevState, title: e.target.value}))}
                               required/>
                    </div>
                    <div>
                        <input type="radio" name="public"
                               onChange={(e) => setData(prevState => ({...prevState, isPublic: true}))} required/>
                        <label>Public </label>
                    </div>
                    <div>
                        <input type="radio" name="public" id="public_input"
                               onChange={(e) => setData(prevState => ({...prevState, isPublic: false}))} required/>
                        <label>Private </label>
                    </div>
                    <button onClick={handleSubmit}>Create new playlist</button>
                </form>
            </div>
        )
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(data)
        const token = window.sessionStorage.getItem('token');
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}


        try{
            axios.post("http://localhost:8080/playlists", data).then(getPlaylists)
        }
        catch (err){

        }
    }



    return (
        <>
            {mapPlaylists()}
            {createPlaylist()}
        </>
    )
}