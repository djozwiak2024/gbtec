package com.gbtec.av532.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbtec.av532.model.EmailModel;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

public class EmailRestControllerTests {

    //Testcase for testing endpoint /emails/get
    @Test
    public void givenNoMailExists_whenMailsAreQueried_thenEmptyResponseIsReceived() throws IOException {
        //Given
        HttpUriRequest request = new HttpGet("https://localhost:8080/email/get");

        //When
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        //Then
        EmailModel resource = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(response.getEntity().getContent(), EmailModel.class);    }

        //assertThat missing
}
