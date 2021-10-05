import labs.RandomString;

import java.io.IOException;
import java.util.*;

public class Main {
    private static int countDay,gameMode;
    private static boolean gameInDay;
    private static Leagues leagues;
    private static ArrayList<Player> players=new ArrayList<>(1000);

    public static void main(String[] args) throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        for (int i = 0; i < 1000; i++) {
            players.add(new Player(RandomString.getString(),new Random().nextInt(7500)));
        }
        leagues=new Leagues(players);
        menu();
    }

    public static void swichKrit(ArrayList<Player> list) throws IOException, InterruptedException {
        int key=CheckerClass.checkKrit();
        switch (key){
            case 1:
                list.sort(Comparator.comparing(Player::getNickname));
                leagues.showList(list);
                break;
            case 2:
                list.sort(Comparator.comparing(Player::getMmr));
                leagues.showList(list);
                break;
            case 3:
                list.sort(Comparator.comparing(Player::getWinstreak));
                leagues.showList(list);
                break;
        }
    }

    public static void switchLeague() throws IOException, InterruptedException {
        int key=CheckerClass.getLig();
        switch (key){
            case 1:
                swichKrit(leagues.leagueOne);
                break;
            case 2:
                swichKrit(leagues.leagueTwo);
                break;
            case 3:
                swichKrit(leagues.leagueThree);
                break;
            case 4:
                swichKrit(leagues.leagueFour);
                break;
        }
    }

    public static void menu() throws IOException, InterruptedException {
        Scanner in=new Scanner(System.in);
        while (true) {
            int key =CheckerClass.getInt();
            switch (key) {
                case 1:
                    leaugeGame(leagues);
                    System.out.println("Кол-во игроков удаливших игру=" + leagues.count);
                    System.out.println("Для продолжения нажмите любую клавишу...");
                    in.nextLine();
                    break;
                case 2:
                    leagues.showList(leagues.allPlayers);
                    break;
                case 3:
                    switchLeague();
                    break;
                case 4:
                    System.out.println("Введите имя игрока:");
                    String name = in.nextLine();
                    if (leagues.removePlayer(name)){
                        System.out.println("Игрок с ником:" + name + " удален");
                        System.out.println("Для продолжения нажмите любую клавишу...");
                        in.nextLine();
                    }
                    else {
                        System.out.println("Такого игрока не существует");
                        System.out.println("Для продолжения нажмите любую клавишу...");
                        in.nextLine();
                    }
                    break;
                case 5:
                    return;
            }
        }
    }

    public static void leaugeGame(Leagues leagues) throws IOException, InterruptedException {
        int numberOfGamePerDay=0;
        Scanner in=new Scanner(System.in);
        String strSwich,strSwichPlay;
        mark:while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("Лига играет до последнего игрока? Y/N");
            strSwich=in.nextLine();
            switch (strSwich){
                case "Y":
                    gameInDay = true;
                    break mark;
                case "N":
                    gameInDay=false;
                    marke2:while (true){
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        System.out.println("Лига играет до последнего игрока? Y/N");
                        System.out.println(strSwich);
                        System.out.println("Введите кол-во игр в день:");
                        numberOfGamePerDay=CheckerClass.getInt(strSwich);
                        break mark;
                    }
            }
        }
        mark:while (true) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("Лига играет до последнего игрока? Y/N");
            System.out.println(strSwich);
            if (!gameInDay){
                System.out.println("Введите кол-во игр в день:");
                System.out.println(numberOfGamePerDay);
            }
            System.out.println("Выберите режим подбора игроков: Random(R)/Mmr(M)/LastGame(L)");
            strSwichPlay=in.nextLine();
            switch (strSwichPlay){
                case "R":
                    gameMode=1;
                    break mark;
                case "M":
                    gameMode=2;
                    break mark;
                case "L":
                    gameMode=3;
                    break mark;
            }
        }
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        System.out.println("Лига играет до последнего игрока? Y/N");
        System.out.println(strSwich);
        if (!gameInDay){
            System.out.println("Введите кол-во игр в день:");
            System.out.println(numberOfGamePerDay);
        }
        System.out.println("Выберите режим подбора игроков: Random(R)/Mmr(M)/LastGame(L)");
        System.out.println(strSwichPlay);
        System.out.println("Введите сколько дней вы хотите пропустить:");
        int numberOfSkippedDays=CheckerClass.getInt(strSwich,strSwichPlay,numberOfGamePerDay);
        Game game=new Game(gameMode);
        for ( int j = 0; j < numberOfSkippedDays; j++) {
            if (gameInDay){
                while (leagues.leagueTwo.stream().anyMatch(it->it.getCountGamesThisDay()==0)) {
                    game.toGame(leagues.leagueTwo);
                }
                while (leagues.leagueOne.stream().anyMatch(it->it.getCountGamesThisDay()==0)) {
                    game.toGame(leagues.leagueOne);
                }
                while (leagues.leagueThree.stream().anyMatch(it->it.getCountGamesThisDay()==0)) {
                    game.toGame(leagues.leagueThree);
                }
                while (leagues.leagueFour.stream().anyMatch(it->it.getCountGamesThisDay()==0)) {
                    game.toGame(leagues.leagueFour);
                }
            }else {
                for (int i = 0; i < numberOfGamePerDay; i++) {
                    int randSwitch = new Random().nextInt(4);
                    switch (randSwitch) {
                        case 0:
                            game.toGame(leagues.leagueOne);
                            break;
                        case 1:
                            game.toGame(leagues.leagueTwo);
                            break;
                        case 2:
                            game.toGame(leagues.leagueThree);
                            break;
                        case 3:
                            game.toGame(leagues.leagueFour);
                            break;
                    }
                }
            }
            leagues.deleteOrNotDelete();
            countDay++;
            if (countDay==30){
                leagues.swapPlayers();
                countDay=0;
            }
            for (Player p:players) {
                p.setCountGamesThisDay(0);
            }
        }
    }

    private static class CheckerClass {
        private static int checkKrit() throws IOException, InterruptedException {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            int key;
            System.out.println("Выберите критерий сортировки:");
            System.out.println("1.Nickname");
            System.out.println("2.Mmr");
            System.out.println("3.Winstreak");
            System.out.println("4.Выход");
            Scanner in=new Scanner(System.in);
            try {
                key=in.nextInt();
                return key;
            }catch (Exception e){
                return checkKrit();
            }
        }

        private static int getLig() throws IOException, InterruptedException {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            int key;
            System.out.println("Выберите лигу:");
            System.out.println("1.leagueOne");
            System.out.println("2.leagueTwo");
            System.out.println("3.leagueThree");
            System.out.println("4.leagueFore");
            System.out.println("5.Выход");
            Scanner in=new Scanner(System.in);
            try {
                key=in.nextInt();
                return key;
            }catch (Exception e){
                return getLig();
            }
        }
        private static int getInt() throws IOException, InterruptedException {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            int key;
            System.out.println("Выберите действие:");
            System.out.println("1.Играть");
            System.out.println("2.Вывести список всех игроков");
            System.out.println("3.Вывести список игроков из одной лиги");
            System.out.println("4.Удалить игрока");
            System.out.println("5.Выход");
            Scanner in=new Scanner(System.in);
            try {
                key=in.nextInt();
                return key;
            }catch (Exception e){
                return getInt();
            }
        }

        private static int getInt(String strSwich) throws IOException, InterruptedException {
            Scanner in=new Scanner(System.in);
            int numberOfGamePerDay;
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("Лига играет до последнего игрока? Y/N");
            System.out.println(strSwich);
            System.out.println("Введите кол-во игр в день:");
            try {
                numberOfGamePerDay=in.nextInt();
                return numberOfGamePerDay;
            }catch (Exception e){
                return getInt(strSwich);
            }
        }
        private static int getInt(String strSwich,String strSwich2,int day) throws IOException, InterruptedException {
            Scanner in=new Scanner(System.in);
            int numberOfGamePerDay;
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("Лига играет до последнего игрока? Y/N");
            System.out.println(strSwich);
            if (!gameInDay){
                System.out.println("Введите кол-во игр в день:");
                System.out.println(day);
            }
            System.out.println("Выберите режим подбора игроков: Random(R)/Mmr(M)/LastGame(L)");
            System.out.println(strSwich2);
            System.out.println("Введите сколько дней вы хотите пропустить:");
            try {
                numberOfGamePerDay=in.nextInt();
                return numberOfGamePerDay;
            }catch (Exception e){
                return getInt(strSwich,strSwich2,day);
            }
        }
    }

}