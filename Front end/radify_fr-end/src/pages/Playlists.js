import React, {useEffect, useState} from "react";
import CreatePlaylist from "../CreatePlaylist";
import GetPlaylist from "../GetPlaylists";

export default function Playlists() {

    const [isAuthorized, setIsAuthorized] = useState(false)


    useEffect(() => {
        if (window.sessionStorage.getItem('token')) {
            setIsAuthorized(true)
        }
    })


    // if (isAuthorized){
        return (
            <div>
                <GetPlaylist/>
                <CreatePlaylist/>
            </div>
        )
    // }
    // else{
    //     return <p>Log in first :)</p>
    // }



}

