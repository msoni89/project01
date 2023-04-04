import * as React from "react";
import {useFormik} from "formik";
import * as Yup from "yup";
import {
    Button,
    Checkbox,
    Container,
    Divider,
    FormControlLabel,
    SelectChangeEvent,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import {
    useGetUIBuilderSelectorsQuery,
    useGetUserPreferencesByIdQuery,
    useUpdateUserPreferencesMutation
} from "../../app/services/api";
import MenuItem from "@mui/material/MenuItem";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {generateOptionWithSpace} from "../utils";


interface FormValues {
    name: string;
    selectorIds: number[]
}

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 5;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

const validationSchema = Yup.object().shape({
    name: Yup.string()
        .required("Name is required"),
    selectorIds: Yup.array().min(1, "Please select at least 1 selector").required("required")
});

const EditForm = () => {

    let {id} = useParams();

    const {data: userPreferences, isLoading: isDataFetching} = useGetUserPreferencesByIdQuery(Number(id));

    if (isDataFetching) {
        return <div>Loading...</div>
    }

    if (!userPreferences) {
        return <div>No Record found</div>
    }

    const {data, isLoading} = useGetUIBuilderSelectorsQuery()
    const [update, {isLoading: isUpdateLoading}] = useUpdateUserPreferencesMutation()
    const navigate = useNavigate()
    const {
        values,
        errors,
        touched,
        handleChange,
        handleBlur,
        isSubmitting,
        handleSubmit,
        resetForm,
        setFieldValue,
        setSubmitting,
    } = useFormik({
        initialValues: {
            name: userPreferences.name,
            selectorIds: userPreferences.selectors.map(function (obj) {
                return obj.id;
            })
        } as FormValues,
        validationSchema: validationSchema,
        onSubmit: (data: FormValues) => {
            update({
                id: userPreferences.id,
                body: {
                    name: data.name,
                    selectorIds: data.selectorIds
                }
            }).unwrap().then(() => {
                resetForm()
                navigate(`/detail/${id}`)
                toast.info("Successfully updated...")
            }).catch((error) => {
                toast.error(`Something went wrong.. ${JSON.stringify(error.data, null, 2)}`)
            });
            setSubmitting(false);
        }
    });

    const handleChangeSelect = (event: SelectChangeEvent<unknown>) => {
        const {
            target: {value},
        } = event;
        setFieldValue("selectorIds", value)
    };
    // @ts-ignore
    return (
        <Container maxWidth="sm">
            <Typography variant="h4" component="h4">
                Edit Preferences
            </Typography>
            <Divider/>
            <form onSubmit={handleSubmit}>
                <Stack spacing={2} direction={{xs: 'column'}} alignItems={"left"}>
                    <TextField
                        type="text"
                        name="name"
                        label="Name"
                        onChange={handleChange}
                        onBlur={handleBlur}
                        value={values.name}
                        helperText={touched.name ? errors.name : ""}
                        error={touched.name && Boolean(errors.name)}
                    />

                    <TextField
                        multiline
                        fullWidth
                        select
                        helperText={touched.selectorIds ? errors.selectorIds : ""}
                        error={touched.selectorIds && Boolean(errors.selectorIds)}
                        label="Selectors"
                        name={"Selectors"}
                        value={values.selectorIds}
                        // @ts-ignore
                        SelectProps={{
                            style: {textOverflow: "chip", whiteSpace: "break-spaces"},
                            multiple: true,
                            MenuProps: MenuProps,
                            onChange: handleChangeSelect
                        }}
                    >
                        {
                            isLoading ? <MenuItem>
                                Loading...
                            </MenuItem> : data && data.map((selector) => {
                                return [
                                    <MenuItem
                                        key={selector.id}
                                        value={selector.id}
                                    >
                                        {selector.title}
                                    </MenuItem>,
                                    selector.selectors.length && generateOptionWithSpace(selector, 1)
                                ]
                            })
                        }
                    </TextField>
                    <FormControlLabel
                        control={
                            <Checkbox onChange={handleChange} disabled={userPreferences.isTermAccepted}
                                      checked={userPreferences.isTermAccepted}
                                      name="isTermAccepted"/>
                        }
                        label="Agree to terms"
                    />
                    <Button
                        variant="contained"
                        type="submit"
                        disabled={
                            isSubmitting
                        }
                    >
                        Update
                    </Button>
                </Stack>
            </form>
        </Container>
    );
}
export default EditForm;