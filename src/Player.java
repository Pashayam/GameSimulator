import java.util.Date;

public class Player {
    private String nickname;
    private int mmr;
    private int winstreak;
    private boolean statusGame;
    private Date dateLastGame=new Date();
    private int countGamesThisDay;

    public void setDateLastGame(Date dateLastGame) {
        this.dateLastGame = dateLastGame;
    }

    public int getCountGamesThisDay() {
        return countGamesThisDay;
    }

    public void setCountGamesThisDay(int countGamesThisDay) {
        this.countGamesThisDay = countGamesThisDay;
    }

    public Date getDateLastGame() {

        return dateLastGame;
    }

    public long gamePrioritet(){
        return countGamesThisDay*10+mmr%100;
//        return dateLastGame.getTime();
    }

    public void setStatusGame(boolean statusGame) {
        this.statusGame = statusGame;
    }
    public void addMrr(int mmr){
        this.mmr+=mmr;
    }
    public boolean isStatusGame() {

        return statusGame;
    }

    public Player(String nickname, int mmr, int winstreak) {
        this.nickname = nickname;
        this.mmr = mmr;
        this.winstreak = winstreak;
    }

    public void setNickname(String nickname) {

        this.nickname = nickname;
    }

    public Player(String nickname, int mmr) {
        this.nickname = nickname;
        this.mmr = mmr;
    }

    public void setMmr(int mmr) {
        this.mmr = mmr;

    }

    public void setWinstreak(int winstreak) {
        this.winstreak = winstreak;
        countGamesThisDay++;
    }

    public String getNickname() {

        return nickname;
    }

    public int getMmr() {
        return mmr;
    }

    public int getWinstreak() {
        return winstreak;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", mmr=" + mmr +
                ", winstreak=" + winstreak +
                '}';
    }

}
