// Mayukh Banik CSE 214 mayukh.banik@stonybrook.edu R03 Homework 5
import java.io.*;
public class NetworkTree
{
    private NetworkNode root;
    private NetworkNode cursor;
    private static String string = "";
    private static String string1 = "";

    /**
     * initializes the data
     */
    public NetworkTree()
    {
        root = new NetworkNode();
        cursor = root;
    }

    /**
     * moves the cursor to the root
     * @return String of cursor
     */
    public String cursorToRoot()
    {
        cursor = this.root;
        return cursor.toString();
    }

    /**
     * adds a child at the specific index
     * @param index number of position of the child in the array
     * @param node node to be added
     */
    public void addChild(int index, NetworkNode node) throws CustomException
    {
        if (index > cursor.maxChildren || cursor.isNintendo())
        {
            throw new CustomException();
        }
        for (int counter = 0, counter2 = 0; counter < 9; counter++)
        {
            if (cursor.getChild(counter) != null)
            {
                counter2++;
            }
            if (counter2 == 8)
            {
                throw new CustomException();
            }
        }
        for (int counter = 0; counter < index; counter++)
        {
            if (cursor.getChild(counter) == null)
            {
                throw new CustomException();
            }
        }
        if (cursor.getChild(index) != null)
        {
            for (int counter = 8, counter2 = 7; counter2 >= index; counter--, counter2--)
            {
                cursor.setChild(counter, cursor.getChild(counter2));
                cursor.setChild(counter2, null);
            }
        }
        cursor.setChild(index, node);
        cursor.getChild(index).setParent(cursor);
        cursor.getChild(index).setLevel(cursor.getLevel() + 1);
        eliminateHolesBelowCursor();
    }

    /**
     * deletes and stores the node
     * @return the node that was deleted, along with its children
     * @throws CustomException in case something bad happens
     */
    public NetworkNode removeAndStoreNode() throws CustomException
    {
        if (cursor == null)
        {
            throw new CustomException();
        }
        if (cursor == root)
        {
            root = null;
            cursor = null;
            return null;
        }
        NetworkNode tempNode = cursor;
        cursorToParent();
        cursor.setChild(tempNode.childNumber(), null);
        return tempNode;
    }

    /**
     * pastes the saved node
     * @param index of the node to be put in
     * @param node node
     */
    public void pasteOldNode(int index, NetworkNode node)
    {
        cursor.setChild(index, node);
        cursor.getChild(index).setLevel(cursor.getLevel() + 1);
    }

    /**
     * moves the cursor to the specified child
     * @return string of cursor
     * @param index of the child in the array to be added to
     */
    public String cursorToChild(int index) throws CustomException
    {
        if (cursor.getChild(index) == null)
        {
            throw new CustomException();
        }
        if (!cursor.isNintendo())
        {
            cursor = cursor.getChildren(index);
        }
        return cursor.toString();
    }

    /**
     * moves the cursor to the parent
     * @return string of cursor
     */
    public String cursorToParent()
    {
        if (cursor != root)
        {
            cursor = cursor.getParent();
        }
        return cursor.toString();
    }

    /**
     * reads from the file and makes sure its valid
     * @param file to edit
     * @return the network tree to be based on
     */
    public NetworkTree readFromFile(File file)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                NetworkNode tempNode1 = new NetworkNode();
                boolean isNintendo = currentLine.contains("-");
                StringBuilder tempStringOfNumbers = new StringBuilder(), tempStringOfName = new StringBuilder();
                for (int counter = 0; counter < currentLine.length(); counter++)
                {
                    currentLine = currentLine.replace("-", "");
                    switch (currentLine.charAt(counter))
                    {
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            tempStringOfNumbers.append(currentLine.charAt(counter));
                            break;
                        default:
                            tempStringOfName.append(currentLine.charAt(counter));
                            break;
                    }
                }
                tempNode1.setName(tempStringOfName.toString());
                if (tempStringOfNumbers.length() == 0)
                {
                    root = tempNode1;
                    root.setLevel(1);
                    cursor = root;
                }
                else
                {
                    if (tempStringOfNumbers.length() == 1)
                    {
                        root.setChild(tempStringOfNumbers.charAt(0) - 48, tempNode1);
                        tempNode1.setParent(root);
                    }
                    else
                    {
                        cursor = root;
                        while (tempStringOfNumbers.length() > 1)
                        {
                            cursor = cursor.getChild(tempStringOfNumbers.charAt(0) - 48);
                            tempStringOfNumbers = new StringBuilder(tempStringOfNumbers.substring(1));
                        }
                        if (cursor.getChildren() == null)
                        {
                            cursor.setChildren(new NetworkNode[9]);
                        }
                        cursor.setChild(tempStringOfNumbers.charAt(0) - 48, tempNode1);
                        tempNode1.setParent(cursor);
                        tempNode1.setLevel(tempNode1.getParent().getLevel() + 1);
                    }
                }
                tempNode1.setNintendo(isNintendo);
            }
        }
        catch (IOException e)
        {
            System.out.println("File doesn't exist.");
        }
        cursorToRoot();
        return this;
    }

    /**
     * writes to the file in question
     * @param filename to be edited
     */
    public void writeToFile(NetworkTree tree, String filename) throws IOException
    {
        FileWriter fileWriter = new FileWriter(filename);
        String tempString = string1;
        tempString = tempString.replace("+", "");
        fileWriter.write(tempString);
        fileWriter.close();
        string1 = "";
    }

    /**
     * toString
     * @return string
     */
    public String toString()
    {
        preorder(root);
        String tempString = string;
        string = "";
        return tempString;
    }

    /**
     * handles tree traversal
     * @param node which is root where traversal will start
     */
    public void preorder(NetworkNode node)
    {
        if (node == null)
        {
            return;
        }
        StringBuilder temp = new StringBuilder();
        StringBuilder temp2 = new StringBuilder();
        NetworkNode tempNode = node;
        if (node != root)
        {
            for (int counter = 0; counter <= node.getLevel(); counter++)
            {
                temp.insert(0, "\t");
            }
            while (tempNode != root)
            {
                temp2.insert(0, tempNode.childNumber());
                tempNode = tempNode.getParent();
            }
        }
        if (cursor == node)
        {
            string = string + temp + "->" + node + "\n";
        }
        else
        {
            string = string + temp + node + "\n";
            string1 = string1 + temp2 + (node.isNintendo() ? "-" : "") + node + "\n";
        }
        if (node == root)
        {
            string1 = temp2.toString() + node + "\n";
        }
        for (int counter = 0; counter < 9; counter++)
        {
            preorder(node.getChild(counter));
        }
    }

    /**
     * eliminates all holes in the cursors children
     */
    public void eliminateHolesBelowCursor()
    {
        if (cursor.isNintendo())
        {
            return;
        }
        for (int counter = 0; counter < 9; counter++)
        {
            if (cursor.getChild(counter) == null)
            {
                for (int counter2 = counter; counter2 < 9; counter2++)
                {
                    if (cursor.getChild(counter2) != null)
                    {
                        cursor.setChild(counter, cursor.getChild(counter2));
                        cursor.setChild(counter2, null);
                        cursor.getChild(counter).setLevel(cursor.getLevel() + 1);
                        break;
                    }
                }
            }
        }
    }
}