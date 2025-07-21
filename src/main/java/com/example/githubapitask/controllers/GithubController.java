package com.example.githubapitask.controllers;

import com.example.githubapitask.dto.RepositoryDto;
import com.example.githubapitask.services.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repos")
    public List<RepositoryDto> getRepositories(@PathVariable String username) {
        return githubService.getNonForkRepositories(username);
    }
}
