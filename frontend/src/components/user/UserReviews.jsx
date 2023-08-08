import { useEffect, useState } from "react";
import reviewService from "../../service/ReviewService";
import ReviewCard from "../review/ReviewCard";
import Navbar from "../Navbar";
import { toast } from "react-toastify";

export default function UserReviews() {

    const [userReviews, setUserReviews] = useState([]);
    
    // const {user, setUser} = useUserContext();
    
    const user = JSON.parse(sessionStorage.getItem("user"));
    const {userId, userName, email, password, gender, age} = user;

    useEffect(() => {
        getUserReviews();
    }, []);

    const getUserReviews = async () => {

      let response = null;

      await reviewService.getAllReviewsByUser(userId)
                      .then(res => {response = res; setUserReviews(response.data);})
                      .catch((error) => { // error is handled in catch block
                          if (error.response) { // status code out of the range of 2xx
                              let data = error.response.data;
                              toast.warn(data.errorMessage);
                          } else if (error.request) { // The request was made but no response was received
                          console.log(error.request);
                          } else {// Error on setting up the request
                          console.log('Error'+ error.message);
                          }
                      })
      
      if (!response) return;
    };

    return (
      <>
          <Navbar/>
          <div className='d-flex justify-content-center my-4'>
                <h5>Your Reviews:</h5>
          </div>
          <div className='d-flex justify-content-center my-1'>
            {userReviews.map(review => (
              <ReviewCard review={review} key={review.reviewId} editable={true} deletable={true}/>
            ))}
          </div>
      </>
    );
}