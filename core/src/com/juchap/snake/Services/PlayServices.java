package com.juchap.snake.Services;


public interface PlayServices {
    void signIn();
    void signOut();
    void rateGame();
    void unlockAchievement(String achievement);
    void incrementAchievement(String achievement, int amount);
    void submitScore(String difficulty, int score);
    void showAchievements();
    void showScores();
    void shareApp();
    boolean isSignedIn();
}
