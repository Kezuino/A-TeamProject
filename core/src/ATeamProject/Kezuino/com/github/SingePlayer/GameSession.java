package ATeamProject.Kezuino.com.github.SingePlayer;

import java.util.Date;

public class GameSession {

	private Date startTime;
	private String pathToSkin;
	private Map Map;
	private Score Score;

	public Date getStartTime() {
		return this.startTime;
	}

	public String getPathToSkin() {
		return this.pathToSkin;
	}

	/**
	 * Will create a new gamesession and set the (relative) path from where the skin must be loaded.
	 * @param pathToSkin
	 * @param PlayerAmount
	 */
	public GameSession(String pathToSkin, int PlayerAmount) {
		// TODO - implement GameSession.GameSession
		throw new UnsupportedOperationException();
	}

	/**
	 * Will return a pactale if found with the specific playerIndex. If not found, null will be returned.
	 * @param playerIndex
	 */
	public Pactale findPactale(int playerIndex) {
		// TODO - implement GameSession.getPactale
		throw new UnsupportedOperationException();
	}

}