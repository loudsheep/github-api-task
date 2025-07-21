package com.example.githubapitask.controllers;

import com.example.githubapitask.dto.RepositoryDto;
import com.example.githubapitask.services.GithubService;

import java.util.List;

public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    public List<RepositoryDto> getRepositories(String username) {
        return githubService.getNonForkRepositories(username);
    }
}
