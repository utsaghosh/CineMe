import { createContext, useContext, useEffect, useRef, useState } from "react";

export const UserContext = createContext(null);

export function UserProvider({children}){
    
    const [user, setUser] = useState();

    // const prevUserRef = useRef(sessionStorage.getItem("user"));

    useEffect(() => {
        const loggedInUser = sessionStorage.getItem("user");
        // const loggedInUser = prevUserRef.current;
        if (loggedInUser) {
          const foundUser = JSON.parse(loggedInUser);
          setUser(foundUser);
        //   prevUserRef.current = foundUser;
        }
    }, []);

    // const loadUser = () => {
    //     const loggedInUser = sessionStorage.getItem("user");
    //     if (loggedInUser) {
    //       const foundUser = JSON.parse(loggedInUser);
    //       setUser(foundUser);
    //     }
    // }
    
    return(
        <UserContext.Provider value={{user, setUser}}>
        {/* <UserContext.Provider value={loadUser}> */}
            {children}
        </UserContext.Provider>
    )
}

export function useUserContext(){
    return useContext(UserContext);
}