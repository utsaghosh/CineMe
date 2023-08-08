import axios from 'axios';

const client = axios.create({
    baseURL: "http://localhost:8080/watchlist" 
});

class WatchlistService {

    async addToWatchlist(data){
        try {
            return client.post("/addtowatchlist", data);
        } catch (error) {
            console.log(error);
        }
    }

    async getUserWatchlist(id){
        try {
            return client.get(`/getuserwatchlist/${id}`);
        } catch (error) {
            console.log(error);
        }
    }

    async getWatchlistEntryId({movieid, userid}){
        try {
            return client.get(`/getwatchlistentryid/${movieid}/${userid}`);
        } catch (error) {
            console.log(error);
        }
    }

    async checkInUserWatchlist({movieid, userid}){
        try {
            return client.get(`/checkmovieinuserwatchlist/${movieid}/${userid}`);
        } catch (error) {
            console.log(error);
        }
    }

    async deleteFromWatchlist(entryid){
        try {
            return client.delete(`/deletewatchlistentry/${entryid}`);
        } catch (error) {
            console.log(error);
        }
    }
}

const watchlistService = new WatchlistService();
export default watchlistService;