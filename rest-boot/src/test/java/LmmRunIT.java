import com.fasterxml.jackson.databind.SerializationFeature;
import it.mapsgroup.gzoom.model.Company;
import it.mapsgroup.gzoom.security.model.LoginResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author Andrea Fossi.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(LmmRun.class)
//@WebIntegrationTest
//@TestPropertySource("file:/Users/anfo/projects/lmm/conf/lmm.properties")
public class LmmRunIT {
    private static final String REST_ENDPOINT = "http://localhost:8081/rest";
    protected RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonHttpMessageConverter.setPrettyPrint(true);
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getMessageConverters().add(jsonHttpMessageConverter);
    }

    @Test
    public void name() throws Exception {
        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(REST_ENDPOINT + "/login", "{\"username\": \"anfo\", \"password\": \"password\"}", LoginResponse.class);
        String jwt = response.getBody().getToken();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        ResponseEntity<Result<Company>> companies = restTemplate.exchange(REST_ENDPOINT + "/registry/companies?page={page}&size={size}",
                HttpMethod.GET, new HttpEntity<Object>(headers), new ParameterizedTypeReference<Result<Company>>() {
                }
                , 1, 20);
        companies.getBody();
//        new Result<Company>(new ArrayList<Company>(), 0)
    }

    private static class Result<T> extends it.mapsgroup.gzoom.model.Result<T> {
        public Result() {
            super(new ArrayList<T>(), 0);
        }
    }
}
