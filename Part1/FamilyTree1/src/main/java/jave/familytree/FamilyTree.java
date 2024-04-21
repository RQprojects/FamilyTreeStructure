package jave.familytree;

public class FamilyTree {

    private FamilyTreeNode ancestor;

    public FamilyTree(String ancestorName, String partnerName) {
        this.ancestor = new FamilyTreeNode(ancestorName, new FamilyTreeNode(partnerName, null));
    }

    public void addChild(String childName) throws IllegalArgumentException {
        addChildToNode(ancestor, childName);
    }

    private void addChildToNode(FamilyTreeNode parentNode, String childName) {
        // Check if the parent already has a child with the same name
        if (hasChildWithName(parentNode, childName)) {
            throw new IllegalArgumentException("ERROR: A child with the same name already exists.");
        }

        // Create a new child node
        FamilyTreeNode newChild = new FamilyTreeNode(childName, null);

        // Check if the parent has any children
        if (parentNode.firstChild == null) {
            // If no children, make this child the first child
            parentNode.firstChild = newChild;
        } else {
            // If there are children, find the last child and add the new child as its sibling
            FamilyTreeNode sibling = parentNode.firstChild;
            while (sibling.nextSibling != null) {
                sibling = sibling.nextSibling;
            }
            sibling.nextSibling = newChild;
        }
    }

    // Helper method to check if a parent node already has a child with the given name
    private boolean hasChildWithName(FamilyTreeNode parentNode, String childName) {
        FamilyTreeNode child = parentNode.firstChild;
        while (child != null) {
            if (child.name.equalsIgnoreCase(childName)) {
                return true;
            }
            child = child.nextSibling;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(ancestor.name).append(" partner ").append(ancestor.partner.name).append("\n");

        // Check if both "James partner Mary" and "Mary partner James" have no children
        if (ancestor.firstChild == null && ancestor.partner.firstChild == null) {
            builder.append("- No children\n");
        } else {
            displayChildren(ancestor.firstChild, builder); // Display children under the ancestor node
            displayChildren(ancestor.partner.firstChild, builder); // Display children under the partner node
        }

        builder.append(ancestor.partner.name).append(" partner ").append(ancestor.name).append("\n");

        // Display children under the ancestor node again
        displayChildren(ancestor.firstChild, builder); 

        return builder.toString();
    }

    private void displayChildren(FamilyTreeNode node, StringBuilder builder) {
        while (node != null) {
            builder.append(node.name);
            if (node.partner != null) {
                builder.append(" partner ").append(node.partner.name);
            }
            builder.append("\n"); // Display the child's name
            displayChildren(node.firstChild, builder); // Display children under the child node
            node = node.nextSibling; // Move to the next child
        }
    }
}
