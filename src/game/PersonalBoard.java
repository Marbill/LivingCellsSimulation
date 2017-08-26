package game;

import gameoflife.GameOfLifeBoard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import jdk.nashorn.internal.ir.ContinueNode;

/* @author marbi */
public class PersonalBoard extends GameOfLifeBoard {

    public PersonalBoard(int width, int height) {
        super(width, height);
    }

    @Override
    public void initiateRandomCells(double d) {
        List<Integer> widthList = new ArrayList<Integer>();
        List<Integer> heightList = new ArrayList<Integer>();
        double items = (super.getWidth() * super.getHeight()) * d;

        while (widthList.size() < Math.floor(items)) {
            int tempW = new Random().nextInt(super.getWidth());
            int tempH = new Random().nextInt(super.getHeight());
            boolean found = false;

            if (widthList.size() > 0) {
                for (int i = 0; i < widthList.size(); i++) {
                    if (widthList.get(i) == tempW && heightList.get(i) == tempH) {
                        found = true;
                        break;
                    }
                }
            }

            if (found == false) {
                widthList.add(tempW);
                heightList.add(tempH);
            }
        }

        for (int i = 0; i < widthList.size(); i++) {
            turnToLiving(widthList.get(i), heightList.get(i));
        }

    }

    @Override
    public boolean isAlive(int i, int i1) {
        if (i >= super.getWidth() || i1 >= super.getHeight()) {
            return false;
        }

        if (i < 0 || i1 < 0) {
            return false;
        }

        return super.getBoard()[i][i1];
    }

    @Override
    public void turnToLiving(int i, int i1) {
        if ((i >= 0 && i < super.getWidth()) && (i1 >= 0 && i1 < super.getHeight())) {
            super.getBoard()[i][i1] = true;
        }

        //super.getBoard()[i][i1] = true;
    }

    @Override
    public void turnToDead(int i, int i1) {
        if ((i >= 0 && i < super.getWidth()) && (i1 >= 0 && i1 < super.getHeight())) {
            super.getBoard()[i][i1] = false;
        }

    }

    @Override
    public int getNumberOfLivingNeighbours(int i, int i1) {
        //return typeCorner(i, i1);
        //return typeSide(i, i1);
        int typeCell = typeCorner(i, i1);
        if (typeCell != 1111) {
            return typeCorner(i, i1);
        }

        typeCell = typeSide(i, i1);
        if (typeCell != 2222) {
            return typeSide(i, i1);
        }

        return central(i, i1);

        //return 0;
    }

    @Override
    public void manageCell(int x, int y, int livingNeighbours) {
        if (livingNeighbours < 2) {
            turnToDead(x, y);
        }
        if (livingNeighbours > 3) {
            turnToDead(x, y);
        }
        if (livingNeighbours == 3) {
            if (!isAlive(x, y)) {
                turnToLiving(x, y);
            }
        }
    }

    private int typeCorner(int w, int h) {
        if (w == 0 && h == 0) {
            return topLeftCorner();
        } else if (w == super.getWidth() - 1 && h == 0) {
            return topRightCorner();
        } else if (w == 0 && h == super.getHeight() - 1) {
            return bottomLeftCorner();
        } else if (w == super.getWidth() - 1 && h == super.getHeight() - 1) {
            return bottomRightCorner();
        }

        return 1111;
    }

    private int topRightCorner() {
        int total = 0;
        for (int i = super.getWidth() - 2; i < super.getWidth(); i++) {
            for (int j = 0; j < 2; j++) {
                if (i == super.getWidth() - 1 && j == 0) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private int topLeftCorner() {
        int total = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private int bottomLeftCorner() {
        int total = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = super.getHeight() - 2; j < super.getHeight(); j++) {
                if (i == 0 && j == super.getHeight()) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private int bottomRightCorner() {
        int total = 0;
        for (int i = super.getWidth() - 2; i < super.getWidth(); i++) {
            for (int j = super.getHeight() - 2; j < super.getHeight(); j++) {
                if (i == super.getWidth() - 1 && j == super.getHeight() - 1) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private int typeSide(int w, int h) {
        if (w == 0 && (h > 0 && h <= super.getHeight() - 2)) {
            return leftSide(h);
        } else if (w == super.getWidth() - 1 && (h > 0 && h <= super.getHeight() - 2)) {
            return rightSide(w, h);
        } else if ((w > 0 && w <= super.getWidth() - 2) && h == 0) {
            return topSide(w);
        } else if ((w > 0 && w <= super.getWidth() - 2) && h == super.getHeight() - 1) {
            return bottomSide(w, h);
        }

        return 2222;
    }

    private int leftSide(int h) {
        int total = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = h - 1; j < h + 2; j++) {
                if (i == 0 && j == h) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private int rightSide(int w, int h) {
        int total = 0;
        for (int i = w - 1; i <= w; i++) {
            for (int j = h - 1; j <= h + 1; j++) {
                if (i == w && j == h) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }

        return total;
    }

    private int topSide(int w) {
        int total = 0;
        for (int i = w - 1; i <= w + 1; i++) {
            for (int j = 0; j <= 1; j++) {
                if (i == w && j == 0) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    private int bottomSide(int w, int h) {
        int total = 0;
        for (int i = w - 1; i <= w + 1; i++) {
            for (int j = h - 1; j <= h; j++) {
                if (i == w && j == h) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    private int central(int w, int h) {
        int total = 0;
        for (int i = w - 1; i <= w + 1; i++) {
            for (int j = h - 1; j <= h + 1; j++) {
                if (i == w && j == h) {
                    continue;
                } else {
                    if (isAlive(i, j)) {
                        total++;
                    }
                }
            }
        }
        return total;
    }
}
