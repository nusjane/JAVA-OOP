import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Roster roster = new Roster("AY");
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        for (int i = 0; i < N; i++) {
            String student = sc.next();
            String course = sc.next();
            String assessment = sc.next();
            String grade = sc.next();
            roster.add(student, course, assessment, grade);
        }
        while (sc.hasNext()) {
            String student = sc.next();
            String course = sc.next();
            String assessment = sc.next();
            System.out.println(roster.getGrade(student, course, assessment));
        }
        sc.close();
    }
}