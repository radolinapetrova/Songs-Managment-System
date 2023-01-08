import React, {useEffect, useState} from 'react';
import {useParams, useNavigate, Link} from "react-router-dom";
import axios from 'axios';
import "./css/Playlist.css";
import {useAuth} from "./auth/AuthProvider";
import Dialog from "./Dialog";

export default function PlaylistInfo() {

    const {claims} = useAuth();
    let {id} = useParams();

    const [dialog, setDialog] = useState({
        message: "Are you sure you want to delete this playlist?",
        isLoading: false
    })
    const data = ({
        userId: null,
        playlistId: id,
        songId: null
    })

    const [access, setAccess] = useState()

    const [playlist, setPlaylist] = useState({
        id: 0,
        title: "",
        dateOfCreation: "",
        songs: [],
        creator: "",
        public: null
    });

    const navigate = useNavigate("");


    useEffect(() => {
        getPlaylist()
        getPlaylistSongs()
    }, [])


    function getPlaylist() {
        const token = window.sessionStorage.getItem('token');

        axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}


        axios.get(`http://localhost:8080/playlists/${id}`)
            .then(res => {
                if(res.data.public){
                    setAccess("public");
                }
                else{
                    setAccess("private");
                }
                setPlaylist(prevState => ({
                    ...prevState,
                    id: res.data.id,
                    title: res.data.title,
                    dateOfCreation: res.data.dateOfCreation,
                    creator: res.data.creator,
                    public: res.data.public

                }))

            }).catch(err => console.log(err))


    }

    function getSongs() {
        axios.get(`http://localhost:8080/songs/playlist/${id}`)
            .then(res => {
                setPlaylist(prevState => ({
                    ...prevState,
                    songs: res.data

                }))

            }).catch(err => console.log(err))
    }

    function getPlaylistSongs() {
        if (sessionStorage.getItem('song')) {

            data.songId = sessionStorage.getItem('song')
            data.userId = claims.id
            try {
                axios.put("http://localhost:8080/playlists", data).then
                (getSongs)
                sessionStorage.removeItem('song')
            } catch (err) {
                if (err.response.status === 401) {
                    alert("why again????")
                }
            }

        } else {
            getSongs()
        }
    }


    const mapSongs = () => {

        return (
            <div className="group">
                {playlist.songs.map((song) => (
                    <div key={song.id} className="single">
                        <Link to={"/song/" + song.id} className="title">{song.title}</Link>
                        <button value={song.id} onClick={deleteSong} className="button">-</button>
                    </div>
                ))}
            </div>
        )
    }

    const deleteSong = async (e) => {
        e.preventDefault()
        data.songId = e.target.value
        data.userId = claims.id
        console.log(data)
        axios.put('http://localhost:8080/playlists/remove', data)
            .then(getSongs)


        return (
            <div className="playlistsInfo">
                {getPlaylistSongs()}
                {mapSongs()}
            </div>
        )
    }

    const changeAccess = async (e) => {
        // const token = window.sessionStorage.getItem('token');
        // axios.defaults.headers.common = {'Authorization': `Bearer ${token}`}
        // e.preventDefault()
        // let changed = !playlist.public;
        // setPlaylist(prevState => ({
        //     ...prevState,
        //     public: true}))
        // console.log("Opsaa", playlist.public)
        // const dat = ({playlist: playlist.id, user: claims.id, isPublic: playlist.public})
        // console.log(dat)
        // axios.put('http://localhost:8080/playlists/details', dat)
        //     .then( res => console.log(res))
    }


    function getPlaylistInfo() {
        console.log(access)
        return (
            <div className="details">
                <p>Title: {playlist.title}</p>
                <p>Date: {playlist.dateOfCreation.substring(0, 10)}</p>
                <p>Access: {access}</p>
                <button onClick={(e) => setDialog(prevState => ({...prevState, isLoading: true}))}
                        className="deletePlaylist">Delete playlist</button>
                <button className="deletePlaylist" onClick={changeAccess}>Change access</button>

            </div>

        )
    }


    const deletePlaylist = async () => {


        if (claims.id == playlist.creator.id) {
            try {

                axios.delete('http://localhost:8080/playlists', {data: {userId: claims.id, playlistId: id}})
                    .then(res => {
                        if (res.status === 200){
                            navigate("/playlists")
                        }
                    })


            } catch (err) {
                console.log(err.message)
            }


        } else {
            alert("No..");
        }

    }


    const confirmDeletion = (choose) => {
        if (choose) {
            deletePlaylist();
        }
        setDialog(prevState => ({...prevState, isLoading: false}))
    };

    return (
        <div className="playlistInfo">
            {getPlaylistInfo()}
            {mapSongs()}
            {dialog.isLoading && <Dialog message={dialog.message} onDialog={confirmDeletion}/>}
        </div>
    )

}