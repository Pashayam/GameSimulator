import java.util.*;

public class Game {
    private int gameMode;
     private ArrayList<Player> teamOne=new ArrayList<>(5);
     private ArrayList<Player> teamTwo=new ArrayList<>(5);
     private boolean winTeam;

    public Game(int gameMode) {
        this.gameMode = gameMode;
    }

    private void makeTeams(ArrayList<Player> leaguePlayers){
         switch (gameMode){
             case 1:
                 makeTeamRandom (leaguePlayers,teamOne);
                 makeTeamRandom(leaguePlayers,teamTwo);
                 break;
             case 2:
                 leaguePlayers.sort(Comparator.comparing(Player::gamePrioritet));
                 makeTeam (leaguePlayers,teamOne);
                 makeTeam(leaguePlayers,teamTwo);
                 break;
             case 3:
                 leaguePlayers.sort(Comparator.comparing(Player::getDateLastGame));
                 makeTeam (leaguePlayers,teamOne);
                 makeTeam(leaguePlayers,teamTwo);
                 break;
         }
    }
     private void makeTeamRandom(ArrayList<Player> leaguePlayers,ArrayList<Player> team){
        for (int i = 0; i < 5; i++) {
            Player temp=leaguePlayers.get(new Random().nextInt(leaguePlayers.size()));
            while (temp.isStatusGame()) {
                temp=leaguePlayers.get(new Random().nextInt(leaguePlayers.size()));
            }
            temp.setStatusGame(true);
            team.add(temp);
        }
    }
    private int gesAvgTeamOne(){
         int cour=0;
        for (Player p:teamOne) {
            cour+=p.getMmr();
        }
        return cour/5;
    }
    private void makeTeam(ArrayList<Player> leaguePlayers,ArrayList<Player> team) {
        int lastMMR = 0;
        if (team == teamOne) {
            for (int i = 0; i < 5; i++) {
                mark:
                for (Player p : leaguePlayers) {
                    if (i == 0 && !p.isStatusGame()) {
                        team.add(p);
                        p.setStatusGame(true);
                        lastMMR = p.getMmr();
                        break mark;
                    } else if (!p.isStatusGame() && (lastMMR - p.getMmr() < 300) && (lastMMR - p.getMmr() > -300)) {
                        team.add(p);
                        p.setStatusGame(true);
                        break mark;
                    }
                }
            }
        } else {
            lastMMR = gesAvgTeamOne();
            for (int i = 0; i < 5; i++) {

                mark:
                for (Player p : leaguePlayers) {
                    if (!p.isStatusGame() && (lastMMR - p.getMmr() < 300) && (lastMMR - p.getMmr() > -300)) {
                        team.add(p);
                        p.setStatusGame(true);
                        break mark;
                    }
                }
            }
        }
    }
     private void whoWin(){
        int random=new Random().nextInt(100);
        random+=(getMrr()/400);
        if(random>50)winTeam=false;
        else winTeam=true;
    }
     private int getMrr(){
        int result=0;
        for (Player p:teamOne) {
            result+=p.getMmr();
        }
        for (Player p:teamOne) {
            result-=p.getMmr();
        }
        return result;
    }
     public void toGame(ArrayList<Player> lig){
        makeTeams(lig);
        int randomMrr=(new Random().nextInt(10)+15);
        whoWin();
        Date dateGame=new Date();
        if (winTeam){
            for (Player p:teamOne) {
                p.setStatusGame(false);
                p.addMrr(randomMrr);
                if(p.getWinstreak()>=0)p.setWinstreak(p.getWinstreak()+1);
                else p.setWinstreak(1);
                p.setDateLastGame(dateGame);
            }
        }else {
            for (Player p:teamTwo) {
                p.setStatusGame(false);
                if (p.getMmr()-randomMrr<=0)p.setMmr(0);
                else p.addMrr(-randomMrr);
                if(p.getWinstreak()>=0)p.setWinstreak(-1);
                else p.setWinstreak(p.getWinstreak()-1);
                p.setDateLastGame(dateGame);
            }
        }
         if (!winTeam){
             for (Player p:teamOne) {
                 p.setStatusGame(false);
                 p.addMrr(randomMrr);
                 if(p.getWinstreak()>=0)p.setWinstreak(p.getWinstreak()+1);
                 else p.setWinstreak(1);
                 p.setDateLastGame(dateGame);
             }
         }else {
             for (Player p:teamTwo) {
                 p.setStatusGame(false);
                 if (p.getMmr()-randomMrr<=0)p.setMmr(0);
                 else p.addMrr(-randomMrr);
                 if(p.getWinstreak()>=0)p.setWinstreak(-1);
                 else p.setWinstreak(p.getWinstreak()-1);
                 p.setDateLastGame(dateGame);
             }
         }
        teamTwo.clear();
        teamOne.clear();
    }

}
