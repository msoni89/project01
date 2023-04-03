//Reference - https://copyprogramming.com/howto/how-to-make-multi-level-dropdown-menu-using-select-option-mentod
// @ts-ignore
import {Selector} from "../app/services/api";
import MenuItem from "@mui/material/MenuItem";

//Reference - https://copyprogramming.com/howto/how-to-make-multi-level-dropdown-menu-using-select-option-mentod
// @ts-ignore
export function generateOptionWithSpace(_selector: Selector, depth: number = 0) {
    return _selector.selectors.map((_selector) => {
        return [
            <MenuItem
                style={{marginLeft: `${10 * (depth * 2)}px`}}
                key={_selector.id}
                value={_selector.id}
            >
                {_selector.title}
            </MenuItem>,
            _selector.selectors.length > 0 && generateOptionWithSpace(_selector, depth * 2)
        ]
        // {_selector.selectors.length > 0 && generateOptionWithSpace(_selector, depth * 2)}
    }).flat()
}
