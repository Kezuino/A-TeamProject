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
        Point walker = new Point(startPosition.getX(), startPosition.getY());//current position is copy of startPosition and will be modified

        Point feeler = new Point();//used to feel the path when stuck on the direct approach
        boolean feelerIsWorking = false;//check if a new feeler needs to be made
        boolean feelerRailUp = true;//feel up or down
        boolean FeelerRailRight = true;//feel left or right
        boolean feelerLookUp = true;//look up or down for a hole
        boolean feelerLookRight = true;//look right or left for a hole
        int xDistanceToTarget = 0;//the x distance the feeler is away from the target
        int yDistanceToTarget = 0;//the y distance the feeler is away from the target
        ArrayList<Node> feeledNodes = new ArrayList<>();//the nodes which are feeled and do not need to be feeled again
        ArrayList<Node> feelerCrossed = new ArrayList<>();//the nodes which the feeler did cross and which needed to be add to the generatePath arrayList

        while (!targetReached) {
            System.out.println("Loop tick" + System.getProperty("line.separator") + "---");
            if (impossible) {
                System.out.println("Impossible to finish");
                return null;
            }

            boolean madeMove = false;

            if (!feelerIsWorking) {
                if (canNodeBeWalked(walker, Direction.Up, map, null) && !madeMove) {
                    if ((Math.abs(walker.y - 1 - endPosition.getY())) < (Math.abs(walker.y - endPosition.getY()))) {//check if the distance is smalle when currentposition.y--
                        walker.y--;
                        pathToReturn.add(map.getNode(walker.x, walker.y));

                        madeMove = true;
                        System.out.println("Moved up");
                    }
                }
                if (canNodeBeWalked(walker, Direction.Right, map, null) && !madeMove) {
                    if ((Math.abs(walker.x + 1 - endPosition.getX())) < (Math.abs(walker.x - endPosition.getX()))) {//check if the distance is smalle when currentposition.x++
                        walker.x++;
                        pathToReturn.add(map.getNode(walker.x, walker.y));

                        madeMove = true;
                        System.out.println("Moved right");
                    }
                }
                if (canNodeBeWalked(walker, Direction.Down, map, null) && !madeMove) {
                    if ((Math.abs(walker.y + 1 - endPosition.getY())) < (Math.abs(walker.y - endPosition.getY()))) {//check if the distance is smalle when currentposition.y++
                        walker.y++;
                        pathToReturn.add(map.getNode(walker.x, walker.y));

                        madeMove = true;
                        System.out.println("Moved down");
                    }
                }
                if (canNodeBeWalked(walker, Direction.Left, map, null) && !madeMove) {
                    if ((Math.abs(walker.x - 1 - endPosition.getX())) < (Math.abs(walker.x - endPosition.getX()))) {//check if the distance is smalle when currentposition.x--
                        walker.x--;
                        pathToReturn.add(map.getNode(walker.x, walker.y));

                        madeMove = true;
                        System.out.println("Moved left");
                    }
                }
            }

            if (!madeMove) {
                System.out.println("No move(s) made by walker");

                if (!feelerIsWorking) {
                    System.out.println("Creating a feeler");
                    feelerIsWorking = true;
                    feeledNodes = new ArrayList<>();

                    feeler.x = walker.x;
                    feeler.y = walker.y;

                    feeledNodes.add(map.getNode(feeler.x, feeler.y));//no need to feel the starting node

                    xDistanceToTarget = Math.abs(walker.x - endPosition.getX());//calculate the distance from the current position to the target
                    yDistanceToTarget = Math.abs(walker.y - endPosition.getY());//calculate the distance from the current position to the target

                    //Calculate if the feeler needs to look up or down
                    feelerLookUp = true;
                    if ((feeler.y - endPosition.getY()) < 0) {
                        feelerLookUp = false;
                    }

                    //Calculate if the feeler needs to look right or left
                    feelerLookRight = false;
                    if ((feeler.x - endPosition.getX()) < 0) {
                        feelerLookRight = true;
                    }

                    System.out.println("current X Position is " + xDistanceToTarget + " node(s) from target. current Y Position is " + yDistanceToTarget + " node(s) from target.");
                }

                if (xDistanceToTarget >= yDistanceToTarget) {//check if it needs horizontal or vertical railing
                    //will move vertical to target

                } else {
                    //will move horizontal to target
                    if (FeelerRailRight) {//check if rail right or left
                        if (canNodeBeWalked(feeler, Direction.Right, map, null)) {//check if feeler can move to the right
                            feeler.x++;
                            feeledNodes.add(map.getNode(feeler.x, feeler.y));//make sure the node will not get feeled again
                            feelerCrossed.add(map.getNode(feeler.x, feeler.y));//make sure that the path will be remebered

                            System.out.println("feelers moves to the right");
                            if (feelerLookUp) {
                                if (canNodeBeWalked(feeler, Direction.Up, map, feeledNodes)) {
                                    System.out.println("feelers did found a hole which is up!");

                                    pathToReturn.addAll(feelerCrossed);//add nodes to the walked path

                                    walker.x = feeler.x;//put the walker into the hole
                                    walker.y = feeler.y - 1;//put the walker into the hole

                                    pathToReturn.add(map.getNode(walker.x, walker.y - 1));//add the hole which was found

                                    feelerIsWorking = false;
                                }
                            } else {
                                if (canNodeBeWalked(feeler, Direction.Down, map, feeledNodes)) {
                                    System.out.println("feelers did found a hole which is down!");

                                    pathToReturn.addAll(feelerCrossed);//add nodes to the walked path

                                    walker.x = feeler.x;//put the walker into the hole
                                    walker.y = feeler.y + 1;//put the walker into the hole

                                    pathToReturn.add(map.getNode(walker.x, walker.y - 1));//add the hole which was found

                                    feelerIsWorking = false;
                                }
                            }
                        } else {
                            FeelerRailRight = false;//prepare railing to the left
                            System.out.println("feelers can't move more to the right");
                            feeler.x = walker.x;//reset feeler position
                            feeler.y = walker.y;
                        }
                    } else {

                        if (canNodeBeWalked(feeler, Direction.Left, map, null)) {//check if feeler can move to the right
                            feeler.x--;
                            feeledNodes.add(map.getNode(feeler.x, feeler.y));//make sure the node will not get feeled again
                            feelerCrossed.add(map.getNode(feeler.x, feeler.y));//make sure that the path will be remebered

                            System.out.println("feelers moves to the left");
                            if (feelerLookUp) {
                                if (canNodeBeWalked(feeler, Direction.Up, map, feeledNodes)) {
                                    System.out.println("feelers did found a hole which is up!");

                                    pathToReturn.addAll(feelerCrossed);//add nodes to the walked path

                                    walker.x = feeler.x;//put the walker into the hole
                                    walker.y = feeler.y - 1;//put the walker into the hole

                                    pathToReturn.add(map.getNode(walker.x, walker.y - 1));//add the hole which was found

                                    feelerIsWorking = false;
                                }
                            } else {
                                if (canNodeBeWalked(feeler, Direction.Down, map, feeledNodes)) {
                                    System.out.println("feelers did found a hole which is down!");

                                    pathToReturn.addAll(feelerCrossed);//add nodes to the walked path

                                    walker.x = feeler.x;//put the walker into the hole
                                    walker.y = feeler.y + 1;//put the walker into the hole

                                    pathToReturn.add(map.getNode(walker.x, walker.y - 1));//add the hole which was found

                                    feelerIsWorking = false;
                                }
                            }
                        } else {
                            System.out.println("feelers can't move more to the left");
                            System.out.println("Feeler did FAIL with railing right and then left");
                            FeelerRailRight = true;//prepare railing to the right again

                            feeler.x = walker.x;//reset feeler position
                            feeler.y = walker.y;

                            boolean stop = false;//if the whole while loop needs to stop

                            if (feeler.y + 2 > map.getHeight() || feeler.y - 1 < 0) {//check if the feeler can move further
                                stop = true;//stop because we are going to get placed out of the map
                            }

                            Node nodeToMoveTo = map.getNode(feeler.x, feeler.y + 1);
                            if (nodeToMoveTo != null) {
                                if (nodeToMoveTo.getWall() != null) {
                                    stop = true;//stop because we are going to get placed on a wall
                                }
                            }

                            if (stop) {
                                System.out.println("Feeler can't be placed any further because then it will go out of the map or it will be placed on a wall");
                                impossible = true;//we stop finding paths
                            }

                            pathToReturn.remove(map.getNode(walker.x, walker.y));//remove the walked node because the path leads to nowhere
                            if (feelerLookUp) {
                                feeler.y++;//move the feeler so that it will feel a row lower
                                walker.y++;//move the walker so that it will stay a row lower
                                System.out.println("Feeler will feel a row lower");
                            } else {
                                feeler.y--;//move the feeler so that it will feel a row higher
                                walker.y--;//move the walker so that it will stay a row higher
                                System.out.println("Feeler wil feel a row higher");
                            }
                        }
                    }
                }

                if (!feelerIsWorking) {
                    System.out.println("Feeler did is job well and will be destroyed");

                }

            }

            if (walker.x == endPosition.getX() && walker.y == endPosition.getY()) {//if a move is made. check if the target is reached
                targetReached = true;
                System.out.println("Target reached. Terminating");
            }

            System.out.println();
        }

        printPath(map, pathToReturn, startPosition, endPosition);
        return pathToReturn;
    }

    private boolean canNodeBeWalked(Point currentPosition, Direction direction, Map map, ArrayList<Node> feeledNodes) {//returns if a node can be walked. feeledNodes can be null or can be added as parameter so that those nodes will be avoided
        if (direction == Direction.Up) {
            if (map.getNode(currentPosition.x, currentPosition.y - 1) != null) {//check if there is a node for UP
                if (map.getNode(currentPosition.x, currentPosition.y - 1).isWall()) {//check if there is not a wall for UP
                    if (feeledNodes != null) {
                        if (feeledNodes.contains(map.getNode(currentPosition.x, currentPosition.y - 1))) {
                            return false;//if feelednodes contains the checked node, return false
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        } else if (direction == Direction.Right) {
            if (map.getNode(currentPosition.x + 1, currentPosition.y) != null) {//check if there is a node for RIGHT
                if (map.getNode(currentPosition.x + 1, currentPosition.y).isWall()) {//check if there is not a wall for RIGHT
                    if (feeledNodes != null) {
                        if (feeledNodes.contains(map.getNode(currentPosition.x + 1, currentPosition.y))) {
                            return false;//if feelednodes contains the checked node, return false
                        } else {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        } else if (direction == Direction.Down) {
            if (map.getNode(currentPosition.x, currentPosition.y + 1) != null) {//check if there is a node for DOWN
                if (map.getNode(currentPosition.x, currentPosition.y + 1).isWall()) {//check if there is not a wall for DOWN
                    if (map.getNode(currentPosition.x, currentPosition.y + 1).getWall() == null) {//check if there is not a wall for RIGHT 
                        if (feeledNodes != null) {
                            if (feeledNodes.contains(map.getNode(currentPosition.x + 1, currentPosition.y))) {
                                return false;//if feelednodes contains the checked node, return false
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                }
            }
        } else if (direction == Direction.Left) {
            if (map.getNode(currentPosition.x - 1, currentPosition.y) != null) {//check if there is a node for LEFT
                if (map.getNode(currentPosition.x - 1, currentPosition.y).isWall()) {//check if there is not a wall for LEFT
                    if (map.getNode(currentPosition.x + 1, currentPosition.y).getWall() == null) {//check if there is not a wall for RIGHT 
                        if (feeledNodes != null) {
                            if (feeledNodes.contains(map.getNode(currentPosition.x + 1, currentPosition.y))) {
                                return false;//if feelednodes contains the checked node, return false
                            } else {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
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
