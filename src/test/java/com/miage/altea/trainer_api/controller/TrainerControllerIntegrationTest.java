package com.miage.altea.trainer_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.miage.altea.trainer_api.bo.Pokemon;
import com.miage.altea.trainer_api.bo.Trainer;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Test
    void trainerController_shouldBeInstanciated(){
        assertNotNull(controller);
    }

    @Test
    void getTrainer_withNameAsh_shouldReturnAsh() {
        var ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonTypeId());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_shouldReturnAshAndMisty() {
        var trainers = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Ash", trainers[0].getName());
        assertEquals("Misty", trainers[1].getName());
    }

    @Test
    void addAsh_shouldReturnAsh() throws JSONException, JsonProcessingException {
        Trainer trainer = new Trainer("Bug Catcher");
        trainer.setTeam(Arrays.asList(new Pokemon(13,6)));
        var bugCatcher = restTemplate.postForObject("http://localhost:" + port + "/trainers/", trainer, Trainer.class);

        assertNotNull(bugCatcher);
        assertEquals("Bug Catcher", bugCatcher.getName());
        assertEquals(13, bugCatcher.getTeam().get(0).getPokemonTypeId());
    }

    @Test
    void updateAsh_shouldReturnUpdatedAsh() {
        var trainer = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        trainer.setTeam(Arrays.asList(new Pokemon(10,6)));
        restTemplate.put("http://localhost:" + port + "/trainers/", trainer);

        var ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(10, ash.getTeam().get(0).getPokemonTypeId());
    }

    @Test
    void removeAsh_shouldReturnNothing() {
        this.restTemplate.delete("http://localhost:" + port + "/trainers/Ash", Trainer[].class);

        var NoAsh = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNull(NoAsh.getName());
    }
}
