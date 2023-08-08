import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import Home from './components/Home'
import Login from './components/auth/Login';
import Signup from './components/auth/Signup';
import MovieList from './components/movie/MovieList';
import Insights from './components/movie/Insights';
import UserProfile from './components/user/UserProfile';
import UserReviews from './components/user/UserReviews';
import UserWatchlist from './components/user/UserWatchlist';
import UserUpdate from "./components/user/UserUpdate";
import Error404 from './components/Error404';

function App() {

  return (
    <BrowserRouter>
    <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/movies" element={<MovieList />} />
        <Route path="/movie/insights/:id" element={<Insights />} />
        <Route path="/user/profile" element={<UserProfile />} />
        <Route path="/user/reviews" element={<UserReviews />} />
        <Route path="/user/watchlist" element={<UserWatchlist />} />
        <Route path="/user/update" element={<UserUpdate />} />
        <Route path="*" element={<Error404 />} />
    </Routes>
    </BrowserRouter>
  );
}

export default App;
