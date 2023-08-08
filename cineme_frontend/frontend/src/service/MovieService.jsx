import axios from 'axios';

const client = axios.create({
    baseURL: "http://localhost:8080/movies" 
});

class MovieService {

    async getAllMovies(){
        try {
            return client.get("/getallmovies");
        } catch (error) {
            console.log(error);
        }
    }

    async getAllRatingsOfMovie(id){
        try {
            return client.get(`/getallratingsofmovie/${id}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getAllReviewsOfMovie(id){
        try {
            return client.get(`/getallreviewsofmovie/${id}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getMovieDetails(id){
        try {
            return client.get(`/getmoviedetails/${id}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getMoviesByRelease({minyear, maxyear}){
        try {
            return client.get(`/getmoviesbyyearofrelease/${minyear}/${maxyear}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getMoviesMatchingName(name){
        try {
            return client.get(`/getmoviesmatchingname/${name}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getMoviesOfLanguage(language){
        try {
            return client.get(`/getmoviesoflanguage/${language}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getMoviesMinAvgRating(minavgrating){
        try {
            return client.get(`/getmovieswithminavgrating/${minavgrating}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getMoviesMinWatchlist(minwatchlist){
        try {
            return client.get(`/getmovieswithminwatchlistadditions/${minwatchlist}`);
        } catch (error) {
            console.log(error);
        }
    }

    async addMovie(data){
        try {
            return axios.post("/addmovie", data);
        } catch (error) {
            console.log(error);
        }
    }

}

const movieService = new MovieService();
export default movieService;