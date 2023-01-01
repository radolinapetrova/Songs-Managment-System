import React, {useEffect, useState} from "react";
import GetPlaylist from "../GetUserPlaylists";
 import "../css/Playlist.css"
import {useAuth} from "../auth/AuthProvider";

export default function Playlists() {

    const {auth, claims} = useAuth();

    if (auth && claims.roles[0] != 'ADMIN'){

        return (
            <div className="playlists">
                <div><GetPlaylist/></div>
                {/*<div className="createPlaylist"><CreatePlaylist/></div>*/}
            </div>
        )
    }






}

