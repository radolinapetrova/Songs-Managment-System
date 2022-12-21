import React, {useEffect, useState} from "react";
import CreatePlaylist from "../CreatePlaylist";
import GetPlaylist from "../GetPlaylists";
import decode from "jwt-claims";
// import "../Playlists.css"

export default function Playlists() {

    const [isAuthorized, setIsAuthorized] = useState(false)
    var decode = require('jwt-claims');

    useEffect(() => {

        const token = window.sessionStorage.getItem('token');

        if (token) {
            const claims = decode(token);
            if(claims.roles == "USER")
            {
                setIsAuthorized(true)
            }

        }
    })

    if (isAuthorized){
        return (
            <div className="playlists">
                <div><GetPlaylist/></div>
                <div><CreatePlaylist/></div>
            </div>
        )
    }






}

