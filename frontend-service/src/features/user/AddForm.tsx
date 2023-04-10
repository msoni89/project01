import * as React from "react";
import {useFormik} from "formik";
import * as Yup from "yup";
import {
    Box,
    Button,
    Checkbox,
    Container,
    Divider,
    FormControlLabel,
    FormHelperText,
    SelectChangeEvent,
    Stack,
    TextField,
    Typography
} from "@mui/material";
import {useCreateUserPreferencesMutation} from "../../app/services/api";
import {setUserPreferences} from "./userSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import SelectorComponent from "../components/SelectorComponent";

interface FormValues {
    name: string;
    isTermAccepted: boolean;
    selectorIds: number[]
}

const validationSchema = Yup.object().shape({
    name: Yup.string()
        .required("Name is required"),
    isTermAccepted: Yup.boolean().test("isTermAccepted", "Required", (val) => {
        return val;
    }),
    selectorIds: Yup.array().min(1, "Please select at least 1 selector").required("required")
});

const AddForm = () => {
    const [create,] = useCreateUserPreferencesMutation()

    const dispatch = useDispatch()
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
        setSubmitting,
        setFieldValue
    } = useFormik({
        initialValues: {
            name: "",
            isTermAccepted: false,
            selectorIds: []
        } as FormValues,
        validationSchema: validationSchema,
        onSubmit: (data: FormValues) => {
            create(data).unwrap().then((response) => {
                resetForm()
                dispatch(setUserPreferences(response))
                navigate(`/detail/${response.id}`)
                toast.info("Successfully created...")
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
                Add Preferences
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
                    <SelectorComponent selectorIds={values.selectorIds} isTouched={!!touched.selectorIds}
                                       hasError={Boolean(errors.selectorIds)}
                                       errorMessage={touched.selectorIds ? errors.selectorIds : ""}
                                       handleChange={handleChangeSelect} />
                    <FormControlLabel
                        control={
                            <Box>
                                <Checkbox onChange={handleChange} checked={values.isTermAccepted}
                                          name="isTermAccepted"/>
                                {touched.isTermAccepted && Boolean(errors.isTermAccepted) && (
                                    <FormHelperText error>
                                        Term {errors.isTermAccepted ? errors.isTermAccepted : " "}
                                    </FormHelperText>
                                )}
                            </Box>
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
                        Save
                    </Button>
                </Stack>
            </form>
        </Container>
    );
};

export default AddForm;
