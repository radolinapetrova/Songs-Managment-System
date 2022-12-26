import CreateSong from "../CreateSong";
import {useAuth} from "../auth/AuthProvider";

const Songs = () =>{

    const {auth, claims} = useAuth();


    if(auth && claims.roles[0] == 'ADMIN'){
        return (
            <>
                <CreateSong/>
            </>
        )
    }

}

export default Songs