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
import {useCreateUserPreferencesMutation, useGetUIBuilderSelectorsQuery} from "../../app/services/api";
import MenuItem from "@mui/material/MenuItem";
import {setUserPreferences} from "./userSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import {generateOptionWithSpace} from "../utils";

interface FormValues {
    name: string;
    isTermAccepted: boolean;
    selectorIds: string[]
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
    isTermAccepted: Yup.boolean().test("isTermAccepted", "Required", (val) => {
        return val;
    }),
    selectorIds: Yup.array().min(1, "Please select at least 1 selector").required("required")
});

const AddForm = () => {
    const {data, isLoading} = useGetUIBuilderSelectorsQuery()
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
