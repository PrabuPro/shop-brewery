package prabu.springboot.shopbrewery.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import prabu.springboot.shopbrewery.web.model.BeerDTO;
import prabu.springboot.shopbrewery.web.service.BeerService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDTO validBeer;

    @BeforeEach
    void setUp() {
        validBeer = BeerDTO.builder().id(UUID.randomUUID())
                .beerName("beer")
                .beerStyle("style")
                .upc(123456L)
                .build();

    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get(BeerController.API_V_1_BEER+ "/" + validBeer.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("beer")));

    }

    @Test
    void saveBeer() throws Exception {
        //given
        BeerDTO beerDto = validBeer;
        beerDto.setId(null);
        BeerDTO savedDto = BeerDTO.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);


        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(savedDto);

        mockMvc.perform(post(BeerController.API_V_1_BEER )
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        //given
        BeerDTO beerDto = validBeer;
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        //when
        mockMvc.perform(put(BeerController.API_V_1_BEER + "/" + validBeer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());

        then(beerService).should().updateBeer(any(), any());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete(BeerController.API_V_1_BEER + "/" + validBeer.getId().toString()))
                .andExpect(status().isNoContent());

        then(beerService).should().deleteBeerById(any());
    }
}