import java.util.Scanner;

public class ZH9072Bot {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm ZH9072Bot");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while(true) {
            String input;
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println("Here are the tasks in your list:");
                for(int i = 0; i < taskCount; i++) {
                    int number = i + 1;
                    System.out.println(number + "." + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark")) {
                String inputNum = input.split(" ")[1];
                int taskId = Integer.parseInt(inputNum)- 1;
                tasks[taskId].markDone();
                System.out.println("____________________________________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskId].toString());
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("unmark")) {
                String inputNum = input.split(" ")[1];
                int taskId = Integer.parseInt(inputNum)- 1;
                tasks[taskId].unMark();
                System.out.println("____________________________________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskId].toString());
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }
        }

        
    }
}
