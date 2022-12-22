import React, {useEffect, useState} from 'react';
import axios from 'axios';
import GetAllSongs from './GetAllSongs'
import GetAllPlaylists from './GetAllPlaylists'

export default function Songs() {

    var decode = require('jwt-claims');

    const [input, setInput] = useState("")
    const [songs, setSongs] = useState([])
    const [playlists, setPlaylists] = useState([])
    const token = window.sessionStorage.getItem('token')

    const [data, setData] = useState({
        title: input,
        user: ""
    })
    let claims;
    let id = 0

    useEffect(() => {
        if(token){
            claims = decode(token)
            console.log(claims)
            id = claims.id
            console.log(id)
            setData(prevState => ({
                ...prevState,
                    user: id
            }))
        }
    }, [])


    const filter = () => {
        console.log(songs)
        if (songs.length == 0 && playlists.length == 0) {
            return (
                <div>
                    <GetAllSongs/>
                    <GetAllPlaylists/>
                </div>
            )
        } else {
            return mapPlaylists()
        }
    }


    function getResults(e) {
        e.preventDefault()

        setData(prevState => ({
            ...prevState,
            title: input
        }))


        console.log(data)
        axios.get(`http://localhost:8080/songs/title/${input}`).then((res) => setSongs(res.data))
        axios.post('http://localhost:8080/playlists/title', data).then((res) => setPlaylists(res.data))

    }


    const mapPlaylists = () => {
        return (
            <div className="userPl">

                <p>Songs</p>
                {songs.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <div className="idk">Title: {playlist.title}</div>
                    </div>
                ))}


                <p>Playlists</p>
                {playlists.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <div className="idk">Title: {playlist.title}</div>
                    </div>
                ))}
            </div>
        )
    }

    return (
        <div>
            <div>
                <input type="text" value={input} onChange={(e) => setInput(e.target.value)}/>
                <button onClick={getResults}>Search</button>
            </div>

            {filter()}
        </div>
    )


}