import { Button } from "@mui/material";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';

export default function ReviewDeleteButton({handleDelete}){
    return(
        <Button 
            startIcon={<DeleteForeverIcon />}
            onClick={handleDelete}
        />
    )
}