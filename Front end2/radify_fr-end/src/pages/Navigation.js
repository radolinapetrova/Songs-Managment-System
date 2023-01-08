import React, {useContext, useEffect, useState} from "react";
import Homepage from "./Homepage";
import Playlists from "./Playlists";
import AuthPage from "./AuthPage";
import Layout from "./Layout";
import Songs from "./Songs";
import PlaylistInfo from "../PlaylistInfo"
import SongInfo from "../SongInfo";
import ChatRoom from "../ChatRoom";


import {BrowserRouter, Route, Routes} from "react-router-dom";


export default function Navigation() {



    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Homepage/>}/>
                    <Route path="playlists" element={<Playlists/>}/>
                    <Route path="login" element={<AuthPage/>}/>
                    <Route path="songs" element={<Songs/>}/>
                    <Route path="playlist/:id" element={<PlaylistInfo/>}/>
                    <Route path="song/:id" element={<SongInfo/>}/>
                    <Route path="chat" element={<ChatRoom/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

