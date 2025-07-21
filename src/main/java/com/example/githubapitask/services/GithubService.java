package com.example.githubapitask.services;

import com.example.githubapitask.dto.BranchDto;
import com.example.githubapitask.dto.RepositoryDto;
import com.example.githubapitask.exceptions.UserNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GithubService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String GITHUB_API_URL = "https://api.github.com";

    public List<RepositoryDto> getNonForkRepositories(String username) {
        String reposUrl = GITHUB_API_URL + "/users/" + username + "/repos";

        RepoResponse[] repos;
        try {
            repos = restTemplate.getForObject(reposUrl, RepoResponse[].class);
        } catch (RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("Non existing user");
            }
            throw e;
        }

        if (repos == null) return List.of();

        return Arrays.stream(repos).filter(repo -> !repo.fork).map(repo -> {
            BranchResponse[] branches = restTemplate.getForObject(
                    GITHUB_API_URL + "/repos/" + username + "/" + repo.name + "/branches",
                    BranchResponse[].class
            );

            List<BranchDto> branchDtos = Arrays.stream(branches != null ? branches : new BranchResponse[0])
                    .map(branch -> new BranchDto(branch.name, branch.commit.sha))
                    .collect(Collectors.toList());

            return new RepositoryDto(repo.name, repo.owner.login, branchDtos);
        }).collect(Collectors.toList());
    }

    private record RepoResponse(String name, boolean fork, Owner owner) {
        private record Owner(String login) {
        }
    }

    private record BranchResponse(String name, Commit commit) {
        private record Commit(String sha) {
        }
    }
}
