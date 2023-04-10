import {Route, Routes} from 'react-router-dom'

import {Box} from "@mui/material";
import AddForm from "./features/user/AddForm";
import DetailView from "./features/user/DetailView";
import EditForm from "./features/user/EditForm";

function App() {

    return (
        <Box>
            <Routes>
                <Route path="/" element={<AddForm/>}/>
                <Route path="/detail/:id" element={<DetailView/>}/>
                <Route path="/edit/:id" element={<EditForm/>}/>
            </Routes>
        </Box>
    )
}

export default App;
