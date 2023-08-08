import React from 'react'
import "./userProfile.css"
import EditIcon from '@mui/icons-material/Edit';
import {useUserContext} from '../../context/UserContext';
import { useNavigate } from 'react-router-dom';
import { Button } from '@mui/material';
import Navbar from '../Navbar';
import { toast } from 'react-toastify';


export default function UserProfile() {
 
  // const {user, setUser} = useUserContext();
  const user = JSON.parse(sessionStorage.getItem("user"));
  const {userId, userName, email, password, gender, age} = user;
  const navigate = useNavigate();

  const handleLogout = () => {
    sessionStorage.clear();
    toast.success("Logged out successfully");
    setTimeout(() => {
      navigate("/");
    }, 1000)
  };

  return (
    <div>
      <Navbar/>
        <div className="container py-5 h-100">
          <div className="row d-flex justify-content-center align-items-center h-100">
            <div className="col col-lg-8 mb-4 mb-lg-0">
              <div className="card mb-3" style={{borderRadius: ".5rem"}}>
                <div className="row g-0">
                  <div className="col-md-4 gradient-custom text-center text-dark d-flex flex-column justify-content-center"
                    style={{borderTopLeftRadius: ".5rem", borderBottomLeftRadius: ".5rem"}}>
                  
                    <h5>{userName}</h5>
                    <div>
                      <Button 
                        startIcon={<EditIcon/>}
                        onClick={() => navigate("/user/update")}
                      />
                    </div>
                  </div>
                  <div className="col-md-8 rounded-4" style={{backgroundColor: "#fafaed"}}>
                    <div className="card-body p-4">
                      <div className='d-flex justify-content-center'>
                        <h6>User Information</h6>
                      </div>
                      <hr className="mt-0 mb-4"/>
                      <div className="row pt-1">
                        <div className="col-6 mb-3">
                          <h6>Email</h6>
                          <p className="text-muted">{email}</p>
                        </div>
                        <div className="col-6 mb-3">
                          <h6>Age</h6>
                          <p className="text-muted">{age}</p>
                        </div>
                        <div className="col-6 mb-3">
                          <h6>Gender</h6>
                          <p className="text-muted">{gender}</p>
                        </div>
                      </div>
                      <hr className="mt-0 mb-4"/>

                      
                      <div className="d-flex justify-content-end">
                      <button type="button" className="btn btn-success m-2" onClick={() => {navigate("/user/reviews")}}>My Reviews</button>
                      <button type="button" className="btn btn-success m-2" onClick={() => {navigate("/user/watchlist")}}>My Watchlist</button>
                      <button type="button" className="btn btn-danger m-2" onClick={handleLogout}>Logout</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
  )
}