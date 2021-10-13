import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Leagues {

    public ArrayList<Player> allPlayers;
    public ArrayList<Player> leagueOne=new ArrayList<>(250);
    public ArrayList<Player> leagueOne1=new ArrayList<>(250);
    public ArrayList<Player> leagueTwo=new ArrayList<>(250);
    public ArrayList<Player> leagueThree=new ArrayList<>(250);
    public ArrayList<Player> leagueFour=new ArrayList<>(250);
    public int count=0;
    public Leagues(ArrayList<Player> players){
        allPlayers=players;
        allPlayers.sort(Comparator.comparing(Player::getMmr));
        leagueOne.addAll(players.subList(0,allPlayers.size()/4));
        leagueTwo.addAll(players.subList(allPlayers.size()/4,allPlayers.size()/2));
        leagueThree.addAll(players.subList(allPlayers.size()/2,allPlayers.size()*3/4));
        leagueFour.addAll(players.subList(allPlayers.size()*3/4,allPlayers.size()));
    }

    private void chekDelete(ArrayList<Player> leg){
        for (Player p:leg) {
            int random=new Random().nextInt(1000);
            if (random+p.getWinstreak()*4<25&&p.getWinstreak()<0){
                Player temp=new Player(RandomString.getString(),new Random().nextInt(7500));
                leg.set(leg.indexOf(p),temp);
                allPlayers.set(allPlayers.indexOf(p),temp);
                count++;
            }
        }
    }

    public void deleteOrNotDelete(){
        chekDelete(leagueOne);
        chekDelete(leagueTwo);
        chekDelete(leagueThree);
        chekDelete(leagueFour);
    }
    public boolean removePlayer(String name){
       return allPlayers.removeIf(it->it.getNickname().equals(name))&&(leagueOne.removeIf(it->it.getNickname().equals(name))||
               leagueTwo.removeIf(it->it.getNickname().equals(name))||
               leagueThree.removeIf(it->it.getNickname().equals(name))||
               leagueFour.removeIf(it->it.getNickname().equals(name)));
    }

    public void showList(ArrayList<Player> list) throws IOException, InterruptedException {
        Scanner in=new Scanner(System.in);
        int count=1;
        System.out.println("+---+----------+----------+----------+");
        System.out.printf("|%-3s|%-10s|%-10s|%-10s|\n","№","Name","Mmr","Winstreak");
        for (Player p : list) {
            if (p.getWinstreak()>=0){
                System.out.println("|---+----------+----------+----------|");
                System.out.printf("|%-3d|%-10s|%-10d| %-9d|\n",count,p.getNickname(),p.getMmr(),p.getWinstreak());
            }
            else{
                System.out.println("|---+----------+----------+----------|");
                System.out.printf("|%-3d|%-10s|%-10d|%-10d|\n",count,p.getNickname(),p.getMmr(),p.getWinstreak());
            }
            if (count%10==0||list.size()==count){
                System.out.println("+---+----------+----------+----------+");
                String str;
                if (list.size()!=count) {
                    System.out.println("n-next/s-stop?");
                    while (true) {
                        str = in.nextLine();
                        if (str.equals("n"))break;
                        else if (str.equals("s")) return;
                            else continue;
                    }
                }
                else {
                    System.out.println("END");
                    System.out.println("Для продолжения нажмите любую клавишу...");
                        str = in.nextLine();
                }
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                System.out.println("+---+----------+----------+----------+");
                System.out.printf("|%-3s|%-10s|%-10s|%-10s|\n","№","Name","Mmr","Winstreak");
            }
            count++;
        }
    }

    public void swapPlayers(){
        leagueOne.sort(Comparator.comparing(Player::getMmr));
        leagueTwo.sort(Comparator.comparing(Player::getMmr));
        leagueThree.sort(Comparator.comparing(Player::getMmr));
        leagueFour.sort(Comparator.comparing(Player::getMmr));
        leagueOne1.addAll(leagueOne);
        ArrayList<Player> temp1=new ArrayList<>();
        ArrayList<Player> temp2=new ArrayList<>();
        ArrayList<Player> temp3=new ArrayList<>();
        ArrayList<Player> temp4=new ArrayList<>();
        temp1.addAll(leagueOne.subList(leagueOne.size()*9/10,leagueOne.size()));//конец 1 лиги
        temp2.addAll(leagueTwo.subList(0,leagueTwo.size()/10));//начало 2 лиги
        temp3.addAll(leagueTwo.subList(leagueTwo.size()*9/10,leagueTwo.size()));//конец 2 лиги
        temp4.addAll(leagueOne.subList(0,leagueOne.size()*9/10));//основная часть 1 лиги
        leagueOne.clear();
        leagueOne.addAll(temp4);
        leagueOne.addAll(temp2);
        temp4.clear();
        temp2.clear();
        temp2.addAll(leagueThree.subList(0,leagueThree.size()/10));//начало 3 лиги
        temp4.addAll(leagueTwo.subList(leagueTwo.size()/10,leagueTwo.size()*9/10));//соновная часть 2 лиги
        leagueTwo.clear();
        leagueTwo.addAll(temp1);
        leagueTwo.addAll(temp4);
        leagueTwo.addAll(temp2);
        temp1.clear();
        temp4.clear();
        temp2.clear();
        temp1.addAll(leagueThree.subList(leagueThree.size()*9/10,leagueThree.size()));//конец 3 лиги
        temp2.addAll(leagueThree.subList(leagueThree.size()/10,leagueThree.size()*9/10));//основная часть 3 лиги
        temp4.addAll(leagueFour.subList(0,leagueFour.size()/10));//начало 4 лиги
        leagueThree.clear();
        leagueThree.addAll(temp3);
        leagueThree.addAll(temp2);
        leagueThree.addAll(temp4);
        temp2.clear();
        temp3.clear();
        temp4.clear();
        temp2.addAll(leagueFour.subList(leagueFour.size()/10,leagueFour.size()));//основная часть 4 лиги
        leagueFour.clear();
        leagueFour.addAll(temp1);
        leagueFour.addAll(temp2);
        temp1.clear();
        temp2.clear();
    }

}
