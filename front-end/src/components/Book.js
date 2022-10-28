import React, {useState} from 'react';
import {Card, CardActions, CardContent, CardHeader, CardMedia} from "@mui/material";
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import FavoriteIcon from '@mui/icons-material/Favorite';
import DeleteIcon from '@mui/icons-material/Delete';
import { red } from '@mui/material/colors';
import axios from "axios";
function Book(props){
const [isBold, setIsBold] = useState(props.book.favorite)


 async   function handleDelete(event) {
       const res = await axios.delete(`http://localhost:8080/books/${props.book.id}`)
     console.log(res)
     if(res.status ===204){
         props.getAllBooks();
         props.refresh("home");
     }

    }

   async function handleFavorite(event) {
     let thisChange = !props.book.favorite;
     const res = await axios.patch(`http://localhost:8080/books/${props.book.id}`,
         {
             "title":props.book.title,
             "author": props.book.author,
             "favorite": thisChange
         }
         )
       props.getAllBooks();
       props.refresh("home");



    }

    if(props.book.favorite){
        return (
            <Card sx={{ maxWidth: 345 }}>
                <CardHeader title={props.book.title}
                            titleTypographyProps={{variant:'h3' }}
                />
                <Typography variant="h1" color="text.secondary">
                    {props.title}
                </Typography>
                <CardMedia
                    component="img"
                    height="194"
                    image="https://picsum.photos/200/300"
                    alt="Random Image"
                />
                <CardContent>
                    <Typography variant="body2"
                                sx={{ color: 'text.primary', fontSize: 34, fontWeight: 'medium' }}
                                color="text.secondary">
                        {props.book.author}
                    </Typography>
                </CardContent>
                <CardActions disableSpacing>
                    {(props.book.favorite)?
                        <IconButton
                            onClick={handleFavorite}
                        >
                            <FavoriteIcon color={'error'} />
                        </IconButton>

                        :
                        <IconButton
                            onClick={handleFavorite}
                        >
                            <FavoriteIcon  />
                        </IconButton>

                    }

                    <IconButton aria-label="delete"
                                onClick={handleDelete}
                    >
                        <DeleteIcon />
                    </IconButton>
                </CardActions>
            </Card>

        )
    }

    return(
        <Card sx={{ maxWidth: 345 }}>
            <CardHeader title={props.book.title}
                        />
            <Typography variant="h1" color="text.secondary">
                {props.title}
            </Typography>
            <CardMedia
                component="img"
                height="194"
                image="https://picsum.photos/200/300"
                alt="Random Image"
            />
            <CardContent>
                <Typography variant="body2" color="text.secondary">
                    {props.book.author}
                </Typography>
            </CardContent>
            <CardActions disableSpacing>
                {(props.book.favorite)?
                    <IconButton
                        onClick={handleFavorite}
                    >
                        <FavoriteIcon color={'error'} />
                    </IconButton>

                    :
                    <IconButton
                        onClick={handleFavorite}
                    >
                        <FavoriteIcon  />
                    </IconButton>

                }

                <IconButton aria-label="delete"
                            onClick={handleDelete}
                >
                    <DeleteIcon />
                </IconButton>
            </CardActions>
        </Card>

    )
}

export default Book;