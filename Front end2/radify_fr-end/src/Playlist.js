import React, {useEffect, useState} from 'react';
import axios from 'axios';


export default function Playlist(id) {

    const [songs, setSongs] = useState([]);

    useEffect(() => {
        getSongs()
    })

    const getSongs = () => {
        axios.get(`http://localhost:8080/songs/playlist/${id}`)
            .then(res => {
                setSongs(res.data)
                console.log(res.data)
            }).catch(err => console.log(err))
    }


    const mapSongs = () => {
        console.log("tuka sum ma")
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
        mapSongs()
    )

}