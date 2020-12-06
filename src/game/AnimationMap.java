/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author fabix
 */
public enum AnimationMap {
    a(9,8),
    s(10,8),
    d(11,8),
    w(8,8),
    idle(2,0);

    private final int row;
    private final int maxFrames;

    private AnimationMap(int i,int maxFrames) {
        this.row = i;
        this.maxFrames = maxFrames;
    }

    public int getRow() {
        return row;
    }
    
    public int getMaxFrames() {
        return maxFrames;
    }
}
