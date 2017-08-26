package game;

import gameoflife.Simulator;

public class Main {

    public static void main(String[] args) {
        /*PersonalBoard board = new PersonalBoard(3, 3);
        board.initiateRandomCells(0.5);

        GameOfLifeTester tester = new GameOfLifeTester(board);
        System.out.println(board.isAlive(0, 0));
        tester.play();*/
        
        PersonalBoard board = new PersonalBoard(100, 100);
        board.initiateRandomCells(0.7);

        Simulator simulator = new Simulator(board);
        simulator.simulate();
        
        
    }
}
