package com.example.githubapitask;

import com.example.githubapitask.dto.BranchDto;
import com.example.githubapitask.dto.RepositoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubIntegrationTest {
    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void shouldReturnNonForkRepositoriesWithBranchesForValidUser() {
        // Given
        String username = "octocat"; // public GitHub user with known repos
        String url = "http://127.0.0.1:" + port + "/api/github/" + username + "/repos";

        // When
        ResponseEntity<RepositoryDto[]> response = restTemplate.getForEntity(url, RepositoryDto[].class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RepositoryDto[] repositories = response.getBody();
        assertThat(repositories).isNotNull();
        assertThat(repositories.length).isGreaterThan(0);

        for (RepositoryDto repo : repositories) {
            assertThat(repo.name()).isNotBlank();
            assertThat(repo.ownerLogin()).isEqualToIgnoringCase(username);
            assertThat(repo.branches()).isNotEmpty();

            for (BranchDto branch : repo.branches()) {
                assertThat(branch.name()).isNotBlank();
                assertThat(branch.lastCommitSha()).matches("^[a-f0-9]{40}$"); // SHA-1 hash format
            }
        }
    }
}
