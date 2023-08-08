import React, { useRef, useState } from 'react'
import "./reviewCard.css"
import EditNoteIcon from '@mui/icons-material/EditNote';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import Button from '@mui/material/Button';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import reviewService from '../../service/ReviewService';
import { Modal } from "bootstrap";


export default function ReviewCard({review, editable, deletable}) {
  
    const modalRef = useRef()
    const {reviewId, movieId, movieName, userId, userName, creationDate, body} = review;
    // const {user,setUser} = useUserContext();
    const user = JSON.parse(sessionStorage.getItem("user"));
    const navigate = useNavigate();


  const [reviewUpdate, setReviewUpdate] = useState();

    const ReviewDelete = async () => {

        let response = null;
        
        await reviewService.deleteReview(reviewId)
                            .then(res => {
                                response = res; 
                                toast.success("Review Deleted");
                                setTimeout(() => {
                                    window.location.reload(true);
                                }, 200);
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
    }

    const ReviewUpdate = async () => {

        let response = null;
        const review = {movieId: movieId,
                        userEmail: user.email,
                        body: reviewUpdate};

        const data = {reviewId, review}
        
        await reviewService.updateReview(data)
                            .then(res => {
                                response = res; 
                                toast.success("Review Updated");
                                hideModal();
                                setTimeout(() => {
                                    window.location.reload(true);
                                }, 1000);
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

    const showModal = () => {
        const modalEle = modalRef.current
        const bsModal = new Modal(modalEle, {
            backdrop: 'static',
            keyboard: false
        })
        bsModal.show()
    };
    
    const hideModal = () => {
        const modalEle = modalRef.current
        const bsModal= Modal.getInstance(modalEle)
        bsModal.hide()
    };
  
  
  
  return (
        <div className='cardd'>
            <div className='upper'>
                
                <div className='name'>
                   <p>By {userName}
                        <div className='d-flex'>
                            for
                            <div className='link-info ms-1' onClick={() => navigate(`/movie/insights/${movieId}`)}>
                            {movieName}
                            </div>
                        </div>
                        on {creationDate.substring(0,10)}
                    </p>
                </div>

            </div>
            <hr className='divider'></hr>
            <div className='rev'>
                <p>
                    {body}
                </p>
                <div className='d-flex justify-content-center'>
                    <div>
                        {editable && 
                        <div>
                            <Button 
                                startIcon={<EditNoteIcon />}
                                onClick={showModal}
                            />
                            <div className="modal fade" ref={modalRef} tabIndex="-1" >
                                <div className="modal-dialog">
                                    <div className="modal-content">
                                    <div className="modal-header">
                                        <h5 className="modal-title" id="staticBackdropLabel">Enter your review</h5>
                                        <button type="button" className="btn-close" onClick={hideModal} aria-label="Close"></button>
                                    </div>
                                    <div className="modal-body">
                                        <textarea rows="5" cols="60" value={reviewUpdate} onChange={e => setReviewUpdate(e.target.value)}></textarea>
                                    </div>
                                    <div className="modal-footer">
                                        <button type="button" className="btn btn-secondary" onClick={hideModal}>Close</button>
                                        <button type="button" className="btn btn-primary" onClick={ReviewUpdate}>Submit</button>
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        }
                    </div>
                    <div>
                        {deletable && 
                        <Button 
                            startIcon={<DeleteForeverIcon />}
                            onClick={ReviewDelete}
                        />
                        }
                    </div>
                </div>
            </div>   
        </div>

  )
}