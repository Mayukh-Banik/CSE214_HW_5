// Mayukh Banik CSE 214 mayukh.banik@stonybrook.edu R03 Homework 5
import java.util.*;
import java.io.*;
public class NintendoNetwork
{
    public static NetworkTree tree = null;
    public static NetworkNode tempNode = new NetworkNode(), tempNode2;
    public static String fileName;
    public static String[] strings = {"L - Load from file\n", "P - Print\n", "C - Cursor to child (index number)\n",
            "A - Add child (index, type, prompt for text)\n", "U - Cursor up (to parent)\n", "X - Cut/Delete cursor\n",
            "V - Paste Subtree (index number)\n", "R - Cursor to root\n", "S - Save to Text File\n", "Q - Quit\n"};
    public static Scanner input = new Scanner(System.in), reader;
    public static boolean hasBeenLoaded = false;

    /**
     * cycles through the reading inputs and selects which method to go to
     * @param args argument
     */
    public static void main(String[] args)
    {
        String optionSelector;
        System.out.println("Welcome to the Nintendo Network Manager. ");
        do
        {
            try
            {
                System.out.println("Menu: ");
                stringArrayOutput(strings);
                optionSelector = allToUpperCase(input.nextLine());
                switch (optionSelector)
                {
                    case "L":
                        loadFromFile();
                        break;
                    case "P":
                        print();
                        break;
                    case "C":
                        cursorToChild();
                        break;
                    case "A":
                        addChild();
                        break;
                    case "U":
                        cursorUp();
                        break;
                    case "X":
                        cutDelete();
                        break;
                    case "V":
                        pasteSubtree();
                        break;
                    case "R":
                        cursorToRoot();
                        break;
                    case "S":
                        saveToFile();
                        break;
                    case "Q":
                        System.exit(0);
                        break;
                }
            }
            catch (CustomException | IOException e)
            {
                System.out.println("Input Error!");
            }
        }
        while(true);
    }

    /**
     * converts every string to uppercase
     * @param string string to be changed
     * @return uppercase string
     */
    private static String allToUpperCase(String string)
    {
        return string.toUpperCase();
    }

    /**
     * Prints out any array of strings
     * @param strings string array to have its contents outputted
     */
    public static void stringArrayOutput(String[] strings)
    {
        for (String string : strings)
        {
            System.out.print(string);
        }
    }

    /**
     * loads the file that has been specified
     * @throws FileNotFoundException in case the file has not been found
     */
    public static void loadFromFile() throws IOException
    {
        tree = new NetworkTree();
        System.out.println("Enter the filename: ");
        fileName = input.nextLine();
        try
        {
            File file = new File(fileName);
            reader = new Scanner(file);
            tree.readFromFile(file);
            System.out.println("File has been loaded. ");
            hasBeenLoaded = true;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Filename doesn't exist in repository. ");
        }
    }

    /**
     * prints out the entire tree that has been loaded
     */
    public static void print() throws CustomException
    {
        if (hasBeenLoaded)
        {
            System.out.println(tree.toString());
        }
        else
        {
            System.out.println("A proper file has not been loaded.");
        }
    }

    /**
     * moves the cursor to the specified child
     */
    public static void cursorToChild() throws CustomException
    {
        System.out.println("What is the index of the child of the cursor you wish to go to?");
        System.out.println("Cursor moved to: " + tree.cursorToChild(input.nextInt()));
    }

    /**
     * adds a child to the tree
     */
    public static void addChild() throws CustomException
    {
        tempNode = new NetworkNode();
        System.out.println("Enter the name of the child: ");
        tempNode.setName(input.nextLine());
        System.out.println("Is it nintendo (y/n) ");
        String string = allToUpperCase(input.nextLine());
        switch (string)
        {
            case "y":
            case "Y":
                tempNode.setNintendo(true);
                break;
            case "n":
            case "N":
                tempNode.setNintendo(false);
                break;
        }
        System.out.println("Enter an index: ");
        tree.addChild(input.nextInt() - 1, tempNode);
    }

    /**
     * cuts and deletes the node
     */
    public static void cutDelete() throws CustomException
    {
        tempNode2 = tree.removeAndStoreNode();
    }

    /**
     * pastes the cut subtree
     */
    public static void pasteSubtree() throws CustomException
    {
        System.out.println("Where would you like to paste the old node?");
        tree.pasteOldNode(input.nextInt() - 1, tempNode2);
    }

    /**
     * moves the cursor to the root
     */
    public static void cursorToRoot()
    {
        System.out.println("Cursor moved to: " + tree.cursorToRoot());
    }

    /**
     * moves the cursor up one root
     */
    public static void cursorUp()
    {
        System.out.println("Cursor moved to: " + tree.cursorToParent());
    }

    /**
     * saves changes to the file
     */
    public static void saveToFile() throws IOException
    {
        System.out.println("What is the name of the file you wish to save to?");
        tree.writeToFile(tree, input.nextLine());
    }
}