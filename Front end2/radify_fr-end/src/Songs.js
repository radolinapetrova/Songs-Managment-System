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

        if (token) {
            claims = decode(token)
            id = claims.id
        }
    }, [])

    useEffect(() => {
        console.log("change")
        setData(prevState => ({
            ...prevState, title: input
        }))
    }, [input])


    const filter = () => {
        console.log(songs)
        if (songs.length == 0 && playlists.length == 0) {
            return (<div>
                <GetAllSongs/>
                <GetAllPlaylists/>
            </div>)
        } else if (songs.length == 0) {
            return (<div className="userPl">
                {mapPlaylists()}
                <p>No songs match the input</p>
            </div>)

        } else if (playlists.length == 0) {
            return (<div className="userPl">
                {mapSongs()}
                <p>No playlists match the input</p>
            </div>)
        } else {
            return (<div className="userPl">
                {mapSongs()}
                {mapPlaylists()}
            </div>)

        }


    }


    function getResults(e) {
        e.preventDefault()

        console.log(data)
        axios.get(`http://localhost:8080/songs/title/${input}`).then((res) => setSongs(res.data))
        axios.post('http://localhost:8080/playlists/title', data).then((res) => setPlaylists(res.data))

    }


    const mapPlaylists = () => {
        return (<div className="pl">
            <p className="title">Playlists</p>
            {playlists.map((playlist) => (<div key={playlist.id} className="playlist">
                <div className="idk">{playlist.title}</div>
            </div>))}
        </div>)
    }

    const mapSongs = () => {
        return (<>
            <p className="title">Songs</p>
            {songs.map((playlist) => (<div key={playlist.id} className="playlist">
                <div className="idk">{playlist.title}</div>
            </div>))}
        </>)
    }


    return (<div>
        <div>
            <input type="text" value={input} onChange={(e) => setInput(e.target.value)}/>
            <button onClick={getResults}>Search</button>
        </div>

        {filter()}
    </div>)


}