import {generateOptionWithSpace} from "../utils";
import {SelectChangeEvent, TextField} from "@mui/material";
import {FC} from "react";
import MenuItem from "@mui/material/MenuItem";
import {Selector, useGetUIBuilderSelectorsQuery} from "../../app/services/api";

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

type SelectorProps = {
    selectorIds: number[],
    isTouched: boolean,
    hasError: boolean,
    errorMessage: string | string[] | undefined,
    handleChange: (event: SelectChangeEvent<unknown>) => void

}

const SelectorComponent: FC<SelectorProps> = ({
                                                  selectorIds,
                                                  errorMessage,
                                                  hasError,
                                                  isTouched,
                                                  handleChange
                                              }) => {


    const {data, isLoading} = useGetUIBuilderSelectorsQuery()

    if (isLoading) {
        return <MenuItem>
            Loading...
        </MenuItem>
    }

    if (!data || !data.length) {
        return <MenuItem>No selectors</MenuItem>
    }

    return <TextField
        multiline
        fullWidth
        select
        helperText={errorMessage}
        error={isTouched && hasError}
        label="Selectors"
        name={"Selectors"}
        value={selectorIds}
        SelectProps={{
            style: {textOverflow: "chip", whiteSpace: "break-spaces"},
            multiple: true,
            MenuProps: MenuProps,
            onChange: handleChange
        }}
    >
        {
            data && data.map((selector: Selector) => {
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
}

export default SelectorComponent;