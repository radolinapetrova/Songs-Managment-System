import Navigation from "./pages/Navigation"
import React, {createContext, useEffect, useState} from 'react';


export default function App() {

    // const AuthContext = createContext(sessionStorage.getItem(''))

    return (
        // <AuthContext.Provider value={sessionStorage.getItem('token')}>
            <Navigation/>
        // </AuthContext.Provider>
    );
}

