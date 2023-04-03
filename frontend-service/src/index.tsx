import React from 'react'
import ReactDOM from 'react-dom/client'
import {BrowserRouter} from 'react-router-dom'
import App from './App'
import {store} from './app/store'
import {Provider} from 'react-redux'
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import {StyledEngineProvider} from '@mui/material/styles';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const rootNode = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
)

rootNode.render(
    <React.StrictMode>
        <StyledEngineProvider injectFirst>
            <Provider store={store}>
                <BrowserRouter>
                    <App/>
                    <ToastContainer/>
                </BrowserRouter>
            </Provider>
        </StyledEngineProvider>
    </React.StrictMode>
)
