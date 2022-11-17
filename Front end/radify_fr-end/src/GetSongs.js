import React, {useEffect, useState} from 'react';
import axios from "axios";


export default function GetSongs() {

    const auth_url = "https://accounts.spotify.com/authorize?client_id=c6141bab26dc40e99f6513735870c211&" +
            "redirect_uri=http://localhost:3000&response_type=token"

    const [token, setToken] = useState("")
    const [searchKey, setSearchKey] = useState("")
    //const [artists, setArtists] = useState([])
    const [tracks, setTracks] = useState([])

    useEffect(() => {

        let token = window.localStorage.getItem("token")
        const hash = window.location.hash

        if (!token && hash) {
            console.log('#3')
            token = hash.substring(1).split("&").find(elem => elem.startsWith("access_token")).split("=")[1]

            window.location.hash = ""
            window.localStorage.setItem("token", token)

        }

        console.log('#1', token)
        setToken(token)

    }, [])

    const logout = () => {
        setToken("")
        window.localStorage.removeItem("token")
    }

    const searchArtists = async (e) => {
        e.preventDefault()
        console.log('#2', token)
        const {data} = await axios.get("https://api.spotify.com/v1/search", {
            headers: {
                //Authorization: `Bearer BQBwrto7BstiZs54BmJgDiTZP2TvOC32405yRhXHUefQaYno26OUsoTwTRr6HNUuCGLG9PXW9C42bTjxIl05gM6kjr6Dzoa1sNvAwai0vcf0JrpS3Nz64B57qp73-quL2mBh5o6CA-buqNWrHTn9uCGoYvilB-NObVk8DdiSg9Ek9it_B3JOBn4Cvux_UmjCmZQ`
                Authorization: `Bearer ${token}`
            },
            params: {
                q: searchKey,
                //type: "artist"
                type: "track"

            }
        })

        // setArtists(data.artists.items)
        setTracks(data.tracks.items)

    }


    const renderArtists = () => {
        console.log(tracks);
        return tracks.map(artist => (
            //return artists.map(artist => (
            <div key={artist.id}>

                {artist.name}
            </div>
        ))
    }


    return (
        <div>
            {!token ?
                <div>
                    <a className="btn" href={auth_url}>Log in</a>
                </div>
                : <button onClick={logout}>Log out</button>}


            {token ?
                <form onSubmit={searchArtists}>
                    <input type="text" onChange={e => setSearchKey(e.target.value)}/>
                    <button type={"submit"}>Search</button>
                </form>

                : <h2>Please login</h2>
            }

            {renderArtists()}

        </div>

    )
}