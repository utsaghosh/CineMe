import React, { useEffect, useState } from 'react'
import StarRoundedIcon from '@mui/icons-material/StarRounded';
import InfoOutlinedIcon from '@mui/icons-material/InfoOutlined';
import WatchlistDeleteIcon from './WatchlistDeleteIcon';
import watchlistService from '../../service/WatchlistService';
import movieService from '../../service/MovieService';

import Button from '@mui/material/Button';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';

export default function WatchlistCard({entry, deleteButton}) {

    const [movie, setMovie] = useState({});

    const entryId = entry.entryId;
    const movieidlocal = entry.movieId;
    const { movieId, movieName, movieLang, synopsis, poster, director, duration, releaseDate, avgRating, totalWatchlistAdd} = movie;

    const navigate = useNavigate();

    useEffect(() => {
        getMovie();
    }, []);

    const getMovie = async () => {
    
        let response = null;

        await movieService.getMovieDetails(movieidlocal)
                          .then(res => {response = res; setMovie(response.data);})
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
    

  return (
    <div className='d-flex flex-row justify-content-center'>
        <div className='container d-flex flex-row border border-2 border-dark rounded-2 m-1 p-1 position-relative text-white' style={{backgroundColor:'#1c2e4a', width:'50%', height:'10%'}}>

            <img src={poster} alt="movie poster" style={{width:'100px'}}></img>

            <div className='ms-2 my-auto p-2'>
                <h3 className='ms-3'>{movieName}</h3> 
                <span className='ms-3'>{releaseDate?.substring(0,4)}</span>
                <span className='ms-3'>{movieLang}</span>
                <span className='ms-3'>{Math.floor(Number(duration)/60)}h {Number(duration)%60 !== 0 && (Number(duration)%60 + "min")}</span>
                <div className='ms-3 mt-2'>
                    <span className='mt-3'>
                        <StarRoundedIcon style={{color:'yellow'}} />
                    </span>
                    <span className='ms-1 mt-4'>{avgRating}</span>
                </div>
            </div>

            <div className='position-absolute' style={{top:'25%', right:'10%'}}>
                {deleteButton}
            </div>

            <div className='position-absolute' style={{top:'50%', right:'10%'}}>
                <Button 
                    startIcon={<InfoOutlinedIcon />}
                    size='20'
                    onClick={() => navigate(`/movie/insights/${movieId}`)} 
                />
            </div>

        </div>
    </div>
  );
}