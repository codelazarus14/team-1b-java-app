//display the full maze in console
package edu.vassar.cmpu203.dungeongame.model;
import java.util.Locale;
import java.util.Scanner;

public class DisplayMazeConsole {
    public DisplayMazeConsole(){}

    public static GameController g = new GameController();

    private static String toString(int size, Maze m, Player p) {
        String out = "";
        char lSymbol = 8838;
        char rSymbol = 8839;
        String avatar = Character.toString(lSymbol) + Character.toString(rSymbol);

        for (int i = 0; i < size; i++) {
            out += "+ == ";
        }
        out += "+\n";
        for (int i = 0; i < size; i++) {
            out += "|";
            for (int j = 0; j < size; j++) {
                out += " ";
                out += p.getPos()[1] == i && p.getPos()[0] == j ? avatar : "  ";
                out += m.mazeArray[i][j].rbarrier ? " |" : "  ";
            }
            out += "\n";
            for (int j = 0; j < size; j++) {
                out += m.mazeArray[i][j].dbarrier ? "+ == " : "+    ";
            }
            out += "+\n";
        }

        out += " ";
        return out;
    }

    public static void ui(int size, Maze m, Player p) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Use WASD to move");

        while (true) {
            System.out.println(toString(size, m, p));

            String mov = scanner.nextLine().toLowerCase(Locale.ROOT);

            switch (mov) {
                case "w":
                case "up":
                    g.handleMovement(p, m, 'u');
                    continue;
                case "a":
                case "left":
                    g.handleMovement(p, m, 'l');
                    continue;
                case "s":
                case "down":
                    g.handleMovement(p, m, 'd');
                    continue;
                case "d":
                case "right":
                    g.handleMovement(p, m, 'r');
                    continue;
                case "print":
                    System.out.println("x:" + p.getPos()[0] + " y:" + p.getPos()[1]);
                    try {
                        System.out.println("up:" + m.mazeArray[p.getPos()[1] - 1][p.getPos()[0]].dbarrier);
                    } catch (Exception e) {
                        System.out.println("up:" + e);
                    }
                    try {
                        System.out.println("left:" + m.mazeArray[p.getPos()[1]][p.getPos()[0] - 1].rbarrier);
                    } catch (Exception e) {
                        System.out.println("left:" + e);
                    }
                    try {
                        System.out.println("down:" + m.mazeArray[p.getPos()[1]][p.getPos()[0]].dbarrier);
                    } catch (Exception e) {
                        System.out.println("down:" + e);
                    }
                    try {
                        System.out.println("right:" + m.mazeArray[p.getPos()[1]][p.getPos()[0]].rbarrier);
                    } catch (Exception e) {
                        System.out.println("right:" + e);
                    }
                    scanner.nextLine();
                    continue;
                default:
//                    System.out.println(m.printWalls(p.getPos()));
            }
            break;
        }
    }

    public static void main(String[] args) {
        int size = 25;
        Maze m = new Maze(size);
        m.buildMaze();
        Player p = new Player(0,0);
        ui(size, m, p);
    }
}