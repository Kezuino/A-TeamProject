/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.pathfinding;

import ateamproject.kezuino.com.github.singleplayer.Direction;
import ateamproject.kezuino.com.github.singleplayer.Map;
import ateamproject.kezuino.com.github.singleplayer.Node;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Jip
 */
public class GameObjectPathfinding {

    public ArrayList<Node> generatePath(Node startPosition, Node endPosition) {
        ArrayList<Node> pathToReturn = new ArrayList<>();
        Map map = startPosition.getMap();
        printMap(map, startPosition, endPosition);

        boolean targetReached = false;
        boolean impossible = false;
        Point currentPosition = new Point(startPosition.getX(), startPosition.getY());//current position is copy of startPosition and will be modified

        while (!targetReached) {
            if (impossible) {
                return null;
            }

            boolean madeMove = false;

            if (canNodeBeWalked(currentPosition, Direction.Up, map)) {
                if ((Math.abs(currentPosition.y - 1 - endPosition.getY())) < (Math.abs(currentPosition.y - endPosition.getY()))) {//check if the distance is smalle when currentposition.y--
                    currentPosition.y--;
                    pathToReturn.add(map.getNode(currentPosition.x, currentPosition.y));

                    madeMove = true;
                }
            }
            if (canNodeBeWalked(currentPosition, Direction.Right, map)) {
                if ((Math.abs(currentPosition.x + 1 - endPosition.getX())) < (Math.abs(currentPosition.x - endPosition.getX()))) {//check if the distance is smalle when currentposition.x++
                    currentPosition.x++;
                    pathToReturn.add(map.getNode(currentPosition.x, currentPosition.y));

                    madeMove = true;
                }
            }
            if (canNodeBeWalked(currentPosition, Direction.Down, map)) {
                if ((Math.abs(currentPosition.y + 1 - endPosition.getY())) < (Math.abs(currentPosition.y - endPosition.getY()))) {//check if the distance is smalle when currentposition.y++
                    currentPosition.y++;
                    pathToReturn.add(map.getNode(currentPosition.x, currentPosition.y));

                    madeMove = true;
                }
            }
            if (canNodeBeWalked(currentPosition, Direction.Left, map)) {//check if there is a node for UP
                if ((Math.abs(currentPosition.x - 1 - endPosition.getX())) < (Math.abs(currentPosition.x - endPosition.getX()))) {//check if the distance is smalle when currentposition.x--
                    currentPosition.x--;
                    pathToReturn.add(map.getNode(currentPosition.x, currentPosition.y));

                    madeMove = true;
                }
            }

            if (!madeMove) {

            } else if (currentPosition.x == endPosition.getX() && currentPosition.y == endPosition.getY()) {//if a move is made. check if the target is reached
                targetReached = true;
            }
        }

        printPath(map, pathToReturn, startPosition, endPosition);
        return pathToReturn;
    }

    private boolean canNodeBeWalked(Point currentPosition, Direction direction, Map map) {
        if (direction == Direction.Up) {
            if (map.getNode(currentPosition.x, currentPosition.y - 1) != null) {//check if there is a node for UP
                if (map.getNode(currentPosition.x, currentPosition.y - 1).isWall()) {//check if there is not a wall for UP
                    return true;
                }
            }
        } else if (direction == Direction.Right) {
            if (map.getNode(currentPosition.x + 1, currentPosition.y) != null) {//check if there is a node for RIGHT
                if (map.getNode(currentPosition.x + 1, currentPosition.y).isWall()) {//check if there is not a wall for RIGHT
                    return true;
                }
            }
        } else if (direction == Direction.Down) {
            if (map.getNode(currentPosition.x, currentPosition.y + 1) != null) {//check if there is a node for DOWN
                if (map.getNode(currentPosition.x, currentPosition.y + 1).isWall()) {//check if there is not a wall for DOWN
                    return true;
                }
            }
        } else if (direction == Direction.Left) {
            if (map.getNode(currentPosition.x - 1, currentPosition.y) != null) {//check if there is a node for LEFT
                if (map.getNode(currentPosition.x - 1, currentPosition.y).isWall()) {//check if there is not a wall for LEFT
                    return true;
                }
            }
        }
        return false;
    }

    private void printMap(Map map, Node startPosition, Node endPosition) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Node mapNode = map.getNode(j, i);
                if (mapNode.equals(startPosition)) {
                    System.out.print("1");
                } else if (mapNode.equals(endPosition)) {
                    System.out.print("2");
                } else if (mapNode.isWall()) {
                    System.out.print("O");
                } else {
                    System.out.print("W");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printPath(Map map, ArrayList<Node> pathNodes, Node startPosition, Node endPosition) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Node mapNode = map.getNode(j, i);
                if (pathNodes.contains(mapNode)) {
                    System.out.print("P");
                } else if (mapNode.equals(startPosition)) {
                    System.out.print("1");
                } else if (mapNode.equals(endPosition)) {
                    System.out.print("2");
                } else if (mapNode.isWall()) {
                    System.out.print("O");
                } else {
                    System.out.print("W");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
