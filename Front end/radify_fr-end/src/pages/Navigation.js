import React from "react";
import Homepage from "./Homepage";
import Playlists from "./Playlists";
import Account from "./Account";
import Layout from "./Layout";


import {BrowserRouter, Route, Routes} from "react-router-dom";

export default function Navigation() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout/>}>
                    <Route index element={<Homepage/>}/>
                    <Route path="playlists" element={<Playlists/>} />
                    <Route path="account" element={<Account/>}/>
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

