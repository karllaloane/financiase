package com.construcao.financiase.reward.service;

import com.construcao.financiase.project.exception.ProjectNotFoundException;
import com.construcao.financiase.project.repository.ProjectRepository;
import com.construcao.financiase.reward.dto.RewardDTO;
import com.construcao.financiase.reward.entity.Reward;
import com.construcao.financiase.reward.exception.RewardAlreadyExistsException;
import com.construcao.financiase.reward.mapper.RewardMapper;
import com.construcao.financiase.reward.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    private final static RewardMapper rewardMapper = RewardMapper.INSTANCE;

    private RewardRepository rewardRepository;

    private ProjectRepository projectRepository;

    @Autowired
    public RewardService(RewardRepository rewardRepository, ProjectRepository projectRepository) {
        this.rewardRepository = rewardRepository;
        this.projectRepository = projectRepository;
    }

    public RewardDTO create(Long id, RewardDTO rewardDTO){

        verifyIfProjectExists(id);

        verifyIfRewardExists(rewardDTO);

        Reward rewardToCreate = rewardMapper.toModel(rewardDTO);
        Reward createdReward = rewardRepository.save(rewardToCreate);

        return rewardMapper.toDTO(createdReward);
    }

    private void verifyIfProjectExists(Long id) {
        projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    private void verifyIfRewardExists(RewardDTO rewardDTO) {
        rewardRepository.findByMinValue(rewardDTO.getMinValue())
                .ifPresent(reward -> {throw new RewardAlreadyExistsException(rewardDTO.getMinValue());
                });
    }
}
