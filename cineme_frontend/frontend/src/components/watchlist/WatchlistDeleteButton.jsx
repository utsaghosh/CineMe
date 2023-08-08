import { Button } from "@mui/material"
import WatchlistDeleteIcon from "./WatchlistDeleteIcon"


export default function WatchlistDeleteButton({handleDelete}){
    return(
        <Button 
            startIcon={<WatchlistDeleteIcon />}
            size='20'
            onClick={handleDelete} 
        />
    )
}