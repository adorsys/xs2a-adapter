package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.AspspMapper;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.rest.api.AspspSearchApi;
import de.adorsys.xs2a.adapter.rest.api.model.AspspTO;
import de.adorsys.xs2a.adapter.service.AspspReadOnlyRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import de.adorsys.xs2a.adapter.service.model.AspspScaApproach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.javatar.commons.reader.JsonReader;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AspspSearchControllerTest {
    private static final String ASPSP_ID = "aspsp-id";
    private MockMvc mockMvc;
    private AspspMapper mapper = Mappers.getMapper(AspspMapper.class);

    @InjectMocks
    private AspspSearchController controller;

    @Mock
    private AspspReadOnlyRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
            .build();
    }

    @Test
    void getById() throws Exception {
        Aspsp aspsp = buildAspsp();
        AspspTO aspspTO = mapper.toAspspTO(aspsp);
        String aspspTOJson = new ObjectMapper().writeValueAsString(aspspTO);
        when(repository.findById(ASPSP_ID)).thenReturn(Optional.of(aspsp));
        MvcResult mvcResult = mockMvc.perform(get(AspspSearchApi.V1_APSPS_BY_ID, ASPSP_ID).content(aspspTOJson))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andReturn();

        AspspTO response = JsonReader.getInstance()
            .getObjectFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);

        assertThat(response).isEqualTo(aspspTO);
    }

    @Test
    void getByIdNotFound() throws Exception {
        when(repository.findById(ASPSP_ID)).thenReturn(Optional.empty());
        mockMvc.perform(get(AspspSearchApi.V1_APSPS_BY_ID, ASPSP_ID).content("{}"))
            .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
            .andReturn();

    }

    private Aspsp buildAspsp() {
        Aspsp aspsp = new Aspsp();
        aspsp.setAdapterId("adapterId");
        aspsp.setBankCode("bankCode");
        aspsp.setBic("bic");
        aspsp.setId(ASPSP_ID);
        aspsp.setName("bankName");
        aspsp.setUrl("https://online-banking.url");
        aspsp.setIdpUrl("https://idp.bank.url");
        aspsp.setScaApproaches(Collections.singletonList(AspspScaApproach.OAUTH));
        return aspsp;
    }
}
