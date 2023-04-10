import {useNavigate, useParams} from "react-router-dom";
import {Button, Card, CardActions, CardContent, Container, Typography} from "@mui/material";
import {useGetUserPreferencesByIdQuery} from "../../app/services/api";
import MenuItem from "@mui/material/MenuItem";
import {generateOptionWithSpace} from "../utils";

export const DetailView = () => {
    let {id} = useParams();

    const navigate = useNavigate()

    const {data, isLoading} = useGetUserPreferencesByIdQuery(Number(id));

    if (isLoading) {
        return <div>Loading...</div>
    }

    if (!data) {
        return <div>No Record found</div>
    }

    return <Container maxWidth="sm">
        <Card sx={{maxWidth: 600}}>
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    Name: {data.name}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Term Accepted {data.isTermAccepted.toString()}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Created date: {data.createdAt.toString()}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Updated date: {data.updatedAt.toString()}
                </Typography>
                <Typography variant="body1" color="text.secondary">
                    Selected Options: {
                    data.selectors.map((selector) => {
                        return [
                            <MenuItem
                                key={selector.id}
                                value={selector.id}
                            >
                                {selector.title}
                            </MenuItem>,
                            generateOptionWithSpace(selector, 1)
                        ]
                    })
                }
                </Typography>
            </CardContent>
            <CardActions>
                <Button onClick={() => navigate(`/edit/${id}`)} size="small">Edit</Button>
                <Button onClick={() => navigate("/")} size="small">Confirmed</Button>
            </CardActions>
        </Card>
    </Container>
}

export default DetailView;