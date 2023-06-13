package com.construcao.financiase.reward.exception;

import jakarta.persistence.EntityExistsException;

public class RewardAlreadyExistsException extends EntityExistsException {

    public RewardAlreadyExistsException(int value ) {
        super(String.format("Project with value %s already exists!", value));
    }

}
