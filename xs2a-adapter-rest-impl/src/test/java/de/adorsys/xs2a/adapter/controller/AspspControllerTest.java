package de.adorsys.xs2a.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.adorsys.xs2a.adapter.api.AspspSearchApi;
import de.adorsys.xs2a.adapter.config.RestExceptionHandler;
import de.adorsys.xs2a.adapter.mapper.HeadersMapper;
import de.adorsys.xs2a.adapter.model.AspspTO;
import de.adorsys.xs2a.adapter.service.AspspRepository;
import de.adorsys.xs2a.adapter.service.model.Aspsp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pro.javatar.commons.reader.JsonReader;

import java.util.Arrays;

import static de.adorsys.xs2a.adapter.controller.AspspController.V1_ASPSP_BY_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AspspControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AspspController controller;

    @Mock
    private AspspRepository repository;

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
        String id = "1111";
        Aspsp aspsp = new Aspsp();
        aspsp.setId(id);
        aspsp.setBic("bic");

        when(repository.save(any())).thenReturn(aspsp);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                  .post(AspspSearchApi.V1_APSPS)
                                                  .contentType(APPLICATION_JSON_UTF8_VALUE)
                                                  .content("{}"))
                                  .andExpect(status().is(HttpStatus.CREATED.value()))
                                  .andReturn();

        AspspTO response = JsonReader.getInstance()
            .getObjectFromString(mvcResult.getResponse().getContentAsString(), AspspTO.class);
        String location = mvcResult.getResponse().getHeader("Location");

        assertThat(location).endsWith("/" + id);
        assertThat(response.getId()).isEqualTo(aspsp.getId());
        assertThat(response.getBic()).isEqualTo(aspsp.getBic());

    }

    @Test
    public void updateAspsp() throws Exception {
        String id = "1111";
        Aspsp aspsp = new Aspsp();
        aspsp.setId(id);
        aspsp.setBic("bic");

        String body = new ObjectMapper().writeValueAsString(aspsp);

        when(repository.save(any())).thenReturn(aspsp);

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
        String id = "1111";

        doNothing().when(repository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(V1_ASPSP_BY_ID, id)
                            .contentType(APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
            .andReturn();
    }

    @Test
    public void export() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setMessageConverters().build();

        byte[] bytes = "91d10225-f393-40e2-90be-92df43581068,VB Hellweg,GENODEM1SOE,https://xs2a-test.fiduciagad.de/xs2a,fiducia-adapter,41460116".getBytes();

        when(repository.getAllRecords()).thenReturn(bytes);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
            .get(AspspSearchApi.V1_APSPS + "/export")
            .accept(MediaType.ALL))
            .andExpect(status().is(HttpStatus.OK.value()))
            .andReturn();

        byte[] results = mvcResult.getResponse().getContentAsByteArray();

        assertThat(Arrays.equals(results, bytes)).isTrue();
    }
}
