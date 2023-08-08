import { useRef, useState } from "react";
import { Modal } from "bootstrap";
import reviewService from "../../service/ReviewService";
import { toast } from 'react-toastify';

export default function AddReviewModal({movieId, userEmail}) {
    const modalRef = useRef()

    const [reviewBody, setReviewBody] = useState("");

    const AddReview = async () => {

      let response = null;

      const data = {movieId: movieId,
                    userEmail: userEmail,
                    body: reviewBody};
      
      await reviewService.addReview(data)
                      .then(res => {
                        response = res; 
                        toast.success("Review Added Successfully");
                        hideModal();
                        setTimeout(() => {
                          window.location.reload();
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
                              console.log('Error'+ error.message);
                          }
                      })
      if(!response) return;
    } 
    
    const showModal = () => {
        const modalEle = modalRef.current
        const bsModal = new Modal(modalEle, {
            backdrop: 'static',
            keyboard: false
        })
        bsModal.show()
    }
    
    const hideModal = () => {
        const modalEle = modalRef.current
        const bsModal= Modal.getInstance(modalEle)
        bsModal.hide()
    }
    
    return(
        <div className="add review">
            <button type="button" className="btn btn-success" onClick={showModal}>Add Review</button>
            <div className="modal fade" ref={modalRef} tabIndex="-1" >
             <div className="modal-dialog">
                <div className="modal-content">
                  <div className="modal-header">
                    <h5 className="modal-title" id="staticBackdropLabel">Enter your review</h5>
                    <button type="button" className="btn-close" onClick={hideModal} aria-label="Close"></button>
                  </div>
                  <div className="modal-body">
                    <textarea rows="5" cols="60" value={reviewBody} onChange={e => setReviewBody(e.target.value)}></textarea>
                  </div>
                  <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" onClick={hideModal}>Close</button>
                    <button type="button" className="btn btn-primary" onClick={AddReview}>Submit</button>
                  </div>
                </div>
              </div>
            </div>
        </div>
    )
}