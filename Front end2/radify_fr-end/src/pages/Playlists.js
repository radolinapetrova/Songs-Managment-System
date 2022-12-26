import React, {useEffect, useState} from "react";
import CreatePlaylist from "../CreatePlaylist";
import GetPlaylist from "../GetUserPlaylists";
 import "../css/Playlists.css"
import {useAuth} from "../auth/AuthProvider";

export default function Playlists() {

    const {auth} = useAuth();

    if (auth){

        return (
            <div className="playlists">
                <div><GetPlaylist/></div>
                <div className="createPlaylist"><CreatePlaylist/></div>
            </div>
        )
    }






}

