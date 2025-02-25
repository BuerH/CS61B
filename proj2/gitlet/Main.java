package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        Repository repository = new Repository();
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                repository.init();
                break;
            case "add":
                repository.add(args);
                break;
            case "commit":
                repository.commit(args[1]);
                break;
            case "rm":
                repository.remove(args);
                break;
        }
    }
}
