package com.semolini.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class GitHubLookupService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

    private final RestTemplate restTemplate;

    @Autowired
    CacheManager cacheManager;

    public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async("exercicio")
    @Cacheable("urls")
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        logger.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
//        User results = restTemplate.getForObject(url, User.class);

        User results = new User();
        results.setName(user);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

    @Scheduled(fixedRate = 10000)
    public void emptyUrlsCache() {
//        Cache urls = cacheManager.getCache("urls");
        logger.info(cacheManager.getCache("urls").getNativeCache().toString());
        logger.info("emptying URLs cache");
    }
}
