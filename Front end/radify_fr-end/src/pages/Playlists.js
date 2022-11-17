import React from "react";
import CreatePlaylist from "../CreatePlaylist";
import GetPlaylist from "../GetPlaylists";

export default function Playlists ()  {
    return (
        <div>
            <GetPlaylist/>
            <CreatePlaylist/>
        </div>
    )

}

