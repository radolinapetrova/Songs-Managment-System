import React, {useEffect, useState} from 'react';
import axios from 'axios';
import "./Playlist.css"

export default function CreatePlaylist() {

    const [data, setData] = useState({
        title: "",
        isPublic: false,
        userId: 3
    })

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post("http://localhost:8080/playlists", data).then(res => console.log(res.data))
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
                           onChange={(e) => setData(prevState => ({...prevState, isPublic: e.target.value}))} required/>
                    <label>Public </label>
                </div>
                <div>
                    <input type="radio" name="public" required/>
                    <label>Private </label>
                </div>
                <button onClick={handleSubmit}>Create new playlist</button>
            </form>
        </div>
    )
}