import Avatar from "@mui/material/Avatar";
import { teal } from "@mui/material/colors";

export default function UserProfileButton({text}){

    return(
        <Avatar sx={{ bgcolor: teal[900] }}>
            {text}
        </Avatar>
    )
}