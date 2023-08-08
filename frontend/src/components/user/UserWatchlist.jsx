import { useEffect, useState } from "react";
import watchlistService from '../../service/WatchlistService';
import WatchlistCard from "../watchlist/WatchlistCard";
import { useUserContext } from "../../context/UserContext";
import Navbar from "../Navbar";
import { toast } from 'react-toastify';
import WatchlistDeleteButton from "../watchlist/WatchlistDeleteButton";


export default function UserWatchlist() {

    const[watchlist, setWatchlist] = useState([]);
    const[change, setChange] = useState(true);

    // const {user, setUser} = useUserContext();

    const user = JSON.parse(sessionStorage.getItem("user"));
    const {userId, userName, email, password, gender, age} = user;

    useEffect(() => {
        getUserWatchlist();
    }, [change]);

    const getUserWatchlist = async () => {

        let response = null;

        await watchlistService.getUserWatchlist(userId)
                        .then(res => {response = res; setWatchlist(response.data);})
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

    const WatchListDelete = async (entryId) => {

      let response = null;
      
      await watchlistService.deleteFromWatchlist(entryId)
                          .then(res => {
                              response = res; 
                              toast.success("Deleted from watchlist");
                              setChange(!change);
                          })
                          .catch((error) => { // error is handled in catch block
                              if (error.response) { // status code out of the range of 2xx
                              let data = error.response.data;
                              let status = error.response.status;
                              toast.error(`Error ${status}: ${data.errorMessage}`);
                              } else if (error.request) { // The request was made but no response was received
                              console.log(error.request);
                              } else {// Error on setting up the request
                              console.log('Error', error.message);
                              }
                          })

      if (!response) return;
  };

    return (
      <>
          <Navbar/>
          <div className='d-flex justify-content-center my-4'>
              <h5>Your Watchlist:</h5>
          </div>
          <div className='d-flex flex-column justify-content-center my-1'>
            {watchlist.map(entry => (
              <WatchlistCard entry={entry} key={entry.entryId} deleteButton={<WatchlistDeleteButton handleDelete={() => WatchListDelete(entry.entryId)}/>}/>
            ))}
          </div>
      </>
    )
}