import type {PayloadAction} from '@reduxjs/toolkit'
import {createSlice} from '@reduxjs/toolkit'
import type {UserPreferenceResponse} from '../../app/services/api'
import type {RootState} from '../../app/store'

const slice = createSlice({
    name: 'auth',
    initialState: {} as UserPreferenceResponse,
    reducers: {
        setUserPreferences: (
            state,
            {
                payload: {
                    name,
                    id,
                    isTermAccepted,
                    updatedAt,
                    selectors,
                    createdAt
                }
            }: PayloadAction<UserPreferenceResponse>
        ) => {
            state = {
                name, id, isTermAccepted, updatedAt, selectors, createdAt
            }
        }
    },
})

export const {setUserPreferences} = slice.actions

export default slice.reducer

export const selectCurrentUser = (state: RootState) => state.data