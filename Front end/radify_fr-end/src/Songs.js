import React, {useEffect, useState} from 'react';
import axios from 'axios';
import GetAllSongs from './GetAllSongs'
import GetAllPlaylists from './GetAllPlaylists'


export default function Songs() {

    const [input, setInput] = useState("")
    const [results, setResults] = useState([])


    const filter = () => {
        if (results) {
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