import './App.css';
import {useEffect, useState} from "react";
import ButtonAppBar from "./components/ButtonAppBar";
import axios from "axios";
import Books from "./components/Books";
import AddANewBook from "./components/AddANewBook";

function App() {
    const [state, setState] = useState("");
    const [bookList, setBookList] = useState([])

    function whatToRender() {
        switch (state) {
            case 'addNew':
                return (<AddANewBook refresh={setState}
                                     getAllBooks={getAllBooks}
                />);

            case 'home':
                return (<Books list={bookList}
                               refresh={setState}
                               getAllBooks={getAllBooks}
                />)
            default:
                return (
                    <Books list={bookList}
                           refresh={setState}
                           getAllBooks={getAllBooks}
                    />
                )
        }

    }

    async function getAllBooks() {
        const res = await axios.get("http://localhost:8080/books")
        setBookList(res.data);
    }

    useEffect(() => {
        setState("");
        getAllBooks();

    }, [])

    return (
        <div>
            <ButtonAppBar setState={setState}/>
            {whatToRender()}
        </div>
    );
}

export default App;
