import React, {useEffect, useState} from 'react';
import {useSearchParams, Link} from "react-router-dom";
import axios from 'axios';
import "./Playlist.css"

export default function PlaylistInfo() {

    const [searchParams, setSearchParams] = useSearchParams();
    searchParams.get("id")
    return (
        <>
            <p>Playlist info</p>
            <p>Number {searchParams}</p>
        </>
    )

}