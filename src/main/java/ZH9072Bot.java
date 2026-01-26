import java.util.Scanner;

public class ZH9072Bot {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(line);
        System.out.println("Hello! I'm ZH9072Bot");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while(true) {
            String input;
            input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                System.out.println("Here are the tasks in your list:");
                for(int i = 0; i < taskCount; i++) {
                    int number = i + 1;
                    System.out.println(number + "." + tasks[i]);
                }
                System.out.println(line);
            } else if (input.startsWith("mark ")) {
                String inputNum = input.split(" ")[1];
                int taskId = Integer.parseInt(inputNum)- 1;
                tasks[taskId].markDone();

                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskId].toString());
                System.out.println(line);
            } else if (input.startsWith("unmark ")) {
                String inputNum = input.split(" ")[1];
                int taskId = Integer.parseInt(inputNum)- 1;
                tasks[taskId].unMark();

                System.out.println(line);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskId].toString());
                System.out.println(line);
            } else if (input.startsWith("todo")) {
                String restContent = input.split("todo ", 2)[1];
                tasks[taskCount] = new ToDo(restContent);
                taskCount++;

                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);
            } else if (input.startsWith("deadline")) {
                String restContent = input.split("deadline", 2)[1];
                int byIndex = restContent.indexOf(" /by ");
                String specificContent = restContent.substring(0, byIndex).trim();
                String by = restContent.split("/by", 2)[1];
                tasks[taskCount] = new Deadline(specificContent, by);
                taskCount++;

                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);
            } else if (input.startsWith("event")) {
                String restContent = input.split("event", 2)[1];
                int fromIndex = restContent.indexOf(" /from ");
                int toIndex = restContent.indexOf(" /to ");
                String specificContent = restContent.substring(0, fromIndex).trim();
                String from = restContent.substring(fromIndex + 7, toIndex).trim();
                String to = restContent.substring(toIndex + 5).trim();

                tasks[taskCount] = new Event(specificContent, from, to);
                taskCount++;

                System.out.println(line);
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks[taskCount - 1]);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(line);
            } else {
            }
        }

        
    }
}
