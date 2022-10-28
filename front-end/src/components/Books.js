import React from 'react';
import Book from "./Book";

function Books(props){

    return(
        <div>
            {(props.list.length > 0 ?
                    props.list.map((book) =>{
                        return(
                            <Book key={book.id}
                                  getAllBooks={props.getAllBooks}
                                  refresh={props.refresh}
                                  book={book}

                            />
                        )
                    })
                    :
                    <>Empty List!</>
            )}

        </div>
    )

}

export default Books;