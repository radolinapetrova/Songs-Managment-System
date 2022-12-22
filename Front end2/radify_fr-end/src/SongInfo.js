import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import axios from 'axios';
import "./Playlist.css"
import decode from "jwt-claims";

export default function SongInfo() {

    let decode = require('jwt-claims');
    const token = window.sessionStorage.getItem('token');
    const claims = decode(token);
    const [response, setResponse] = useState("");

    let {id} = useParams();
    const [song, setSong] = useState({
        title: "",
        genre: "",
        seconds: "",
        artists: "",
        listeners: 0,
        yearlyListeners: 0
    });


    const [data, setData] = useState({
        user: claims.id,
        song: id
    })

    useEffect(() => {
        getSongInfo()
        getMonthlyListeners()
        getYearlyListeners()
    }, [])


    function getSongInfo() {
        axios.get(`http://localhost:8080/songs/${id}`)
            .then(res => {
                setSong(prevState => ({
                    ...prevState,
                    title: res.data.title,
                    genre: res.data.genre,
                    seconds: res.data.seconds
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


    function getYearlyListeners() {


        axios.get(`http://localhost:8080/listeners/year/${id}`)
            .then(res => {

                setSong(prevState => ({
                    ...prevState,
                    yearlyListeners: res.data
                }))
            })


    }


    function getArtists() {
        axios.get(`http://localhost:8080/songs/artists/${id}`)
            .then(res => {
                setSong(prevState => ({
                    ...prevState,
                    artists: res.data.artists
                }))

            }).catch(err => console.log(err))

    }

    function playSong() {
        console.log(data);
        axios.post("http://localhost:8080/listeners", data)
            .then(res => {
                if (res.status == 200) {
                    setSong(prevState => ({
                        ...prevState,
                        listeners: song.listeners + 1,
                        yearlyListeners: song.yearlyListeners+1
                    }))
                }
            })


    }


    return (
        <>
            <p>Title: {song.title}</p>
            <p>Genre: {song.genre}</p>
            <p>Seconds: {song.seconds}</p>
            <p>Monthly listeners: {song.listeners}</p>
            <p>Yearly listeners: {song.yearlyListeners}</p>

            <button onClick={playSong}>Play song</button>
        </>
    )
}