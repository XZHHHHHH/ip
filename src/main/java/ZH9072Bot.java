import java.util.Scanner;
import java.util.ArrayList;

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
        ArrayList<Task> tasks = new ArrayList<>();

        while(true) {
            String input;
            input = sc.nextLine();
            try {
                if (input.equals("bye")) {
                    System.out.println(line);
                    System.out.println("Bye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                } else if (input.equals("list")) {
                    System.out.println(line);
                    System.out.println("Here are the tasks in your list:");
                    for(int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(line);
                } else if (input.startsWith("mark ")) {
                    String inputNum = input.split(" ")[1];
                    int taskId = Integer.parseInt(inputNum)- 1;
                    tasks.get(taskId).markDone();

                    System.out.println(line);
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskId));
                    System.out.println(line);
                } else if (input.startsWith("unmark ")) {
                    String inputNum = input.split(" ")[1];
                    int taskId = Integer.parseInt(inputNum)- 1;
                    tasks.get(taskId).unMark();

                    System.out.println(line);
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskId));
                    System.out.println(line);
                } else if (input.startsWith("todo") || input.equals("todo")) {
                    if (input.length() <= 4) {
                        throw new BotException("Oops — please add some content after 'todo'.");
                    }
                    String restContent = input.split("todo ", 2)[1];
                    if (restContent.isEmpty()) {
                        throw new BotException("Oops — please add some content after 'todo'.");
                    }
                    
                    tasks.add(new ToDo(restContent));
                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("deadline")) {
                    String restContent = input.split("deadline", 2)[1];
                    int byIndex = restContent.indexOf(" /by ");
                    String specificContent = restContent.substring(0, byIndex).trim();
                    String by = restContent.split("/by", 2)[1];
                    tasks.add(new Deadline(specificContent, by));

                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("event")) {
                    String restContent = input.split("event", 2)[1];
                    int fromIndex = restContent.indexOf(" /from ");
                    int toIndex = restContent.indexOf(" /to ");
                    String specificContent = restContent.substring(0, fromIndex).trim();
                    String from = restContent.substring(fromIndex + 7, toIndex).trim();
                    String to = restContent.substring(toIndex + 5).trim();

                    tasks.add(new Event(specificContent, from, to));

                    System.out.println(line);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else if (input.startsWith("delete ")) {
                    String inputNum = (input.split(" ")[1]);
                    int taskId = Integer.parseInt(input.split(" ")[1]) - 1;
                    Task removed = tasks.remove(taskId);

                    System.out.println(line);
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removed);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println(line);
                } else {
                    throw new BotException("Sorry, I don't understand your command. Please try again.");
                }
            } catch (BotException e) {
                System.out.println(line);
                System.out.println(e.getMessage());
                System.out.println(line);
            }
        }

        
    }
}
