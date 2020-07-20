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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AspspSearchControllerTest {
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
    public void getById() throws Exception {
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
    public void getByIdNotFound() throws Exception {
        when(repository.findById(ASPSP_ID)).thenReturn(Optional.empty());
        mockMvc.perform(get(AspspSearchApi.V1_APSPS_BY_ID, ASPSP_ID).content("{}"))
            .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
            .andReturn();

    }

    @Test
    void getAspsp_byIban() throws Exception {
        Aspsp aspsp = buildAspsp();
        AspspTO aspspTO = mapper.toAspspTO(aspsp);

        when(repository.findByIban(anyString(), any(), anyInt()))
            .thenReturn(Collections.singletonList(aspsp));

        MvcResult mvcResult = mockMvc.perform(get(AspspSearchApi.V1_APSPS)
            .queryParam("iban", "DE8749999960000000512"))
            .andExpect(status().isOk())
            .andReturn();

        verify(repository, times(1)).findByIban(anyString(), any(), anyInt());

        List<AspspTO> response = JsonReader.getInstance()
            .getListFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);

        assertThat(response.get(0)).isEqualTo(aspspTO);
    }

    @Test
    void getAspsp_all() throws Exception {
        Aspsp aspsp = buildAspsp();
        List<Aspsp> aspsps = Arrays.asList(aspsp, aspsp);

        when(repository.findAll(any(), anyInt())).thenReturn(aspsps);

        MvcResult mvcResult = mockMvc.perform(get(AspspSearchApi.V1_APSPS))
            .andExpect(status().isOk())
            .andReturn();

        List<AspspTO> response = JsonReader.getInstance()
            .getListFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);

        verify(repository, times(1)).findAll(any(), anyInt());

        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0)).isEqualTo(mapper.toAspspTO(aspsp));
    }

    @Test
    void getAspsp_byName() throws Exception {
        String bankName = "bank name";
        Aspsp aspsp = buildAspsp();
        aspsp.setName(bankName);

        when(repository.findLike(any(Aspsp.class), any(), anyInt()))
            .thenReturn(Collections.singletonList(aspsp));

        MvcResult mvcResult = mockMvc.perform(get(AspspSearchApi.V1_APSPS)
            .queryParam("name", bankName))
            .andExpect(status().isOk())
            .andReturn();

        verify(repository, times(1)).findLike(any(Aspsp.class), any(), anyInt());

        List<AspspTO> response = JsonReader.getInstance()
            .getListFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);

        assertThat(response.get(0).getName()).isEqualTo(bankName);
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
