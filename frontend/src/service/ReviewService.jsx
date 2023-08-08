import axios from 'axios';

const client = axios.create({
    baseURL: "http://localhost:8080/reviews" 
});

class ReviewService {

    async addReview(review){
        try {
            return client.post("/addreview", review);
        } catch (error) {
            console.log(error);
        }
    }

    async updateReview({reviewId, review}){
        try {
            return client.put(`/updatereview/${reviewId}`, review);
        } catch (error) {
            console.log(error);
        }
    }

    async getReviewOfMovieByUser({movieId, userId}){
        try {
            return client.get(`/getreviewofmoviebyuser/${movieId}/${userId}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getAllReviewsOfMovie(movieid){
        try {
            return client.get(`/getallreviewsofmovie/${movieid}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getAllReviewsByUser(userid){
        try {
            return client.get(`/getallreviewsbyuser/${userid}`);
        } catch (error) {
            console.log(error);
        }
    }

    async deleteReview(reviewid){
        try {
            client.delete(`/deletereview/${reviewid}`);
        } catch (error) {
            console.log(error);
        }
    }
}

const reviewService = new ReviewService();
export default reviewService;