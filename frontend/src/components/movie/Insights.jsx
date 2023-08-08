import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import movieService from '../../service/MovieService';
import reviewService from '../../service/ReviewService';
import { toast } from 'react-toastify';
import ReviewCard from '../review/ReviewCard';
import { useUserContext } from '../../context/UserContext';
import AddReviewModal from '../review/AddReviewModal';
import ratingService from '../../service/RatingService';
import Rating from "@mui/material/Rating";
import Navbar from '../Navbar';
import ReviewDeleteButton from '../review/ReviewDeleteButton';


export default function Insights() {

    const {id} = useParams();
    // const {user,setUser} = useUserContext();
    const user = JSON.parse(sessionStorage.getItem("user"));

    const [movie, setMovie] = useState({});
    const [userReview, setUserReview] = useState({});
    const [userRating, setUserRating] = useState({});
    const [allReviews, setAllReviews] = useState([]);

    const [tempScore, setTempScore] = useState(0);
    const { movieId, movieName, movieLang, synopsis, poster, director, duration, releaseDate, avgRating, totalWatchlistAdd} = movie;

    useEffect(() => {
        getMovie();
        getAllReviews();
        getUserReview();
        getUserRating();
    }, []);

    const getMovie = async () => {
    
        let response = null;

        await movieService.getMovieDetails(id)
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

    const getAllReviews = async () => {
        
        let response = null;
        
        await movieService.getAllReviewsOfMovie(id)
                        .then(res => {response = res; setAllReviews(response.data);})
                        .catch((error) => { // error is handled in catch block
                            if (error.response) { // status code out of the range of 2xx
                                console.log(error.response);
                            } else if (error.request) { // The request was made but no response was received
                                console.log(error.request);
                            } else {// Error on setting up the request
                                console.log('Error'+ error.message);
                            }
                        })
        if(!response) return;
    };

    const getUserReview = async () => {
        
        let response = null;
        const userId = user.userId;
        const movieId = id;
        const data = {movieId, userId};
        
        await reviewService.getReviewOfMovieByUser(data)
                        .then(res => {response = res; setUserReview(response.data);})
                        .catch((error) => { // error is handled in catch block
                            if (error.response) { // status code out of the range of 2xx
                                console.log(error.response);
                            } else if (error.request) { // The request was made but no response was received
                                console.log(error.request);
                            } else {// Error on setting up the request
                                console.log('Error'+ error.message);
                            }
                        })
        if(!response) return;
    };

    const getUserRating = async () => {
        
        let response = null;
        const userId = user.userId;
        const movieId = id;
        const data = {movieId, userId};
        
        await ratingService.getRatingOfMovieByUser(data)
                        .then(res => {response = res; setUserRating(response.data); setTempScore(response.data.score);})
                        .catch((error) => { // error is handled in catch block
                            if (error.response) { // status code out of the range of 2xx
                                console.log(error.response);
                            } else if (error.request) { // The request was made but no response was received
                                console.log(error.request);
                            } else {// Error on setting up the request
                                console.log('Error'+ error.message);
                            }
                        })
        if(!response) return;
    };
    
    const AddRating = async () => {
        
        let response = null;
        const userEmail = user.email;
        const movieId = id;
        const score = tempScore;
        const data = {movieId: movieId,
                        userEmail: userEmail,
                        score: score};
        
        await ratingService.addRating(data)
                        .then(res => {
                            response = res; 
                            toast.success("Rating added successfully");
                            setTempScore(response.data.score);
                            setUserRating(response.data);
                        })
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
        if(!response) return;
    };

    const UpdateRating = async () => {
        
        let response = null;
        const ratingid = userRating.ratingId;
        const userEmail = user.email;
        const movieId = id;
        const score = tempScore;
        const rating = {movieId: movieId,
                        userEmail: userEmail,
                        score: score};
        const data = {ratingid, rating};
        
        await ratingService.updateRating(data)
                        .then(res => {
                            response = res; 
                            toast.success("Rating updated successfully");
                            setTempScore(response.data.score);
                            setUserRating(response.data);
                        })
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
        if(!response) return;
    };

    return (
        <>
        <Navbar search={false} />
        <div className="card mb-3 ms-5 mt-5" style={{maxWidth: "88rem", backgroundColor: "#fafaed"}}>
            <div className="row g-0">
                <div className="col-md-4">
                    <img src={poster} className="img-fluid rounded-start" alt="movie poster"/>
                </div>
                <div className="col-md-8">
                <div className="card-body m-5">
                    <h1 className="card-title">{movieName}</h1>
                    <div className='details m-3 mt-5'>
                        <p className="card-text fw-bold mb-4">Release: <span className="text-muted ms-1">{releaseDate}</span></p>
                        <p className="card-text fw-bold mb-4">Language: <span className="text-muted ms-1">{movieLang}</span></p>
                        <p className="card-text fw-bold mb-4">Duration: <span className="text-muted ms-1">{Math.floor(Number(duration)/60)}h {Number(duration)%60 !== 0 && (Number(duration)%60 + "min")}</span></p>
                        <p className="card-text fw-bold mb-4">Director: <span className="text-muted ms-1">{director}</span></p>
                        <p className="card-text fw-bold mb-4">Average rating: <span className="text-muted ms-1">{avgRating}/10 </span></p>
                        <p className="card-text fw-bold mb-4">Watchlist additions: <span className="text-muted ms-1">{totalWatchlistAdd}</span></p>
                    </div>
                    
                    <p className="card-text fw-bold mt-5">
                        {synopsis}
                    </p>
                </div>
                </div>
            </div>
        </div>

        {Object.keys(userRating).length > 0 ?
            (
                <div className='d-flex justify-content-center my-4'>
                    <div>
                        <h5>Your Rating:</h5>
                    </div>
                    <div className='d-flex mx-4'>
                        <div>
                            <Rating
                                name="simple-controlled"
                                value={tempScore}
                                onChange={(event, newValue) => {
                                setTempScore(newValue);
                                }}
                            />
                        </div>
                        <div className='mx-4'>
                            <button className='btn btn-success btn-sm' onClick={UpdateRating}>Update rating</button>
                        </div>
                    </div>
                </div>
            ) :
            (
                <div className='d-flex justify-content-center my-4'>
                    <div>
                        <h5>You haven't rated this movie yet:</h5>
                    </div>
                    <div className='d-flex mx-4'>
                        <div>
                            <Rating
                                name="simple-controlled"
                                value={tempScore}
                                onChange={(event, newValue) => {
                                setTempScore(newValue);
                                }}
                            />
                        </div >
                        <div className='mx-4'>
                            <button className='btn btn-success btn-sm' onClick={AddRating}>Add rating</button>
                        </div>
                    </div>
                </div>
            )

        }

        {Object.keys(userReview).length > 0  ?
            (
            <div className='my-3'>
                <div className='ms-5'>
                    <h5>Your Review:</h5>
                </div>
                <div className='my-2 ms-5'>
                    <ReviewCard review={userReview} editable={true} deletable={true}/>
                </div>
            </div>
            ) :
            (
                <div className='my-3'>
                    <div className='ms-5'>
                        <h5>You haven't reviewed this movie yet</h5>
                    </div>
                    <div className='my-2 ms-5'>
                        <AddReviewModal movieId={id} userEmail={user.email} />
                    </div>
                </div>
            )
        }

        {allReviews.length > 0 ?
            (
                <div className='my-5'>
                    <div className='ms-5'>
                        <h5>All Reviews:</h5>
                    </div>
                    <div className='d-flex ms-5 my-2'>
                        {allReviews.map(review => (
                            <ReviewCard review={review} key={review.reviewId} editable={false} deletable={false}/>
                        ))}
                    </div>
                </div>
            ) :
            (
                <div className='ms-5'>
                    <h5>No reviews for this movie yet</h5>
                </div>
            )
        }
    </>
  )
}