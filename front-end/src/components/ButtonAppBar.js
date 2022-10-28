import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Button from '@mui/material/Button';
import {Typography} from "@mui/material";


export default function ButtonAppBar(props) {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <Typography
                        variant="h6"
                        noWrap
                        color={'secondary'}

                    >
                        Austin Mock Library
                    </Typography>
                    <Button color="inherit"
                            onClick={()=>{props.setState("")}}
                    >Home</Button>
                    <Button color="inherit"
                            onClick={()=>{props.setState("addNew")}}
                    >Add A New Book</Button>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
