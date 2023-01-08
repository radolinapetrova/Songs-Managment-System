import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import axios from 'axios';
import "./css/Playlist.css"
import {useAuth} from "./auth/AuthProvider";

export default function SongInfo() {


    const {auth, claims} = useAuth();

    let {id} = useParams();
    const [song, setSong] = useState({
        title: "",
        genre: "",
        seconds: "",
        artists: [],
        listeners: 0,
        yearlyListeners: 0
    });


    useEffect(() => {
        getSongInfo()
        getMonthlyListeners()

    }, [])


    function getSongInfo() {
        axios.get(`http://localhost:8080/songs/${id}`)
            .then(res => {
                console.log(res.data)
                setSong(prevState => ({
                    ...prevState,
                    title: res.data.title,
                    genre: res.data.genre,
                    seconds: res.data.seconds,
                    artists: res.data.artists
                }))

            }).catch(err => console.log(err))

    }

    function getMonthlyListeners() {


        axios.get(`http://localhost:8080/listeners/${id}`)
            .then(res => {

                setSong(prevState => ({
                    ...prevState,
                    listeners: res.data
                }))
                console.log(song.listeners)
            })


    }

    const mapArtists = () => {

        return (
            <div>
                <p className="category">Artists</p>
                {song.artists.map((artist) => (
                    <div key={artist.id} className="detail">
                        <p className="label">{artist.fname}  { }  {artist.lname}</p>
                    </div>
                ))}
            </div>

        )
    }


    function playSong() {

        const data = ({
            user: claims.id,
            song: id
        })

        console.log(data);
        axios.post("http://localhost:8080/listeners", data)
            .then(res => {
                if (res.status == 200) {
                    setSong(prevState => ({
                        ...prevState,
                        listeners: song.listeners + 1,
                        yearlyListeners: song.yearlyListeners + 1
                    }))
                }
            })


    }

    if (auth && claims.roles[0] === "USER") {
        return (
            <div className="songInfo">
                <div className="detail">
                    <p className="label">Title:</p>
                    <p>{song.title}</p>
                </div>
                <div className="detail">
                    <p className="label">Genre:</p>
                    <p>{song.genre}</p>
                </div>
                <div className="detail">
                    <p className="label">Seconds:</p>
                    <p>{song.seconds}</p>
                </div>
                <div className="detail">
                    <p className="label">Monthly listeners:</p>
                    <p>{song.listeners}</p>
                </div>
                {mapArtists()}
                <button onClick={playSong}>Play song</button>
            </div>
        )
    } else {
        return (
            <div className="songInfo">
                <div className="detail">
                    <p className="label">Title:</p>
                    <p>{song.title}</p>
                </div>
                <div className="detail">
                    <p className="label">Genre:</p>
                    <p>{song.genre}</p>
                </div>
                <div className="detail">
                    <p className="label">Seconds:</p>
                    <p>{song.seconds}</p>
                </div>
                <div className="detail">
                    <p className="label">Monthly listeners:</p>
                    <p>{song.listeners}</p>
                </div>
                {mapArtists()}
            </div>
        )
    }


}