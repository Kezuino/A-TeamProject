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
        ArrayList<Node> pathToReturn = new ArrayList<>();//the path of nodes to return
        Map map = startPosition.getMap();
        printMap(map, startPosition, endPosition);//printout the map

        int[][] convertedMap = new int[map.getHeight()][map.getWidth()];//converted map to give to the pathfinder
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                if (map.getNode(j, i).getWall() == null) {
                    convertedMap[i][j] = 1;//if there is no wall set 1
                } else {
                    convertedMap[i][j] = 0;//if there is a wall set 0
                }
            }
        }

        PathFinder.Node beginNode = new PathFinder.Node(startPosition.getX(), startPosition.getY());//convert startposition node to pathFinder.Node
        PathFinder.Node endNode = new PathFinder.Node(endPosition.getX(), endPosition.getY());//convert endPosition node to pathFinder.Node

        PathFinder finder = new PathFinder(convertedMap, endNode);//create pathfinder, give map and endnode
        List<PathFinder.Node> pathToConvert = finder.compute(beginNode);//calculate the path

        if (pathToConvert == null) {
            System.out.println("No path could be found!");
            return null;
        } else {
            for (PathFinder.Node currNode : pathToConvert) {//convert path to pathToReturn
                pathToReturn.add(map.getNode(currNode.x, currNode.y));
            }
            pathToReturn.remove(0);//remove first node because it is the startingposition and not part of the part
            printPath(map, pathToReturn, startPosition, endPosition);//print the map+path
        }

        return pathToReturn;
    }

    private void printMap(Map map, Node startPosition, Node endPosition) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                Node mapNode = map.getNode(j, i);
                if (mapNode.equals(startPosition)) {
                    System.out.print("1");//startingposition
                } else if (mapNode.equals(endPosition)) {
                    System.out.print("2");//endposition
                } else if (mapNode.getWall() == null) {
                    System.out.print("O");//open/walkable
                } else {
                    System.out.print("W");//wall
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
                    System.out.print("P");//path
                } else if (mapNode.equals(startPosition)) {
                    System.out.print("1");//startinposition
                } else if (mapNode.equals(endPosition)) {
                    System.out.print("2");//endposition
                } else if (mapNode.getWall() == null) {
                    System.out.print("O");//open/walkable
                } else {
                    System.out.print("W");//wall
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
