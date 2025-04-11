package com.example.neumaster;

public class Cruse {

    private String name;
    private String term;
    private String point;
    private String score;
    private String score1;
    private String score2;
    private String score3;
    private String score4;

    public String getScore4() {
        return score4;
    }

    public void setScore4(String score4) {
        this.score4 = score4;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Crouse{" +
                "name='" + name + '\'' +
                ", point='" + point + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public Cruse(String name, String point, String score) {
        this.name = name;
        this.point = point;
        this.score = score;
    }

    public Cruse(String name, String term, String point, String score, String score1, String score2, String score3) {
        this.name = name;
        this.term = term;
        this.point = point;
        this.score = score;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
    }
}
