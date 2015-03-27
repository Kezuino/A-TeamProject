
/*    
 * A* algorithm implementation.
 * Copyright (C) 2007, 2009 Giuseppe Scrivano <gscrivano@gnu.org>

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
                        
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>.
 */
package ateamproject.kezuino.com.github.pathfinding;

import java.util.LinkedList;
import java.util.List;


/*
 * Example.
 */
public class PathFinder extends AStar<PathFinder.Node> {

    private int[][] map;
    private Node endPoint;

    PathFinder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class Node {

        public int x;
        public int y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ") ";
        }
    }

    public PathFinder(int[][] map, Node endPoint) {
        this.map = map;
        this.endPoint = endPoint;
    }

    protected boolean isGoal(Node node) {
        return (node.x == endPoint.x) && (node.y == endPoint.y);
    }

    protected Double g(Node from, Node to) {

        if (from.x == to.x && from.y == to.y) {
            return 0.0;
        }

        if (map[to.y][to.x] == 1) {
            return 1.0;
        }

        return Double.MAX_VALUE;
    }

    protected Double h(Node from, Node to) {
        /* Use the Manhattan distance heuristic.  */
        return new Double(Math.abs(map[0].length - 1 - to.x) + Math.abs(map.length - 1 - to.y));
    }

    protected List<Node> generateSuccessors(Node node) {
        List<Node> ret = new LinkedList<Node>();
        int x = node.x;
        int y = node.y;
        if (y < map.length - 1 && map[y + 1][x] == 1) {
            ret.add(new Node(x, y + 1));
        }

        if (x < map[0].length - 1 && map[y][x + 1] == 1) {
            ret.add(new Node(x + 1, y));
        }

        return ret;
    }

 

}
