import axios from 'axios';

const client = axios.create({
    baseURL: "http://localhost:8080/ratings" 
});

class RatingService {

    async addRating(rating){
        try {
            return client.post("/addrating", rating);
        } catch (error) {
            console.log(error);
        }
    }

    async updateRating({ratingid, rating}){
        try {
            return client.put(`/updaterating/${ratingid}`, rating);
        } catch (error) {
            console.log(error);
        }
    }

    async getRatingOfMovieByUser({movieId, userId}){
        try {
            return client.get(`/getratingofmoviebyuser/${movieId}/${userId}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getAllRatingsOfMovie(movieid){
        try {
            return client.get(`/getallratingsofmovie/${movieid}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getAllRatingsByUser(userid){
        try {
            return client.get(`/getallratingsbyuser/${userid}`);
        } catch (error) {
            console.log(error);
        }
    }

    async deleterating(ratingid){
        try {
            client.delete(`/deleterating/${ratingid}`);
        } catch (error) {
            console.log(error);
        }
    }
}

const ratingService = new RatingService();
export default ratingService;