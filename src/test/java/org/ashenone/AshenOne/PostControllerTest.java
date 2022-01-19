package org.ashenone.AshenOne;


import org.ashenone.AshenOne.controller.PostController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/posts-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/posts-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "admin")
public class PostControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostController controller;

    @Value("${hostname}")
    private String hostname;

    @Test
    public void postsPageTest() throws Exception
    {
        this.mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='navbarContent']/ul[2]/li/a").string("admin"));
    }

    @Test
    public void postListTest() throws Exception
    {
        this.mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='post-list']/div").nodeCount(4));
    }

    @Test
    public void filterPostTest() throws Exception
    {
        this.mockMvc.perform(get("/posts").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='post-list']/div").nodeCount(2))
                .andExpect(xpath("//div[@id='post-list']/div/div[@data-id='1']").exists())
                .andExpect(xpath("//div[@id='post-list']/div/div[@data-id='3']").exists());
    }

    @Test
    public void addPostToListTest() throws Exception
    {
        MockHttpServletRequestBuilder multipart = multipart("/posts")
                .file("file", "123".getBytes())
                .param("text", "fifth")
                .param("tag", "new one")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='post-list']/div").nodeCount(5))
                .andExpect(xpath("//div[@id='post-list']/div/div[@data-id='10']").exists())
                .andExpect(xpath("//div[@id='post-list']/div/div[@data-id='10']/div/div/span").string("fifth"))
                .andExpect(xpath("//div[@id='post-list']/div/div[@data-id='10']/div/div/i").string("new one"));
    }
}
