import React from "react";
import { useNavigate } from "react-router-dom";
import UserProfileButton from "./user/UserProfileButton";

export default function Navbar({search}) {

	const navigate = useNavigate();
    // const {user,setUser} = useUserContext();
    const user = JSON.parse(sessionStorage.getItem("user"));

    return(
        <nav className="navbar navbar-expand-lg navbar-light" style={{backgroundColor: "#152238"}}>
            <div className="container-fluid">

                <div className="ms-3" onClick={() => navigate("/")}>
                    <a className="navbar-brand me-auto order-1 order-md-0 text-light" href="#">CineMe</a>
                </div>

                <div className="order-3 ms-auto navbar-nav me-3" id="navbarSupportedContent" style={{right:'10%'}} onClick={() => {navigate("/user/profile")}}>
                    <UserProfileButton text={user.userName.charAt(0)} />
                </div>
            </div>  
        </nav>
    )
}