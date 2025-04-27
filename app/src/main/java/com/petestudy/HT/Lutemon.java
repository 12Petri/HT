package com.petestudy.HT;

import java.io.Serializable;

public abstract class Lutemon implements Serializable {
    private static int idCounter = 0;

    private final int id;
    private final String name;
    private final String color;
    private int attack;
    private int defence;
    private int experience;
    private int level;
    private int health;
    private int maxHealth;

    private boolean isDefending = false;

    public Lutemon(String name, String color, int attack, int defence, int maxHealth) {
        this.id = idCounter++;
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defence = defence;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.experience = 0;
        this.level = 1;
    }

    public int attack() {
        return attack;
    }

    public void takeDamage(int incomingDamage) {
        int damage = incomingDamage - defence;
        if (damage < 0) {
            damage = 0;
        }
        if (isDefending) {
            damage /= 2;
            isDefending = false;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    public void startDefending() {
        isDefending = true;
    }

    public void gainExperience(int xp) {
        experience += xp;

        while (experience >= level * 100) {
            experience -= level * 100;
            level++;
            maxHealth += 5;
            attack += 1;

            if (level % 2 == 1) {
                defence += 1;
            }

            health = maxHealth;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getAttackStat() {
        return attack;
    }

    public int getDefenceStat() {
        return defence;
    }

    public int getExperience() {
        return experience;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getPrice() {
        return 5 + level * 2;
    }

    public void setExperience(int xp) {
        experience += xp;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLevel() {
        return level;
    }

    public abstract String getImageName();
}