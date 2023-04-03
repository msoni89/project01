import {createApi, fetchBaseQuery} from '@reduxjs/toolkit/query/react'

export interface Selector {
    readonly id: number
    readonly  title: string
    readonly selectors: Selector[]
}

export interface UserPreferenceResponse {
    readonly  id: number
    readonly name: string
    readonly  isTermAccepted: boolean
    readonly selectors: Selector[]
    readonly createdAt: Date;
    readonly  updatedAt: Date
}

export interface CreateRequest {
    readonly name: String
    readonly  isTermAccepted: boolean
    readonly selectorIds: string[]
}


export interface UpdateRequest {
    readonly id: number
    readonly body: UpdatedRequestBody
}


export interface UpdatedRequestBody {
    readonly name: String
    readonly selectorIds: number[]
}

export const api = createApi({
    baseQuery: fetchBaseQuery({
        baseUrl: process.env.REACT_APP_API_BASE_URL,
    }),
    tagTypes: ['UserPreferences', "selectors"],
    endpoints: (builder) => ({
        createUserPreferences: builder.mutation<UserPreferenceResponse, CreateRequest>({
            query: (body) => ({
                url: `/api/v1/user-preferences`,
                method: 'POST',
                body,
            }),
            invalidatesTags: ["UserPreferences"],
        }),
        updateUserPreferences: builder.mutation<void, UpdateRequest>({
            query: (request) => ({
                url: `/api/v1/user-preferences/${request.id}`,
                method: 'PUT',
                body: request.body,
            }),
            invalidatesTags: ["UserPreferences"],
        }),
        getUserPreferencesById: builder.query<UserPreferenceResponse, number>({
            query: (id: number) => `/api/v1/user-preferences/id/${id}`,
            providesTags: (result, error, id) => [{type: 'UserPreferences', id}],
        }),
        getUIBuilderSelectors: builder.query<Selector[], void>({
            query: () => "/api/v1/ui-builder/selectors",
            providesTags: (result) =>
                result
                    ? [
                        ...result.map(({id}) => ({type: 'selectors' as const, id})),
                        "selectors",
                    ]
                    : ["selectors"],
        }),
    }),
})

export const {
    useUpdateUserPreferencesMutation,
    useCreateUserPreferencesMutation,
    useGetUserPreferencesByIdQuery,
    useGetUIBuilderSelectorsQuery,
} = api