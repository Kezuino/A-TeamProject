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
import java.util.List;

/**
 *
 * @author Jip
 */
public class GameObjectPathfinding {

    public ArrayList<Node> generatePath(Node startPosition, Node endPosition) {
        ArrayList<Node> pathToReturn = new ArrayList<>();
        Map map = startPosition.getMap();
        printMap(map, startPosition, endPosition);

        int[][] convertedMap = new int[map.getHeight()][map.getWidth()];
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (map.getNode(j, i).getWall() == null) {
                    convertedMap[i][j] = 1;
                } else {
                    convertedMap[i][j] = 0;
                }
            }
        }

        PathFinder.Node beginNode = new PathFinder.Node(startPosition.getX(), startPosition.getY());
        PathFinder.Node endNode = new PathFinder.Node(endPosition.getX(), endPosition.getY());

        PathFinder finder = new PathFinder(convertedMap, endNode);
        List<PathFinder.Node> pathToConvert = finder.compute(beginNode);

        if (pathToConvert == null) {
            return null;
        } else {
            for (PathFinder.Node currNode : pathToConvert) {
                pathToReturn.add(map.getNode(currNode.x, currNode.y));
            }
            printPath(map, pathToReturn, startPosition, endPosition);
        }

        return pathToReturn;
    }

    private void printMap(Map map, Node startPosition, Node endPosition) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Node mapNode = map.getNode(j, i);
                if (mapNode.equals(startPosition)) {
                    System.out.print("1");
                } else if (mapNode.equals(endPosition)) {
                    System.out.print("2");
                } else if (mapNode.getWall() == null) {
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
                } else if (mapNode.getWall() == null) {
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
