package com.pranay.code_review_platform_backend.security;

import com.pranay.code_review_platform_backend.entity.GithubUser;
import com.pranay.code_review_platform_backend.service.GithubUserService;
import com.pranay.code_review_platform_backend.service.TokenEncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GithubOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final GithubUserService githubUserService;
    private final TokenEncryptionService tokenEncryptionService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        OAuth2User oauthUser = oauthToken.getPrincipal();

        String registrationId = oauthToken.getAuthorizedClientRegistrationId();

        OAuth2AuthorizedClient authorizedClient =
                authorizedClientService.loadAuthorizedClient(
                        registrationId,
                        oauthUser.getName()
                );

        String accessToken = authorizedClient
                .getAccessToken()
                .getTokenValue();

        String githubId = oauthUser.getAttribute("id").toString();
        String githubUsername = oauthUser.getAttribute("login");
        String githubEmail = oauthUser.getAttribute("email");
        String avatarUrl = oauthUser.getAttribute("avatar_url");

        String encryptedToken =
                tokenEncryptionService.encrypt(accessToken);

        GithubUser githubUser = githubUserService
                .findByGithubId(githubId)
                .orElse(
                        GithubUser.builder()
                                .githubId(githubId)
                                .build()
                );

        githubUser.setGithubUsername(githubUsername);
        githubUser.setGithubEmail(githubEmail);
        githubUser.setAvatarUrl(avatarUrl);
        githubUser.setAccessToken(encryptedToken);

        githubUserService.save(githubUser);

        response.sendRedirect("/");
    }
}
