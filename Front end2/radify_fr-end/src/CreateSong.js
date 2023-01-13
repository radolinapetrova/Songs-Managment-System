import React, {useEffect, useState} from "react";
import axios from 'axios';
import './css/Playlist.css'

export default function CreateSong() {

    const token = window.sessionStorage.getItem('token');
    const [numArtists, setNumArtists] = useState(1);

    const handleAddArtist = (e) => {
        e.preventDefault()
        setNumArtists(numArtists + 1);
    }

    const [song, setSong] = useState({
        title: "",
        seconds: "",
        genre: "",
        artistsIds: []
    })
    const [value, setValue] = useState([])

    const [allArtists, setAllArtists] = useState([]);

    useEffect(() => {
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
        getArtists();
    }, [])

    function getArtists() {
        axios.get("http://localhost:8080/artists/all").then(res => setAllArtists(res.data));
    }


    const addSong = async (e) => {
        e.preventDefault()
        for (let i = 0; i < value.length; i++){
            song.artistsIds.push(value[i])
        }
        console.log(song)
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
         axios.post("http://localhost:8080/songs", song).then(res => console.log(res))
        setSong(prevState => ({...prevState, artistsIds: []}))
    }

    const getValue =  (e) => {
        e.preventDefault()
        if (Number.isInteger(parseInt(e.target.value))){
            value.push(e.target.value);
        }

        console.log(value)
    }

    function fullName(obj) {
        if (obj.lname === null) {
            return `${obj.fname}`
        }
        return `${obj.fname} ${obj.lname}`
    }


    function createForm() {
        return (
            <form>
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


                {Array.from({ length: numArtists }, (_, i) => (
                    <div key={i}>
                        <input key={i} list="browsers" onSelect={getValue} name="browser"/>
                        <datalist id="browsers">
                            {allArtists.map((a) => (
                                <option key={a.id} className="artists" value={a.id}>{fullName(a)}</option>))}
                        </datalist>
                    </div>
                ))}





                <button onClick={handleAddArtist}>Add artist</button>
                <button onClick={addSong}>Add song</button>
            </form>
        )
    }



    return (
        <div className="song-form">
            {createForm()}
            {/*{(plusArtist && addArtist())}*/}

        </div>
    )

}