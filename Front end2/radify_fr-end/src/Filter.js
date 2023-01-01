import React, {useEffect, useState} from 'react';
import axios from 'axios';
import GetAllSongs from './GetAllSongs'
import GetAllPlaylists from './GetAllPlaylists'
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "./auth/AuthProvider";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faMagnifyingGlass} from "@fortawesome/free-solid-svg-icons";

export default function Filter() {

    const {auth, claims} = useAuth();

    const [input, setInput] = useState("")
    const [songs, setSongs] = useState([])
    const [playlists, setPlaylists] = useState([])
    const [song, setSong] = useState(null)
    const [topSongs, setTopSongs] = useState([])

    const [data, setData] = useState({
        title: input,
        user: ""
    })

    let id = 0

    useEffect(() => {
        axios.get("http://localhost:8080/listeners/top").then(res => setTopSongs(res.data))
        if (auth) {
            id = claims.id
        }
    }, [])

    useEffect(() => {
        setData(prevState => ({
            ...prevState, title: input
        }))
    }, [input])

    // function getTopSongs(){
    //

    //
    //     return (
    //         <div className="group">
    //             <div className="title">Most listened songs for {year}</div>
    //             {mapSongs(topSongs)}
    //         </div>
    //     )
    // }


    const filter = () => {
        console.log(songs)
        if ((songs.length === 0 && playlists.length === 0)) {
            return (<div className="groups">
                <GetAllSongs/>
                <GetAllPlaylists/>
            </div>)
        } else if (songs.length === 0) {
            return (<div className="groups">
                {mapPlaylists()}
                <p>No songs match the input</p>
            </div>)

        } else if (playlists.length === 0) {
            return (<div className="groups">
                {mapSongs(songs)}
                <p>No playlists match the input</p>
            </div>)
        } else {
            return (<div className="groups">
                {mapSongs(songs)}
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
        return (<div className="group">
            <p className="category">Playlists</p>
            {playlists.map((playlist) => (<div key={playlist.id} className="single">
                <Link to={"/playlist/" + playlist.id} className="title">{playlist.title}</Link>
            </div>))}
        </div>)
    }

    const getTop = () => {
        const year = new Date().getFullYear()

        if (topSongs != [])

        return (<div className="group">
            <p className="category">Top five songs for {year}</p>
            {mapSongs(topSongs)}
        </div>)
    }

    const mapSongs = (songs) => {
        console.log(songs)

        if (auth && claims.roles[0] === 'USER') {

            return (<div className="group">
                <p className="category">Songs</p>
                {songs.map((song) => (<div key={song.id} className="single">
                    <Link to={"/song/" + song.id} className="title">{song.title}</Link>
                    <button value={song.id} onClick={getSong} className="button">+</button>
                </div>))}
            </div>)
        } else {
            return (<div className="group">
                <p className="category">Songs</p>
                {songs.map((song) => (<div key={song.id} className="single">
                    <Link to={"/song/" + song.id} className="title">{song.title}</Link>
                </div>))}
            </div>)
        }
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
        <div className="searchbar">
            <input type="text" value={input} placeholder="Search..." onChange={(e) => setInput(e.target.value)}/>
            <FontAwesomeIcon icon={faMagnifyingGlass} onClick={getResults}/>
        </div>

        {filter()}
        {getTop()}
    </div>)


}