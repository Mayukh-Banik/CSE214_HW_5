// Mayukh Banik CSE 214 mayukh.banik@stonybrook.edu R03 Homework 5
public class NetworkNode
{
    private String name;
    private boolean isNintendo;
    private NetworkNode parent;
    private NetworkNode[] children;
    final int maxChildren = 9;
    private int level;

    /**
     * returns the children array
     * @return children array
     */
    public NetworkNode[] getChildren()
    {
        return children;
    }

    /**
     * initializes the data
     */
    public NetworkNode()
    {
        name = null;
        isNintendo = false;
        boolean isBroken = false;
        children = new NetworkNode[9];
        level = 0;
    }

    /**
     * sets up the level of the tree
     * @param level of the node
     */
    public void setLevel(int level)
    {
        this.level = level;
    }

    /**
     * returns the node level
     * @return int of the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * toString
     * @return string of the thing
     */
    public String toString()
    {
        return (isNintendo ? "-" : "+") + name;
    }

    /**
     * returns the parent of the node
     * @return the Network node of the parent
     */
    public NetworkNode getParent() {
        return parent;
    }

    /**
     * gets the children of that node
     * @return the children of the node in array form
     */
    public NetworkNode getChildren(int index)
    {
        return children[index];
    }

    /**
     * sets the children of the node
     * @param children children array of the node
     */
    public void setChildren(NetworkNode[] children)
    {
        this.children = children;
    }

    /**
     * sets a specific child of the node
     * @param index of the child to be set to
     * @param child to be set
     */
    public void setChild(int index, NetworkNode child)
    {
        if (children == null)
        {
            children = new NetworkNode[9];
        }
        if (children[index] == null)
        {
            children[index] = new NetworkNode();
        }
        children[index] = child;
    }

    /**
     * returns the specific child
     * @param index of the child to be sought after
     * @return child
     */
    public NetworkNode getChild(int index)
    {
        return children[index];
    }

    /**
     * sets the name
     * @param name to be set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * sets if the node is nintendo
     * @param nintendo node boolean
     */
    public void setNintendo(boolean nintendo)
    {
        this.isNintendo = nintendo;
    }

    /**
     * sets the parent
     * @param parent node
     */
    public void setParent(NetworkNode parent)
    {
        this.parent = parent;
    }

    /**
     * checks if its equal
     * @param o object to compare
     * @return boolean if they are equal
     */
    public boolean equals(Object o)
    {
        if (o instanceof NetworkNode)
        {
            NetworkNode that = (NetworkNode) o;
            return isNintendo == that.isNintendo && name.equalsIgnoreCase(that.name);
        }
        return false;
    }

    /**
     * returns the nintendo value
     * @return nintendo value
     */
    public boolean isNintendo()
    {
        return isNintendo;
    }

    /**
     * returns the number of the child with respect to its parent
     * @return child number
     */
    public int childNumber()
    {
        for (int counter = 0; counter < 9; counter++)
        {
            if (this.parent.getChild(counter) == this)
            {
                return counter;
            }
        }
        return 0;
    }
}
