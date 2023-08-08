import axios from 'axios';

const client = axios.create({
    baseURL: "http://localhost:8080/users" 
});

class UserService {

    async loginUser({email, password}){
        try {
            return client.get(`/userlogin/${email}/${password}`);


        } catch (error) {
            console.log(error);
        }
    }

    //post
    async  signupUser(data){
        try {
            return client.post("/adduser", data);

        } catch (error) {
            console.log(error);
        }
    }

    //put
    async updateUser({currentEmail, currentPassword, userData}){
        try {
            return client.put(`/updateuserdetails/${currentEmail}/${currentPassword}`, userData);

        } catch (error) {
            console.log(error);
        }
    }

    async getUserWatchList(id){
        try {
            return client.get(`/getuserwatchlist/${id}`);

        } catch (error) {
            console.log(error);
        }
    }

    async getUserReviews(id){
        try {
            return client.get(`/getallreviewsbyuser/${id}`);

        } catch (error) {
            console.log(error);
        }
    }

    async getUserRatings(id){
        try {
            return client.get(`/getallratingsbyuser/${id}`);
            
        } catch (error) {
            console.log(error);
        }
    }

    //delete
    async deleteUser({email, password}){
        try {
            return client.delete(`/deleteuser/${email}/${password}`);
            
        } catch (error) {
            console.log(error);
        }
    }
}

const userService = new UserService();
export default userService;