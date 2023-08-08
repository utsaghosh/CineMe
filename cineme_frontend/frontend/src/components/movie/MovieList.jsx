import React, { useEffect, useState } from 'react'
import MovieCard from './MovieCard.jsx'
import movieService from '../../service/MovieService.jsx';
import { toast } from 'react-toastify';
import Navbar from '../Navbar.jsx';

export default function MovieList() {

  const[movies, setMovies] = useState([]);
  const[search, setSearch] = useState("");

  useEffect(() => {
    if(search){
      getSearchedMovies();
    }
    else{
      getMovies();
    }
  },[search]);

  const getMovies = async () => {
    
    let response = null;

    await movieService.getAllMovies()
                      .then(res => {response = res; setMovies(response.data);})
                      .catch((error) => { // error is handled in catch block
                        if (error.response) { // status code out of the range of 2xx
                          let data = error.response.data;
                          let status = error.response.status;
                          toast.error(`Error ${status}: ${data.errorMessage}`);
                        } else if (error.request) { // The request was made but no response was received
                          console.log(error.request);
                        } else {// Error on setting up the request
                          console.log('Error'+ error.message);
                        }
                    })
    
    if (!response) return;
  };

  const getSearchedMovies = async () => {
    
    let response = null;

    await movieService.getMoviesMatchingName(search)
                      .then(res => {response = res; setMovies(response.data);})
                      .catch((error) => { // error is handled in catch block
                        if (error.response) { // status code out of the range of 2xx
                          let data = error.response.data;
                          let status = error.response.status;
                          toast.error(`Error ${status}: ${data.errorMessage}`);
                        } else if (error.request) { // The request was made but no response was received
                          console.log(error.request);
                        } else {// Error on setting up the request
                          console.log('Error'+ error.message);
                        }
                    })
    
    if (!response) return;
  }

  

  return (
    <>
      <Navbar/>
      <div className="d-flex justify-content-center mx-auto my-2">
                    
        <input className="form-control border border-success me-2" style={{width:'450px'}} type="text" placeholder="Search" value={search} onChange={e => setSearch(e.target.value)}/>
      </div>
      <div className='d-flex flex-column justify-content-center'>
        {movies.map(movie => (
          <MovieCard movie={movie} key={movie.movieId} />
        ))}
      </div>
    </>
  )
}