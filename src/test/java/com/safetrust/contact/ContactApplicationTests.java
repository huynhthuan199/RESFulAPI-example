package com.safetrust.contact;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.safetrust.contact.ulti.Contants.ErrorMessage;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class ContactApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	

	@Test
	@Order(1)
	void testAllContact_norrmal() throws Exception {
		
		mockMvc.perform(get("/contact"))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
		        .andReturn();
	}
	
	@Test
	@Order(2)
	void testAllContact_norrmalWithSearch() throws Exception {
		
		mockMvc.perform(
				get("/contact")
				.param("search", "CHINA")
				)
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
		        .andReturn();
	}
	
	@Test
	@Order(3)
	void testFindIdByContact_norrmal() throws Exception {
		
		mockMvc.perform(
				get("/contact/500"))
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("CHINA"))
		        .andReturn();
	}
	
	@Order(4)
	@Test
	void testFindIdByContact_whenNotFoundContact() throws Exception {
		
		//expected
		String strErrorMsg = String.format(ErrorMessage.CONTACT_NOT_FOUND.getStrErrorMsg(), 501);
		
		mockMvc.perform(
				get("/contact/501"))
		.andExpect(status().isBadRequest())
		.andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(strErrorMsg))
		.andReturn();
	}
	
	@Test
	@Order(5)
	void testUpdateContact_nomal() throws Exception {
		
		mockMvc.perform(
				put("/contact/500")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"name\": \"c\",\n"
						+ "    \"emailAddress\": \"ab22c222@gmail.com\",\n"
						+ "    \"telephoneNumber\": \"+849823334167\",\n"
						+ "    \"postalAddress\": \"abc\"\n"
						+ "}")
				)
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("c"))
		        .andReturn();
	}
	
	@Test
	@Order(6)
	void testUpdateContact_whenNotFoundContact() throws Exception {
		
		//expected
		String strErrorMsg = String.format(ErrorMessage.CONTACT_NOT_FOUND.getStrErrorMsg(), 501);
		
		mockMvc.perform(
				put("/contact/501")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"name\": \"c\",\n"
						+ "    \"emailAddress\": \"ab22c223122@gmail.com\",\n"
						+ "    \"telephoneNumber\": \"+845823334167\",\n"
						+ "    \"postalAddress\": \"abc\"\n"
						+ "}")
				)
		        .andExpect(status().isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(strErrorMsg))
		        .andReturn();
	}
	
	@Test
	@Order(7)
	void testDeleteContact_nomal() throws Exception {
		
		mockMvc.perform(
				delete("/contact/500")
				)
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.content().string("Delete success"))
		        .andReturn();
	}
	
	@Test
	@Order(8)
	void testDeleteContact_whenNotFoundContact() throws Exception {
		
		//expected
		String strErrorMsg = String.format(ErrorMessage.CONTACT_NOT_FOUND.getStrErrorMsg(), 501);
		
		mockMvc.perform(
				delete("/contact/501")
				)
		        .andExpect(status().isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").value(strErrorMsg))
		        .andReturn();
	}
	
	@Test
	@Order(9)
	void testPostContact_normal() throws Exception {
		
		mockMvc.perform(
				post("/contact")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"name\": \"thuan\",\n"
						+ "    \"emailAddress\": \"thuanabcax@gmail.com\",\n"
						+ "    \"telephoneNumber\": \"+84868976135\",\n"
						+ "    \"postalAddress\": \"abc\"\n"
						+ "}")
				)
		        .andExpect(status().isOk())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("thuan"))
		        .andReturn();
	}
	
	@Order(10)
	@ParameterizedTest
	@MethodSource("provideStringsForIsBlank")
	void testValidation_invalid(String strName, String strEmailAddress, String strPhone, String strPostalAddress) throws Exception {
		
		mockMvc.perform(
				put("/contact/499")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\n"
						+ "    \"name\": \""+ strName +"\",\n"
						+ "    \"emailAddress\": \" " + strEmailAddress + "\",\n"
						+ "    \"telephoneNumber\": \"" + strPhone + "\",\n"
						+ "    \"postalAddress\": \"" + strPostalAddress + "\"\n"
						+ "}")
				)
		        .andExpect(status().isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isNotEmpty())
		        .andReturn();
	}
	
	private static Stream<Arguments> provideStringsForIsBlank() {
	    return Stream.of(
	      Arguments.of("", "", "", ""),
	      Arguments.of("name", "B@gmail.com", "+84828916131", "HCM"),
	      Arguments.of("name", "thuanabc2@gmail.com","+84868976135", "HCM")
	    );
	}
}
