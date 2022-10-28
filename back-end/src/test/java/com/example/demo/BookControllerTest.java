package com.example.demo;


import com.example.demo.dao.BookRepository;
import com.example.demo.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {


    @Autowired
    MockMvc mvc;

    @Autowired
    BookRepository repository;

    public Book book1;
    public Book book2;
    ObjectMapper mapper = new ObjectMapper().registerModule( new JavaTimeModule());

    @BeforeEach
    void initialize(){
        book1 = new Book();
        book2 = new Book();
        book1.setAuthor("Ray Bradbury");
        book2.setAuthor("Isaac Asimov");
        book1.setTitle("Fahrenheit 451");
        book2.setTitle("Foundation");
        book1.setFavorite(true);
        book2.setFavorite(false);

    }


    @Test
    @Transactional
    @Rollback
    @Order(1)
    public void getAllTest() throws Exception {
        this.repository.save(book1);
        this.repository.save(book2);
        MockHttpServletRequestBuilder getThisID = get("/books");
        MvcResult resultOfGetAll = this.mvc.perform(getThisID)
                .andExpect(status().isOk())
                .andReturn();
        String response = resultOfGetAll.getResponse().getContentAsString();
        Integer idToCheck = JsonPath.parse(response).read("$[0].id");
        String author = JsonPath.parse(response).read("$[0].author");
        MockHttpServletRequestBuilder request = get("/books");
        this.mvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("[0].author").value(author));
//        MockHttpServletRequestBuilder request = get("/books");
//        this.mvc.perform(request).andExpect(status().isOk())
//                .andExpect(jsonPath("[1].author").value("Ray Bradbury"));


    }
    @Test
    @Transactional
    @Rollback
    @Order(2)
    public void deleteById() throws Exception {
        this.repository.save(book1);
        this.repository.save(book2);
        MockHttpServletRequestBuilder getThisID = get("/books");
        MvcResult resultOfGetAll = this.mvc.perform(getThisID)
                .andExpect(status().isOk())
                .andReturn();
        String response = resultOfGetAll.getResponse().getContentAsString();
        Integer idToDelete = JsonPath.parse(response).read("$[0].id");
        String urlToDelete = String.format("/books/%d", idToDelete);
        MockHttpServletRequestBuilder request = delete(urlToDelete);
        this.mvc.perform(request).andExpect(status().isNoContent());
//        System.out.println("Testing this ID !!!!!" + "  " + idToDelete);
//        System.out.println(response);

//        String response = mvcResult.getResponse().getContentAsString();
//        Long id = JsonPath.parse(response).read("$[0].id");

//        MvcResult mvcResult = (MvcResult) mvc.perform(get("/books").accept(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$[0].id", is(1))).andReturn();
//        MockHttpServletRequestBuilder request = delete("/books/1");
//        this.mvc.perform(request).andExpect(status().isNoContent());
    }


    @Test
    @Transactional
    @Rollback
    @Order(3)
    public void getById() throws Exception {

        this.repository.save(book1);
        this.repository.save(book2);

        MockHttpServletRequestBuilder getThisID = get("/books");
        MvcResult resultOfGetAll = this.mvc.perform(getThisID)
                .andExpect(status().isOk())
                .andReturn();
        String response = resultOfGetAll.getResponse().getContentAsString();
        Integer idToGet = JsonPath.parse(response).read("$[0].id");
//        System.out.println(idToGet + "  Testing!!!");
        String urlToGet = String.format("/books/%d", idToGet);

        MockHttpServletRequestBuilder request = get(urlToGet);
        this.mvc.perform(request).andExpect(status().isOk());
//        MockHttpServletRequestBuilder request = get("/books/1");
//        this.mvc.perform(request).andExpect(status().isOk());
    }
    @Test
    @Transactional
    @Rollback
    @Order(4)
    public void updateById() throws Exception {
        this.repository.save(book1);
//        this.repository.save(book2);
        Book starShipTroopers = new Book();
        starShipTroopers.setFavorite(true);
        starShipTroopers.setAuthor("Robert A Heinlein");
        starShipTroopers.setTitle("Starship Troopers");
        String json = mapper.writeValueAsString(starShipTroopers);

        MockHttpServletRequestBuilder request = patch("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("author").value("Robert A Heinlein"));
    }
    @Test
    @Transactional
    @Rollback
    @Order(5)
    public void postById() throws Exception {
        this.repository.save(book1);
        this.repository.save(book2);
        Book starShipTroopers = new Book();
        starShipTroopers.setFavorite(true);
        starShipTroopers.setAuthor("Robert A Heinlein");
        starShipTroopers.setTitle("Starship Troopers");
        String json = mapper.writeValueAsString(starShipTroopers);

        MockHttpServletRequestBuilder request = post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request).andExpect(status().isOk())
                .andExpect(jsonPath("author").value("Robert A Heinlein"));
    }


}
