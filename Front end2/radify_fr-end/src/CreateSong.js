import React, {useEffect, useState} from "react";
import axios from 'axios';


export default function CreateSong() {

    const token = window.sessionStorage.getItem('token');


    const [song, setSong] = useState({
        title: "",
        seconds: "",
        genre: "",
        artistIds: []
    })
    const [selectedArtist, setSelectedArtist] = useState([])
    const [value, setValue] = useState("")

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
         setSelectedArtist(prevArray => [...prevArray, value])
        console.log(selectedArtist)
         setSong(prevState => ({...prevState, artistsIds: selectedArtist}))
        console.log(song)
        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
       // axios.post("http://localhost:8080/songs", song).then(res => console.log(res))
        setSong(prevState => ({...prevState, artistsIds: []}))
    }

    const getValue = async (e) => {
        e.preventDefault()
        setValue(e.target.value);
    }

    function fullName(obj) {
        if (obj.lname === null) {
            return `${obj.fname}`
        }
        return `${obj.fname} ${obj.lname}`
    }



    return (
        <div>
            {
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

                    <input list="browsers"  onSelect={getValue} name="browser"/>
                    <datalist id="browsers" >
                        {allArtists.map((a) => (
                            <option key={a.id} className="artists"  value={a.id}>{fullName(a)}</option>))}
                    </datalist>


                    <button onClick={addSong}>Add song</button>
                </form>
            }
        </div>
    )

}