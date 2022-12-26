import React, {useEffect, useState} from 'react';
import axios from 'axios';
import "./css/Playlist.css"
import {useAuth} from "./auth/AuthProvider";
export default function CreatePlaylist() {

    const {claims} = useAuth();

    const [data, setData] = useState({
        title: "",
        isPublic: false,
        userId: 0
    })

    const [playlist, setPlaylist] = useState("");


        useEffect(() => {

            const token = window.sessionStorage.getItem('token');
            axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}

            setData(prevState => ({...prevState, userId: claims.id}))
        }, [])


    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(data)
        axios.post("http://localhost:8080/playlists", data).then(res => setPlaylist(res.data.title))
    }


    return (
        <div  >

            <form className="newPlaylist">
                <div>
                    <label>Title </label>
                    <input type="text"
                           onChange={(e) => setData(prevState => ({...prevState, title: e.target.value}))} required/>
                </div>
                <div>
                    <input type="radio" name="public"
                           onChange={(e) => setData(prevState => ({...prevState, isPublic: true}))} required/>
                    <label>Public </label>
                </div>
                <div>
                    <input type="radio" name="public"
                           onChange={(e) => setData(prevState => ({...prevState, isPublic: false}))} required/>
                    <label>Private </label>
                </div>
                <button onClick={handleSubmit}>Create new playlist</button>
            </form>
        </div>
    )
}