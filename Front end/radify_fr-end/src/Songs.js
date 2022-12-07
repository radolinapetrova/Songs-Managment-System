import React, {useEffect, useState} from 'react';
import axios from 'axios';
import GetAllSongs from './GetAllSongs'
import GetAllPlaylists from './GetAllPlaylists'
import jwt from 'jwt-decode';


export default function Songs() {

    var decode = require('jwt-claims');

    const [input, setInput] = useState("")
    const [results, setResults] = useState([])
    const [resultss, setResultss] = useState([])
    const token = window.sessionStorage.getItem('token')
    var claims
    let id = 0

    useEffect(() => {
        console.log("herreeee")
        if(token){
            claims = decode(token)
            console.log(claims)
            id = claims.id
            console.log(id)
        }
    }, [])


    const filter = () => {
        console.log(results)
        if (results.length == 0 && resultss.length == 0) {
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
        axios.get(`http://localhost:8080/songs/${input}`).then((res) => setResults(res.data))
        axios.post('http://localhost:8080/playlists/title', {id, input}).then((res) => console.log(res.data))

    }


    const mapPlaylists = () => {
        return (
            <div className="userPl">

                <p>Songs</p>
                {results.map((playlist) => (
                    <div key={playlist.id} className="playlist">
                        <div className="playlist">Title: {playlist.title}</div>
                    </div>
                ))}

                {/*<p>Playlists</p>*/}
                {/*{resultss.map((playlist) => (*/}
                {/*    <div key={playlist.id} className="playlist">*/}
                {/*        <div className="playlist">Title: {playlist.title}</div>*/}
                {/*    </div>*/}
                {/*))}*/}
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