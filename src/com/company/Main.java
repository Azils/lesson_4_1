package com.company;

import java.util.Random;

public class Main {
    public static String bossDefenceType;
    public static int bossDamage = 50;
    public static int bossHealth = 700;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk"};
    public static int[] heroesDamage = {25, 20, 15, 0, 5, 20, 30};
    public static int[] heroesHealth = {320, 270, 250, 400, 600, 500, 450};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundNumber++;
        bossDefenceType = chooseDefence();
        ThorSill();
        bossHits();
        heroesHit();
        medicHeal();
        golemsil();
        LuckySill();
        BerserkSill();
        printStatistics();
    }

    public static String chooseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        System.out.println("Boss chose " + heroesAttackType[randomIndex]);
        return heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] + " x "
                            + coeff + " = " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/

        int totalHealth = 0;
        for (int health : heroesHealth) {
            totalHealth += health; // totalHealth = totalHealth + health;
        }
        if (totalHealth <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND ___________________________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " +
                    heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }

    public static void medicHeal() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == heroesHealth[3]) {
                continue;
            } else if (heroesHealth[i] <= 100 && heroesHealth[i] > 0 && heroesHealth[3] > 0) {
                heroesHealth[i] += 50;
                System.out.println("Medic heal " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void golemsil() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] > 0 && heroesHealth[i] > 0 && bossDamage > 0) {
                heroesHealth[i] += 10;
                heroesHealth[4] -= 10;
            }

        }
    }

    public static void LuckySill() {
        Random random = new Random();
        boolean lucky = random.nextBoolean();
        if (lucky == true) {
            heroesHealth[5] += 40;
            System.out.println("Лаки увернулся от удара");
        }
    }

    public static void BerserkSill() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[6] > 0) {
                bossHealth -= bossDamage / 2;
                heroesHealth[6]+=bossDamage / 2;
                System.out.println("Прибавляет урон к своему урону");
                break;

            }

        }
    }

    public static void ThorSill(){
        Random random=new Random();
        boolean Thor=random.nextBoolean();
        if (Thor==true){
            bossDamage = 0;
            System.out.println("Босс оглушен");
        }else{
            bossDamage = 50;
        }
    }
}
