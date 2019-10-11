package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.AspspSearchApi;
import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.model.AspspTO;
import de.adorsys.xs2a.adapter.service.AspspService;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.javatar.commons.reader.JsonReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.adorsys.xs2a.adapter.controller.AspspController.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AspspControllerTest {
    private static final String ID = "1111";
    private static final String BIC = "bic";

    private MockMvc mockMvc;

    @InjectMocks
    private AspspController controller;

    @Mock
    private AspspService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .setControllerAdvice(new RestExceptionHandler(new HeadersMapper()))
            .build();
    }

    @Test
    public void addAspsp() throws Exception {
        Aspsp aspsp = buildAspsp();

        when(service.create(any())).thenReturn(aspsp);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
            .post(AspspSearchApi.V1_APSPS)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content("{}"))
            .andExpect(status().is(HttpStatus.CREATED.value()))
            .andReturn();

        AspspTO response = JsonReader.getInstance()
            .getObjectFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);
        String location = mvcResult.getResponse().getHeader("Location");

        assertThat(location).endsWith("/" + ID);
        assertThat(response.getId()).isEqualTo(aspsp.getId());
        assertThat(response.getBic()).isEqualTo(aspsp.getBic());

    }

    @Test
    public void updateAspsp() throws Exception {
        Aspsp aspsp = buildAspsp();

        String body = new ObjectMapper().writeValueAsString(aspsp);

        when(service.update(any())).thenReturn(aspsp);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
            .put(AspspSearchApi.V1_APSPS)
            .contentType(APPLICATION_JSON_UTF8_VALUE)
            .content(body))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andReturn();

        AspspTO response = JsonReader.getInstance()
            .getObjectFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);

        assertThat(response.getId()).isEqualTo(aspsp.getId());
        assertThat(response.getBic()).isEqualTo(aspsp.getBic());
    }

    @Test
    public void delete() throws Exception {
        doNothing().when(service).deleteById(ID);

        mockMvc.perform(MockMvcRequestBuilders
            .delete(V1_ASPSP_BY_ID, ID)
            .contentType(APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
            .andReturn();
    }

    @Test
    public void readAll() throws Exception {
        Aspsp aspsp = buildAspsp();

        when(service.readAll()).thenReturn(Collections.singletonList(aspsp));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                  .get(V1_ASPSP_EXPORT)
                                                  .contentType(APPLICATION_JSON_UTF8_VALUE))
                                  .andExpect(status().isOk())
                                  .andReturn();

        verify(service, times(1)).readAll();

        List<AspspTO> tos = JsonReader
                                .getInstance()
                                .getListFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);

        assertThat(tos).isNotNull();
        assertThat(tos.size()).isEqualTo(1);
        assertThat(tos.get(0)).isEqualToComparingFieldByField(aspsp);
    }

    @Test
    public void importAll() throws Exception {
        List<Aspsp> aspsps = new ArrayList<>(Collections.singletonList(buildAspsp()));

        doNothing().when(service).importAspsps(aspsps);

        mockMvc.perform(MockMvcRequestBuilders
                            .put(V1_ASPSP_IMPORT)
                            .contentType(APPLICATION_JSON_UTF8_VALUE)
                            .content(new ObjectMapper().writeValueAsString(Collections.singletonList(buildAspspTO()))))
            .andExpect(status().isNoContent())
            .andReturn();

        verify(service, times(1)).importAspsps(aspsps);
    }

    private Aspsp buildAspsp() {
        Aspsp aspsp = new Aspsp();

        aspsp.setId(ID);
        aspsp.setBic(BIC);

        return aspsp;
    }

    private AspspTO buildAspspTO() {
        AspspTO to = new AspspTO();

        to.setId(ID);
        to.setBic(BIC);

        return to;
    }
}
