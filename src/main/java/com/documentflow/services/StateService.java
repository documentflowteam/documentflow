package com.documentflow.services;

import com.documentflow.entities.DTOs.StateDTO;
import com.documentflow.entities.State;
import com.documentflow.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StateService {

    private StateRepository stateRepository;

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State convertFromDTO(StateDTO stateDTO){
        return new State(stateDTO.getId(),
                stateDTO.getName(),
                stateDTO.getBusinessKey());
    }

    public StateDTO convertToDTO(State state){
        return new StateDTO(state.getId(),
                state.getName(),
                state.getBusinessKey());
    }
}
