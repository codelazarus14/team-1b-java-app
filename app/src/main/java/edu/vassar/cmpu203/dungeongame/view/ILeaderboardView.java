package edu.vassar.cmpu203.dungeongame.view;

public interface ILeaderboardView {
    interface Listener {
        void onPlayerNameInput(String name, ILeaderboardView leaderboardView);
        void onReturnToMenu(ILeaderboardView leaderboardView);
    }
    void updateLeaderboardView(String lbText);
}
