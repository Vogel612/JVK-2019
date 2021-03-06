/*
 * This source file is part of the FIUS JVK 2019 project.
 * For more information see github.com/FIUS/JVK-2019
 *
 * Copyright (c) 2019 the FIUS JVK 2019 project authors.
 * 
 * This software is available under the MIT license.
 * SPDX-License-Identifier:    MIT
 */
package de.unistuttgart.informatik.fius.jvk2019.tasks;

import de.unistuttgart.informatik.fius.icge.simulation.Direction;
import de.unistuttgart.informatik.fius.icge.simulation.Position;
import de.unistuttgart.informatik.fius.icge.simulation.Simulation;
import de.unistuttgart.informatik.fius.icge.simulation.tasks.Task;
import de.unistuttgart.informatik.fius.jvk2019.provided.entity.Neo;
import de.unistuttgart.informatik.fius.jvk2019.provided.entity.Wall;


/**
 * An example task
 * 
 * @author Tim-Julian Ehret
 */
public abstract class Task2_1 implements Task {
    
    /**
     * the simulation
     */
    protected Simulation sim;
    
    /**
     * The spinning neo
     */
    protected Neo turningNeo;
    
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    
    @Override
    public void prepare(Simulation sim) {
        this.sim = sim;
        
        this.turningNeo = new Neo();
        
        sim.getPlayfield().addEntity(new Position(0, 0), this.turningNeo);
        
    }
    
    @Override
    public final void solve() {
        
        this.flag1 = false;
        this.flag2 = false;
        Direction last = this.turningNeo.getLookingDirection();
        turnLeft();
        this.flag1 = this.turningNeo.getLookingDirection() == last.clockWiseNext().clockWiseNext().clockWiseNext();
        turnAround();
        this.flag2 = this.turningNeo.getLookingDirection() == last.clockWiseNext();
        int actualCoins = Helper.getCoinCount(turningNeo);
        this.flag3 = this.getBalance() == actualCoins*2;
        gainCoins(42);
        this.flag4 = (actualCoins+42) == Helper.getCoinCount(turningNeo);
    }
    
    /**
     * turns Neo to the left
     */
    public abstract void turnLeft();
    
    /**
     * turns Neo around
     */
    public abstract void turnAround();
    
    /**
     * 
     * @return Neo's balance
     */
    public abstract int getBalance();
    
    /**
     * increases Neo's coins
     * 
     * @param additionalCoins
     *     number of coins to add
     */
    public abstract void gainCoins(final int additionalCoins);
    
    
    @Override
    public final boolean verify() {
        return this.flag1 && this.flag2 && this.flag3 && this.flag4;
    }
    
}
