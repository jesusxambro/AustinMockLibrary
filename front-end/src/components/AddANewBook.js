import React, {useState} from 'react';
import {Checkbox, FormControlLabel, FormGroup, TextField} from "@mui/material";
import Button from "@mui/material/Button";
import axios from "axios";

function AddANewBook(props){
    const[author, setAuthor] = useState("");
    const [title, setTitle] = useState("");
    const [checked, setChecked] = useState(false);

   async function OnSubmit(event){
        if(author != "" && title != ""){
            const res = await axios.post("http://localhost:8080/books/",
                {"title":title,
                      "author": author,
                      "favorite": checked
                })
            props.getAllBooks();
            props.refresh("");
        }

    }

    function handleAuthorChange(event) {
        setAuthor(event.target.value)
    }
    function handleTitleChange(event) {
        setTitle(event.target.value);
    }
    function handleCheck(event){
       setChecked(!checked);
    }

    return(
        <div style={{  padding: 20 }}>
            <TextField id="outlined-basic"
                       required label="Author"
                       value={author}
                       onChange={handleAuthorChange}
                       variant="outlined" />
            <TextField id="outlined-basic"
                       required label="Title"
                       value={title}
                       onChange={handleTitleChange}
                       variant="outlined" />
            <FormGroup>
                <FormControlLabel control={<Checkbox onChange={handleCheck} />} label="Is It A Favorite?" />
            </FormGroup>

            <Button variant="contained"
                    onClick={OnSubmit}
            >Submit</Button>



        </div>
    )
}

export default AddANewBook;