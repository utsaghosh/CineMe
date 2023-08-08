import { useNavigate } from "react-router-dom"

export default function Error404(){
    const navigate = useNavigate();
    return(
        <div>
        <h1 className="d-flex flex-column justify-content-center my-auto">Error 404</h1>
        <button className="text-primary my-4" onClick={() => navigate(-1)}>Go Back</button>
        </div>
    )
}