import Navbar from './Navbar.jsx';
import Login from './auth/Login.jsx';
import MovieList from './movie/MovieList.jsx';
import { useUserContext } from '../context/UserContext.jsx';

function Home() {

    // const {user, setUser} = useUserContext();
    const user = JSON.parse(sessionStorage.getItem("user"));

    if(!user){
        return(
            <Login />
        )
    }

    return(
        <MovieList />
    )
}

export default Home;