package com.example.roguelike;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatScreen {
    Level level = new Level();
    Enemy m;
    Player p;
    public CombatScreen(){
        try {
            if (level.level == 0 && level.round == 0){
                p = new Player("Player",100,14,0);
            }
            if (level.level == 0){
                m = new Enemy("Banana",200,1,0,"Null");
                m.setLevel(level.level+1);
            }
            level.levelup();
            level.roundUp();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void Attacked(){
        try {
            Random random = new Random();
            int crit = random.nextInt(100) + 1;
            int attackpower = p.getAttackPower();
            int critChance = p.getCriticalAreaSize();
            double critDam = p.getCriticalDamageMultiplier();
            int outputpower = attackpower;
    
            if (critChance != 0){
                if (crit <= critChance){
                    outputpower *= critDam;
                }
            }
    
            if(p.isLifeSteal()){
<<<<<<< Updated upstream
                double lifesteal = attackpower * 0.25;
                int health = p.getHealth();
=======
                double lifesteal = outputpower * 0.25;
                int health = p.getCurrentHealth();
>>>>>>> Stashed changes
                int total = (int)lifesteal + health;
                p.setCurrentHealth(total);
            }
    
            if(p.getReflectDamage() > 0){
                outputpower += p.getReflectDamage();
            }
    
            int attacktimes = 1;
            if(p.checkExtraActions()){
                attacktimes *= 2;
            }
    
            if(p.isDoubleAttack()){
                attacktimes *= 2;
            }
    
            for (int i = 0; i < attacktimes; i++) {
                System.out.println("Attacked " + m.getName() + " for " + (outputpower-m.getDefense()));
                enemyAttacked(outputpower);
            }
            attacktimes = 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void enemyAttacked(int outputpower) {
        try {
            int enemydefense = m.getDefense();
            int enemyHealth = m.getHealth();
            int totaldamage = outputpower - enemydefense;
    
            m.setHealth(enemyHealth-totaldamage);
            enemyAttack();
            System.out.println("Enemy got hit for " + totaldamage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void enemyAttack() {
        try {
            Random rand = new Random();
            int random = rand.nextInt(3)+1;
            int enemyAttack = m.getAttackPower();
            for(int i = 0; i < random; i++){
                playerAttacked(enemyAttack);
            }
            Round();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void playerAttacked(int enemyAttack) {
        try {
            int playerHealth = p.getCurrentHealth();
            int playerDefense = p.getDefense();
            int totaldamage = enemyAttack - playerDefense;
            if (totaldamage < 0) { 
                totaldamage = 0;
            }
            p.setCurrentHealth(playerHealth - totaldamage);
            System.out.println("Player got attacked for " + totaldamage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void Round(){
        try {
            System.out.println("Round = " + level.getRound());
            System.out.println("Player HP: " + p.getCurrentHealth());
            System.out.println("Enemy HP: " + m.getHealth());
            level.roundUp();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public boolean isEnemyDead(){
        return m.isDead();
    }
}
