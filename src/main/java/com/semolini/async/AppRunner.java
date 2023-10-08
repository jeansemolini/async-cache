package com.semolini.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

        CompletableFuture.allOf(page1, page2, page3).join();

        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());

        Thread.sleep(10000L);

        start = System.currentTimeMillis();

        CompletableFuture<User> page4 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page5 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page6 = gitHubLookupService.findUser("jeansemolini");

        CompletableFuture.allOf(page4, page5, page6).join();

        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page4.get());
        logger.info("--> " + page5.get());
        logger.info("--> " + page6.get());

        Thread.sleep(3000L);

        start = System.currentTimeMillis();

        CompletableFuture<User> page7 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page8 = gitHubLookupService.findUser("CloudFoundry");
        CompletableFuture<User> page9 = gitHubLookupService.findUser("jeansemolini");

        CompletableFuture.allOf(page7, page8, page9).join();

        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page7.get());
        logger.info("--> " + page8.get());
        logger.info("--> " + page9.get());
    }
}
