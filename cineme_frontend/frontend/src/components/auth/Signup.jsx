import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import './auth.css';
import dayjs from 'dayjs';
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import userService from "../../service/UserService";
import { toast } from 'react-toastify';

export default function Signup() {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("");
    const [dob, setDOB] = useState(dayjs());
    const [password, setPassword] = useState("");
    const [formErrors, setFormErrors] = useState({});

    const navigate = useNavigate();

    const validateInput = (values) => {
        const { username, email, password } = values;
        const errors = {};

        if(!username) errors.username = "Username is required";
        if(!username.trim()) errors.username = "Username cannot be only whitespace";
        if(username.length > 50) errors.username = "Username cannot longer than 50 characters";

        const emailRegex =
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if (!email) errors.email = "Email is required";
        else if (!emailRegex.test(email)) errors.email = "Please enter a valid email";

        if (!password) errors.password = "Password is required";

        setFormErrors(errors);
        if (Object.keys(errors).length > 0) return false;
        return true;
    };

    // signup handler
    const handleSignup = async (e) => {
        e.preventDefault();
        const data = { userName: username,
                        email: email,
                        password: password,
                        gender: gender,
                        dateOfBirth: dob.format('DD/MM/YYYY')
                     };

        if (!validateInput({ username, email, password })) return;

        let response = null;

        await userService.signupUser(data)
                        .then(res => {
                            response = res;
                            let user = response.data;

                            if(user){

                                toast.success('User Signup Successful !');

                                setTimeout(() => {
                                    navigate("/login");
                                }, 2000)
                            }
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
                        });

        if (!response) return;
    };

    
    return(
    <section className="background-radial-gradient overflow-hidden">

        <div className="container px-4 py-5 px-md-5 text-center text-lg-start my-5">
            <div className="row gx-lg-5 align-items-center mb-5">
            <div className="col-lg-6 mb-5 mb-lg-0" style={{zIndex: "10"}}>
                 <img
                            src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
                            className="img-fluid"
                            alt="Sample image"
                />
            </div>

            <div className="col-lg-6 mb-5 mb-lg-0 position-relative">
                <div id="radius-shape-1" className="position-absolute rounded-circle shadow-5-strong"></div>
                <div id="radius-shape-2" className="position-absolute shadow-5-strong"></div>

                <div className="card bg-glass">
                <div className="card-body px-4 py-5 px-md-5">
                    <form onSubmit={handleSignup}>
                        
                        <div className="display-6 fw-bold mb-3 pb-3 d-flex justify-content-center" style={{fontFamily: "sans-serif"}}>Signup</div>
                        
                        {/* <!-- UserName input --> */}
                        <div className="form-outline mb-4">
                            <input type="text" id="username" className="form-control"  placeholder="Name"
                                value = {username}
                                onChange={e => setUsername(e.target.value)}
                                required
                            />
                        </div>

                        {/* <!-- Email input --> */}
                        <div className="form-outline mb-4">
                            <input type="email" id="email" className="form-control"  placeholder="Email address" 
                                value = {email}
                                onChange = {e => setEmail(e.target.value)}
                                required
                            />
                            <p className="text-danger fw-bold mt-2 pt-1 mb-0">{formErrors.email}</p>
                        </div>

                        {/* <!-- Gender input --> */}
                        <div className="form-outline mb-4">
                            <label>
                                Select your Gender

                                <select className="form-control mt-2" value = {gender} onChange = {e => setGender(e.target.value)}>
                                    <option value hidden>select</option>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                    <option value="OTHER">Other</option>
                                </select>
                            </label>
                        </div>

                        {/* <!-- DOB input --> */}
                        <div className="form-outline mb-4">
                            <LocalizationProvider dateAdapter={AdapterDayjs}>
                                <DatePicker 
                                    label = "Date of Birth"
                                    format = "DD/MM/YYYY"
                                    value = {dob}
                                    onChange = {newval => setDOB(newval)}
                                />
                            </LocalizationProvider>
                        </div>



                        {/* <!-- Password input --> */}
                        <div className="form-outline mb-4">
                            <input type="password" id="password" className="form-control" placeholder="Create Password"
                                value = {password}
                                onChange={e => setPassword(e.target.value)}
                                required
                            />
                        </div>

                        {/* <!-- Submit button --> */}
                        <div className="d-flex justify-content-center">
                            <button type="submit" className="btn btn-primary btn-block mb-4">
                                Sign up
                            </button>
                        </div>
                    </form>
                </div>
                </div>
            </div>
            </div>
        </div>
    </section>
    )
}