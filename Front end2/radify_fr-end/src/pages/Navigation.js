import React, {useEffect, useState} from "react";
import Homepage from "./Homepage";
import Playlists from "./Playlists";
import AuthPage from "./AuthPage";
import Layout from "./Layout";
import SongsPage from "./SongsPage";
import PlaylistInfo from "../PlaylistInfo"


import {BrowserRouter, Route, Routes} from "react-router-dom";

export default function Navigation() {


    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Homepage/>}/>
                    <Route path="playlists" element={<Playlists/>}/>
                    <Route path="login" element={<AuthPage/>}/>
                    <Route path="songs" element={<SongsPage/>}/>
                    <Route path="playlist/:id" element={<PlaylistInfo/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

