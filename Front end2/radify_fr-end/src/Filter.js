import React, {useEffect, useState} from 'react';
import axios from 'axios';
import GetAllSongs from './GetAllSongs'
import GetAllPlaylists from './GetAllPlaylists'
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "./auth/AuthProvider";

export default function Filter() {

    const {auth, claims} = useAuth();

    const [input, setInput] = useState("")
    const [songs, setSongs] = useState([])
    const [playlists, setPlaylists] = useState([])
    const [song, setSong] = useState(null)

    const [data, setData] = useState({
        title: input,
        user: ""
    })

    let id = 0

    useEffect(() => {
        if (auth) {
            id = claims.id
        }
    }, [])

    useEffect(() => {
        setData(prevState => ({
            ...prevState, title: input
        }))
    }, [input])


    const filter = () => {
        console.log(songs)
        if (songs.length === 0 && playlists.length === 0) {
            return (<div>
                <GetAllSongs/>
                <GetAllPlaylists/>
            </div>)
        } else if (songs.length === 0) {
            return (<div className="userPl">
                {mapPlaylists()}
                <p>No songs match the input</p>
            </div>)

        } else if (playlists.length === 0) {
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
            {songs.map((song) => (<div key={song.id} className="playlist">
                <Link to={"/song/" + song.id} className="singlePlaylist">{song.title}</Link>
                <button value={song.id} onClick={getSong}>+</button>
            </div>))}
        </>)
    }


    let navigate = useNavigate()

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


    return (<div>
        <div>
            <input type="text" value={input} onChange={(e) => setInput(e.target.value)}/>
            <button onClick={getResults}>Search</button>
        </div>

        {filter()}
    </div>)


}