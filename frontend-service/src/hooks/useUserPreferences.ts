import {useMemo} from 'react'
import {useSelector} from 'react-redux'
import {selectCurrentUser} from '../features/user/userSlice'

export const useUserPreferences = () => {
    const data = useSelector(selectCurrentUser)

    return useMemo(() => ({data: data}), [data])
}