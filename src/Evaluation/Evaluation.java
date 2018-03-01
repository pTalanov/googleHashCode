package Evaluation;

import model.Model;
import model.Point;
import model.Ride;
import submission.Assignment;
import submission.Submission;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {

    private static Point start_point = new Point(0,0);
    public int getTotalReward(int bonus, Submission sub) {
        int total_reward = 0;
        long start_time = 0;
        long end_time = 0;
        int applied_bonus = 0;
        for (Assignment assign : sub.getAssignments()) {

            long current_time = 0;
            Point current_point = new Point(0,0);
            for(Ride ride : assign.getRides()){
                start_time = Math.max(current_time + distTwoPoints(ride.getStartPoint(),current_point), ride.getEarliestStart());
                end_time = start_time + distTwoPoints(ride.getStartPoint(),ride.getEndPoint());
                applied_bonus = 0 : bonus ? start_time != ride.getEarliestStart();
                if(end_time<=ride.getLatestFinish()){
                    total_reward += (end_time-start_time+applied_bonus);

                }
                current_point = ride.getEndPoint();
                current_time = end_time;

            }
        }
        return total_reward;
    }



    private int distTwoPoints(Point first, Point second){
        return Math.abs(second.getX()-first.getX())+Math.abs(second.getY()-first.getY());
    }


    public static void main(String[] args) {
        Evaluation eval = new Evaluation();
        List<Ride> rides1 = new ArrayList<>();
        rides1.add(new Ride(0,new Point(0,0), new Point(1,3),2, 9));

        List<Ride> rides2 = new ArrayList<>();
        rides2.add(new Ride(2,new Point(2,0), new Point(2,2),0, 9));
        rides2.add(new Ride(1,new Point(1,2), new Point(1,0),0, 9));


        List<Assignment> assignments  = new ArrayList<>();
        assignments.add(new Assignment(rides1));
        assignments.add(new Assignment(rides2));

        Submission sub = new Submission(assignments);
        System.out.println(eval.getTotalReward(2, sub));
    }
}