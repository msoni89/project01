import {Navigate, Outlet, useLocation} from 'react-router-dom'
import {useUserPreferences} from '../hooks/useUserPreferences'

export function PrivateOutlet() {
    const auth = useUserPreferences()
    const location = useLocation()

    return auth.data ? (
        <Outlet/>
    ) : (
        <Navigate to="/login" state={{from: location}}/>
    )
}