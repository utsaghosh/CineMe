import React, { useState } from "react";
import './auth.css';
import { Link, useNavigate } from "react-router-dom";
import userService from "../../service/UserService";
import { toast } from 'react-toastify';

export default function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [formErrors, setFormErrors] = useState({});

    const navigate = useNavigate();

    const validateInput = (values) => {
        const { email, password } = values;
        const errors = {};
        const emailRegex =
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|.(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

        if (!email) errors.email = "Email is required";
        else if (!emailRegex.test(email)) errors.email = "Please enter a valid email";
        if (!password) errors.password = "Password is required";

        setFormErrors(errors);
        if (Object.keys(errors).length > 0) return false;
        return true;
    };

    // login handler
    const handleLogin = async (e) => {
        e.preventDefault();
        const data = { email, password };

        if (!validateInput({ email, password })) return;

        let response = null;

        await userService.loginUser(data)
                        .then(res => {
                            response = res;
                            let loggedinuser = response.data;

                            if(loggedinuser){

                                sessionStorage.setItem("user", JSON.stringify(loggedinuser));
                                // setUser(loggedinuser);

                                toast.success('User Login Successful !');

                                setTimeout(() => {
                                    navigate("/movies");
                                }, 1000);
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
        <section className="vh-100 background-radial-gradient overflow-hidden">
    
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
                        <form onSubmit={handleLogin} autoComplete="on">
                            
                            <div className="display-6 fw-bold mb-3 pb-3 d-flex justify-content-center" style={{fontFamily: "sans-serif"}}>Login</div>
    
                            {/* <!-- Email input --> */}
                            <div className="form-outline mb-4">
                                <input type="email" id="email" className="form-control"  placeholder="Email address" value = {email}
                                    onChange = {e => setEmail(e.target.value)} required/>
                                <p className="text-danger fw-bold mt-2 pt-1 mb-0">{formErrors.email}</p>
                            </div>
    
                            {/* <!-- Password input --> */}
                            <div className="form-outline mb-4">
                                <input type="password" id="password" className="form-control" placeholder="Password" value = {password}
                                    onChange = {e => setPassword(e.target.value)} required/>
                                <p className="text-danger fw-bold mt-2 pt-1 mb-0">{formErrors.password}</p>
                            </div>
    
                            {/* <!-- Submit button --> */}
                            <div className="d-flex justify-content-center">
                                <button type="submit" className="btn btn-primary btn-block mb-4">
                                    Login
                                </button>
                            </div>

                            <div className="container d-flex justify-content-center">
                                <p className="small fw-bold mt-2 pt-1 mb-0"> Don't have an account?
                                    <Link to="/signup" className="link-info ms-1">
                                        Signup
                                    </Link>
                                </p>
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