import React, {useEffect, useState} from "react";
import axios from 'axios';
import songsPage from "./pages/SongsPage";


export default function AddSongs() {

    var decode = require('jwt-claims');

    const token = window.sessionStorage.getItem('token');
    let firstArtist;

    const [song, setSong] = useState({
        title: "",
        seconds: "",
        genre: "",
        artists: []
    })
    const [selectedArtist, setSelectedArtist] = useState("")
    const [value, setValue] = useState(firstArtist)

    const [allArtists, setAllArtists] = useState([]);

    useEffect(() => {
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
        getArtists();
    }, [])

    function getArtists() {
        axios.get("http://localhost:8080/artists/all").then(res => setAllArtists(res.data));
    }



    function addSong(){
        axios.post("http://localhost:8080/songs", song).then(res => console.log(res))
    }

    function handleChange(e){
        setValue(e.target.value);
    }


    return (
        <div>
            <label>Title</label>
            <input type="text" value={song.title}
                   onChange={(e) => setSong(prevState => ({...prevState, title: e.target.value}))} required/>

            <label>Seconds</label>
            <input type="number" value={song.seconds}
                   onChange={(e) => setSong(prevState => ({...prevState, seconds: e.target.value}))} required/>

            <label>Genre</label>
            <input type="text" value={song.genre}
                   onChange={(e) => setSong(prevState => ({...prevState, genre: e.target.value}))} required/>

            <label>Artists</label>
            <input type="text" value={song.artists}/>
            <select name="artists" id="artists" value={value} onChange={handleChange}>
                {allArtists.map((a) => (
                    <option key={a.id} className="artists" value={a}>{a.fname} {a.lname}</option>
                ))}
            </select>

            <button onClick={addSong}>Add song</button>
        </div>
    )

}