package com.miage.altea.trainer_api.service;

import com.miage.altea.trainer_api.bo.Trainer;
import com.miage.altea.trainer_api.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Iterable<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer getTrainer(String name) {
        return trainerRepository.findById(name).orElse(new Trainer());
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainer(Trainer trainer, String name) {
        return trainerRepository.save(trainer);
    }

    @Override
    public void deleteTrainerById(String name) {
        trainerRepository.deleteById(name);
    }

}
